package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IMineFragmentPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMineFragmentView;


/**
 * Created by Administrator on 2018/3/6.
 */

public class MainFragment3 extends BaseFragment {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared=false;
    private boolean isLoadFinish = false;
    private View RootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment3, container, false);
        FindView();
        isPrepared = true;
        lazyLoad();
        return RootView;
    }

    private void FindView() {

    }

    private void SetUp() {


    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            SetUp();
            LogUtils.e("加载第三个Fragment");
        }
        isLoadFinish = true;
    }
}