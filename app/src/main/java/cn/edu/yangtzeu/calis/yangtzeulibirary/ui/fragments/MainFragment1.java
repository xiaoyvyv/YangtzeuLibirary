package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IMainFragment1Presenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainFragment1View;


/**
 * Created by Administrator on 2018/3/6.
 */

public class MainFragment1 extends BaseFragment implements IMainFragment1View {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;
    private boolean isLoadFinish = false;
    private View RootView;
    private SearchView mSearchView;
    private IMainFragment1Presenter iMainFragment1Presenter;
    private LinearLayout mLayout1;
    private LinearLayout mLayout2;
    private LinearLayout mLayout3;
    private TextView high_search;
    private RadioGroup radio_group;
    private CheckBox mCheckBox;
    private TextView mCheckTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment1, container, false);
        FindView();
        isPrepared = true;
        lazyLoad();
        return RootView;
    }

    private void FindView() {
        mSearchView = RootView.findViewById(R.id.mSearchView);
        mLayout1 = RootView.findViewById(R.id.Layout1);
        mLayout2 = RootView.findViewById(R.id.Layout2);
        mLayout3 = RootView.findViewById(R.id.Layout3);
        high_search = RootView.findViewById(R.id.high_search);
        mCheckBox = RootView.findViewById(R.id.mCheckBox);
        radio_group = RootView.findViewById(R.id.Radio_Group);
        mCheckTextView = RootView.findViewById(R.id . mCheckTextView);
    }

    private void SetUp() {
        mCheckTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    mCheckBox.setChecked(false);
                } else {
                    mCheckBox.setChecked(true);
                }
            }
        });


        iMainFragment1Presenter = new IMainFragment1Presenter(MainFragment1.this);
        iMainFragment1Presenter.searchData(getActivity());
        iMainFragment1Presenter.fitHotWord(getActivity(),mSearchView,mLayout1,mLayout2,mLayout3);
        iMainFragment1Presenter.setOnlySavedLib();
        iMainFragment1Presenter.setSearchWay();

        high_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iMainFragment1Presenter.showHighSearch(getActivity());
            }
        });
    }

    @Override
    public SearchView getSearchView() {
        return mSearchView;
    }

    @Override
    public RadioGroup getRadioGroup() {
        return radio_group;
    }

    @Override
    public CheckBox getCheckBox() {
        return mCheckBox;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            SetUp();
            LogUtils.e("加载第一个Fragment");
        }
        isLoadFinish = true;
    }

}