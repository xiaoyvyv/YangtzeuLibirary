package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookAskBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;

/**
 * Created by Administrator on 2018/4/12.
 *
 * @author 王怀玉
 * @explain MoreAdapter
 */

public class AskBookAdapter extends RecyclerView.Adapter<AskBookAdapter.ViewHolder> {
    private Context context;
    private List<BookAskBean> collectionBean = new ArrayList<>();

    public AskBookAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BookAskBean> itemBeans) {
        this.collectionBean.addAll(itemBeans);
    }

    public void clear() {
        collectionBean.clear();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_book_ask_item, parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        BookAskBean itemBean = collectionBean.get(position);

        final String name = itemBean.getBookName();
        final String url =itemBean.getBookUrl();
        final String timeIn = itemBean.getTimeIn();
        final String timeOut = itemBean.getTimeOut();
        final String bookTM = itemBean.getBookTM();
        final String user = itemBean.getUser();


        holder.title.setText(name);
        holder.bookTM.setText("图书条码："+bookTM);
        holder.user.setText("借阅证号："+user);
        holder.kind.setText("借入时间："+timeIn);
        holder.time.setText("应还时间："+timeOut);

        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.openUrl(context,url);
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
        TextView time ;
        TextView kind ;
        TextView bookTM ;
        LinearLayout OnClick;
        ViewHolder(View view) {
            super(view);
            bookTM = view.findViewById(R.id.bookTM);
            title = view.findViewById(R.id.title);
            user = view.findViewById(R.id.user);
            kind = view.findViewById(R.id.kind);
            time = view.findViewById(R.id.time);
            OnClick = view.findViewById(R.id.onclick);
        }
    }
}