package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.MainModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainView;

public class IMainPresenter {
    //数据源
    private IMainView mainView;
    //处理业务逻辑
    private MainModel mainModel;

    public IMainPresenter(IMainView mainView) {
        this.mainView = mainView;
        mainModel = new MainModel();
    }

    public void addHomeFragment(AppCompatActivity context) {
        mainModel.addHomeFragment(context, mainView.getFrameLayout().getId());
    }
    public void addToolFragment(AppCompatActivity context) {
        mainModel.addToolFragment(context, mainView.getFrameLayout().getId());
    }
    public void addMeFragment(AppCompatActivity context) {
        mainModel.addMeFragment(context, mainView.getFrameLayout().getId());
    }
    public void fitBottomView(AppCompatActivity context,BottomNavigationView bottomNavigationView) {
        mainModel.fitBottomView(context,bottomNavigationView);
    }

}











