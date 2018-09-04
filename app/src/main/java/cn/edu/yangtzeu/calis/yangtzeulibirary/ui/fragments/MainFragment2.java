package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IMainFragment2Presenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.Fragment2Adapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainFragment2View;


/**
 * Created by Administrator on 2018/3/6.
 */

public class MainFragment2 extends BaseFragment implements IMainFragment2View {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared=false;
    private boolean isLoadFinish = false;
    private View RootView;
    private BGABanner mBGABanner;
    private IMainFragment2Presenter mainFragment2Presenter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private ProgressDialog progressDialog;
    private Fragment2Adapter adapter;
    private String from_url;
    private int which;

    public static MainFragment2 newInstance(String url, int which) {
        MainFragment2 fragment = new MainFragment2();
        Bundle bundle = new Bundle();
        bundle.putString("from_url", url);
        bundle.putInt("which", which);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            from_url = bundle.getString("from_url", "");
            which = bundle.getInt("which", -1);
        }
        RootView = inflater.inflate(R.layout.fragment2, container, false);
        FindView();
        isPrepared = true;
        lazyLoad();
        return RootView;
    }

    private void FindView() {
        mRecyclerView = RootView.findViewById(R.id.mRecyclerView);
        mBGABanner = RootView.findViewById(R.id.mBGABanner);
        mSmartRefreshLayout = RootView.findViewById(R.id.mSmartRefreshLayout);
        mainFragment2Presenter = new IMainFragment2Presenter(MainFragment2.this);
    }

    private void SetUp() {
        progressDialog = MyUtils.getProgressDialog(getActivity(), "加载中");
        progressDialog.show();

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(false);

        adapter = new Fragment2Adapter(getActivity());
        mRecyclerView.setAdapter(adapter);

        if (which == 1) {
            mainFragment2Presenter.getBanner(getActivity());
        } else {
            mBGABanner.setVisibility(View.GONE);
            mBGABanner.setAutoPlayAble(false);
        }
        mainFragment2Presenter.getNotice(getActivity(), adapter, false);
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            SetUp();
            LogUtils.e("加载第二个Fragment");
        }
        isLoadFinish = true;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public BGABanner getBGABanner() {
        return mBGABanner;
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    @Override
    public String getUrl() {
        return from_url;
    }

    @Override
    public int getWhich() {
        return which;
    }
}