package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.IndexModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.NewsModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IImageView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IIndexView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.INewsView;

public class IIndexPresenter {
    //数据源
    private IIndexView view;
    //处理业务逻辑
    private IndexModel model;

    public IIndexPresenter(IIndexView view) {
        this.view = view;
        model = new IndexModel();
    }

    public void applyToolbar(AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }
    public void getAllKind(final AppCompatActivity activity,int id) {
        model.getAllKind(activity, view,id);
    }
}

