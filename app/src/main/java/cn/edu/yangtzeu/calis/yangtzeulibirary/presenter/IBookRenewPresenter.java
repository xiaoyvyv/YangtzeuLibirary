package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.BookAskModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.BookRenewModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.AskBookAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookRenewAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookAskView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookRenewView;

public class IBookRenewPresenter {
    private IBookRenewView view;
    private BookRenewModel model;

    public IBookRenewPresenter(IBookRenewView view) {
        this.view = view;
        model = new BookRenewModel();
    }

    public void applyToolbar(final AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }


    public void getData(AppCompatActivity activity, BookRenewAdapter adapter, int index) {
        model.getData(activity, view, adapter,index);
    }
}
