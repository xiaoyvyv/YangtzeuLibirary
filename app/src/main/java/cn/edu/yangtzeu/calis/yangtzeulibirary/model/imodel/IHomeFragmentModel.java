package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHomeFragmentView;


public interface IHomeFragmentModel {
    void fitView(AppCompatActivity activity, IHomeFragmentView tabLayout, ViewPager viewPager);
}
