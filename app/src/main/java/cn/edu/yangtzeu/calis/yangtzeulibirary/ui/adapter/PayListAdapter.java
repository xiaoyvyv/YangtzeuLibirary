package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookAskBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.PayListBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;

/**
 * Created by Administrator on 2018/4/12.
 *
 * @author 王怀玉
 * @explain MoreAdapter
 */

public class PayListAdapter extends RecyclerView.Adapter<PayListAdapter.ViewHolder> {
    private Context context;
    private List<PayListBean> collectionBean = new ArrayList<>();

    public PayListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PayListBean> itemBeans) {
        this.collectionBean.addAll(itemBeans);
    }

    public void clear() {
        collectionBean.clear();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_pay_list_item, parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        PayListBean itemBean = collectionBean.get(position);

        String kind = itemBean.getKind();
        String money = itemBean.getMoney();
        String time = itemBean.getTime();
        String people = itemBean.getPeople();
        String lib = itemBean.getLib();
        String lib_where = itemBean.getLib_where();
        String bookTm = itemBean.getBookTM();
        String bookNumber = itemBean.getBookNumber();
        String payState = itemBean.getPayState();


        holder.kindTv.setText("财经类型："+kind);
        holder.moneyTv.setText("发生费用："+money);
        holder.timeTv.setText("发生时间："+time);
        holder.peopleTv.setText("经手人员："+people);
        holder.libTv.setText("发生地点："+lib);
        holder.lib_whereTv.setText("具体位置："+lib_where);
        holder.bookTmTv.setText("图书条码："+bookTm);
        holder.bookNumberTv.setText("书记录号："+bookNumber);
        holder.payStateTv.setText("交付标识："+payState);


        holder.OnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        TextView kindTv ;
        TextView moneyTv;
        TextView timeTv;
        TextView peopleTv ;
        TextView libTv ;
        TextView lib_whereTv ;
        TextView bookTmTv ;
        TextView bookNumberTv ;
        TextView payStateTv ;

        LinearLayout OnClick;
        ViewHolder(View view) {
            super(view);
            kindTv = view.findViewById(R.id.kind);
            moneyTv = view.findViewById(R.id.money);
            timeTv = view.findViewById(R.id.time);
            peopleTv = view.findViewById(R.id.people);
            libTv = view.findViewById(R.id.lib);
            lib_whereTv = view.findViewById(R.id.lib_where);
            bookTmTv = view.findViewById(R.id.bookTM);
            bookNumberTv = view.findViewById(R.id.bookNumber);
            payStateTv = view.findViewById(R.id.payState);
            OnClick = view.findViewById(R.id.onclick);
        }
    }
}