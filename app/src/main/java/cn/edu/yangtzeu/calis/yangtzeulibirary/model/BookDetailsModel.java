package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookWhereBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.DouBanBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IBookDetailsModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IBookDetailsView;

public class BookDetailsModel implements IBookDetailsModel {
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
    public void getBookInfo(final AppCompatActivity bookDetailsActivity, final IBookDetailsView view) {

        Glide.with(bookDetailsActivity).load(Urls.Url_Book_Holder).into(view.getImageView());

        String url = "https://api.douban.com/v2/book/isbn/:" + view.getIsbn();
        OkHttp.do_Get(url, new OnResultListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                view.getProgressDialog().dismiss();

                Gson gson = new Gson();
                DouBanBean douBanBean = gson.fromJson(response, DouBanBean.class);

                final String pic = douBanBean.getImage();
                if (!ObjectUtils.isEmpty(pic)) {
                    Glide.with(bookDetailsActivity).load(pic).into(view.getImageView());
                    view.getImageView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyUtils.openImage(bookDetailsActivity,pic);
                        }
                    });
                } else {
                    MyUtils.findBookPicture(bookDetailsActivity, view.getIsbn(), view.getImageView(), true);
                }

                String pages = douBanBean.getPages();
                view.getPagesView().setText(bookDetailsActivity.getString(R.string.page) + pages);

                String summary = douBanBean.getSummary();
                if (!ObjectUtils.isEmpty(summary)) {
                    view.getSummaryView().setText("\u3000\u3000" + summary);
                } else {
                    view.getSummaryView().setText("书目暂无简介");
                }
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET)&&!error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL)) {
                    getBookInfo(bookDetailsActivity, view);
                }
            }
        });

    }

    @Override
    public void getBookHtmlInfo(final AppCompatActivity bookDetailsActivity, final IBookDetailsView view) {
        OkHttp.do_Get(view.getFromUrl(), new OnResultListener() {
            @Override
            public void onResponse(String response) {


            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL)) {
                    getBookHtmlInfo(bookDetailsActivity, view);
                }
            }
        });
    }


    @Override
    public void getBookWhereInfo(final AppCompatActivity bookDetailsActivity, final IBookDetailsView view) {
        String url = Urls.Url_Book_Where +view.getRecno();
        OkHttp.do_Get(url, new OnResultListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    BookWhereBean libBookState = gson.fromJson(response, BookWhereBean.class);

                    if (libBookState.getHoldingList().size() != 0) {
                        view.getLibContainer().removeAllViews();
                        view.getLibSizeView().setText("馆藏数目：" + libBookState.getHoldingList().size());
                    } else {
                        view.getLibSizeView().setTextColor(Color.parseColor("#ff0000"));
                        view.getLibSizeView().setText("暂无馆藏");
                        view.getShowContainerView().setClickable(false);
                    }

                    for (int i = 0; i < libBookState.getHoldingList().size(); i++) {

                        String callno = libBookState.getHoldingList().get(i).getCallno();

                        String barcode = libBookState.getHoldingList().get(i).getBarcode();

                        String BookState = libBookState.getHoldingList().get(i).getState();
                        BookState = MyUtils.GetBookStateInfo(bookDetailsActivity, 0, BookState);

                        String where = libBookState.getHoldingList().get(i).getOrglocal();
                        where = MyUtils.GetBookStateInfo(bookDetailsActivity, 1, where);

                        String which = libBookState.getHoldingList().get(i).getOrglib();
                        which = MyUtils.GetBookStateInfo(bookDetailsActivity, 2, which);

                        View Item = View.inflate(bookDetailsActivity, R.layout.activity_book_details_item, null);
                        view.getLibContainer().addView(Item);

                        TextView Callno = Item.findViewById(R.id.SuoShuHao);
                        TextView Barcode = Item.findViewById(R.id.TiaoMaHao);
                        TextView State = Item.findViewById(R.id.State);
                        TextView Orglib = Item.findViewById(R.id.WhichLib);
                        TextView Orglocal = Item.findViewById(R.id.WhereLib);

                        Callno.setText("索书号：" + callno);
                        Barcode.setText("条码号：" + barcode);
                        State.setText(BookState);
                        Orglib.setText("所在馆："+which);
                        Orglocal.setText("馆位置：" + where);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL)) {
                    getBookWhereInfo(bookDetailsActivity, view);
                }
            }
        });
    }
}
