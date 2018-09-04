package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.MineFragmentModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMineFragmentView;

public class IMineFragmentPresenter {
    //数据源
    private IMineFragmentView view;
    //处理业务逻辑
    private MineFragmentModel model;

    public IMineFragmentPresenter(IMineFragmentView view) {
        this.view = view;
        model = new MineFragmentModel();
    }

    public void getUserInfo(Activity activity) {
        model.getUserInfo(activity,view);
    }

}
