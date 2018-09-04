package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookRenewBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/12.
 *
 * @author 王怀玉
 * @explain MoreAdapter
 */

public class BookRenewAdapter extends RecyclerView.Adapter<BookRenewAdapter.ViewHolder> {
    private Context context;
    private List<BookRenewBean> collectionBean = new ArrayList<>();

    public BookRenewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BookRenewBean> itemBeans) {
        this.collectionBean.addAll(itemBeans);
    }

    public void clear() {
        collectionBean.clear();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_book_renew_item, parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        BookRenewBean itemBean = collectionBean.get(position);

        final String name = itemBean.getBookName();
        final String url =itemBean.getBookUrl();
        final String timeIn = itemBean.getTimeIn();
        final String timeOut = itemBean.getTimeOut();
        final String bookTM = itemBean.getBookTM();
        final String suoShu = itemBean.getSuoShu();
        final String times = itemBean.getTimes();
        final String kind = itemBean.getKind();
        final String lib = itemBean.getLib();


        holder.title.setText(name);
        holder.user.setText("索书号："+suoShu);
        holder.bookTM.setText("图书条码："+bookTM);
        holder.lib.setText("图书位置："+lib);
        holder.kind.setText("图书种类："+kind);
        holder.timeIn.setText("借入时间："+timeIn);
        holder.timeOut.setText("应还时间："+timeOut);
        holder.times.setText("借阅次数："+times);

        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle(R.string.trip)
                        .setMessage("请选择操作")
                        .setPositiveButton("续借", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog = MyUtils.getProgressDialog(context, "加载中");
                                progressDialog.show();
                                RequestBody formBody = new FormBody.Builder()
                                        .add("barcodeList",bookTM)
                                        .add("furl","/opac/loan/renewList")
                                        .build();

                                Request request=new Request.Builder()
                                        .post(formBody)
                                        .url(Urls.Url_Book_Renew)
                                        .build();

                                OkHttp.do_Post(request, new OnResultListener() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        Document document = Jsoup.parse(response);
                                        String message = document.select("#content").text();

                                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                                .setTitle(R.string.trip)
                                                .setMessage(message)
                                                .setPositiveButton("知道了", null)
                                                .create();
                                        alertDialog.show();
                                        alertDialog.setCancelable(false);
                                        alertDialog.setCanceledOnTouchOutside(false);
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        progressDialog.dismiss();
                                        ToastUtils.showShort(error);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("查看详情", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyUtils.openUrl(context,url);
                            }
                        })
                        .create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });

    }


    @Override
    public long getItemId(int i) {
        return super.getItemId(i);
    }

    @Override
    public int getItemCount() {
        return collectionBean.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView user ;
        TextView title;
        TextView lib;
        TextView kind;
        TextView timeOut ;
        TextView times ;
        TextView timeIn ;
        TextView bookTM ;
        LinearLayout OnClick;
        ViewHolder(View view) {
            super(view);
            lib = view.findViewById(R.id.lib);
            kind = view.findViewById(R.id.kind);
            times = view.findViewById(R.id.times);
            bookTM = view.findViewById(R.id.bookTM);
            title = view.findViewById(R.id.title);
            user = view.findViewById(R.id.user);
            timeIn = view.findViewById(R.id.timeIn);
            timeOut = view.findViewById(R.id.timeOut);
            OnClick = view.findViewById(R.id.onclick);
        }
    }

}