package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.database.DatabaseUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.SearchHistoryBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.ISearchHistoryModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.SearchHistoryAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ISearchHistoryView;

public class SearchHistoryModel implements ISearchHistoryModel {

    @Override
    public void applyToolbar(final AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    public void getHistory(final AppCompatActivity activity, SearchHistoryAdapter searchHistoryAdapter, ISearchHistoryView iSearchHistoryView) {
        List<SearchHistoryBean> list = DatabaseUtils.getHelper(activity, "searchHistory.db").queryAll(SearchHistoryBean.class);
        iSearchHistoryView.getSmartRefreshLayout().finishRefresh();

        if (list == null || list.size() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .setTitle(R.string.trip)
                    .setMessage("你还没有书目浏览记录")
                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    })
                    .create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            return;
        }

        Collections.reverse(list);

        searchHistoryAdapter.setData(list);
        searchHistoryAdapter.notifyItemRangeChanged(0, searchHistoryAdapter.getItemCount());
    }
}
