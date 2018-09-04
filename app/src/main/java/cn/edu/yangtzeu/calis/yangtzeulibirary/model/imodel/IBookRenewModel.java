package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookAskBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookRenewBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.AskBookAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookRenewAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookAskView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookRenewView;

public interface IBookRenewModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);

    void getData(AppCompatActivity activity, IBookRenewView iBookRenewView, BookRenewAdapter adapter, int page);

    void setAdapter(AppCompatActivity activity, RecyclerView recyclerView, BookRenewAdapter adapter, List<BookRenewBean> bookRenewBeans, int old_index);
}
