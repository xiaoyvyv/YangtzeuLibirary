package cn.edu.yangtzeu.calis.yangtzeulibirary.model;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.custom.MyGridView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.HomeNewsBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.ToolItemBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IToolFragmentModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.CollectionActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.EmptyActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.IndexActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.LogActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.NewsActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.NewsListActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.ToolFragmentAdapter;

public class ToolFragmentModel implements IToolFragmentModel {

    private Class<?> activity[] = {EmptyActivity.class, IndexActivity.class, EmptyActivity.class,
            CollectionActivity.class, NewsListActivity.class, EmptyActivity.class,
            EmptyActivity.class, EmptyActivity.class, EmptyActivity.class};

    private String name[] = {"图书馆", "图书", "电子书",
            "书单", "资讯", "排行榜",
            "期刊", "听书", "视频"};
    private int bg[] = {R.drawable.bg_home_menu_lib, R.drawable.bg_home_menu_book, R.drawable.bg_home_menu_epb,
            R.drawable.bg_home_menu_action, R.drawable.bg_home_menu_news, R.drawable.bg_home_menu_ranking,
            R.drawable.bg_home_menu_periodical, R.drawable.bg_home_menu_listen, R.drawable.bg_home_menu_video,};


    @Override
    public void fitGridView(AppCompatActivity context, MyGridView gridView) {
        ToolFragmentAdapter toolFragmentAdapter = new ToolFragmentAdapter(context);
        for (int i = 0; i < activity.length; i++) {
            ToolItemBean toolItemBean = new ToolItemBean();
            toolItemBean.setBg(bg[i]);
            toolItemBean.setName(name[i]);
            toolItemBean.setContext(activity[i]);
            toolFragmentAdapter.addData(toolItemBean);
        }
        gridView.setAdapter(toolFragmentAdapter);
    }

    @Override
    public void fitBGABanner(final AppCompatActivity context, final BGABanner bgaBanner) {

        OkHttp.do_Get(Urls.Url_Home_News, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                HomeNewsBean homeNewsBean = gson.fromJson(response, HomeNewsBean.class);
                if (homeNewsBean.getStatus() != 200) {
                    fitBGABanner(context,bgaBanner);
                } else {
                    List<HomeNewsBean.DataBean> datas = homeNewsBean.getData();
                    List<String> trip = new ArrayList<>();
                    for (HomeNewsBean.DataBean name : datas) {
                        trip.add(name.getTitle());
                    }

                    bgaBanner.setData(datas, trip);
                    bgaBanner.setAdapter(new BGABanner.Adapter<ImageView,HomeNewsBean.DataBean>(){
                        @Override
                        public void fillBannerItem(BGABanner banner, ImageView itemView, HomeNewsBean.DataBean model, int position) {
                            itemView.setScaleType(ImageView.ScaleType.FIT_XY);
                            Glide.with(context)
                                    .load(Urls.Url_Image_Host +model.getImage())
                                    .into(itemView);
                        }
                    });

                    bgaBanner.setDelegate(new BGABanner.Delegate<ImageView, HomeNewsBean.DataBean>() {
                        @Override
                        public void onBannerItemClick(BGABanner banner, ImageView itemView, HomeNewsBean.DataBean model, int position) {
                            String url= Urls.Url_News_ID +model.getNewsId();
                            Intent intent = new Intent(context, NewsActivity.class);
                            intent.putExtra("from_url", url);
                            context.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                fitBGABanner(context,bgaBanner);
            }
        });
    }
}
