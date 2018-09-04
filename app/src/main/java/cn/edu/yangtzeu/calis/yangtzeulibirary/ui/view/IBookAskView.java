package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public interface IBookAskView {
    Toolbar getToolBar();
    RecyclerView getRecyclerView();
    TextView BookListSizeView();

    SmartRefreshLayout getSmartRefreshLayout();
    ProgressDialog getProgressDialog();

    String getNumber();
}
