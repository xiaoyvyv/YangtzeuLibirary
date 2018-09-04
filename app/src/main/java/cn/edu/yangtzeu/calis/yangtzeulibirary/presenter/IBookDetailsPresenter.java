package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.BookDetailsModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookDetailsActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookDetailsView;

public class IBookDetailsPresenter {
    private IBookDetailsView view;
    private BookDetailsModel model;


    public IBookDetailsPresenter(IBookDetailsView bookDetailsView) {
        this.view = bookDetailsView;
        model = new BookDetailsModel();
    }

    public void applyToolbar(final AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }

    public void getBookInfo(AppCompatActivity bookDetailsActivity) {
        model.getBookInfo(bookDetailsActivity, view);
    }

    public void getBookHtmlInfo(final AppCompatActivity bookDetailsActivity) {
        model.getBookHtmlInfo(bookDetailsActivity, view);
    }

    public void getBookWhereInfo(final AppCompatActivity bookDetailsActivity) {
        model.getBookWhereInfo(bookDetailsActivity, view);
    }
}
