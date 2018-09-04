package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.SearchHistoryModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.SearchHistoryAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ISearchHistoryView;

public class ISearchHistoryPresenter {
    //数据源
    private ISearchHistoryView view;
    //处理业务逻辑
    private SearchHistoryModel model;

    public ISearchHistoryPresenter(ISearchHistoryView view) {
        this.view = view;
        model = new SearchHistoryModel();
    }

    public void applyToolbar(AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }

    public void getHistory(AppCompatActivity activity, SearchHistoryAdapter adapter) {
        model.getHistory(activity,adapter, view);
    }
}

