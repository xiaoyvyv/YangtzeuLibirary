package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import cn.bingoogolapple.bgabanner.BGABanner;

public interface IMainFragment2View {
    RecyclerView getRecyclerView();
    BGABanner getBGABanner();
    SmartRefreshLayout getSmartRefreshLayout();

    ProgressDialog getProgressDialog();

    String getUrl();

    int getWhich();
}
