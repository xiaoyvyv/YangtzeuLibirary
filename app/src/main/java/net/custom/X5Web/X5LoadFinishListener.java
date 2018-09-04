package net.custom.X5Web;

import com.tencent.smtt.sdk.WebView;

import net.custom.WebViewProgressBar;

/**
 * Created by Administrator on 2018/4/9.
 *
 * @author 王怀玉
 * @explain X5LoadFinishListener
 */

public interface X5LoadFinishListener {
    void onLoadFinish(WebView webView, WebViewProgressBar progressBar, String s);
}