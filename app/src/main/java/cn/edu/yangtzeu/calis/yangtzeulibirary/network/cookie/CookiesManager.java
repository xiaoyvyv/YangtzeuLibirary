package cn.edu.yangtzeu.calis.yangtzeulibirary.network.cookie;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;

import java.net.CookieManager;
import java.net.URL;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {
    private final PersistentCookieStore cookieStore;
    public CookiesManager(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }

        String doLogin = url.url().toString();
        if (doLogin.equals(Urls.Url_Login)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < cookies.size(); i++) {
                stringBuilder.append(cookies.get(i).toString());
                stringBuilder.append(";");
            }
            SPUtils.getInstance("UserInfo").put("Cookie", stringBuilder.toString());
        }
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookieStore.get(url);
    }
}
