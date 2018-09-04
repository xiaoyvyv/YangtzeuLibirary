package cn.edu.yangtzeu.calis.yangtzeulibirary.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.cookie.CookiesManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class OkHttp {
    @SuppressLint("StaticFieldLeak")
    private static OkHttp okHttp;
    private static OkHttpClient okHttpClient;
    private static Handler UIHandler;
    private Context context;
    private static String nullString = "";

    private OkHttp(Context context) {
        this.context = context;
    }

    public static void initOkHttp(Context context) {
        if (okHttp == null) {
            okHttp = new OkHttp(context);
        }
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .proxy(Proxy.NO_PROXY)
                    .cookieJar(new CookiesManager(okHttp.getContent()))
                    .build();
        }
        if (UIHandler == null) {
            UIHandler = new Handler();
        }
    }

    public static void do_Get(String url, final OnResultListener onResultListener) {
        LogUtils.i("Get--加载链接：" + url);
        if (!URLUtil.isNetworkUrl(url)) {
            onResultListener.onFailure("错误的网址：" + url);
            onResultListener.onFailure(OkhttpError.ERROR_INTERNET);
        }

        final Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                final String error = e.toString();
                UIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (error.contains(OkhttpError.SCHOOL_NETWORK_STREAM)) {
                            ToastUtils.showShort(OkhttpError.ERROR_SCHOOL_NETWORK_URL);
                            onResultListener.onFailure(OkhttpError.ERROR_SCHOOL_NETWORK_URL);
                        } else if (!NetworkUtils.isConnected()) {
                            ToastUtils.showShort(OkhttpError.ERROR_INTERNET);
                            onResultListener.onFailure(OkhttpError.ERROR_INTERNET);
                        } else {
                            onResultListener.onFailure(error);
                        }
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                ResponseBody result = response.body();
                final String string = result != null ? result.string() : nullString;
                UIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (string.contains(OkhttpError.SCHOOL_NETWORK_HOST)) {
                            onResultListener.onFailure(OkhttpError.ERROR_SCHOOL_NETWORK_URL);
                        } else {
                            onResultListener.onResponse(string);
                        }
                    }
                });
            }
        });
    }

    public static void do_Post(Request request, final OnResultListener onResultListener) {
        LogUtils.i("Post--加载链接：" + request.url());

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                final String error = e.toString();
                UIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (error.contains(OkhttpError.SCHOOL_NETWORK_STREAM)) {
                            ToastUtils.showShort(OkhttpError.ERROR_SCHOOL_NETWORK_URL);
                            onResultListener.onFailure(OkhttpError.ERROR_SCHOOL_NETWORK_URL);
                        } else if (!NetworkUtils.isConnected()) {
                            ToastUtils.showShort(OkhttpError.ERROR_INTERNET);
                            onResultListener.onFailure(OkhttpError.ERROR_INTERNET);
                        } else {
                            onResultListener.onFailure(error);
                        }
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                ResponseBody result = response.body();
                final String string = result != null ? result.string() : nullString;
                UIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (string.contains(OkhttpError.SCHOOL_NETWORK_HOST)) {
                            onResultListener.onFailure(OkhttpError.ERROR_SCHOOL_NETWORK_URL);
                        } else {
                            onResultListener.onResponse(string);
                        }
                    }
                });
            }
        });
    }

    public Context getContent() {
        return context;
    }
}
