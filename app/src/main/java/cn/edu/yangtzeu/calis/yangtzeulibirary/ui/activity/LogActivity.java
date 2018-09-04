package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener.ILogListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.ILogUserPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ILogUserView;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * A login screen that offers login via email/password.
 */
public class LogActivity extends BaseActivity implements ILogUserView,ILogListener {

    //指示器与View层进行交互
    private ILogUserPresenter userPresenter;

    // UI references.
    private AutoCompleteTextView mNumView;
    private EditText mPasswordView;
    private Button mSignInButton;
    public ImageView mBackgroundImage;
    public ProgressDialog progressDialog;
    public TextView rule;
    public TextView Psd_lose;
    private TextView skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtils.setFullScreen(this);
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        mNumView = findViewById(R.id.et_number);
        mPasswordView = findViewById(R.id.et_psd);
        mSignInButton = findViewById(R.id.bt_login);
        mBackgroundImage = findViewById(R.id.img_bg);
        rule = findViewById(R.id.rule);
        Psd_lose= findViewById(R.id.Psd_lose);
        skip= findViewById(R.id.skip);

    }

    @Override
    public void initMethod() {
        progressDialog = MyUtils.getProgressDialog (this,getString(R.string.loging));
        userPresenter = new ILogUserPresenter(this);
        userPresenter.setAutoSave(mNumView,mPasswordView);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                userPresenter.login(LogActivity.this);
            }
        });

        rule.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(LogActivity.this)
                        .setTitle("App服务条款")
                        .setMessage("1.个人信息保护\n    除非长江大学的规定外，未经用户许可开发者不会向第三方公开、透露用户个人信息。账户数据只会向长江大学图书馆提交并登录，不会向其它任意第三方发送（学号及密码及相关学业信息）！\n\n2.App相关\n"+
                                "    本App是本人独立开发的，没有任何技术指导，您在使用的过程中若遇到了不可避免的问题，应自行承担由此造成的风险，并且最终解释权归开发者所有！")
                        .setPositiveButton("知道了",null)
                        .create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
            }
        });

        Psd_lose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(LogActivity.this)
                        .setTitle("温馨提示")
                        .setMessage("忘记了图书馆密码请联系图书馆工作人员\n\n并带上学生证到图书馆进行密码清除。")
                        .setPositiveButton("知道了", null)
                        .create();
                dialog.show();
            }
        });
        skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(MainActivity.class);
                LogActivity.this.finish();
            }
        });
    }


    @Override
    public String getUserName() {
        return mNumView.getText().toString().trim();
    }

    @Override
    public String getUserPsd() {
        return mPasswordView.getText().toString().trim();
    }


    @Override
    public void onSuccess(String info) {
        progressDialog.dismiss();
        ToastUtils.showShort("登陆成功");
        ActivityUtils.startActivity(MainActivity.class);
        ActivityUtils.finishActivity(this);
    }

    @Override
    public void onError(String error) {
        progressDialog.dismiss();
        ToastUtils.showShort(error);
    }
}

