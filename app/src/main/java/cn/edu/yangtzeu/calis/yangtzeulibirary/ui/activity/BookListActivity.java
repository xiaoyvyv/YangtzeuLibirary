package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.TimeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.database.DatabaseUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.SearchHistoryBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookDetailsPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookListPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.NewsListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookDetailsView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookListView;

public class BookListActivity extends BaseActivity implements IBookListView {
    private String from_url;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    public int index=1;
    private BookListAdapter bookListAdapter;
    private IBookListPresenter iBookListPresenter;
    private String title;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        from_url = getIntent().getStringExtra("from_url");
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_book_list);
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
        toolbar.setTitle(title+"-搜索结果");


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        bookListAdapter = new BookListAdapter(this);
        recyclerView.setAdapter(bookListAdapter);
        iBookListPresenter = new IBookListPresenter(this);
        iBookListPresenter.applyToolbar(this);

        progressDialog = MyUtils.getProgressDialog(BookListActivity.this, getString(R.string.loading));
        progressDialog.show();
        iBookListPresenter.getData(this, bookListAdapter, index);


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index++;
                iBookListPresenter.getData(BookListActivity.this, bookListAdapter, index);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("收藏")
                .setIcon(R.mipmap.star)
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
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
    public String getFromUrl() {
        return from_url;
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
