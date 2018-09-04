package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IPayListPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.PayListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IPayListView;

public class PayListActivity extends BaseActivity implements IPayListView{
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    public int page =1;
    private PayListAdapter payListAdapter;
    private IPayListPresenter iPayListPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay_list);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.mRecyclerView);
        smartRefreshLayout= findViewById(R.id.mSmartRefreshLayout);

    }

    @Override
    public void initMethod() {
        mProgressDialog = MyUtils.getProgressDialog(this, getString(R.string.loading));
        mProgressDialog.show();


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        payListAdapter = new PayListAdapter(this);
        recyclerView.setAdapter(payListAdapter);

        iPayListPresenter = new IPayListPresenter(this);
        iPayListPresenter.applyToolbar(this);
        iPayListPresenter.getData(this, payListAdapter, page);


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                iPayListPresenter.getData(PayListActivity.this, payListAdapter, page);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("阅读原文")
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        MyUtils.openUrl(PayListActivity.this, Urls.Url_PayList + page);
                        return false;
                    }
                });
        return true;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }


    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }
}
