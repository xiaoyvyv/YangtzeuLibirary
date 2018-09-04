package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

public interface IMainModel {

    void addHomeFragment(AppCompatActivity context, int container);

    void addToolFragment(AppCompatActivity context, int container);

    void addMeFragment(AppCompatActivity context, int container);

    void fitBottomView(AppCompatActivity context, BottomNavigationView bottomNavigationView);

}
