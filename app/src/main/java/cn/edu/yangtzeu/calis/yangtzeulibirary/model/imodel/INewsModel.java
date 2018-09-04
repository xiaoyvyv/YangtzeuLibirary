package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import net.custom.WebViewProgressBar;
import net.custom.X5Web.X5WebView;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.NewsInfoBean;

public interface INewsModel {

    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);

    void showMessage(AppCompatActivity activity, X5WebView webView, WebViewProgressBar web_progress,
                     Toolbar toolbar, NewsInfoBean newsInfoBean);

    void getData(AppCompatActivity activity, X5WebView webView, WebViewProgressBar web_progress, Toolbar toolbar, String from_url);
}
