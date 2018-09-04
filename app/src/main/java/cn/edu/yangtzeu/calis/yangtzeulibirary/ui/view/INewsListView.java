package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scwang.smartrefresh.layout.api.RefreshLayout;


public interface INewsListView {
    Toolbar getToolBar();
    ProgressDialog getProgressDialog();

    RefreshLayout getSmartRefreshLayout();
}
