package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IHomeFragmentPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHomeFragmentView;


/**
 * Created by Administrator on 2018/3/6.
 */

public class HomeFragment extends BaseFragment implements IHomeFragmentView {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared=false;
    private boolean isLoadFinish = false;
    private View RootView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private IHomeFragmentPresenter IHomeFragmentPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_home, container, false);
        FindView();
        isPrepared = true;
        lazyLoad();
        return RootView;
    }

    private void FindView() {
        mTabLayout = RootView.findViewById(R.id.mTabLayout);
        mViewPager = RootView.findViewById(R.id.mViewPager);
        IHomeFragmentPresenter = new IHomeFragmentPresenter(HomeFragment.this);

    }
    private void SetUp() {
        IHomeFragmentPresenter.fitView((AppCompatActivity) getActivity());

    }

    @Override
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    @Override
    public ViewPager getViewPager() {
        return mViewPager;
    }



    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            SetUp();
            LogUtils.e("加载第主页 Fragment");
        }
        isLoadFinish = true;
    }
}