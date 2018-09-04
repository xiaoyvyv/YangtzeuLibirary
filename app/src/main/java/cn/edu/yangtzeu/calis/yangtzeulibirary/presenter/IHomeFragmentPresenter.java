package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.HomeFragmentModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHomeFragmentView;

public class IHomeFragmentPresenter {
    //数据源
    public static IHomeFragmentView IHomeFragmentView;
    //处理业务逻辑
    private HomeFragmentModel mainModel;

    public IHomeFragmentPresenter(IHomeFragmentView IHomeFragmentView) {
        IHomeFragmentPresenter.IHomeFragmentView = IHomeFragmentView;
        mainModel = new HomeFragmentModel();
    }
    //填充主页 Fragment 视图
    public void fitView(AppCompatActivity activity){
        mainModel.fitView(activity, IHomeFragmentView, IHomeFragmentView.getViewPager());
    }
}
