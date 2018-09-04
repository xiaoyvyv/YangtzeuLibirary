package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.LogUserModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.AlipayUtil;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.ShareUtils;

import static cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BaseActivity.UtilsTool;

public class SettingActivity extends PreferenceActivity {
    private final static String TAG = "【SettingActivity】";
    private android.support.v7.widget.Toolbar Toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
        Toolbar.setTitle(getTitle());
        SetUp();
    }

    private void SetUp() {

        //文件保存路径设置
        final EditTextPreference FileLocation= (EditTextPreference) findPreference("FileLocation");
        FileLocation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                String input =  o.toString();
                if (!(input.substring(input.length() - 1, input.length())).equals("/")) {
                    input = input + "/";
                } else if ((input.substring(0, 1)).equals("/")) {
                    input = input.substring(1, input.length());
                }
                Log.e(TAG, "保存的文件下载路径" + input);
                UtilsTool.putShareString("DownloadInfo", "SavePath", input);

                UtilsTool.createSDCardDir(input);
                FileLocation.setSummary(input);
               return true;
            }
        });

        //修改密码
        Preference ChangePassword = findPreference("ChangePassword");
        ChangePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new LogUserModel().changePsd(SettingActivity.this);
                return true;
            }
        });

        String name = UtilsTool.getShareStringWithDefeat("UserInfo", "Number", "未知");
        //切换用户
        Preference Login = findPreference("Login");
        Login.setSummary("用户：" + name);
        Login.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new LogUserModel().logout(SettingActivity.this);
                return true;
            }
        });


        Preference mShare = findPreference("ShareText");
        mShare.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ShareUtils.shareMsg(SettingActivity.this, "长江大学图书馆", "一个超级好用的图书查询、文档搜索软件", null);
                return true;
            }
        });

        final Preference Update = findPreference("Update");
        Update.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                MyUtils.checkUpDate(SettingActivity.this);
                return true;
            }
        });

        final Preference myApp = findPreference("myApp");
        myApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ActivityUtils.startActivity(AppListActivity.class);
                return true;
            }
        });



        Preference OpenCode = findPreference("OpenCode");
        OpenCode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                @SuppressLint("InflateParams")
                AlertDialog dialog = new AlertDialog.Builder(SettingActivity.this)
                        .setView(getLayoutInflater().inflate(R.layout.activity_settings_open_dialog,null))
                        .setPositiveButton(R.string.done,null)
                        .create();
                dialog.show();
                return true;
            }
        });

        Preference pay = findPreference("pay");
        pay.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                UtilsTool.PutStringToClipboard(getString(R.string.apply_redbag_code_key));
                if(AlipayUtil.hasInstalledAlipayClient(SettingActivity.this)){
                    //第二个参数代表要给被支付的二维码code  可以在用草料二维码在线生成
                    AlipayUtil.startAlipayClient(SettingActivity.this, getString(R.string.apply_redbag_code_key));
                }else{
                    ToastUtils.showLong(R.string.no_apply_app);
                }
                return true;
            }
        });

        Preference donate = findPreference("donate");
        donate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                UtilsTool.PutStringToClipboard(getString(R.string.apply_redbag_code_key));
                if(AlipayUtil.hasInstalledAlipayClient(SettingActivity.this)){
                    //第二个参数代表要给被支付的二维码code  可以在用草料二维码在线生成
                    AlipayUtil.startAlipayClient(SettingActivity.this, getString(R.string.apply_me_key));
                }else{
                    ToastUtils.showLong(R.string.no_apply_app);
                }
                return true;
            }
        });



    }


    @Override
    public void setContentView(int layoutResID) {
        ViewGroup contentView = (ViewGroup) LayoutInflater
                .from(this)
                .inflate(R.layout.activity_settings, new LinearLayout(this), false);

        Toolbar = contentView.findViewById(R.id.action_bar);
        Toolbar.setNavigationIcon(R.drawable.ic_back);
        Toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });


        contentView.findViewById(R.id.exit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //退出程序
                        AppUtils.exitApp();
                    }
                });

        ViewGroup contentWrapper = contentView.findViewById(R.id.content_wrapper);

        LayoutInflater
                .from(this)
                .inflate(layoutResID, contentWrapper, true);
        getWindow().setContentView(contentView);
    }

}