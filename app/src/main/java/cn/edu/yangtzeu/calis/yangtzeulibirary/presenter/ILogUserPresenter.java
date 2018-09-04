package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SPUtils;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.LogUserModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener.ILogListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ILogUserView;

public class ILogUserPresenter {
    //数据源
    private ILogUserView userView;
    //处理业务逻辑
    private LogUserModel userModel;

    public ILogUserPresenter(ILogUserView userView) {
        this.userView = userView;
        userModel = new LogUserModel();
    }


    /**
     * 登陆方法，进行M,V层的关系建立
     *
     * @param iLogListener iLogListener
     */
    public void login(ILogListener iLogListener) {
        userModel.login(userView.getUserName(), userView.getUserPsd(), iLogListener);
    }

    /**
     * 注销方法
     *
     * @param activity activity
     */
    public void logout(Activity activity) {
        userModel.logout(activity);
    }

    /**
     * 自动填充
     * @param autoSave 学号框
     * @param editText 密码框
     */
    public void setAutoSave(AutoCompleteTextView autoSave, EditText editText) {
        userModel.setAutoSave(autoSave,editText);
    }



}











