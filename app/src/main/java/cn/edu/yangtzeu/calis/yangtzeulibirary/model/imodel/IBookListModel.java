package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookListView;

public interface IBookListModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);

    void getData(AppCompatActivity activity, IBookListView iBookListView, BookListAdapter bookListAdapter, String url, int page);
    void setAdapter( BookListAdapter bookListAdapter, List<BookListBean> bookListBeans , int old_index);
}
