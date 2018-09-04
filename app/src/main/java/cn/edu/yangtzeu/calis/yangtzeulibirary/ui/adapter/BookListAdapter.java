package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookDetailsActivity;

/**
 * Created by Administrator on 2018/4/12.
 *
 * @author 王怀玉
 * @explain MoreAdapter
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    private Context context;
    private List<BookListBean> bookListBeans = new ArrayList<>();

    public BookListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BookListBean> itemBeans) {
        this.bookListBeans.addAll(itemBeans);
    }

    public void clear() {
        bookListBeans.clear();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_book_list_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookListBean itemBean = bookListBeans.get(position);

        final String title = itemBean.getBookNameText();
        final String url =itemBean.getOnClickUrl();
        final String time = itemBean.getBookDateText();
        final String source = itemBean.getBookChuBanSheText();
        final String bookID = itemBean.getBookImgIsbn();
        final String bookpi = itemBean.getBookPi();
        final String author = itemBean.getBookAuthorText();
        final String recno = itemBean.getBookrecno();

        final String kind = itemBean.getBookKindText();

        holder.notice.setText(title);
        holder.time.setText(time);
        holder.kind.setText(source);
        Glide.with(context).load(Urls.Url_Book_Holder).into(holder.image);

        Glide.with(context).load(bookpi).into(holder.image_pi);

        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("from_url", url);
                intent.putExtra("isbn", bookID);
                intent.putExtra("author", author);
                intent.putExtra("year", time);
                intent.putExtra("source", source);
                intent.putExtra("kind", kind);
                intent.putExtra("name", title);
                intent.putExtra("recno", recno);
                context.startActivity(intent);
            }
        });

        MyUtils.findBookPicture((Activity) context, bookID, holder.image,false);
    }
    @Override
    public long getItemId(int i) {
        return super.getItemId(i);
    }

    @Override
    public int getItemCount() {
        return bookListBeans.size();
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

}