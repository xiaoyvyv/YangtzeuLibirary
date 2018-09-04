package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.custom.FallingView.FallObject;
import net.custom.FallingView.FallingView;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.AppBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookListPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.AppListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import tyrantgit.widget.HeartLayout;


public class AppListActivity extends BaseActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private AppListAdapter appListAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_app_list);
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppListActivity.this.finish();
            }
        });
    }

    @Override
    public void initViews() {
        toolbar =  findViewById(R.id.about_toolbar);
        mRecyclerView =  findViewById(R.id.mRecyclerView);
    }


    @Override
    public void initMethod() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        appListAdapter = new AppListAdapter(this);
        mRecyclerView.setAdapter(appListAdapter);


        progressDialog = MyUtils.getProgressDialog(AppListActivity.this, getString(R.string.loading));
        progressDialog.show();

        OkHttp.do_Get(Urls.Url_MyApp, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AppBean bean=gson.fromJson(response, AppBean.class);
                appListAdapter.setData(bean);
                appListAdapter.notifyItemRangeChanged(0, appListAdapter.getItemCount());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(String error) {
                ToastUtils.showShort(R.string.load_error);
                progressDialog.dismiss();
            }
        });

    }
}

