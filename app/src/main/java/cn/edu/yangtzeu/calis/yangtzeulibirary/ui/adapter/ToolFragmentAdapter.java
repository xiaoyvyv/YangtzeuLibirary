package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.ToolItemBean;

public class ToolFragmentAdapter extends BaseAdapter {
    private Context context;
    private List<ToolItemBean> toolItemBeans=new ArrayList<>();

    public ToolFragmentAdapter(Context context) {
        this.context = context;
    }

    public void addData(ToolItemBean toolItemBean) {
        toolItemBeans.add(toolItemBean);
    }

    public void claer() {
        toolItemBeans.clear();
    }


    @Override
    public int getCount() {
        return toolItemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return toolItemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ToolItemBean toolItemBean = toolItemBeans.get(position);
        final ViewHolder viewHolder;
        if (view==null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.fragment_tool_item, null);
            viewHolder.title = view.findViewById(R.id.menu_title_tv);
            viewHolder.layout = view.findViewById(R.id.menu_layout_tv);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.layout.setBackground(context.getDrawable(toolItemBean.getBg()));
        }
        viewHolder.title.setText(toolItemBean.getName());

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> activity = toolItemBean.getContext();
                Intent intent = new Intent(context, activity);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder {
        TextView title;
        FrameLayout layout;
    }
}
