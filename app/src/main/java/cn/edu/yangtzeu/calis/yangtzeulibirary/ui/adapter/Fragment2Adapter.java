package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.content.Context;
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
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.ItemBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BaseActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookDetailsActivity;

/**
 * Created by Administrator on 2018/4/12.
 *
 * @author 王怀玉
 * @explain MoreAdapter
 */

public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.ViewHolder> {
    private Context context;
    private List<ItemBean> itemBeans = new ArrayList<>();

    public Fragment2Adapter(Context context) {
        this.context = context;
    }

    public void setData(List<ItemBean> itemBeans) {
        this.itemBeans.addAll(itemBeans);
    }

    public void clear() {
        itemBeans.clear();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment2_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemBean itemBean = itemBeans.get(position);
        final String title = itemBean.getTitle();
        final String url = itemBean.getUrl();
        final String time = itemBean.getTime();
        final boolean isNew = itemBean.isNew();
        if (isNew) {
            Glide.with(context)
                    .load(Urls.Url_IsNew)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.isNew);
        }

        holder.notice.setText(title);
        holder.time.setText(time);
        holder.kind.setText(context.getString(R.string.lib_notice));

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
        return itemBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView isNew ;
        TextView notice ;
        TextView time ;
        TextView kind ;
        LinearLayout OnClick;
        ViewHolder(View view) {
            super(view);
            isNew = view.findViewById(R.id.isNew);
            notice = view.findViewById(R.id.title);
            kind = view.findViewById(R.id.kind);
            time = view.findViewById(R.id.time);
            OnClick = view.findViewById(R.id.onclick);
        }
    }
}