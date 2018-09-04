package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookRenewBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.PayListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookRenewAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.PayListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookRenewView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IPayListView;

public interface IPayListModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);

    void getData(AppCompatActivity activity, IPayListView view, PayListAdapter adapter, int page);

    void setAdapter(AppCompatActivity activity, RecyclerView recyclerView, PayListAdapter adapter, List<PayListBean> listBeans, int old_index);
}
