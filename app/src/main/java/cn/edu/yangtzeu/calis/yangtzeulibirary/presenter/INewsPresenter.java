package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.NewsModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.INewsView;

public class INewsPresenter {
    //数据源
    private INewsView view;
    //处理业务逻辑
    private NewsModel model;

    public INewsPresenter(INewsView view) {
        this.view = view;
        model = new NewsModel();
    }

    public void applyToolbar(AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }

    public void getData(final AppCompatActivity activity) {
        model.getData(activity, view.getMessageView(), view.getWebViewProgressBar(), view.getToolBar(), view.getFromUrl());
    }

}

