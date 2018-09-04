package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.ItemBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IMainFragment2Model;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.Fragment2Adapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainFragment2View;
import okhttp3.FormBody;
import okhttp3.Request;

public class MainFragment2Model implements IMainFragment2Model {
    private String __VIEWSTATEGENERATOR = "AB3BDE6C";
    private String __EVENTTARGET = "ctl00$ContentPlaceHolder1$GridView1";
    private String __EVENTARGUMENT = "Page$Next";
    private String __VIEWSTATE = "";
    private String __EVENTVALIDATION = "";

    @Override
    public void getBanner(final Context context, final IMainFragment2View iMainFragment2View) {
        OkHttp.do_Get(Urls.Url_Home, new OnResultListener() {
            @Override
            public void onResponse(String response) {

                final List<String> content = new ArrayList<>();
                List<String>  image = new ArrayList<>();
                List<String>  trips = new ArrayList<>();
                Document document = Jsoup.parse(response);
                Elements Tds_A = document.select("#demo1 > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) td");
                for (Element td : Tds_A) {
                    String trip=td.select("td a").attr("title");
                    String  content_url=Urls.Url_Home+td.select("td a").attr("href");
                    content.add(content_url);
                    image.add(content_url);
                    trips.add(trip);
                }
                iMainFragment2View.getBGABanner().setData(image, trips);
                iMainFragment2View.getBGABanner().setAdapter(new BGABanner.Adapter<ImageView, String>() {
                    @Override
                    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                        itemView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(context)
                                .load(model)
                                .into(itemView);
                    }
                });
                iMainFragment2View.getBGABanner().setDelegate(new BGABanner.Delegate<ImageView, String>() {
                    @Override
                    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                        MyUtils.openUrl(context, content.get(position));
                    }
                });

                iMainFragment2View.getProgressDialog().dismiss();
            }
            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                getBanner(context, iMainFragment2View);
            }
        });

    }

    @Override
    public void getNotice(final Context context, final Fragment2Adapter adapter, final IMainFragment2View iMainFragment2View, final boolean isNext) {

        final int OldPosition = adapter.getItemCount();
        OnResultListener onResultListener = new OnResultListener() {
            @Override
            public void onResponse(String response) {
                iMainFragment2View.getProgressDialog().dismiss();
                iMainFragment2View.getSmartRefreshLayout().finishLoadMore();

                List<ItemBean> itemBeans = new ArrayList<>();
                Document document = Jsoup.parse(response);
                Elements elements = document.select("#ctl00_ContentPlaceHolder1_GridView1 > tbody:nth-child(1) tr");

                __VIEWSTATE = document.select("input#__VIEWSTATE").attr("value");
                __VIEWSTATEGENERATOR = document.select("input#__VIEWSTATEGENERATOR").attr("value");
                __EVENTVALIDATION = document.select("input#__EVENTVALIDATION").attr("value");

                LogUtils.i(elements);
                for (int i = 0; i < elements.size()-2; i++) {
                    ItemBean itemBean = new ItemBean();
                    Elements elements1 = elements.get(i).select("tr td");

                    if (elements1.size() == 4) {
                        String title = elements1.get(1).text();
                        String url = Urls.Url_Home + elements1.get(1).select("td a").attr("href");
                        String time = elements1.get(3).text();
                        String image = elements1.get(2).select("td img").attr("src");
                        boolean is_new = ObjectUtils.isNotEmpty(image);

                        itemBean.setTitle(title);
                        itemBean.setTime(time);
                        itemBean.setNew(is_new);
                        itemBean.setUrl(url);
                        itemBeans.add(itemBean);
                    }

                }
                adapter.setData(itemBeans);
                adapter.notifyItemRangeChanged(OldPosition, adapter.getItemCount());

            }

            @Override
            public void onFailure(String error) {
                iMainFragment2View.getSmartRefreshLayout().finishLoadMore();
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL)) {
                    getNotice(context, adapter, iMainFragment2View, isNext);
                }
            }
        };

        FormBody formBody = new FormBody.Builder()
                .add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR)
                .add("__EVENTTARGET", __EVENTTARGET)
                .add("__EVENTARGUMENT", __EVENTARGUMENT)
                .add("__VIEWSTATE", __VIEWSTATE)
                .add("__EVENTVALIDATION", __EVENTVALIDATION)
                .add("__VIEWSTATEENCRYPTED", "")
                .build();
        Request request = new Request.Builder()
                .url(iMainFragment2View.getUrl())
                .post(formBody)
                .build();

        if (isNext) {
            LogUtils.i("表单请求",__EVENTARGUMENT,__EVENTTARGET,__VIEWSTATEGENERATOR,__VIEWSTATE,__EVENTVALIDATION);
            OkHttp.do_Post(request, onResultListener);
        } else {
            OkHttp.do_Get(iMainFragment2View.getUrl(), onResultListener);
        }

        iMainFragment2View.getSmartRefreshLayout().setEnableRefresh(false);
        iMainFragment2View.getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getNotice(context, adapter,iMainFragment2View, true);
            }
        });

    }
}
