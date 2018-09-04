package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookAskPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookRenewPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookRenewAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookRenewView;

public class BookRenewActivity extends BaseActivity implements IBookRenewView {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    public int page =1;
    private BookRenewAdapter bookRenewAdapter;
    private IBookRenewPresenter iBookRenewPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book_renew);
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
        bookRenewAdapter = new BookRenewAdapter(this);
        recyclerView.setAdapter(bookRenewAdapter);

        iBookRenewPresenter = new IBookRenewPresenter(this);
        iBookRenewPresenter.applyToolbar(this);
        iBookRenewPresenter.getData(this, bookRenewAdapter, page);


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                iBookRenewPresenter.getData(BookRenewActivity.this, bookRenewAdapter, page);
            }
        });


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
