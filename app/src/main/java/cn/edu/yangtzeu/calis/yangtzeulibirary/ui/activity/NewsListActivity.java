package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.INewsListPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.NewsListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.INewsListView;

public class NewsListActivity extends BaseActivity implements INewsListView {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private SmartRefreshLayout smartRefreshLayout;
    public int index = 1;
    private INewsListPresenter iNewsListPresenter;
    private NewsListAdapter newsListAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_news_list);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        recyclerView = findViewById(R.id.mRecyclerView);
        smartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void initMethod() {
        progressDialog = MyUtils.getProgressDialog(this, getString(R.string.loading));
        progressDialog.show();

//        //设置RecyclerView 设置每次都滑到中间！
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        newsListAdapter = new NewsListAdapter(this);
        recyclerView.setAdapter(newsListAdapter);

        iNewsListPresenter = new INewsListPresenter(this);
        iNewsListPresenter.applyToolbar(this);

        iNewsListPresenter.getData(this, newsListAdapter,index);

        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index++;
                iNewsListPresenter.getData(NewsListActivity.this,newsListAdapter,index);
            }
        });
    }


    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    @Override
    public RefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

}
