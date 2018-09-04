package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;

import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.NewsListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.INewsListModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.NewsListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.INewsListView;

public class NewsListModel implements INewsListModel {
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
    public void getData(final AppCompatActivity activity, final INewsListView iNewsListView, final NewsListAdapter newsListAdapter, final int page) {
        OkHttp.do_Get(Urls.Url_News_List + page, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                iNewsListView.getSmartRefreshLayout().finishLoadMore();
                iNewsListView.getProgressDialog().dismiss();

                int old_index = newsListAdapter.getItemCount();
                Gson gson = new Gson();
                NewsListBean newsListBean = gson.fromJson(response, NewsListBean.class);
                if (newsListBean.getStatus() != 200) {
                    getData(activity,iNewsListView,newsListAdapter, page);
                } else {
                    setAdapter( newsListAdapter, newsListBean.getData(), old_index);
                }
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET)&& !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                getData(activity,iNewsListView,newsListAdapter,page);
            }
        });

    }

    @Override
    public void setAdapter(NewsListAdapter newsListAdapter, NewsListBean.DataBean dataBean, int old_index) {
        newsListAdapter.setData(dataBean.getResultList());
        newsListAdapter.notifyItemRangeChanged(old_index,newsListAdapter.getItemCount());
    }

}
