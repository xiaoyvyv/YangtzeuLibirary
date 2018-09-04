package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.BookListModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.CollectionModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.CollectionAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookListView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ICollectionView;

public class ICollectionPresenter {
    private ICollectionView view;
    private CollectionModel model;

    public ICollectionPresenter(ICollectionView bookListView) {
        this.view = bookListView;
        model = new CollectionModel();
    }

    public void applyToolbar(final AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }


    public void getData(AppCompatActivity activity, CollectionAdapter adapter, int index) {
        model.getData(activity, view, adapter,view.getFromUrl(),index);
    }
}
