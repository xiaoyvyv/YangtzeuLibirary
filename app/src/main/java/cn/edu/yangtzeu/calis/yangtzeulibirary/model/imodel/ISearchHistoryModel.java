package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.SearchHistoryAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ISearchHistoryView;

public interface ISearchHistoryModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolBar);

    void getHistory(AppCompatActivity activity, SearchHistoryAdapter adapter, ISearchHistoryView iSearchHistoryView);
}
