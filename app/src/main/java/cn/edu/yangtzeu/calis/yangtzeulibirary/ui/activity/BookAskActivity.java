package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookAskPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.AskBookAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookAskView;

public class BookAskActivity extends BaseActivity implements IBookAskView {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    public int page =1;
    private AskBookAdapter askBookAdapter;
    private IBookAskPresenter iBookAskPresenter;
    private TextView SizeView;
    private ProgressDialog mProgressDialog;
    private String mNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setContentView(R.layout.activity_book_ask);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.mRecyclerView);
        smartRefreshLayout= findViewById(R.id.mSmartRefreshLayout);
        SizeView= findViewById(R.id.SizeView);

    }

    @Override
    public void initMethod() {
        mProgressDialog = MyUtils.getProgressDialog(this, getString(R.string.loading));
        mProgressDialog.show();



        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        askBookAdapter = new AskBookAdapter(this);
        recyclerView.setAdapter(askBookAdapter);

        iBookAskPresenter = new IBookAskPresenter(this);
        iBookAskPresenter.applyToolbar(this);
        iBookAskPresenter.getData(this, askBookAdapter, page);


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                iBookAskPresenter.getData(BookAskActivity.this, askBookAdapter, page);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("我的过期未还书目")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mNumber= SPUtils.getInstance("UserInfo").getString("Number", "");
                        askBookAdapter.clear();
                        askBookAdapter.notifyDataSetChanged();
                        page = 1;
                        smartRefreshLayout.setEnableLoadMore(true);
                        iBookAskPresenter.getData(BookAskActivity.this, askBookAdapter, page);
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
    public TextView BookListSizeView() {
        return SizeView;
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    @Override
    public String getNumber() {
        return mNumber;
    }
}
