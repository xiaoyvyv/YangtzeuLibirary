package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookRenewBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IBookRenewModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookRenewAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookRenewView;

public class BookRenewModel implements IBookRenewModel {
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
    public void getData(final AppCompatActivity activity, final IBookRenewView iBookRenewView, final BookRenewAdapter adapter, final int page) {
        OkHttp.do_Get(Urls.Url_Book_Renew_List +page, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                iBookRenewView.getSmartRefreshLayout().finishLoadMore();
                iBookRenewView.getProgressDialog().dismiss();
                LogUtils.i(response);

                List<BookRenewBean> bookRenewModel = new ArrayList<>();

                int old_index = adapter.getItemCount();

                Document document = Jsoup.parse(response);

                String bookListSize = document.select("span.disabled:nth-child(1)").text();
                String pageSize = document.select("span.disabled:nth-child(2)").text();
                LogUtils.i(bookListSize);
                LogUtils.i(pageSize);

                String message = document.select(".message").text();
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
                }else {
                    String pageSizeStr = pageSize.replace("总共", "").trim();
                    pageSizeStr = pageSizeStr.replace("页", "").trim();
                    int pageSizeInt = Integer.parseInt(pageSizeStr);

                    if (page > pageSizeInt) {
                        iBookRenewView.getSmartRefreshLayout().setEnableLoadMore(false);
                        ToastUtils.showShort(R.string.no_more);
                    } else {
                        Elements table = document.select("#contentTable");
                        Elements book_trs = table.select("#contentTable > tbody:nth-child(1) > tr");

                        for (int i = 1; i < book_trs.size(); i++) {
                            Element book_tr = book_trs.get(i);
                            Elements book_td = book_tr.select("tr td");
                            BookRenewBean bookAskBean = new BookRenewBean();
                            String TM = book_td.get(1).text();
                            String name = book_td.get(2).text();
                            String bookUrl = Urls.Url_Host + book_td.get(2).select("td a").attr("href");

                            String suoShu = book_td.get(3).text();
                            String lib = book_td.get(4).text();
                            String kind = book_td.get(5).text();
                            String timeIn = book_td.get(7).text();
                            String timeOut = book_td.get(8).text();
                            String times = book_td.get(9).text();

                            bookAskBean.setBookTM(TM);
                            bookAskBean.setTimeOut(timeOut);
                            bookAskBean.setTimeIn(timeIn);
                            bookAskBean.setBookName(name);
                            bookAskBean.setBookUrl(bookUrl);

                            bookAskBean.setSuoShu(suoShu);
                            bookAskBean.setLib(lib);
                            bookAskBean.setKind(kind);
                            bookAskBean.setTimes(times);

                            bookRenewModel.add(bookAskBean);

                        }

                        setAdapter(activity, iBookRenewView.getRecyclerView(), adapter, bookRenewModel, old_index);

                    }
                }

            }


            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    getData(activity, iBookRenewView, adapter, page);
            }
        });
    }

    @Override
    public void setAdapter(AppCompatActivity activity, RecyclerView recyclerView, BookRenewAdapter adapter, List<BookRenewBean> bookRenewBeans, int old_index) {
        adapter.setData(bookRenewBeans);
        int size = adapter.getItemCount();
        adapter.notifyItemRangeChanged(old_index,size);
    }

}
