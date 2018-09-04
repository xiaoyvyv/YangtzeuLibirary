package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.custom.WebViewProgressBar;
import net.custom.X5Web.X5WebView;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.INewsPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.INewsView;

public class NewsActivity extends BaseActivity implements INewsView {
    private Toolbar toolbar;
    private X5WebView webView  ;
    private String from_url;
    private WebViewProgressBar web_progress;
    private FrameLayout web_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        from_url = getIntent().getStringExtra("from_url");
        setContentView(R.layout.activity_news);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        webView = new X5WebView(this);
        web_progress = findViewById(R.id.web_progress);
        web_container = findViewById(R.id.web_container);
    }

    @Override
    public void initMethod() {
        web_container.addView(webView,0, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        INewsPresenter iNewsPresenter = new INewsPresenter(this);
        iNewsPresenter.applyToolbar(this);
        iNewsPresenter.getData(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("收藏")
                .setIcon(R.mipmap.star)
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });

        return true;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    public X5WebView getMessageView() {
        return webView;
    }

    @Override
    public String getFromUrl() {
        return from_url;
    }

    @Override
    public WebViewProgressBar getWebViewProgressBar() {
        return web_progress;
    }
}
