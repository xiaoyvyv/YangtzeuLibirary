package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.database.DatabaseUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.SearchHistoryBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ilistener.ItemTouchHelperAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookDetailsActivity;

public class SearchHistoryAdapter extends RecyclerView.Adapter <SearchHistoryAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private List<SearchHistoryBean> searchHistoryBeans = new ArrayList<>();

    public SearchHistoryAdapter(Context context){
        this.context = context;
    }

    public void setData(List<SearchHistoryBean> histories) {
        this.searchHistoryBeans.clear();
        this.searchHistoryBeans.addAll(histories);
    }

    public void clearData() {
        this.searchHistoryBeans.clear();
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_search_history_item, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final SearchHistoryBean searchHistoryBean = searchHistoryBeans.get(position);

        final long id = searchHistoryBean.getId();
        final String from_url = searchHistoryBean.getUrl();
        final String isBn_Str = searchHistoryBean.getIsbn();
        final String name_Str = searchHistoryBean.getTitle();
        final String author_Str = searchHistoryBean.getAuthor();
        final String chuban_Str = searchHistoryBean.getChuban();
        final String kind_Str = searchHistoryBean.getBookKind();
        final String year_Str = searchHistoryBean.getBookYear();
        final long recno = searchHistoryBean.getRecno();
        final long time = searchHistoryBean.getTime();

        final String activityType = searchHistoryBean.getActivityType();



        viewHolder.name.setText(name_Str);
        viewHolder.message.setText(chuban_Str);
        viewHolder.time.setText(TimeUtils.millis2String(time));

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("from_url",from_url);
                intent.putExtra("isbn", isBn_Str);
                intent.putExtra("author", author_Str);
                intent.putExtra("year", year_Str);
                intent.putExtra("source", chuban_Str);
                intent.putExtra("kind", kind_Str);
                intent.putExtra("name", name_Str);
                intent.putExtra("recno", String.valueOf(recno));
                context.startActivity(intent);
            }
        });

        viewHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyUtils.putStringToClipboard(context, String.valueOf(from_url));
                ToastUtils.showShort(R.string.copy_right_link);
                return true;
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return searchHistoryBeans.size();
    }

    @Override
    public void onItemChange(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(searchHistoryBeans,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);

    }

    @Override
    public void onItemRemove(int position) {
        SearchHistoryBean history = searchHistoryBeans.get(position);
        DatabaseUtils.getHelper(context, "searchHistory.db").delete(history);

        //移除数据
        searchHistoryBeans.remove(position);
        notifyItemRemoved(position);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;
        TextView time;
        RelativeLayout linearLayout;
        ViewHolder(View convertView) {
            super(convertView);
            linearLayout = convertView.findViewById(R.id.mLinearLayout);
            name = convertView.findViewById(R.id.name);
            message = convertView.findViewById(R.id.message);
            time = convertView.findViewById(R.id.time);
        }
    }

}
