package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import net.custom.FallingView.FallObject;
import net.custom.FallingView.FallingView;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import tyrantgit.widget.HeartLayout;


public class AboutActivity extends BaseActivity {
    private Toolbar toolbar;
    private ImageView QQImageView;
    private ImageView WXImageView;
    private ImageView Me_Img;
    private ImageView BG_Img;
    private HeartLayout mHeartLayout;
    private Timer mTimer = new Timer();
    private Random mRandom = new Random();
    private FallingView fallingView;
    private ImageView image_rotate;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_about);
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();
            }
        });
    }

    @Override
    public void initViews() {
        toolbar =  findViewById(R.id.about_toolbar);
        mHeartLayout =  findViewById(R.id.heart_layout);
        QQImageView = findViewById(R.id.QQ_Img);
        WXImageView =  findViewById(R.id.WX_Img);
        Me_Img =  findViewById(R.id.Me_Img);
        BG_Img = findViewById(R.id.BG_Img);
        image_rotate =findViewById(R.id.image_rotate);
        fallingView =  findViewById(R.id.fallingView);
    }


    @Override
    public void initMethod() {

        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(Urls.Url_Music);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });

        Animation circle_anim = AnimationUtils.loadAnimation(AboutActivity.this, R.anim.anim_round_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);
        image_rotate.startAnimation(circle_anim);  //开始动画
        image_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            }
        });


        //初始化一个雪花样式的fallObject
        FallObject.Builder builder = new FallObject.Builder(getResources().getDrawable(R.mipmap.huaban));
        FallObject fallObject = builder
                .setSpeed(4, true)
                .setSize(60, 60, true)
                //设置风力等级、方向以及随机因素，
                // level：风力等级
                // isWindRandom：物体初始风向和风力大小比例是否随机
                // isWindChange：在物体下落过程中风的风向和风力是否会产生随机变化
                .setWind(100, false, true)
                .build();
        fallingView.addFallObject(fallObject, 50);//添加50个下落物体对象

        Glide.with(this)
                .load(Urls.Url_QQ)
                .asBitmap()
                .into(QQImageView);
        Glide.with(this)
                .load(Urls.Url_Wx)
                .asBitmap()
                .into(WXImageView);

        Glide.with(this)
                .load("http://q.qlogo.cn/headimg_dl?bs=qq&dst_uin=" + 1223414335 + "&src_uin=www.qqjike.com&fid=blog&spec=100")
                .asBitmap()
                .into(Me_Img);

        String[] list = {"http://p04pfl7p6.bkt.clouddn.com/App/Me_bg1.jpg",
                "http://p04pfl7p6.bkt.clouddn.com/App/Me_bg2.jpg",
                "http://p04pfl7p6.bkt.clouddn.com/App/Me_bg3.jpg",
                "http://p04pfl7p6.bkt.clouddn.com/App/Me_bg4.jpg",
                "http://p04pfl7p6.bkt.clouddn.com/App/Me_bg5.jpg"};
        int rand = Integer.parseInt(UtilsTool.GetRand(list.length));
        Glide.with(this)
                .load(list[rand])
                .asBitmap()
                .into(BG_Img);

        Me_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.openUrl(AboutActivity.this,getResources().getString(R.string.Me_QQ));
            }
        });
        QQImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ImageActivity.class);
                intent.putExtra("from_url", Urls.Url_QQ);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        WXImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, ImageActivity.class);
                intent.putExtra("from_url", Urls.Url_Wx);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHeartLayout.clearAnimation();
        mTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new mTimeTask(), 500, 200);
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    @Override
    public void onBackPressed() {
        AboutActivity.this.finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer=null;
        }
        mTimer.cancel();
    }

    private class mTimeTask extends TimerTask {
        @Override
        public void run() {
            mHeartLayout.post(new Runnable() {
                @Override
                public void run() {
                    mHeartLayout.addHeart(randomColor());
                }
            });
        }
    }
}

