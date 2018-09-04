package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Network;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.CollectionBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookDetailsActivity;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/12.
 *
 * @author 王怀玉
 * @explain MoreAdapter
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<CollectionBean> collectionBean = new ArrayList<>();

    public HistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CollectionBean> itemBeans) {
        this.collectionBean.addAll(itemBeans);
    }

    public void clear() {
        collectionBean.clear();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_collection_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        CollectionBean itemBean = collectionBean.get(position);

        final String title = itemBean.getBookName();
        final String url =itemBean.getOnClickUrl();

        final String time = itemBean.getBookDate();
        final String source = itemBean.getBookChuBanShe();

        final String author = itemBean.getBookAuthor();
        final String recno = itemBean.getBookrecno();

        final String kind = itemBean.getBookSuoShu();
        final String size = itemBean.getBookSize();

        final String lib = itemBean.getBooLib();
        final String collection_time = itemBean.getCollectionTime();


        holder.notice.setText(title);
        holder.kind.setText(source);
        holder.time.setText(collection_time);
        Glide.with(context).load(Urls.Url_Book_Holder).into(holder.image);


        getIsbn(holder, position,title, url, time, source, author, recno, kind);

        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(R.string.wait_load_finish);
            }
        });

    }

    private void getIsbn(@NonNull final ViewHolder holder,final int position, final String title,
                         final String url, final String time, final String source,
                         final String author, final String recno, final String kind) {
        OkHttp.do_Get(url, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                Document document = Jsoup.parse(response);
                final String isBn_Str = document.select("#bookcover_img").attr("isbn");

                holder.OnClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BookDetailsActivity.class);
                        intent.putExtra("from_url", url);
                        intent.putExtra("isbn", isBn_Str);
                        intent.putExtra("author", author);
                        intent.putExtra("year", time);
                        intent.putExtra("source", source);
                        intent.putExtra("kind", kind);
                        intent.putExtra("name", title);
                        intent.putExtra("recno", recno);
                        context.startActivity(intent);
                    }
                });

                holder.OnClick.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle(R.string.trip)
                                .setMessage(R.string.save_book)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        saveBook(recno);
                                    }
                                })
                                .setNegativeButton(R.string.clear,null)
                                .create();
                        alertDialog.show();
                        return true;
                    }
                });


                MyUtils.findBookPicture((Activity) context, isBn_Str, holder.image, false);
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL)) {
                    getIsbn(holder,position, title, url, time, source, author, recno, kind);
                }
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
        ImageView image ;
        ImageView image_pi;
        TextView notice ;
        TextView time ;
        TextView kind ;
        LinearLayout OnClick;
        ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            notice = view.findViewById(R.id.title);
            kind = view.findViewById(R.id.kind);
            time = view.findViewById(R.id.time);
            OnClick = view.findViewById(R.id.onclick);
            image_pi = view.findViewById(R.id.image_pi);
        }
    }

    public void saveBook(String tiaoma) {
        final ProgressDialog alertDialog = new ProgressDialog(context);
        alertDialog.setTitle(R.string.trip);
        alertDialog.setMessage("收藏中");
        alertDialog.show();

        OkHttp.do_Get(Urls.Url_Add_My_Book + tiaoma, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                ToastUtils.showShort(response);
            }
            @Override
            public void onFailure(String error) {
                ToastUtils.showShort(error);
            }
        });

    }
}