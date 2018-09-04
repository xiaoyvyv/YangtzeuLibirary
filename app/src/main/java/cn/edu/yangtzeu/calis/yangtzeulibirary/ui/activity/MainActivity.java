package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.LogUserModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener.ILogListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IMainPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.ShareUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,IMainView {

    private IMainPresenter iMainPresenter;
    private FrameLayout fragment_container;
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        boolean isCrash = getIntent().getBooleanExtra("isCrash", false);
        if (isCrash) {
            @SuppressLint("SdCardPath") final String path = "/sdcard/A_Tool/Lib_Crash/";
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.trip)
                    .setMessage("发现了App漏洞，为了提升用户体验，建议提交")
                    .setNeutralButton("取消", null)
                    .setNegativeButton("提交日志", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<File> list = FileUtils.listFilesInDir(path, false);
                            Collections.sort(list, new MyUtils.FileComparator2());
                            String bug = FileIOUtils.readFile2String(list.get(0));
                            MyUtils.putStringToClipboard(MainActivity.this, bug);
                            ToastUtils.showLong("Bug已复制");
                            MyUtils.openUrl(MainActivity.this,getResources().getString(R.string.Me_QQ));
                        }
                    })
                    .setPositiveButton("提交文件", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<File> list = FileUtils.listFilesInDir(path, false);
                            Collections.sort(list, new MyUtils.FileComparator2());
                            Uri uri = Uri.fromFile(list.get(0));
                            ShareUtils.shareFile(MainActivity.this,uri);
                        }
                    })
                    .create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }
    @Override
    public void initViews() {
        iMainPresenter = new IMainPresenter(MainActivity.this);
        fragment_container = findViewById(R.id.fragment_container);
        mBottomNavigationView = findViewById(R.id.mBottomNavigationView);
    }

    @Override
    public void initMethod() {
        mBottomNavigationView.setItemIconTintList(null);

        iMainPresenter.addHomeFragment(this);
        iMainPresenter.addToolFragment(this);
        iMainPresenter.addMeFragment(this);

        iMainPresenter.fitBottomView(this,mBottomNavigationView);

        MyUtils.checkUpDate(this);
        MyUtils.dialogNotice(this);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_book_list:
                // Handle the camera action
                break;
            case R.id.nav_my_book:

                break;
            case R.id.nav_search_list:

                break;
            case R.id.nav_setting:

                break;
            case R.id.nav_share:
                Intent intent = IntentUtils.getShareTextIntent("长江大学图书馆App,借阅超级方便哦！", true);
                ActivityUtils.startActivity(intent);
                break;
            case R.id.nav_logout:
                new LogUserModel().logout(MainActivity.this);
                break;
        }
        return true;
    }

    @Override
    public FrameLayout getFrameLayout() {
        return fragment_container;
    }
}
