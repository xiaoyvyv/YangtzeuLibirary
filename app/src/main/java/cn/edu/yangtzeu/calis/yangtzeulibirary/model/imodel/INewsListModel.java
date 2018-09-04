package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.HomeNewsBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.NewsListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.NewsListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.INewsListView;

public interface INewsListModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);

    void getData(AppCompatActivity activity, INewsListView recyclerView,
                 NewsListAdapter newsListAdapter, int page);


    void setAdapter(NewsListAdapter newsListAdapter, NewsListBean.DataBean dataBean, int old_index);
}
