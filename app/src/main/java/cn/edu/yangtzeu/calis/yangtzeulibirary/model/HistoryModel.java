package cn.edu.yangtzeu.calis.yangtzeulibirary.model;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IHistoryModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.SetFragmentAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.HistoryFragment;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHistoryView;

public class HistoryModel implements IHistoryModel {
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
    public void setAdapter(AppCompatActivity activity, IHistoryView iHistoryView) {
        FragmentManager manger = activity.getSupportFragmentManager();

        HistoryFragment fragmentMid =  HistoryFragment.newInstance(-1);
        HistoryFragment fragmentIn =  HistoryFragment.newInstance(0);
        HistoryFragment fragmentOut = HistoryFragment.newInstance(1);

        SetFragmentAdapter setFragmentAdapter = new SetFragmentAdapter(manger);
        setFragmentAdapter.addFragment("借阅中", fragmentMid);
        setFragmentAdapter.addFragment("已借阅", fragmentIn);
        setFragmentAdapter.addFragment("已归还", fragmentOut);

        iHistoryView.getViewPager().setAdapter(setFragmentAdapter);
        iHistoryView.getViewPager().setOffscreenPageLimit(2);

        iHistoryView.getTabLayout().setupWithViewPager(iHistoryView.getViewPager());
    }

}
