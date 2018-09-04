package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.ISearchHistoryPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.SearchHistoryAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ISearchHistoryView;

public class SearchHistoryActivity extends BaseActivity implements ISearchHistoryView{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayouts;
    private ISearchHistoryPresenter iSearchHistoryPresenter;
    private SearchHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getString("test") != null) {
            Log.i("打印", savedInstanceState.getString("test"));
        }



        setContentView(R.layout.activity_search_history);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayouts = findViewById(R.id.swipeRefreshLayout);
    }

    @Override
    public void initMethod() {
        adapter = new SearchHistoryAdapter(this);
        recyclerView.setAdapter(adapter);

        iSearchHistoryPresenter = new ISearchHistoryPresenter(this);
        iSearchHistoryPresenter.applyToolbar(this);

        refreshLayouts.setEnableLoadMore(false);
        refreshLayouts.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                iSearchHistoryPresenter.getHistory(SearchHistoryActivity.this, adapter);
            }
        });
        refreshLayouts.autoRefresh();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder) {
                // 拖拽的标记，这里允许上下左右四个方向
                int dragFlags = ItemTouchHelper.START;
                // | ItemTouchHelper.END | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT;   //只允许从右向左侧滑

                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                //onItemMove是接口方法
                adapter.onItemChange(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //onItemDissmiss是接口方法
                adapter.onItemRemove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return refreshLayouts;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("帮助")
                .setIcon(R.mipmap.ic_launcher)
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog alertDialog = new AlertDialog.Builder(SearchHistoryActivity.this)
                                .setTitle(R.string.trip)
                                .setMessage("左滑Item可以清除浏览记录\n长按Item可以复制浏览链接")
                                .setPositiveButton("知道了", null)
                                .create();
                        alertDialog.show();
                        return false;
                    }
                });

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("test", "测试保存数据");
    }
}
