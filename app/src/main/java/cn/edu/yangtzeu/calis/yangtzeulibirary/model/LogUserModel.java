package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.app.MyApplication;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.ILogUserModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener.ILogListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.LogActivity;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * 业务逻辑处理
 * @author Administrator
 *
 */
public class LogUserModel implements ILogUserModel {

    /**
     * 登录
     */
    @Override
    public void login(String name, String pwd, final ILogListener loginListener) {
        if (name.isEmpty()) {
            loginListener.onError("请输入学号");
            return;
        }
        if (pwd.isEmpty()) {
            loginListener.onError("请输入密码");
            return;
        }

        String MD5_pwd = EncryptUtils.encryptMD5ToString(pwd);
        String md5_pwd = MyUtils.swapCase(MD5_pwd);

        SPUtils.getInstance("UserInfo").put("Number", name,true);
        SPUtils.getInstance("UserInfo").put("Password", pwd,true);

        FormBody formBody = new FormBody.Builder()
                .add("rdid", name)
                .add("rdPasswd", md5_pwd)
                .build();

        Request request = new Request.Builder()
                .post(formBody)
                .url(Urls.Url_Login)
                .build();

        OkHttp.do_Post(request, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                String Str = document.select("div.form").text();
                Str =Str+ document.select("div.navbar_info_zh").text();
                LogUtils.d(Str);
                if (Str.contains("用户名或密码错误")) {
                    MyApplication.isLogin = false;
                    loginListener.onError("用户名或密码错误!");
                } else {
                    MyApplication.isLogin = true;
                    loginListener.onSuccess(response);
                }
            }

            @Override
            public void onFailure(String error) {
                MyApplication.isLogin = false;
                loginListener.onError(error);
            }
        });
    }

    /**
     * 注销
     */
    @Override
    public void logout(final Activity activity) {
        final ProgressDialog progressDialog = MyUtils.getProgressDialog(activity, "注销中");
        progressDialog.show();
        OkHttp.do_Get(Urls.Url_Logout, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                MyApplication.isLogin = false;
                Document document = Jsoup.parse(response);
                String Str = document.select("div.navbar_info_zh").text();
                progressDialog.dismiss();
                if (Str.contains("登录")) {
                    ActivityUtils.startActivity(LogActivity.class);
                    ActivityUtils.finishAllActivities();
                    SPUtils.getInstance("UserInfo").put("Password", "");
                } else {
                    ToastUtils.showShort("注销出现错误");
                }
            }
            @Override
            public void onFailure(String error) {
                MyApplication.isLogin = false;
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 修改密码
     */
    @Override
    public void changePsd(final Activity activity) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(activity).inflate(R.layout.view_change_psd, null);
        final AlertDialog viewDialog = new AlertDialog.Builder(activity)
                .setTitle(R.string.change_psd)
                .setMessage("请设置纯数字密码，以兼容电话续借和自助借还终端密码输入")
                .setView(view)
                .create();
        viewDialog.show();
        viewDialog.setCanceledOnTouchOutside(false);

        Button change = view.findViewById(R.id.change_psd_bt);
        final TextInputEditText new_psd = view.findViewById(R.id.new_psd);
        final TextInputEditText do_new_psd = view.findViewById(R.id.do_new_psd);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPsd = new_psd.getText().toString().trim();
                String doNewPsd = do_new_psd.getText().toString().trim();
                if (newPsd.isEmpty()) {
                    new_psd.setError(activity.getString(R.string.please_input_newPsd));
                    new_psd.requestFocus();
                } else if (newPsd.length()<6){
                    new_psd.setError(activity.getString(R.string.psd_too_short));
                    new_psd.requestFocus();
                } else if (doNewPsd.isEmpty()) {
                    do_new_psd.setError(activity.getString(R.string.please_sure_newPsd));
                    do_new_psd.requestFocus();
                } else if (!doNewPsd.equals(newPsd)) {
                    do_new_psd.setError(activity.getString(R.string.do_newPsd_error));
                    do_new_psd.requestFocus();
                } else {
                    KeyboardUtils.hideSoftInput(activity);
                    //修改密码
                    final ProgressDialog progressDialog = MyUtils.getProgressDialog(activity, "修改密码中");
                    progressDialog.show();

                    String num = SPUtils.getInstance("UserInfo").getString("Number", "");

                    RequestBody fromBody = new FormBody.Builder()
                            .add("rdid", num)
                            .add("password", doNewPsd)
                            .build();

                    Request request=new Request.Builder()
                            .post(fromBody)
                            .url(Urls.Url_Change_Psd)
                            .build();
                    OkHttp.do_Post(request, new OnResultListener() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            viewDialog.dismiss();

                            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                                    .setTitle(R.string.trip)
                                    .setMessage(response)
                                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            logout(activity);
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);

                        }
                        @Override
                        public void onFailure(String error) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void setAutoSave(AutoCompleteTextView autoSave, EditText editText) {
        String num = SPUtils.getInstance("UserInfo").getString("Number", "");
        String psd = SPUtils.getInstance("UserInfo").getString("Password", "");
        autoSave.setText(num);
        editText.setText(psd);

    }

}
