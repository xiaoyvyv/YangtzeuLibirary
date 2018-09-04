package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.Fragment2Adapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMineFragmentView;

public interface IMineFragmentModel {
    void getUserInfo(Activity activity, IMineFragmentView iMineFragmentView);

}
