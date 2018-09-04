package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.BookAskModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.CollectionModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.AskBookAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.CollectionAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookAskView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ICollectionView;

public class IBookAskPresenter {
    private IBookAskView view;
    private BookAskModel model;

    public IBookAskPresenter(IBookAskView bookListView) {
        this.view = bookListView;
        model = new BookAskModel();
    }

    public void applyToolbar(final AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }


    public void getData(AppCompatActivity activity, AskBookAdapter adapter, int index) {
        model.getData(activity, view, adapter,index);
    }
}
