package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IImageView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IIndexView;

public interface IIndexModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolBar);

    void getAllKind(AppCompatActivity activity, IIndexView iIndexView, int id);
}
