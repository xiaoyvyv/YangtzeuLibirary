package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IBookListModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.BookListAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookListView;

public class BookListModel implements IBookListModel {
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
    public void getData(final AppCompatActivity activity, final IBookListView iBookListView, final BookListAdapter bookListAdapter, final String url, final int page) {
        OkHttp.do_Get(url + page, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                iBookListView.getSmartRefreshLayout().finishLoadMore();
                iBookListView.getProgressDialog().dismiss();
                try {
                    Document document = Jsoup.parse(response);
                    Elements resultTile = document.select("div#resultTile");
                    Elements disabled = resultTile.select("span.disabled");

                    String pageSize = disabled.get(0).text();

                    String Str = resultTile.toString();
                    Document Title = Jsoup.parse(Str);
                    Elements resultTable = Title.select("div.resultList table tbody tr");

                    if (resultTable.size() != 0) {
                        int AdapterSizeOld = bookListAdapter.getItemCount();
                        List<BookListBean> bookListBeans = new ArrayList<>();

                        for (int i = 0; i <resultTable.size() ; i++) {
                            //解析搜索结果
                            Elements bookmetaTD = resultTable.get(i).select("tr td.bookmetaTD");
                            Elements bookmeta = bookmetaTD.select("td div.bookmeta div");

                            //解析封面
                            Elements coverTD = resultTable.get(i).select("tr td.coverTD");
                            String bookisBn = coverTD.select("td a img.bookcover_img").attr("isbn");

                            String bookrecno =coverTD.select("td a img.bookcover_img").attr("bookrecno");

                            final String bookUrl = "http://calis.yangtzeu.edu.cn:8000/opac/" + bookmeta.get(0).select("div span a").attr("href");
                            String bookName = bookmeta.get(0).select("div span a").text();

                            String bookPi = "http://calis.yangtzeu.edu.cn:8000" + bookmeta.get(0).select("div img").get(1).attr("src");

                            LogUtils.i(bookPi);

                            String bookAuthor = bookmeta.get(1).text();
                            String bookChuBanShe = "出版社：" + bookmeta.get(2).select("div a").text();
                            String Date = bookmeta.get(2).text();
                            String bookDate = "出版日期：" + Date.substring(Date.lastIndexOf(" ", Date.length())) + "年";
                            String bookKind = bookmeta.get(3).text();



                            BookListBean bookListBean = new BookListBean();
                            bookListBean.setPageSize(pageSize);
                            bookListBean.setOnClickUrl(bookUrl);
                            bookListBean.setBookIsbn(bookisBn);
                            bookListBean.setBookrecno (bookrecno);
                            bookListBean.setBookNameText(bookName);
                            bookListBean.setBookAuthorText(bookAuthor);
                            bookListBean.setBookChuBanSheText (bookChuBanShe);
                            bookListBean.setBookDateText (bookDate);
                            bookListBean.setBookKindText(bookKind);
                            bookListBean.setBookPi(bookPi);

                            bookListBeans.add(bookListBean);
                        }

                        setAdapter(bookListAdapter,bookListBeans,AdapterSizeOld);
                    } else {
                        ToastUtils.showShort(R.string.no_more);
                    }
                } catch (Exception e) {
                    ToastUtils.showShort(R.string.no_data);
                }
            }
            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                getData(activity, iBookListView, bookListAdapter, url, page);
            }
        });
    }


    @Override
    public void setAdapter(BookListAdapter bookListAdapter,List<BookListBean> bookListBeans, int old_index) {
        bookListAdapter.setData(bookListBeans);
        bookListAdapter.notifyItemRangeChanged(old_index,bookListAdapter.getItemCount());
    }
}
