package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.BookRenewModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.PayListModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookRenewAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.PayListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookRenewView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IPayListView;

public class IPayListPresenter {
    private IPayListView view;
    private PayListModel model;

    public IPayListPresenter(IPayListView view) {
        this.view = view;
        model = new PayListModel();
    }

    public void applyToolbar(final AppCompatActivity activity) {
        model.applyToolbar(activity, view.getToolBar());
    }


    public void getData(AppCompatActivity activity, PayListAdapter adapter, int index) {
        model.getData(activity, view, adapter,index);
    }
}
