package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public interface ISearchHistoryView {
    RecyclerView getRecyclerView();
    Toolbar getToolBar();
    SmartRefreshLayout getSmartRefreshLayout();
}
