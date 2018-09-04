package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookDetailsActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookDetailsView;

public interface IBookDetailsModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);

    void getBookInfo(AppCompatActivity bookDetailsActivity, IBookDetailsView view);

    void getBookHtmlInfo(AppCompatActivity bookDetailsActivity, IBookDetailsView view);

    void getBookWhereInfo(AppCompatActivity bookDetailsActivity, IBookDetailsView view);
}
