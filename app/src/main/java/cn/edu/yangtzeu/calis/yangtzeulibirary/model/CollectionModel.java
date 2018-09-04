package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.CollectionBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.ICollectionModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.CollectionAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.ICollectionView;

public class CollectionModel implements ICollectionModel {
    @Override
    public void applyToolbar(final AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    public void getData(final AppCompatActivity activity, final ICollectionView iCollectionView, final CollectionAdapter collectionAdapter, final String url, final int page) {
        OkHttp.do_Get(iCollectionView.getFromUrl()+page, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                iCollectionView.getSmartRefreshLayout().finishLoadMore();
                iCollectionView.getProgressDialog().dismiss();
                int old_index = collectionAdapter.getItemCount();

                Document document = Jsoup.parse(response);
                Elements elements = document.select("div#content");
                String message = elements.select("span.message").text();
                if (!message.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
                            .setTitle(R.string.trip)
                            .setMessage(message)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.finish();
                                }
                            })
                            .create();
                    alertDialog.show();
                    alertDialog.setCancelable(false);
                    alertDialog.setCanceledOnTouchOutside(false);
                } else {
                    List<CollectionBean> collectionBeans = new ArrayList<>();

                    Elements elements_size = elements.select("span.disabled");
                    String bookListSize = elements_size.get(0).text();
                    String pageSize = elements_size.get(1).text();

                    TextView textView = iCollectionView.BookListSizeView();
                    textView.setText(bookListSize);

                    String pageSizeStr = pageSize.replace("总共", "").trim();
                    pageSizeStr = pageSizeStr.replace("页", "").trim();
                    int pageSizeInt = Integer.parseInt(pageSizeStr);

                    if (page > pageSizeInt) {
                        ToastUtils.showShort(R.string.no_more);
                    } else {
                        Elements table = elements.select("#contentTable");
                        Elements book_trs = table.select("#contentTable > tbody:nth-child(1) > tr");

                        for (int i = 1; i < book_trs.size(); i++) {
                            Element book_tr = book_trs.get(i);
                            Elements book_td = book_tr.select("tr td");
                            CollectionBean collectionBean = new CollectionBean();

                            if (book_td.size() <= 8) {
                                //解析借阅历史页面
                                String caoZuo = book_td.get(0).text();

                                String tiaoma = book_td.get(0).select("td input").attr("value");
                                String name = book_td.get(2).text();
                                String url = Urls.Url_Host + book_td.get(2).select("td a").attr("href");
                                String author = book_td.get(3).text();
                                String suoshu = book_td.get(4).text();
                                suoshu = suoshu.replace("更多...", "");
                                String lib = book_td.get(5).text();
                                String kind = book_td.get(6).text();
                                String collectionTime = book_td.get(7).text();


                                collectionBean.setBookSuoShu(suoshu);
                                collectionBean.setBookrecno(tiaoma);
                                collectionBean.setBooLib(lib);
                                collectionBean.setBookName(name);
                                collectionBean.setOnClickUrl(url);
                                collectionBean.setBookAuthor(author);
                                collectionBean.setBookChuBanShe(kind);
                                collectionBean.setCollectionTime(collectionTime);

                                collectionBeans.add(collectionBean);
                            }else {
                                //解析书单页面
                                String tiaoma = book_td.get(0).select("td input").attr("value");
                                String lib = book_td.get(1).text();
                                String suoshu = book_td.get(2).text();
                                suoshu = suoshu.replace("更多...", "");

                                String name = book_td.get(3).text();
                                String url = Urls.Url_Host + book_td.get(3).select("td a").attr("href");
                                String author = book_td.get(4).text();
                                String chuban = book_td.get(5).text();
                                String chubanDate = book_td.get(6).text();
                                String collectionTime = book_td.get(7).text();
                                String boosSaveSize = book_td.get(8).text();

                                collectionBean.setBookSuoShu(suoshu);
                                collectionBean.setBookrecno(tiaoma);
                                collectionBean.setBooLib(lib);
                                collectionBean.setBookName(name);
                                collectionBean.setOnClickUrl(url);
                                collectionBean.setBookAuthor(author);
                                collectionBean.setBookChuBanShe(chuban);
                                collectionBean.setBookDate(chubanDate);
                                collectionBean.setBookSize(boosSaveSize);
                                collectionBean.setCollectionTime(collectionTime);

                                collectionBeans.add(collectionBean);
                                LogUtils.i(suoshu, tiaoma, lib, name, url, author, chuban, chubanDate, boosSaveSize, collectionTime);
                            }
                        }

                        setAdapter(activity, iCollectionView.getRecyclerView(), collectionAdapter, collectionBeans, old_index);

                    }
                }
            }
            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    getData(activity, iCollectionView, collectionAdapter, url, page);
            }
        });

    }

    @Override
    public void setAdapter(AppCompatActivity activity, RecyclerView recyclerView, CollectionAdapter collectionAdapter, List<CollectionBean> collectionBeans, int old_index) {
        collectionAdapter.setData(collectionBeans);
        int size = collectionAdapter.getItemCount();
        collectionAdapter.notifyItemRangeChanged(old_index,size);
    }
}
