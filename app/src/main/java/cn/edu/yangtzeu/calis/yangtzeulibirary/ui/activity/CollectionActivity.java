package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookListPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.ICollectionPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.CollectionAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookListView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ICollectionView;

public class CollectionActivity extends BaseActivity implements ICollectionView {
    private String from_url;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    public int index=1;
    private CollectionAdapter collectionAdapter;
    private ICollectionPresenter iCollectionPresenter;
    private TextView SizeView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        from_url = getIntent().getStringExtra("from_url");
        if (from_url == null) {
            from_url = Urls.Url_My_Book_List;
        }
        setContentView(R.layout.activity_collection);
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

//        //设置RecyclerView 设置每次都滑到中间！
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        collectionAdapter = new CollectionAdapter(this);
        recyclerView.setAdapter(collectionAdapter);

        iCollectionPresenter = new ICollectionPresenter(this);
        iCollectionPresenter.applyToolbar(this);
        iCollectionPresenter.getData(this, collectionAdapter, index);


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index++;
                iCollectionPresenter.getData(CollectionActivity.this, collectionAdapter, index);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public TextView BookListSizeView() {
        return SizeView;
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    @Override
    public RefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }
}
