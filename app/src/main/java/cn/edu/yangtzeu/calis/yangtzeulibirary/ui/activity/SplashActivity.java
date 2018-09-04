package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.bumptech.glide.Glide;

import net.permissions.FcPermissions;
import net.permissions.FcPermissionsCallbacks;

import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.LogUserModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener.ILogListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.Utils_Tool;

public class SplashActivity extends BaseActivity implements FcPermissionsCallbacks {
    private static final String WRITE_EXTERNAL_STORAGE= Manifest.permission.WRITE_EXTERNAL_STORAGE;//需要请求的权限
    private static final String READ_EXTERNAL_STORAGE= Manifest.permission.READ_EXTERNAL_STORAGE;//需要请求的权限
    private String[] permission = new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};//需要请求的权限list
    private boolean IsFirstOpen = true;

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        image = findViewById(R.id.image);
    }

    @Override
    public void initMethod() {
        UtilsTool = new Utils_Tool(this);
        Glide.with(this).load(Urls.Url_Lib_Png).into(image);
        String SavePath = SPUtils.getInstance("DownloadInfo").getString("SavePath", "A_Tool/Download/");
        UtilsTool.createSDCardDir(SavePath);
        UtilsTool.createSDCardDir("A_Tool/WebCache/");
        UtilsTool.createSDCardDir("A_Tool/Download/Image/");

        IsFirstOpen = SPUtils.getInstance("AppInfo").getBoolean("isPermission", true);
        LogUtils.i("是否第一次使用App", IsFirstOpen);
        //请求权限
        requestPermission();
    }

    private void requestPermission() {
        if (IsFirstOpen){
            AlertDialog dialog = new AlertDialog.Builder(SplashActivity.this)
                    .setTitle("授权提示")
                    .setMessage("请允许以下权限保证App正常运行\n\n* 储存权限")
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SplashActivity.this.finish();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FcPermissions();
                        }
                    })
                    .setCancelable(false)
                    .create();
            dialog.show();
        } else FcPermissions();
    }

    private void FcPermissions(){
        FcPermissions.requestPermissions(SplashActivity.this ,"请允许以下权限保证App正常运行\n\n* 储存权限" ,
                FcPermissions.REQ_PER_CODE ,permission);
    }

    @Override
    public void onPermissionsGranted(int var1, List<String> var2) {
        //权限获取成功的操作

        //设置不是首次启动
        SPUtils.getInstance("AppInfo").put("isPermission", false);

        String Number = SPUtils.getInstance("UserInfo").getString("Number");
        String Password = SPUtils.getInstance("UserInfo").getString("Password");

        new LogUserModel().login(Number, Password, new ILogListener() {
            @Override
            public void onSuccess(String info) {
                ActivityUtils.startActivity(MainActivity.class);
                SplashActivity.this.finish();
            }

            @Override
            public void onError(String error) {
                ActivityUtils.startActivity(LogActivity.class);
                SplashActivity.this.finish();
            }
        });
    }

    @Override
    public void onPermissionsDenied(int var1, List<String> var2) {
        //若只禁止，执行下面的
        Snackbar.make(image, "授权失败，请重新授权！", 5000)

                .setAction("重新授权", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FcPermissions();
                    }
                }).show();

        //检查是否是禁止且不再提示，是，则执行弹窗提示到设置打开
        FcPermissions.checkDeniedPermissionsNeverAskAgain(SplashActivity.this,
                "请到设置中请允许以下权限\n\n* 储存权限",
                R.string.setting, R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SplashActivity.this.finish();
                    }
                }, var2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode , permissions , grantResults , SplashActivity.this);
    }

}
