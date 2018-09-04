package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;

import net.custom.TouchImageView;
import net.custom.WebViewProgressBar;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IImagePresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IImageView;


public class ImageActivity extends BaseActivity implements IImageView {
    private TouchImageView TouchImage;
    private String from_url;
    private Toolbar toolbar;
    private ImageView point_menu;
    private int i = 0;
    private WebViewProgressBar progress;
    private AppBarLayout app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenUtils.setFullScreen(this);
        setContentView(R.layout.activity_image);
        from_url = getIntent().getStringExtra("from_url");
        LogUtils.i("接收图片地址", from_url);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        TouchImage = findViewById(R.id.TouchImageView);
        app_bar = findViewById(R.id.app_bar);
        point_menu = findViewById(R.id.img_menu);
    }

    @Override
    public void initMethod(){
        IImagePresenter iImagePresenter = new IImagePresenter(this);
        iImagePresenter.fitImageView(this);
        iImagePresenter.applyToolbar(this, toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.change:
                i = i + 1;
                int which = i % 3;
                switch (which) {
                    case 0:
                        app_bar.setBackgroundColor(getResources().getColor(R.color.translate));
                        TouchImage.setBackgroundColor(Color.BLACK);
                        break;
                    case 1:
                        app_bar.setBackgroundColor(Color.parseColor("#50000000"));
                        TouchImage.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        app_bar.setBackgroundColor(Color.parseColor("#50000000"));
                        TouchImage.setBackgroundColor(Color.GRAY);
                        break;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public TouchImageView getTouchImageView() {
        return TouchImage;
    }

    @Override
    public WebViewProgressBar getWebViewProgressBar() {
        return progress;
    }

    @Override
    public ImageView getPointMenu() {
        return point_menu;
    }


    @Override
    public String getFromUrl() {
        return from_url;
    }
}
