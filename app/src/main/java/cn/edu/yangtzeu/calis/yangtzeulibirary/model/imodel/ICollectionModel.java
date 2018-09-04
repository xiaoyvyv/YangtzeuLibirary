package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.CollectionBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.AskBookAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.CollectionAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookAskView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ICollectionView;

public interface ICollectionModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);


    void getData(AppCompatActivity activity, ICollectionView iCollectionView, CollectionAdapter collectionAdapter, String url, int page);

    void setAdapter(AppCompatActivity activity, RecyclerView recyclerView, CollectionAdapter collectionAdapter, List<CollectionBean> collectionBeans, int old_index);

}
