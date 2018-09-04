package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener.ILogListener;

/**
 * 业务逻辑处理
 * @author Administrator
 *
 */
public interface ILogUserModel {
    /**
     *提取的一个登陆方法，当然还可以有其它方法，比如获取数据，保存用户信息之类
     * @param name  用户名
     * @param pwd   密码
     * @param loginListener 登陆监听
     */
    void login(String name,String pwd,ILogListener loginListener);

    void logout(Activity activity);

    void changePsd(Activity activity);

    void setAutoSave(AutoCompleteTextView autoSave, EditText editText);
}