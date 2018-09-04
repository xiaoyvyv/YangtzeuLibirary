package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.app.MyApplication;
import cn.edu.yangtzeu.calis.yangtzeulibirary.database.DatabaseUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.SearchHistoryBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IBookDetailsPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.ShareUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookDetailsView;

public class BookDetailsActivity extends BaseActivity implements IBookDetailsView {
    private Toolbar toolbar;
    private String from_url;
    private String isBn_Str;
    private String name_Str;
    private String author_Str;
    private String chuban_Str;
    private String kind_Str;
    private String year_Str;
    private String recno_Str;

    private String pages_Str = "查询中...";


    private TextView name;
    private TextView author;
    private TextView chuban;
    private TextView year;
    private TextView isbn;
    private TextView kind;
    private TextView pages;
    private TextView summaryView;

    private ImageView imageView;
    private ImageView right;

    private TextView lib_size;
    private RelativeLayout show_libs;
    private LinearLayout lib_container;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        from_url = getIntent().getStringExtra("from_url");
        isBn_Str = getIntent().getStringExtra("isbn");
        author_Str = getIntent().getStringExtra("author");
        year_Str = getIntent().getStringExtra("year");
        chuban_Str = getIntent().getStringExtra("source");
        name_Str = getIntent().getStringExtra("name");
        recno_Str = getIntent().getStringExtra("recno");
        kind_Str = getIntent().getStringExtra("kind");
        kind_Str = kind_Str.replace(",", "\n");
        kind_Str = kind_Str.replaceAll(" ", "");
        kind_Str = kind_Str.replaceAll(":", "：");

        setContentView(R.layout.activity_book_details);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);

        name = findViewById(R.id.name);
        author = findViewById(R.id.author);
        chuban = findViewById(R.id.chuban);
        year = findViewById(R.id.year);
        isbn = findViewById(R.id.isbn);
        kind = findViewById(R.id.kind);
        pages = findViewById(R.id.pages);
        summaryView = findViewById(R.id.summaryView);
        imageView = findViewById(R.id.book_image);
        right = findViewById(R.id.right);

        lib_size = findViewById(R.id.find_trip);
        show_libs = findViewById(R.id.where_lib);
        lib_container = findViewById(R.id.where_container);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initMethod() {
        mProgressDialog = MyUtils.getProgressDialog(this, getString(R.string.loading));
        mProgressDialog.show();

        long recno = Long.parseLong(recno_Str);
        SearchHistoryBean searchHistoryBean = new SearchHistoryBean();

        searchHistoryBean.setId(recno);
        searchHistoryBean.setUrl(from_url);
        searchHistoryBean.setIsbn(isBn_Str);
        searchHistoryBean.setTitle(name_Str);
        searchHistoryBean.setAuthor(author_Str);
        searchHistoryBean.setChuban(chuban_Str);
        searchHistoryBean.setBookKind(kind_Str);
        searchHistoryBean.setBookYear(year_Str);
        searchHistoryBean.setRecno(recno);
        searchHistoryBean.setTime(TimeUtils.getNowMills());
        searchHistoryBean.setActivityType(getLocalClassName());

        DatabaseUtils.getHelper(this, "searchHistory.db").save(searchHistoryBean);

        toolbar.setTitle(name_Str + "-详情");
        name.setText(name_Str);
        author.setText(author_Str);
        chuban.setText(chuban_Str);
        year.setText(year_Str);
        kind.setText(kind_Str);
        isbn.setText("ISBN：" + isBn_Str);
        pages.setText("页数：" + pages_Str);

        final IBookDetailsPresenter iBookDetailsPresenter = new IBookDetailsPresenter(this);
        iBookDetailsPresenter.applyToolbar(this);


        iBookDetailsPresenter.getBookInfo(this);
        iBookDetailsPresenter.getBookHtmlInfo(this);
        iBookDetailsPresenter.getBookWhereInfo(BookDetailsActivity.this);
        iBookDetailsPresenter.getBookWhereInfo(this);


        show_libs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible = ((ScrollView) lib_container.getParent()).getVisibility();
                if (visible == View.GONE) {
                    ((ScrollView) lib_container.getParent()).setVisibility(View.VISIBLE);
                    right.setImageDrawable(getResources().getDrawable(R.drawable.ic_bottom));
                } else {
                    ((ScrollView) lib_container.getParent()).setVisibility(View.GONE);
                    right.setImageDrawable(getResources().getDrawable(R.drawable.ic_right));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("收藏")
                .setVisible(true)
                .setIcon(R.drawable.ic_save)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (MyApplication.isLogin) {
                            OkHttp.do_Get(Urls.Url_Add_My_Book + recno_Str, new OnResultListener() {
                                @Override
                                public void onResponse(String response) {
                                    if (lib_size.getText().toString().contains("查询")) {
                                        ToastUtils.showShort("馆藏查询中");
                                    } else if (lib_size.getText().toString().equals("暂无馆藏")) {
                                        ToastUtils.showLong("此书目暂无馆藏");
                                    } else {
                                        AlertDialog alertDialog = new AlertDialog.Builder(BookDetailsActivity.this)
                                                .setTitle(R.string.trip)
                                                .setMessage(response)
                                                .setPositiveButton("知道了", null)
                                                .create();
                                        alertDialog.show();

                                    }
                                }

                                @Override
                                public void onFailure(String error) {
                                    ToastUtils.showShort(error);
                                }
                            });
                        } else {
                            android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(BookDetailsActivity.this)
                                    .setTitle(R.string.trip)
                                    .setMessage("请您先登录")
                                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ActivityUtils.finishAllActivities();
                                            ActivityUtils.startActivity(LogActivity.class);
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);
                        }
                        return true;
                    }
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("查看原文")
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        MyUtils.openUrl(BookDetailsActivity.this, from_url);
                        return true;
                    }
                });

        menu.add("复制链接")
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BaseActivity.UtilsTool.PutStringToClipboard(from_url);
                        ToastUtils.showShort(R.string.copy_right_link);
                        return true;
                    }
                });

        menu.add("复制ISBN")
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BaseActivity.UtilsTool.PutStringToClipboard(isBn_Str);
                        ToastUtils.showShort(R.string.copy_right_isbn);
                        return true;
                    }
                });

        menu.add("复制索书号")
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BaseActivity.UtilsTool.PutStringToClipboard(kind_Str);
                        ToastUtils.showShort(R.string.copy_right_book);
                        return true;
                    }
                });


        menu.add("分享此书目")
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ShareUtils.shareMsg(BookDetailsActivity.this, name_Str, from_url, null);
                        return true;
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
    public String getIsbn() {
        return isBn_Str;
    }

    @Override
    public String getRecno() {
        return recno_Str;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public TextView getPagesView() {
        return pages;
    }

    @Override
    public TextView getSummaryView() {
        return summaryView;
    }

    @Override
    public TextView getLibSizeView() {
        return lib_size;
    }

    @Override
    public RelativeLayout getShowContainerView() {
        return show_libs;
    }

    @Override
    public LinearLayout getLibContainer() {
        return lib_container;
    }

    @Override
    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }
}
