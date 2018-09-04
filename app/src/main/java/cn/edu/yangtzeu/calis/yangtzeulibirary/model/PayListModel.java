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
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.PayListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IBookRenewModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IPayListModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookRenewAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.PayListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookRenewView;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IPayListView;

public class PayListModel implements IPayListModel {
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
    public void getData(final AppCompatActivity activity, final IPayListView view, final PayListAdapter adapter, final int page) {
        OkHttp.do_Get(Urls.Url_PayList +page, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                view.getSmartRefreshLayout().finishLoadMore();
                view.getProgressDialog().dismiss();
                LogUtils.i(response);

                List<PayListBean> payListBeans = new ArrayList<>();

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
                        view.getSmartRefreshLayout().setEnableLoadMore(false);
                        ToastUtils.showShort(R.string.no_more);
                    } else {
                        Elements table = document.select("#contentTable");
                        Elements book_trs = table.select("#contentTable > tbody:nth-child(1) > tr");

                        for (int i = 1; i < book_trs.size(); i++) {
                            Element book_tr = book_trs.get(i);
                            Elements book_td = book_tr.select("tr td");
                            PayListBean payListBean = new PayListBean();


                            String kind = book_td.get(0).text();
                            String money = book_td.get(1).text();
                            String time = book_td.get(2).text();
                            String people = book_td.get(3).text();
                            String lib = book_td.get(4).text();
                            String lib_where = book_td.get(6).text();
                            String bookTm = book_td.get(7).text();
                            String bookNumber = book_td.get(8).text();
                            String payState = book_td.get(9).text();

                            payListBean.setKind(kind);
                            payListBean.setMoney(money);
                            payListBean.setTime(time);
                            payListBean.setPeople(people);
                            payListBean.setLib(lib);
                            payListBean.setLib_where(lib_where);
                            payListBean.setBookTM(bookTm);
                            payListBean.setBookNumber(bookNumber);
                            payListBean.setPayState(payState);

                            payListBeans.add(payListBean);
                        }

                        setAdapter(activity, view.getRecyclerView(), adapter, payListBeans, old_index);

                    }
                }

            }


            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    getData(activity, view, adapter, page);
            }
        });
    }

    @Override
    public void setAdapter(AppCompatActivity activity, RecyclerView recyclerView, PayListAdapter adapter, List<PayListBean> listBeans, int old_index) {
        adapter.setData(listBeans);
        int size = adapter.getItemCount();
        adapter.notifyItemRangeChanged(old_index,size);
    }


}
