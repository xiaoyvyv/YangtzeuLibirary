package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

public interface IHistoryView {
    Toolbar getToolBar();
    ViewPager getViewPager();
    TabLayout getTabLayout();
}
