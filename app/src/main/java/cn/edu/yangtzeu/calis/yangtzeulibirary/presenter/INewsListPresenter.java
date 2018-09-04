package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.NewsListModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.NewsListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.INewsListView;

public class INewsListPresenter {
    //数据源
    private INewsListView view;
    //处理业务逻辑
    private NewsListModel model;

    public INewsListPresenter(INewsListView newsView) {
        this.view = newsView;
        model = new NewsListModel();
    }


    public void applyToolbar(AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }

    public void getData(AppCompatActivity activity, NewsListAdapter newsListAdapter, int index) {
        model.getData(activity, view, newsListAdapter,index);
    }

}
