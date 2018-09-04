package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.custom.TouchImageView;
import net.custom.WebViewProgressBar;

public interface IImageModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar , String from_url);

    void fitImageView(Context context, TouchImageView imageView, WebViewProgressBar progressBar, String from_url);

    void showDialog(Context context,String url);
}
