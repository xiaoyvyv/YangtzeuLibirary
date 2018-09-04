package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

public interface ICollectionView {
    Toolbar getToolBar();
    String getFromUrl();
    RecyclerView getRecyclerView();
    TextView BookListSizeView();

    ProgressDialog getProgressDialog();

    RefreshLayout getSmartRefreshLayout();
}
