package cn.edu.yangtzeu.calis.yangtzeulibirary.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.CookieManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.ImgBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.UpDateBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BaseActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.ImageActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.WebActivity;

public class MyUtils {

    public static ProgressDialog getProgressDialog(Context context,String s) {
    ProgressDialog progressDialog;
    progressDialog = new ProgressDialog(context);
    progressDialog.setTitle(context.getString(R.string.trip));
    progressDialog.setMessage(s);
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.setCancelable(false);
    return progressDialog;
}

    public static void dialogNotice(final Context context) {
        OkHttp.do_Get(Urls.Url_DialogNotice, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UpDateBean upDateBean = gson.fromJson(response, UpDateBean.class);

                String oldVersion = SPUtils.getInstance("AppInfo").getString("NoticeVersion", "0");
                final String newAppVersion = upDateBean.getNewAppVersion();
                if (oldVersion.equals(newAppVersion)) {
                    return;
                }

                String title = upDateBean.getTitle();
                String message = upDateBean.getMessage();
                String rightText = upDateBean.getRightText();
                String centerText = upDateBean.getCenterText();
                String leftText = upDateBean.getLeftText();
                final String canClose = upDateBean.getCanClose();
                final String rightUrl = upDateBean.getRightUrl();
                final String centerUrl = upDateBean.getCenterUrl();
                final String leftUrl = upDateBean.getLeftUrl();

                @SuppressLint("InflateParams")
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(rightText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SPUtils.getInstance("AppInfo").put("NoticeVersion", newAppVersion);
                                if (!rightUrl.isEmpty()) {
                                    MyUtils.openUrl(context, rightUrl);
                                }
                                if (!canClose.equals("否")) {
                                    canCloseDialog(dialog, true);
                                } else {
                                    canCloseDialog(dialog, false);
                                }
                            }
                        })
                        .setNegativeButton(leftText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SPUtils.getInstance("AppInfo").put("NoticeVersion", newAppVersion);
                                MyUtils.openUrl(context, leftUrl);
                                if (!canClose.equals("否")) {
                                    canCloseDialog(dialog, true);
                                } else {
                                    canCloseDialog(dialog, false);
                                }
                            }
                        })
                        .setNeutralButton(centerText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SPUtils.getInstance("AppInfo").put("NoticeVersion", newAppVersion);
                                MyUtils.openUrl(context, centerUrl);
                                if (!canClose.equals("否")) {
                                    canCloseDialog(dialog, true);
                                } else {
                                    canCloseDialog(dialog, false);
                                }
                            }
                        })
                        .create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                if (canClose.equals("否")) {
                    alertDialog.setCancelable(false);
                }
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    checkUpDate(context);
            }
        });

    }

    public static void checkUpDate(final Context context) {
        OkHttp.do_Get(Urls.Url_UpDate, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UpDateBean upDateBean = gson.fromJson(response, UpDateBean.class);

                String oldVersion = AppUtils.getAppVersionName();
                String newAppVersion = upDateBean.getNewAppVersion();
                if (oldVersion.equals(newAppVersion)) {
                    return;
                }

                String title = upDateBean.getTitle();
                String message = upDateBean.getMessage();
                String rightText = upDateBean.getRightText();
                String centerText = upDateBean.getCenterText();
                String leftText = upDateBean.getLeftText();
                final String canClose = upDateBean.getCanClose();
                final String rightUrl = upDateBean.getRightUrl();
                final String centerUrl = upDateBean.getCenterUrl();
                final String leftUrl = upDateBean.getLeftUrl();

                @SuppressLint("InflateParams")
                View view = LayoutInflater.from(context).inflate(R.layout.view_upadte_dialog, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.update_dialog)
                        .setView(view).create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                if (canClose.equals("否")) {
                    alertDialog.setCancelable(false);
                }

                TextView titleTv = view.findViewById(R.id.title);
                TextView versionTv = view.findViewById(R.id.version);
                TextView messageTv = view.findViewById(R.id.message);
                TextView leftBtn = view.findViewById(R.id.leftBtn);
                TextView centerBtn = view.findViewById(R.id.centerBtn);
                TextView rightBtn = view.findViewById(R.id.rightBtn);

                versionTv.setText(newAppVersion);
                titleTv.setText(title);
                messageTv.setText(message);
                leftBtn.setText(leftText);
                centerBtn.setText(centerText);
                rightBtn.setText(rightText);

                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtils.openUrl(context, leftUrl);
                        if (!canClose.equals("否")) {
                            alertDialog.dismiss();
                        }
                    }
                });
                centerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtils.openUrlByOther(context, centerUrl);
                        if (!canClose.equals("否")) {
                            alertDialog.dismiss();
                        }

                    }
                });
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyUtils.openUrl(context, rightUrl);
                        if (!canClose.equals("否")) {
                            alertDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    checkUpDate(context);
            }
        });

    }


    /**
     * MD5加密
     */
    public static String swapCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        char[] buffer = str.toCharArray();

        for (int i = 0; i < buffer.length; i++) {
            char ch = buffer[i];
            if (Character.isUpperCase(ch)) {
                buffer[i] = Character.toLowerCase(ch);
            } else if (Character.isTitleCase(ch)) {
                buffer[i] = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                buffer[i] = Character.toUpperCase(ch);
            }
        }
        return new String(buffer);
    }


    /**
     * 保存文件
     * @param  context context
     * @param  url url
     * @param fileName fileName
     * @param SavePath SavePath
     */
    public static void saveFile(final Context context, String url, final String fileName,String SavePath) {
        if (SavePath == null) {
            SavePath = "A_Tool/Download/";
        }
        final String finalSavePath = SavePath;
        final Activity activity = (Activity) context;

        ToastUtils.showShort(R.string.saving);
        DownloadUtils.get().download(url, SavePath, fileName, new DownloadUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                ToastUtils.showLong(activity.getString(R.string.save_local) + "\n\n" + finalSavePath);
            }
            @Override
            public void onDownloading(int progress) {

            }
            @Override
            public void onDownloadFailed(String error) {
                ToastUtils.showShort(R.string.save_error);
            }
        });
    }

    /**
     * 复制内容到剪切板
     */
    public static void putStringToClipboard(Context context, String text) {
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        Objects.requireNonNull(mClipboardManager).setPrimaryClip(ClipData.newPlainText(null, text));
    }

    /**
     * 读取Assets内部文件
     *
     * @param fileName Assets内部文件路径
     * @return 文件内容
     */
    public static String ReadAssetsFile(Context context,String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件";
    }

    /**
     * 图书馆相关方法
     *
     * @param WhichJson WhichJson
     * @param Getkey    Getkey
     * @return GetBookStateInfo
     */
    public static String GetBookStateInfo(Context context,int WhichJson, String Getkey) {
        switch (WhichJson) {
            case 0:
                String Map0 = ReadAssetsFile(context,"LibState/BookState.json");
                Map<String, String> Hash0 = parseData(Map0);
                return Hash0.get(Getkey);
            case 1:
                String Map1 = ReadAssetsFile(context,"LibState/WhereLib.json");
                Map<String, String> Hash1 = parseData(Map1);
                return Hash1.get(Getkey);
            case 2:
                String Map2 = ReadAssetsFile(context,"LibState/WhichCollege.json");
                Map<String, String> Hash2 = parseData(Map2);
                return Hash2.get(Getkey);
        }
        return "";
    }

    private static Map<String, String> parseData(String data) {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        return g.fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
    }


    //查找图书封面
    public static void findBookPicture(final Activity context, final String bookID, final ImageView bookImg,final boolean canOpen) {
        String book_Bn = bookID.replace("-", "");
        final String url = Urls.Url_ISBN + book_Bn;
        OkHttp.do_Get(url, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                LogUtils.i(response);
                Gson gson = new Gson();
                response = response.substring(1, response.length() - 1);

                ImgBean imgbean = gson.fromJson(response,ImgBean.class);
                List<ImgBean.ImgResult> result = imgbean.getResult();
                if (result.size() != 0) {
                    final String ImgUrl = result.get(0).getCoverlink();
                    if (!context.isDestroyed()) {
                        Glide.with(context).load(ImgUrl).into(bookImg);
                    }
                    if (canOpen) {
                        bookImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyUtils.openImage(context, ImgUrl);
                            }
                        });
                    }
                } else {
                    LogUtils.i("未查询到封面图", url);
                }
            }
            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET)&& !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                findBookPicture(context,bookID, bookImg,canOpen);
            }
        });

    }



    /**
     * 获取新闻Html模板代码
     * @param title 标题
     * @param scoure 来源
     * @param summary 简介
     * @param time 时间
     * @param pic 封面
     * @param content 内容
     * @return 新闻Html模板代码
     */
    public static String getNewsModel(String title, String scoure, String summary, String time,String pic ,String content) {
        return "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\"/>\n" +
                "<meta name=\"viewport\"\n" +
                "\tcontent=\"width=device-width, initial-scale=1.0, user-scalable=no\"/>\n" +
                "<title>"+title+"</title>\n" +
                "<style type=\"text/css\">\n" +
                "* {\n" +
                "\tpadding: 0;\n" +
                "\tmargin: 0;\n" +
                "}\n" +
                "img{\n" +
                "\tmax-width:100%;\n" +
                "\tdisplay:inline-block;\n" +
                "}\n" +
                "html,body {\n" +
                "\tmargin: 0;\n" +
                "\tpadding: 0;\n" +
                "}\n" +
                "\n" +
                "body {\n" +
                "\tmin-width: 320px;\n" +
                "\tmax-width: 750px;\n" +
                "\tmargin: 0 auto;\n" +
                "}\n" +
                "\n" +
                "#wrapper {\n" +
                "\tpadding: 20px 10px 0px 10px;\n" +
                "\theight: 100%;\n" +
                "}\n" +
                "\n" +
                "#space {\n" +
                "\theight: 50px;\n" +
                "}\n" +
                "\n" +
                "#footer {\n" +
                "\tposition: fixed;\n" +
                "\tbottom: 0;\n" +
                "\twidth: 100%;\n" +
                "\theight: 50px;\n" +
                "\tmax-width: 750px;\n" +
                "\tbackground: url(http://img.ytsg.cn/images/htmlPage/logo.png) no-repeat;\n" +
                "\tbackground-size: 40px 40px;\n" +
                "\tbackground-color: #fff;\n" +
                "\tbackground-position: 10px 5px;\n" +
                "\tbox-shadow: 0 -3px 5px rgba(0, 0, 0, 0.4);\n" +
                "}\n" +
                "#content{\n" +
                "\tfont-family:'微软雅黑';\n" +
                "\ttext-align:justify;\n" +
                "}\n" +
                " .qr_wraper{position:fixed;}\n" +
                "\n" +
                "div.qr_code {\n" +
                "\twidth: 130px;\n" +
                "\tposition: absolute;\n" +
                "\tright: -140px;\n" +
                "\ttop: 0;\n" +
                "\tpadding: 10px;\n" +
                "\tborder: 1px solid #ccc;\n" +
                "}\n" +
                "\n" +
                "img.qr_img {\n" +
                "\theight: 130px;\n" +
                "\twidth: 130px;\n" +
                "}\n" +
                "\n" +
                ".qr_related span {\n" +
                "\tdisplay: inline-block;\n" +
                "\twidth: 100%;\n" +
                "\ttext-align: center;\n" +
                "\tfont-size: 16px;\n" +
                "\tcolor: #8d633b;\n" +
                "\tfont-family: \"微软雅黑\"\n" +
                "}\n" +
                "\n" +
                ".qr_related .saoma {\n" +
                "\t\n" +
                "}\n" +
                "\n" +
                ".qr_related .load {\n" +
                "\tmargin-top: 4px;\n" +
                "}\n" +
                "\n" +
                "#appName {\n" +
                "\tfont-size: 16px;\n" +
                "\tfont-weight: bold;\n" +
                "\tfloat: left;\n" +
                "\tcolor: #000;\n" +
                "\tmargin-left: 60px;\n" +
                "\theight: 50px;\n" +
                "\tline-height: 50px;\n" +
                "}\n" +
                "\n" +
                "#openButton {\n" +
                "\theight: 28px;\n" +
                "\twidth: 60px;\n" +
                "\tbackground: #9E724D;\n" +
                "\tmargin-top: 11px;\n" +
                "\tmargin-right: 10px;\n" +
                "\tfloat: right;\n" +
                "}\n" +
                "\n" +
                "#openButtonTitle {\n" +
                "\theight: 100%;\n" +
                "\tline-height: 100%;\n" +
                "\tcolor: #fff;\n" +
                "\ttext-align: center;\n" +
                "\tfont-size: 16px;\n" +
                "\tmargin-top: 6px;\n" +
                "}\n" +
                "</style>"+
                "</head>\n" +
                "\n" +
                "<body id=\"container\">\n" +
                "\t<script type=\"text/javascript\">\n" +
                "\t\t//根据不同的platform来设置不同的样式。\n" +
                "\t\t(function() {\n" +
                "\t\t\tvar platform = self.getPlatform();\n" +
                "\t\t\tif (platform == \"others\") {\n" +
                "\t\t\t\tdocument.getElementById(\"container\").setAttribute(\"style\",\n" +
                "\t\t\t\t\t\t\"width:750px;margin:0 auto;\")\n" +
                "\t\t\t}\n" +
                "\t\t})();\n" +
                "\t</script>" +
                "\n" +
                "\t<div id=\"wrapper\">\n" +
                "\t\t<p style=\"color: #ccc; font-size: 14px;\">"+scoure+"</p>\n" +
                "\t\t<p style=\"font-size: 21px; line-height: 1.5; margin:20px 0 10px 0;font-family:'微软雅黑';\">"+summary+"</p>\n" +
                "\t\t\n" +
                "\t\t<p style=\"color: #ccc; margin-bottom:10px; line-height: 16px; font-size: 14px\">"+time+"</p>\n" +
                "\t\t\n" +
                "\t\t<div style=\"text-align: center\">\n" +
                "\t\t\t<img src=\""+pic+"\">\n" +
                "\t\t</div>\n" +
                "\t\t\n" +
                "\t\t<div id=\"content\">\n" +
                "\t\t"+content+"\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "\t\n" +
                "</body></html>";

    }

    /**
     * 跳转网页
     * @param url 网页链接
     */
    public static void openUrl(Context context,String url) {
        context.startActivity(new Intent(context, WebActivity.class)
                .putExtra("from_url",url));
    }

    /**
     * 跳转网页
     * @param url 网页链接
     * @param cookie Cookie
     */
    public static void openUrl(Context context,String url,String cookie) {
        context.startActivity(new Intent(context, WebActivity.class)
                .putExtra("from_url", url)
                .putExtra("cookie", cookie));
    }


    /**
     * 跳转网页
     * @param url 网页链接
     */
    public static void openUrlByOther(Context context,String url) {
        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }

    /**
     * 正则获取Host
     * @param url 链接
     * @return Host
     */
    public static String getHost(String url){
        if(url==null||url.trim().equals("")){
            return "";
        }
        String host = "";
        Pattern p =  Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if(matcher.find()){
            host = matcher.group();
        }
        return host;
    }

    /**
     * 将文件按时间降序排列
     */
    public static class FileComparator2 implements Comparator<File> {
        @Override
        public int compare(File file1, File file2) {
            if (file1.lastModified() < file2.lastModified()) {
                return 1;// 最后修改的文件在前
            } else {
                return -1;
            }
        }
    }

    /**
     * 将cookie同步到X5WebView
     *
     * @param host    WebView要加载的host
     * @param cookie 要同步的cookie
     */
    public static void syncX5Cookie(String host, String cookie) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(host, cookie);
    }

    /**
     * 将X5WebView的cookie清除
     */
    public static void clearX5Cookie(Context context) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }


    /**
     * 打开图片
     * @param url 图片链接
     */
    public static void openImage(Context context,String url) {
        context.startActivity(new Intent(context, ImageActivity.class)
                .putExtra("from_url", url));
    }

    /**
     * 控制震动
     *
     * @param time 震动时间
     */
    public void mVibrator(Context Context ,int time) {
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
    public static int dp2px(Context Context ,float dpValue) {
        float scale = Context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     *
     * @param pxValue px的值
     * @return dp的值
     */
    public static int px2dp(Context Context ,float pxValue) {
        float scale = Context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     *
     * @param spValue sp的值
     * @return px的值
     */
    public static int sp2px(Context Context ,float spValue) {
        float fontScale = Context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     *
     * @param pxValue px的值
     * @return sp的值
     */
    public static int px2sp(Context Context ,float pxValue) {
        float fontScale = Context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * tabLayout 设置自适应宽度
     * @param tabLayout tabLayout
     */
    public static void reflexTabLayout(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp5 =dp2px(tabLayout.getContext(), 5);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp5;
                        params.rightMargin = dp5;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    /**
     * 重写对话框关闭事件
     *
     * @param dialogInterface 对话框
     * @param close           是否能关闭
     */
    public static void canCloseDialog(DialogInterface dialogInterface, boolean close) {
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialogInterface, close);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
