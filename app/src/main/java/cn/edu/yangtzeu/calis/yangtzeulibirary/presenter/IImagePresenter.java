package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ImageModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IImageView;

public class IImagePresenter {
    //数据源
    private IImageView iImageView;
    //处理业务逻辑
    private ImageModel imageModel;

    public IImagePresenter(IImageView iImageView) {
        this.iImageView = iImageView;
        imageModel = new ImageModel();
    }

    public void applyToolbar(AppCompatActivity activity, Toolbar toolbar)  {
        imageModel.applyToolbar(activity, toolbar,iImageView.getFromUrl());
    }

    public void fitImageView(AppCompatActivity activity) {
        imageModel.fitImageView( activity,iImageView.getTouchImageView(),iImageView.getWebViewProgressBar(),iImageView.getFromUrl());
    }
}











