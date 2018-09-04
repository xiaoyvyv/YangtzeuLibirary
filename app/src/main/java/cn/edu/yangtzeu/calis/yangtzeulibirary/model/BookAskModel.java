package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookAskBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.CollectionBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IBookAskModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.AskBookAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookAskView;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BookAskModel implements IBookAskModel {
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
    public void getData(final AppCompatActivity activity, final IBookAskView iBookAskView, final AskBookAdapter askBookAdapter, final int page) {
        RequestBody formBody = new FormBody.Builder()
                .add("page", String.valueOf(page))
                .add("searchType", "rdid")
                .add("rows", "10")
                .add("prevPage", "1")
                .add("searchValue",iBookAskView.getNumber())
                .build();

        Request request=new Request.Builder()
                .post(formBody)
                .url(Urls.Url_Book_Back)
                .build();
        OkHttp.do_Post(request, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                iBookAskView.getSmartRefreshLayout().finishLoadMore();
                iBookAskView.getProgressDialog().dismiss();
                LogUtils.i(response);

                List<BookAskBean> bookAskBeans = new ArrayList<>();

                int old_index = askBookAdapter.getItemCount();

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
                    TextView textView = iBookAskView.BookListSizeView();
                    textView.setText(bookListSize);

                    String pageSizeStr = pageSize.replace("总共", "").trim();
                    pageSizeStr = pageSizeStr.replace("页", "").trim();
                    pageSizeStr = pageSizeStr.replace(",", "").trim();

                    int pageSizeInt = Integer.parseInt(pageSizeStr);
                    if (page > pageSizeInt) {
                        ToastUtils.showShort(R.string.no_more);
                    } else {
                        Elements table = document.select("#contentTable");
                        Elements book_trs = table.select("#contentTable > tbody:nth-child(1) > tr");

                        for (int i = 1; i < book_trs.size(); i++) {
                            Element book_tr = book_trs.get(i);
                            Elements book_td = book_tr.select("tr td");
                            BookAskBean bookAskBean = new BookAskBean();

                            String user = book_td.get(0).text();
                            String suoshu = book_td.get(1).text();
                            String name = book_td.get(2).text();
                            String bookUrl = Urls.Url_Host + book_td.get(2).select("td a").attr("href");
                            String timeIn = book_td.get(3).text();
                            String timeOut = book_td.get(4).text();

                            bookAskBean.setUser(user);
                            bookAskBean.setBookTM(suoshu);
                            bookAskBean.setTimeOut(timeOut);
                            bookAskBean.setTimeIn(timeIn);
                            bookAskBean.setBookName(name);
                            bookAskBean.setBookUrl(bookUrl);


                            bookAskBeans.add(bookAskBean);

                        }

                        setAdapter(activity, iBookAskView.getRecyclerView(), askBookAdapter, bookAskBeans, old_index);

                    }
                }

            }


            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    getData(activity, iBookAskView, askBookAdapter, page);
            }
        });

    }

    @Override
    public void setAdapter(AppCompatActivity activity, RecyclerView recyclerView, AskBookAdapter askBookAdapter, List<BookAskBean> bookAskBeans, int old_index) {
        askBookAdapter.setData(bookAskBeans);
        int size = askBookAdapter.getItemCount();
        askBookAdapter.notifyItemRangeChanged(old_index,size);
    }
}
