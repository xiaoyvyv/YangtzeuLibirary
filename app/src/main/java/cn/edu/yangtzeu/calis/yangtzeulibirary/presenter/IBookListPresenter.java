package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.BookListModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookListView;

public class IBookListPresenter {
    private IBookListView view;
    private BookListModel model;


    public IBookListPresenter(IBookListView bookListView) {
        this.view = bookListView;
        model = new BookListModel();
    }

    public void applyToolbar(final AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }
    public void getData(AppCompatActivity activity, BookListAdapter bookListAdapter, int index) {
        model.getData(activity, view, bookListAdapter,view.getFromUrl(),index);
    }
}
