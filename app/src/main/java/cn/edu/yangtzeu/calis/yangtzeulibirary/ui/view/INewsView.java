package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import net.custom.WebViewProgressBar;
import net.custom.X5Web.X5WebView;

public interface INewsView {
    Toolbar getToolBar();
    X5WebView getMessageView();
    String getFromUrl();
    WebViewProgressBar getWebViewProgressBar();
}
