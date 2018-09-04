package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.Fragment2Adapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainFragment2View;

public interface IMainFragment2Model {
    void getBanner(Context context, IMainFragment2View bgaBanner);

    void getNotice(Context context, Fragment2Adapter adapter, IMainFragment2View iMainFragment2View, boolean isNext);
}
