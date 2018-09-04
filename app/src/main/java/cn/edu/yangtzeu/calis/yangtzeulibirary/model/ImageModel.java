package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import net.custom.TouchImageView;
import net.custom.WebViewProgressBar;
import net.glide.ProgressInterceptor;
import net.glide.ProgressListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IImageModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BaseActivity;

public class ImageModel implements IImageModel {
    @Override
    public void applyToolbar(final AppCompatActivity activity, Toolbar toolbar, final String from_url) {

        String name = BaseActivity.UtilsTool.getFileName(from_url);
        toolbar.setTitle(name);

        long time = new File(from_url).lastModified();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String File_Time = formatter.format(time);
        String size = BaseActivity.UtilsTool.getFileSize(from_url);
        toolbar.setSubtitle(File_Time+" "+size);


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
    public void fitImageView(final Context context, TouchImageView imageView,
                             final WebViewProgressBar progressBar, final String from_url) {
        if (URLUtil.isNetworkUrl(from_url)) {
            ProgressInterceptor.addListener(from_url, new ProgressListener() {
                @Override
                public void onProgress(int i) {
                    progressBar.setProgress(i);
                }
            });

            Glide.with(context)
                    .load(from_url)
                    .fitCenter().thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.color.translate)
                    .into(new GlideDrawableImageViewTarget(imageView) {
                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                        }

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            ProgressInterceptor.removeListener(from_url);
                        }
                    });
        } else {
            File from_file = new File(from_url);
            Glide.with(context)
                    .load(from_file)
                    .fitCenter().thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.color.translate)
                    .into(imageView);

        }

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(context,from_url);
                return true;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) context).finish();
            }
        });
    }

    @SuppressLint("InflateParams")
    @Override
    public void showDialog(final Context context, final String url) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_image_dialog, null);

        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.style_dialog)
                .setView(view)
                .setCancelable(true).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


        TextView save = view.findViewById(R.id.save_img);
        TextView share = view.findViewById(R.id.share);
        TextView clear = view.findViewById(R.id.clear);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BaseActivity.UtilsTool.ShareText("图片："+url+"\n\n数据来自：" + Urls.Url_AppDownUrl);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (url != null && URLUtil.isNetworkUrl(url) ) {
                    MyUtils.saveFile(context, url, BaseActivity.UtilsTool.getFileName(url),"A_Tool/Download/Image/");
                } else {
                    ToastUtils.showShort(R.string.cant_save);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Window window = dialog.getWindow();
            Objects.requireNonNull(window).setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setWindowAnimations(R.style.BottomToBottom);  //添加动画
            WindowManager windowManager = ((Activity) context).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = Objects.requireNonNull(dialog.getWindow()).getAttributes();
            lp.width = display.getWidth(); //设置宽度
            dialog.getWindow().setAttributes(lp);
        }
    }

}
