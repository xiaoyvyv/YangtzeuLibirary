package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

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
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.HistoryAdapter;


/**
 * Created by Administrator on 2018/3/6.
 */
public class HistoryFragment extends BaseFragment {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared=false;
    private boolean isLoadFinish = false;
    private View RootView;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private int which = 0;
    private int page=1;
    private HistoryAdapter historyAdapter;
    private String Url;

    public static HistoryFragment newInstance(int which) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("which", which);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            which = bundle.getInt("which", 0);
        }

        RootView = inflater.inflate(R.layout.fragment_history, container, false);
        FindView();
        isPrepared = true;
        lazyLoad();
        return RootView;

    }
    private void FindView() {
        smartRefreshLayout = RootView.findViewById(R.id.refresh);
        recyclerView = RootView.findViewById(R.id.recyclerView);
    }

    private void SetUp() {
        if (which == -1) {
            Url = Urls.Url_Current;
        }
        if (which == 0) {
            Url = Urls.Url_History;
        }
        if (which == 1) {
            Url = Urls.Url_History;
        }

        historyAdapter = new HistoryAdapter(getActivity());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(historyAdapter);


        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData(page);
                page++;
            }
        });

        smartRefreshLayout.autoLoadMore();
    }

    private void getData(final int page) {
        final int old_index = historyAdapter.getItemCount();
        OkHttp.do_Get(Url+page, new OnResultListener() {
            @Override

            public void onResponse(String response) {
                smartRefreshLayout.finishLoadMore();
                Document document = Jsoup.parse(response);
                Elements elements = document.select("div#content");
                String message = elements.select("span.message").text();
                if (!message.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.trip)
                            .setMessage(message)
                            .setPositiveButton("知道了", null)
                            .create();
                    alertDialog.show();
                    alertDialog.setCancelable(false);
                    alertDialog.setCanceledOnTouchOutside(false);
                } else {
                    List<CollectionBean> collectionBeans = new ArrayList<>();

                    Elements elements_size = elements.select("span.disabled");
                    String pageSize = elements_size.get(1).text();

                    String pageSizeStr = pageSize.replace("总共", "").trim();
                    pageSizeStr = pageSizeStr.replace("页", "").trim();
                    int pageSizeInt = Integer.parseInt(pageSizeStr);

                    if (page > pageSizeInt) {
                        ToastUtils.showShort(R.string.no_more);
                        smartRefreshLayout.setEnableLoadMore(false);
                    } else {
                        Elements table = elements.select("#contentTable");
                        Elements book_trs = table.select("#contentTable > tbody:nth-child(1) > tr");

                            for (int i = 1; i < book_trs.size(); i++) {
                                Element book_tr = book_trs.get(i);
                                Elements book_td = book_tr.select("tr td");
                                CollectionBean collectionBean = new CollectionBean();

                                //解析借阅历史页面
                                String caoZuo = book_td.get(0).text();
                                if (caoZuo.contains("借书")) {

                                    if (which == 1)
                                        continue;
                                }

                                if (caoZuo.contains("还书")) {
                                    if (which == 0)
                                        continue;
                                }

                                if (pageSizeInt > 1) {
                                    ToastUtils.showShort("请下拉显示更多");
                                }

                                String tiaoma = "";
                                String name = "";
                                String author = "";
                                String suoshu = "";
                                String lib = "";
                                String kind = "";
                                String collectionTime = "";
                                String url = "";
                                String backTime = "";

                                if (which ==-1) {
                                    tiaoma = book_td.get(0).select("td input").attr("value");
                                    name = book_td.get(1).text();
                                    url = Urls.Url_Host + book_td.get(1).select("td a").attr("href");
                                    author = book_td.get(2).text();
                                    suoshu = book_td.get(3).text();
                                    suoshu = suoshu.replace("更多...", "");
                                    lib = book_td.get(4).text();
                                    kind = book_td.get(5).text();
                                    collectionTime = book_td.get(6).text();
                                    backTime = book_td.get(7).text();
                                }

                                if (which == 0 || which == 1){
                                    tiaoma = book_td.get(0).select("td input").attr("value");
                                    name = book_td.get(2).text();
                                    url = Urls.Url_Host + book_td.get(2).select("td a").attr("href");
                                    author = book_td.get(3).text();
                                    suoshu = book_td.get(4).text();
                                    suoshu = suoshu.replace("更多...", "");
                                    lib = book_td.get(5).text();
                                    kind = book_td.get(6).text();
                                    collectionTime = book_td.get(7).text();
                                }

                                collectionBean.setBookSuoShu(suoshu);
                                collectionBean.setBookrecno(tiaoma);
                                collectionBean.setBooLib(lib);
                                collectionBean.setBookName(name);
                                collectionBean.setOnClickUrl(url);
                                collectionBean.setBookAuthor(author);
                                collectionBean.setBookChuBanShe(kind);
                                if (!backTime.isEmpty() && which == -1) {
                                    collectionBean.setCollectionTime(collectionTime + " 至 " + backTime);
                                } else {
                                    collectionBean.setCollectionTime(collectionTime);
                                }

                                collectionBeans.add(collectionBean);

                            }

                        historyAdapter.setData(collectionBeans);
                        historyAdapter.notifyItemRangeChanged(old_index, historyAdapter.getItemCount());

                    }
                }
            }
            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    getData(page);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            SetUp();
            LogUtils.e("加载第四个Fragment");
        }
        isLoadFinish = true;
    }
}