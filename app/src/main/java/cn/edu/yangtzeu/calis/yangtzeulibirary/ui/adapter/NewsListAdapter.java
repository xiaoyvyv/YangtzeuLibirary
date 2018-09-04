package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.HomeNewsBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.ItemBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.NewsListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BaseActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.NewsActivity;

/**
 * Created by Administrator on 2018/4/12.
 *
 * @author 王怀玉
 * @explain MoreAdapter
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private Context context;
    private List<NewsListBean.DataBean.ResultListBean> homeNewsBeans = new ArrayList<>();

    public NewsListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NewsListBean.DataBean.ResultListBean> itemBeans) {
        this.homeNewsBeans.addAll(itemBeans);
    }

    public void clear() {
        homeNewsBeans.clear();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_news_list_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsListBean.DataBean.ResultListBean itemBean = homeNewsBeans.get(position);

        final String title = itemBean.getTitle();
        final String url = Urls.Url_News_ID+itemBean.getNewsId();
        final String time = itemBean.getCreateDate();
        final String source = itemBean.getSource();
        final String image = Urls.Url_Image_Host + itemBean.getImage();

        holder.notice.setText(title);
        holder.time.setText(time);
        holder.kind.setText(source);
        Glide.with(context).load(image).into(holder.image);

        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("from_url", url);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public long getItemId(int i) {
        return super.getItemId(i);
    }

    @Override
    public int getItemCount() {
        return homeNewsBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image ;
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
        }
    }
}