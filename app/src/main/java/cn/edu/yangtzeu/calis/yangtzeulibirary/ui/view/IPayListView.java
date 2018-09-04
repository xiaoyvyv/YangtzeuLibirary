package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public interface IPayListView {
    Toolbar getToolBar();
    RecyclerView getRecyclerView();
    SmartRefreshLayout getSmartRefreshLayout();
    ProgressDialog getProgressDialog();
}
