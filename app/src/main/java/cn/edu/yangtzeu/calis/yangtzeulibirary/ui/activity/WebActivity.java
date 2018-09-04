package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebView;
import net.custom.WebViewProgressBar;
import net.custom.X5Web.X5JavaScriptFunction;
import net.custom.X5Web.X5LoadFinishListener;
import net.custom.X5Web.X5WebView;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.cookie.PersistentCookieStore;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

import static net.custom.X5Web.X5WebView.uploadFile;
import static net.custom.X5Web.X5WebView.uploadFiles;

public class WebActivity extends BaseActivity {
    private final String TAG = "【WebActivity】";
    private X5WebView mWebView;
    private Toolbar toolbar;
    private WebViewProgressBar progress;

    private String from_url;
    private String base_url;
    private String cookie;

    private boolean isShowCode=false;
    private boolean isSeeImg=false;
    private boolean isLoadingFinish=false;
    private boolean isKJS = false;
    private FrameLayout web_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception ignored) {}
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_web);


        from_url = getIntent().getStringExtra("from_url");
        base_url = getIntent().getStringExtra("base_url");
        cookie = getIntent().getStringExtra("cookie");
        isKJS = getIntent().getBooleanExtra("isKJS", false);

        super.onCreate(savedInstanceState);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("test", "----------横屏------------");
            toolbar.setVisibility(View.GONE);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("test", "----------竖屏------------");
            toolbar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mWebView.getSettings().setJavaScriptEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.getSettings().setJavaScriptEnabled(true);
    }



    @Override
    public void initViews() {
        mWebView = new X5WebView(this);
        toolbar =  findViewById(R.id.web_toolbar);
        progress = findViewById(R.id.web_progress);
        web_container = findViewById(R.id.web_container);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initMethod() {
        web_container.addView(mWebView,0, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        progress.setProgressColor(Color.GREEN);
        mWebView.setTitleAndProgressBar(toolbar,progress);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        //设置cookie
        if (cookie == null) {
            cookie = SPUtils.getInstance("UserInfo").getString("Cookie");
        }

        String cookie_list[] = cookie.split(";");
        MyUtils.clearX5Cookie(WebActivity.this);
        for (String mCookie : cookie_list) {
            if (URLUtil.isNetworkUrl(from_url)) {
                MyUtils.syncX5Cookie( MyUtils.getHost(from_url), mCookie);
                LogUtils.i("X5WebView同步Cookie", mCookie);
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();
            }
        });

        mWebView.addJavascriptInterface(new X5JavaScriptFunction() {
            @Override
            public void onJsFunctionCalled(String tag) {

            }
            @JavascriptInterface
            public void openWebImage(final String ImgUrl) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Web图片地址", ImgUrl);
                        Intent intent= new Intent(WebActivity.this,ImageActivity.class);
                        intent.putExtra("from_url", ImgUrl);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    }
                });
            }
        }, "android");



        if (base_url != null) {
            LogUtils.i("加载方式：loadDataWithBaseURL()");
            mWebView.loadDataWithBaseURL(base_url, from_url, "text/html", "utf-8", null);
        } else {
            mWebView.loadUrl(from_url);
            LogUtils.i("加载方式：loadUrl()");
        }

        mWebView.setX5LoadFinishListener(new X5LoadFinishListener() {
            @Override
            public void onLoadFinish(WebView webView, WebViewProgressBar progressBar, String s) {
                isLoadingFinish = true;

                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(s);
                LogUtils.i("X5网页Cookie：Cookies ========== " + CookieStr);

                if (isSeeImg) {
                    mWebView.showImage();
                    isSeeImg = false;
                }
                if (isShowCode) {
                    mWebView.showCode();
                    isShowCode = false;
                }
                if (isKJS) {
                    mWebView.showFreeRoom();
                    isKJS = false;
                }

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.save:
                if (mWebView.getUrl().contains("about:blank"))
                    ToastUtils.showShort(R.string.cant_save);
                else
                if (isLoadingFinish) {
                    String Year = UtilsTool.getShareString("DataInfo", "DataYear");
                    String Month = UtilsTool.getShareString("DataInfo", "DataMonth");
                    String Day = UtilsTool.getShareString("DataInfo", "DataDay");
                    String Data = Year + "-" + Month + "-" + Day;

                    String Json = "{\"CollectionName\":\"" + mWebView.getTitle().trim()
                            + "\",\"CollectionUrl\":\"" + mWebView.getUrl()
                            + "\",\"CollectionImgUrl\":\"" + Urls.Url_Web_Png
                            + "\"," + "\"CollectionDate\":\"" + Data
                            + "\"," + "\"CollectionWhich\":\"" + "WebActivity" + "\"}";
                    LogUtils.i("收藏Json", Json);

                    UtilsTool.putShareString("Collection", mWebView.getTitle().trim(), Json);

                    Snackbar.make(toolbar, "收藏成功", Snackbar.LENGTH_LONG)
                            .setAction("我的收藏", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //startActivity(new Intent(WebActivity.this, CollectionActivity.class));
                                        }
                                    }, 250);
                                }
                            }).show();
                } else {
                    Snackbar.make(toolbar, "加载未完成，请稍等...", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            case R.id.baseweb_refresh:
                if (mWebView.getUrl().contains("about:blank"))
                    ToastUtils.showShort(R.string.cant_refresh);
                else
                mWebView.reload();
                return true;
            case R.id.share:
                UtilsTool.ShareText(mWebView.getTitle()+"\n"+mWebView.getUrl()+"\n\n数据来自："+Urls.Url_AppDownUrl);
                break;
            case R.id.baseweb_copy:
                if (mWebView.getUrl().contains("asset")||mWebView.getUrl().contains("xyll520")||mWebView.getUrl().contains("about:blank")) {
                    ToastUtils.showShort( R.string.cant_copy);
                } else {
                    UtilsTool.PutStringToClipboard(mWebView,mWebView.getUrl());
                }
                return true;
            case R.id.baseweb_open:
                if (mWebView.getUrl().contains("asset")||mWebView.getUrl().contains("xyll520")||mWebView.getUrl().contains("about:blank")) {
                    ToastUtils.showShort( R.string.cant_open);
                } else {
                    UtilsTool.openBrowser(mWebView.getUrl());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                Exit();
            }
    }


    private boolean isExit = false;
    @SuppressLint("HandlerLeak")
    private void Exit() {
        if (!isExit) {
            isExit = true;
            ToastUtils.showShort(R.string.double_close_web);
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    isExit = false;
                }
            }.sendEmptyMessageDelayed(0, 2000); // 利用handler延迟发送更改状态信息
        } else {
            super.onBackPressed();
            WebActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == X5WebView.CHOOSE_FILE) {
                if (null != uploadFile) {
                    Uri result = data == null || resultCode != RESULT_OK ? null
                            : data.getData();
                    uploadFile.onReceiveValue(result);
                    uploadFile = null;
                }
                if (null != uploadFiles) {
                    Uri result = data == null || resultCode != RESULT_OK ? null
                            : data.getData();
                    uploadFiles.onReceiveValue(new Uri[]{result});
                    uploadFiles = null;
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
    }
}
