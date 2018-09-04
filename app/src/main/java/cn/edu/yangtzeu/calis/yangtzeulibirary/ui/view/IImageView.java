package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.widget.ImageView;

import net.custom.TouchImageView;
import net.custom.WebViewProgressBar;

public interface IImageView {
    TouchImageView getTouchImageView();
    WebViewProgressBar getWebViewProgressBar();
    ImageView getPointMenu();
    String getFromUrl();
}
