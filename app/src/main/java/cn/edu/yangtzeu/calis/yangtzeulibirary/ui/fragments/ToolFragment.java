package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.blankj.utilcode.util.LogUtils;

import net.custom.MyGridView;

import java.util.Objects;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IToolFragmentPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IToolFragmentView;


/**
 * Created by Administrator on 2018/3/6.
 */

public class ToolFragment extends BaseFragment implements IToolFragmentView {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared=false;
    private boolean isLoadFinish = false;
    private View RootView;
    private BGABanner bgaBanner;
    private MyGridView gridView;
    private IToolFragmentPresenter IToolFragmentPresenter;
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_tool, container, false);
        FindView();
        isPrepared = true;
        lazyLoad();
        return RootView;
    }

    private void FindView() {
        IToolFragmentPresenter = new IToolFragmentPresenter(ToolFragment.this);
        bgaBanner = RootView.findViewById(R.id.bgaBanner);
        gridView = RootView.findViewById(R.id.gridView);
        toolbar = RootView.findViewById(R.id.toolbar);
    }
    private void SetUp() {
        IToolFragmentPresenter.fitView((AppCompatActivity) getActivity());
        toolbar.inflateMenu(R.menu.fragment_tool_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.jianyi:
                        MyUtils.openUrl(Objects.requireNonNull(getActivity()),"http://cn.mikecrm.com/izNFxcz");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            SetUp();
            LogUtils.e("加载功能 Fragment");
        }
        isLoadFinish = true;
    }

    @Override
    public BGABanner getBanner() {
        return bgaBanner;
    }

    @Override
    public MyGridView getGridView() {
        return gridView;
    }
}