package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;

import net.custom.WebViewProgressBar;
import net.custom.X5Web.X5WebView;

import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.NewsInfoBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.INewsModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BaseActivity;

public class NewsModel implements INewsModel {
    @Override
    public void applyToolbar(final AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    public void getData(final AppCompatActivity activity, final X5WebView webView, final WebViewProgressBar web_progress,
                        final Toolbar toolbar,  final String from_url) {
        OkHttp.do_Get(from_url, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                NewsInfoBean newsInfoBean = gson.fromJson(response, NewsInfoBean.class);
                if (newsInfoBean.getStatus() != 200) {
                    getData(activity,webView,web_progress,toolbar,from_url);
                } else {
                    showMessage(activity,webView,web_progress,toolbar,newsInfoBean);
                }
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) || !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                getData(activity,webView,web_progress,toolbar,from_url);
            }
        });
    }

    @Override
    public void showMessage(AppCompatActivity activity, X5WebView webView, WebViewProgressBar web_progress,
                            Toolbar toolbar, NewsInfoBean newsInfoBean) {

        web_progress.setProgressColor(Color.GREEN);
        webView.setTitleAndProgressBar(toolbar,web_progress);


        NewsInfoBean.DataBean data = newsInfoBean.getData();
        String title = data.getTitle();
        String summary = data.getSummary();
        String source = data.getSource();
        String createDate = data.getCreateDate();
        String image_url = Urls.Url_Image_Host + data.getImage();
        String content = data.getContent();
        int viewCount = data.getViewCount();
        int praiseCount = data.getPraiseCount();

        // textView.setText(Html.fromHtml(content));

        String base_url = MyUtils.getNewsModel(title, source, summary, title, image_url, content);
        webView.loadDataWithBaseURL(Urls.Url_M_Host, base_url, "text/html", "utf-8", null);
        LogUtils.i(content);

    }
}
