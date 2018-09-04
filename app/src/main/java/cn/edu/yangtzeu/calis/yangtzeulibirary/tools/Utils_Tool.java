package cn.edu.yangtzeu.calis.yangtzeulibirary.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.ImageActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.WebActivity;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

/**
 * Created by 2016 on 2017/11/29.
 *
 */

public class Utils_Tool {
    public final static String NameList[] = {"doc", "docx", "ppt", "pptx", "xls", "xlsx", "pdf", "txt", "epub"};
    public final static String ImageList[] = {"png", "jpg", "gif", "webp", "bmp", "jpeg"};
    private Activity activity;
    private Context Context;

    public Utils_Tool(Context context) {
        Context = context;
        activity = (Activity) Context;
    }

    public Utils_Tool(Context context, boolean is_activity) {
        Context = context;
        if (is_activity)
            activity = (Activity) Context;
    }


    public String getFileSize(String path) {
        String size = FormatSize(String.valueOf(new File(path).length()));
        return size;
    }

    /**
     * 规则：必须同时包含大小写字母及数字
     *
     * @param str 被判断字符串
     * @return 是否同时包含大小写字母及数字
     */
    public static boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
    }

    /**
     * 获取手机网络状态是否连接
     *
     * @return 是否有网
     */
    public boolean getInternetState() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) Context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
        // 判断当前网络状态是否为连接状态
        for (int i = 0; i < networkInfo.length; i++) {
            if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得根目录
     *
     * @return 根目录
     */
    public String rootPath() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        //得到一个路径，内容是sdcard的文件夹路径和名字
        String path = sdcardDir.getPath();
        return path + "/";
    }

    /**
     * 文件大小换算
     *
     * @param target_size 字节大小
     * @return 文件大小
     */
    public String FormatSize(String target_size) {
        return Formatter.formatFileSize(Context, Long.valueOf(target_size));
    }

    /**
     * 在SD卡上创建一个文件夹"A_Tool"
     *
     * @param DirName 文件夹名称
     */
    public void createSDCardDir(String DirName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            //得到一个路径，内容是sdcard的文件夹路径和名字
            String path = sdcardDir.getPath() + "/" + DirName;
            File path1 = new File(path);
            if (!path1.exists()) {
                //若不存在，创建目录，可以在应用启动的时候创建
                path1.mkdirs();
                Log.e("文件夹" + DirName, "创建成功！");
            } else {
                Log.e("文件夹" + DirName, "已存在！");
            }
        } else {
            Log.e("文件夹" + DirName, "创建失败！");
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹和文件夹里面的文件
     *
     * @param Path 传入路径
     */
    public void DeleteDir(final String Path) {
        File dir = new File(Path);
        DeleteDirWihtFile(dir);
    }

    /**
     * 删除文件夹和文件夹里面的文件
     *
     * @param dir 传入文件
     */
    public void DeleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                DeleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 获取文件扩展名
     *
     * @param file 文件
     * @return 文件扩展名
     */
    public String getExtension(File file) {
        final String name = file.getName();
        final int idx = name.lastIndexOf(".");
        String suffix = "";
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    /**
     * 根据扩展名获取文件Mime类型
     *
     * @param file 文件
     * @return 文件Mime类型
     */
    public String getMimeType(File file) {
        String extension = getExtension(file);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    /**
     * 根据路径截取“/”后的字符串为文件名
     *
     * @param path 文件路径
     * @return 文件名
     */
    public String getFileName(String path) {
        // 截取的 b即为下载文件的名字
        String b = path.substring(path.lastIndexOf("/") + 1, path.length());
        return b;
    }

    /**
     * 传入路径，打开文件
     *
     * @param Path 文件路径
     */

    public void OpenFileByPath(String Path) {
        try {
            File file = new File(Path);
            String url_type = getMimeType(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判读版本是否在7.0以上
            if (Build.VERSION.SDK_INT >= 24) {
                //provider authorities
                Uri apkUri = FileProvider.getUriForFile(Context, "NewYangtzeuProvider", file);
                //Granting Temporary Permissions to a URI
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(apkUri, url_type);
            } else {
                intent.setDataAndType(Uri.fromFile(file), url_type);
            }
            Context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 传入路径，根据文件扩展名类型打开文件
     *
     * @param Path      传入路径
     * @param file_type 文件类型
     */
    public void OpenFileByType(String Path, String file_type) {
        try {
            File file = new File(Path);
            //根据文件类型获取Mime
            String url_type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file_type);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判读版本是否在7.0以上
            if (Build.VERSION.SDK_INT >= 24) {
                //provider authorities
                Uri apkUri = FileProvider.getUriForFile(Context, "NewYangtzeuProvider", file);
                //Granting Temporary Permissions to a URI
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(apkUri, url_type);
            } else {
                intent.setDataAndType(Uri.fromFile(file), url_type);
            }
            Context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取取随机数
     *
     * @param num 最大数限制
     * @return 随机数`
     */
    public String GetRand(int num) {
        Random ran = new Random(System.currentTimeMillis());
        int refresh_int = ran.nextInt(num);
        String st = String.valueOf(refresh_int);
        Log.e(num + "以内，获取的随机数为", st);
        return st;
    }

    /**
     * MD5加密字符串
     *
     * @param MdStr 要加密的内容
     * @return 加密后的内容
     */
    public String GetMD5(String MdStr) {
        if (TextUtils.isEmpty(MdStr)) {
            return "未输入字符串";
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(MdStr.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复制内容到剪切板
     */
    public void PutStringToClipboard(String text) {
        ClipboardManager mClipboardManager = (ClipboardManager) Context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**
     * 复制内容到剪切板，定制弹出提示
     *
     * @param ToClipboardText 复制内容
     * @param view            View布局
     */
    public void PutStringToClipboard(View view, String ToClipboardText) {
        ClipboardManager mClipboardManager = (ClipboardManager) Context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, ToClipboardText));
        Snackbar.make(view, "已经复制到剪切板", Snackbar.LENGTH_SHORT)
                .show();
    }

    /**
     * 复制内容到剪切板，定制弹出内容
     *
     * @param ToClipboardText 复制内容
     * @param view            View布局
     * @param ToastString     弹出内容
     */
    public void PutStringToClipboard(String ToClipboardText, View view, String ToastString) {
        ClipboardManager mClipboardManager = (ClipboardManager) Context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, ToClipboardText));
        Snackbar.make(view, ToastString, Snackbar.LENGTH_SHORT)
                .show();

    }

    /**
     * 判断是否是数字
     *
     * @param str 被判断的字符串
     * @return 是否是数字
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    /**
     * 保存imageview 图片到本地
     *
     * @param imageView 加载图片的容器
     */
    public void SaveImage(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);//开启catch，开启之后才能获取ImageView中的bitmap
        Bitmap bitmap = imageView.getDrawingCache();//获取imageview中的图像
        MediaStore.Images.Media.insertImage(Context.getContentResolver(), bitmap, "图片", "网页图片");
        Snackbar.make(imageView, "已经保存至系统相册", Snackbar.LENGTH_SHORT).show();
        imageView.setDrawingCacheEnabled(false);//关闭catch
    }

    /**
     * 储存字符串
     *
     * @param Name Name
     * @param Key  Key
     */
    public void putShareString(String Name, String Key, String Value) {
        Value = Value.replace("\n", "#");
        Value = Value.replace(" ", "#");

        SharedPreferences mSharedPreferences = Context.getSharedPreferences(Name, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(Key, Value);
        edit.apply();
    }

    /**
     * 储存布尔值
     *
     * @param Name Name
     * @param Key  Key
     */
    public void putShareBoolean(String Name, String Key, boolean Value) {
        SharedPreferences mSharedPreferences = Context.getSharedPreferences(Name, android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(Key, Value);
        edit.apply();
    }

    /**
     * 取出字符串
     *
     * @param Name Name
     * @param Key  Key
     * @return 字符串
     */
    public String getShareString(String Name, String Key) {
        SharedPreferences SharedPreferences = Context.getSharedPreferences(Name, android.content.Context.MODE_PRIVATE);
        return SharedPreferences.getString(Key, "");
    }

    /**
     * 取出字符串，带缺省值
     *
     * @param Name   Name
     * @param Key    Key
     * @param Defeat 缺省值
     * @return 字符串
     */
    public String getShareStringWithDefeat(String Name, String Key, String Defeat) {
        SharedPreferences SharedPreferences = Context.getSharedPreferences(Name, android.content.Context.MODE_PRIVATE);
        String text = SharedPreferences.getString(Key, Defeat);
        text = text.replace("#", "\n");
        return text;
    }

    /**
     * 取出布尔值，带缺省值
     *
     * @param Name   Name
     * @param Key    Key
     * @param Defeat 缺省值
     * @return 布尔值
     */
    public boolean getShareBooleanWithDefeat(String Name, String Key, boolean Defeat) {
        SharedPreferences SharedPreferences = Context.getSharedPreferences(Name, android.content.Context.MODE_PRIVATE);
        return SharedPreferences.getBoolean(Key, Defeat);
    }

    /**
     * 取出布尔值
     *
     * @param Name Name
     * @param Key  Key
     * @return 布尔值
     */
    public boolean getShareBoolean(String Name, String Key) {
        SharedPreferences SharedPreferences = Context.getSharedPreferences(Name, android.content.Context.MODE_PRIVATE);
        return SharedPreferences.getBoolean(Key, false);
    }

    /**
     * 在浏览器中打开链接
     *
     * @param targetUrl 要打开的网址
     */
    public void openBrowser(String targetUrl) {
        if (!TextUtils.isEmpty(targetUrl) && targetUrl.startsWith("file://")) {
            ToastUtils.showShort( R.string.cant_open);
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri url = Uri.parse(targetUrl);
        intent.setData(url);
        Context.startActivity(intent);
    }
    /**
     * 在App中开链接
     *
     * @param targetUrl 要打开的网址
     */
    public void openBrowserByApp(String targetUrl,String cookie) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("from_url", targetUrl);
        intent.putExtra("cookie", cookie);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    /**
     * 根据下载ID 获取下载信息
     *
     * @param downloadManager 下载管理器
     * @param mDownloadId     下载地址
     * @return 下载进度
     */
    public int GetDownLoadInfo(DownloadManager downloadManager, long mDownloadId) {
        int bytes_downloaded = 0;
        int bytes_total = 0;

        DownloadManager.Query query = new DownloadManager.Query();
        Cursor cursor = downloadManager.query(query.setFilterById(mDownloadId));

        if (cursor != null && cursor.moveToFirst()) {
            //下载的文件到本地的目录
            String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            //已经下载的字节数
            bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            //总需下载的字节数
            bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            //Notification 标题
            String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            //描述
            String description = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
            //下载对应id
            long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            //下载文件的URL链接
            String url = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
        }

        return bytes_downloaded / bytes_total;
    }

    /**
     * 获取网页设置
     *
     * @param webview 网页对象
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void GetWebSettings(WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//图片显示
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //https问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


        webSettings.setAppCacheEnabled(true);
        webSettings.setGeolocationEnabled(true);

        // 缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        // 其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     */
    @SuppressLint("ObsoleteSdkInt")
    public void syncCookie(String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.CookieSyncManager.createInstance(Context);
        }
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean value) {
                    LogUtils.i("Cookie清除：" + value);
                }
            });
        }
        cookieManager.setCookie(url, cookie);
    }

    /**
     * 将cookie同步到X5WebView
     *
     * @param url    WebView要加载的url
     * @param cookie 要同步的cookie
     */
    @SuppressLint("ObsoleteSdkInt")
    public void syncX5Cookie(String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(Context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setCookie(url, cookie);
    }

    /**
     * 分享文件
     *
     * @param what 分享内容
     */
    public void ShareText(String what) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, "分享了：\n" + what);
        share.setType("text/plain");
        Context.startActivity(Intent.createChooser(share, "分享到"));
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public String getAPPVersionName() {
        String appVersionName = "";
        float currentVersionCode;
        PackageManager manager = Context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(Context.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " " + appVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }

    /**
     * 获取时间信息
     *
     * @param Calendar_What 信息种类
     * @return 时间信息
     */
    public String GetDateInfo(int Calendar_What) {
        int mYear, mMonth, mDay, mWay, mHour, mMinute;
        Calendar c = Calendar.getInstance();
        switch (Calendar_What) {
            case Calendar.YEAR:
                mYear = c.get(Calendar.YEAR); // 获取当前年份
                return String.valueOf(mYear);
            case Calendar.MONTH:
                mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
                return String.valueOf(mMonth);
            case Calendar.DAY_OF_MONTH:
                mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
                return String.valueOf(mDay);
            case Calendar.DAY_OF_WEEK:
                mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前星期
                return String.valueOf(mWay);
            case Calendar.HOUR_OF_DAY:
                mHour = c.get(Calendar.HOUR_OF_DAY);//时
                return String.valueOf(mHour);
            case Calendar.MINUTE:
                mMinute = c.get(Calendar.MINUTE);//分
                return String.valueOf(mMinute);
            default:
                return (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        }
    }

    /**
     * 加载图片
     *
     * @param imageView 图片容器
     * @param o         图片内容（链接、文件等等）
     */
    public void LoadImage(ImageView imageView, Object o) {
        Glide.with(Context).load(o).into(imageView);
    }


    /**
     * 控制震动
     *
     * @param time 震动时间
     */
    public void mVibrator(int time) {
        Vibrator vibrator = (Vibrator) Context.getSystemService(android.content.Context.VIBRATOR_SERVICE);
        long[] pattern = {0, time}; //0ms—500ms
        vibrator.vibrate(pattern, -1);
    }

    /**
     * dp转换成px
     *
     * @param dpValue dp的值
     * @return px的值
     */
    public int dp2px(float dpValue) {
        float scale = Context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     *
     * @param pxValue px的值
     * @return dp的值
     */
    public int px2dp(float pxValue) {
        float scale = Context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     *
     * @param spValue sp的值
     * @return px的值
     */
    public int sp2px(float spValue) {
        float fontScale = Context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     *
     * @param pxValue px的值
     * @return sp的值
     */
    public int px2sp(float pxValue) {
        float fontScale = Context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 十进制转为16进制
     *
     * @param a 10进制数
     * @return 16进制数
     */
    public String formattingH(int a) {
        String i = Integer.toHexString(a);
        if (i.length() != 2) {
            i = "0" + i;
        }
        return i;
    }

    /**
     * 拨打电话
     *
     * @param phone 电话号码
     */
    public void Call(String phone) {
        Uri uri = Uri.parse("tel:" + phone); //设置要操作的路径
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);  //设置要操作的Action
        intent.setData(uri); //要设置的数据
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * QQ会话
     *
     * @param qq QQ号码
     */
    public void QQ_Chat(String qq) {
        String qq_str = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq + "&version=1";
        Log.e("QQ对话", qq_str);
        Uri uri = Uri.parse(qq_str); //设置要操作的路径
        Intent intent = new Intent();
        intent.setData(uri); //要设置的数据
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 拖动排序，第adapterPosition个调到第position个，然后其他的顺延
     *  @param list_Title   数组
     * @param fromPosition 从哪个位置调
     * @param toPosition   调到哪个位置
     */
    public void ListSwap(List<?> list_Title, int fromPosition, int toPosition) {
        if (fromPosition > toPosition) {
            for (int i = fromPosition; i >toPosition; i--) {
                Collections.swap(list_Title, i, i - 1);// 改变实际的数据集
            }
        } else {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list_Title, i + 1,i );// 改变实际的数据集
            }
        }
    }

    private static boolean isNotEmojiCharacter(char codePoint)
    {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source 被过滤文本
     * @return 过滤后文本
     */
    public  String filterEmoji(String source)
    {
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++)
        {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint))
            {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }

    /**
     * QB_SDK打开文件
     * @param path 文件路径
     */
    public void openFile(final String path) {
        boolean QB_canOpen = false;
        boolean Image_canOpen = false;
        String Name = getExtension(new File(path));
        for (int i = 0; i < NameList.length; i++) {
            String Item = NameList[i];
            if (Name.equals(Item)) {
                QB_canOpen = true;
            }
        }
        if (QB_canOpen) {
            QbSdk.openFileReader(Context, path, null,null);
        } else {
            for (int i = 0; i < ImageList.length; i++) {
                String Item = ImageList[i];
                if (Name.equals(Item)) {
                    Image_canOpen = true;
                }
            }
            if (Image_canOpen) {
                MyUtils.openImage(Context,path);
            } else {
                OpenFileByPath(path);
            }
        } 

    }

    /**
     * 跳转网页
     * @param url 网页链接
     */
    public void openUrl(String url) {
        activity.startActivity(new Intent(activity, WebActivity.class)
                .putExtra("from_url",url));
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 跳转网页
     * @param url 网页链接
     * @param cookie Cookie
     */
    public void openUrl(String url,String cookie) {
        activity.startActivity(new Intent(activity, WebActivity.class)
                .putExtra("from_url", url)
                .putExtra("cookie", cookie));
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    /**
     * 把bitmap压缩到指定的宽高
     */
    public  Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    /**
     * bitmap -> drawable
     * @param context
     * @param bm
     * @return
     */
    public static Drawable getDrawable(Context context, Bitmap bm){
        BitmapDrawable bd= new BitmapDrawable(context.getResources(),bm);
        return bd;
    }

    /**
     * 设置隐藏底栏
     * @param visible visible
     */
    public void setNavigationBar(int visible){
        View decorView = activity.getWindow().getDecorView();
        //显示NavigationBar
        if (View.GONE == visible){
            int option = SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
        }
    }
    /**
     *
     * @Title: base64ToBitmap
     * @param  string base64值
     * @return    返回类型
     */
    public Bitmap base64ToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * check whether has root permission
     *
     * @return
     */
    public static boolean checkRootPermission() {
        return ShellUtils.execCmd("echo root", true, false).result == 0;
    }
}
