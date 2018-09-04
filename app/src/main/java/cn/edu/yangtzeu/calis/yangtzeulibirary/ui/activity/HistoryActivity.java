package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IHistoryPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHistoryView;

public class HistoryActivity extends BaseActivity implements IHistoryView {
    private Toolbar toolbar;
    public int index=1;
    private ViewPager viewpager;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_history);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        mTabLayout = findViewById(R.id.mTabLayout);
        viewpager= findViewById(R.id.viewpager);
    }

    @Override
    public void initMethod() {
        IHistoryPresenter iHistoryPresenter = new IHistoryPresenter(this);
        iHistoryPresenter.applyToolbar(this);
        iHistoryPresenter.setAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }


    @Override
    public ViewPager getViewPager() {
        return viewpager;
    }

    @Override
    public TabLayout getTabLayout() {
        return mTabLayout;
    }
}
