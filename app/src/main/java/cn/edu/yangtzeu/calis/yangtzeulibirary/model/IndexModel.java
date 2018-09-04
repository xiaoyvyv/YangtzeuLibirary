package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.List;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.BookIndexBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IIndexModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.XmlUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookListActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IIndexView;

public class IndexModel implements IIndexModel {

    @Override
    public void applyToolbar(final AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    public void getAllKind(final AppCompatActivity activity, final IIndexView iIndexView, final int id) {
        final TreeNode root = TreeNode.root();

        OkHttp.do_Get(Urls.Url_All_Book_CLS + id, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                ToastUtils.showShort("加载完成");
                List<BookIndexBean> records = XmlUtils.parseBookIndexBean(response);

                LogUtils.e(records.size());



                for (int i = 0; i < records.size(); i++) {
                    BookIndexBean record = records.get(i);

                    int childrenCount = 0;
                    int id = 0;  //2
                    try {
                        childrenCount = Integer.parseInt(record.getChildrenCount());
                        id = Integer.parseInt(record.getId());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    final TreeNode root1 = new TreeNode(record).setViewHolder(new MyHolder(activity));
                    root1.setExpanded(true);
                    root.addChild(root1);

                    final int finalChildrenCount = childrenCount;
                    final int finalId = id;
                    root1.setClickListener(new TreeNode.TreeNodeClickListener() {
                        @Override
                        public void onClick(TreeNode node, Object value) {
                            int size = root1.getChildren().size();
                            LogUtils.i("点击Item孩子大小", size);
                            if (finalChildrenCount != 0 && size == 0) {
                                ToastUtils.showShort(R.string.loading);
                                getNode(activity, iIndexView, root1, finalId);
                            }

                            if (root1.isExpanded()) {
                                ImageView imageView = root1.getViewHolder().getView().findViewById(R.id.folder);
                                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_folder));
                            } else {
                                ImageView imageView = root1.getViewHolder().getView().findViewById(R.id.folder);
                                imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_folder_open));
                            }
                        }
                    });

                }

                AndroidTreeView treeView = new AndroidTreeView(activity, root);
                iIndexView.getContainer().addView(treeView.getView());

            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    getAllKind(activity, iIndexView, id);
            }
        });
    }

    private void getNode(final AppCompatActivity activity, final IIndexView iIndexView, final TreeNode treeNode, final int id) {
        OkHttp.do_Get(Urls.Url_All_Book_CLS + id, new OnResultListener() {
            @Override
            public void onResponse(String response) {
                ToastUtils.showShort("加载完成");

                List<BookIndexBean> records = XmlUtils.parseBookIndexBean(response);

                for (int i = 0; i < records.size(); i++) {
                    try {
                        BookIndexBean record = records.get(i);

                        final String categoryDesc = record.getCategoryDesc(); //A 马列主义、毛泽东思想、邓小平理论
                        int childrenCount = 0;
                        int id = 0;  //2
                        try {
                            childrenCount = Integer.parseInt(record.getChildrenCount());
                            id = Integer.parseInt(record.getId());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        final String name = record.getName(); //A

                        final TreeNode root1 = new TreeNode(record).setViewHolder(new MyHolder(activity));
                        root1.setExpanded(true);

                        treeNode.addChild(root1);

                        if (childrenCount == 0) {
                            ImageView imageView = root1.getViewHolder().getView().findViewById(R.id.folder);
                            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_folder_open));
                        }

                        final int finalChildrenCount = childrenCount;
                        final int finalId = id;
                        root1.setClickListener(new TreeNode.TreeNodeClickListener() {
                            @Override
                            public void onClick(TreeNode node, Object value) {
                                if (finalChildrenCount != 0 && root1.getChildren().size() == 0) {
                                    ToastUtils.showShort(R.string.loading);
                                    getNode(activity, iIndexView, root1, finalId);
                                } else if (finalChildrenCount == 0) {
                                    String from_url = Urls.Url_All_Book_List + "&q=" + name + "&classname=" + categoryDesc + "&page=";
                                    Intent intent = new Intent(activity, BookListActivity.class);
                                    intent.putExtra("title", categoryDesc);
                                    intent.putExtra("from_url", from_url);
                                    activity.startActivity(intent);
                                }
                                if (root1.isExpanded() && finalChildrenCount != 0) {
                                    ImageView imageView = root1.getViewHolder().getView().findViewById(R.id.folder);
                                    imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_folder));
                                } else {
                                    ImageView imageView = root1.getViewHolder().getView().findViewById(R.id.folder);
                                    imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_folder_open));
                                }
                            }
                        });
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                    getNode(activity, iIndexView, treeNode, id);
            }
        });

    }

    public class MyHolder extends TreeNode.BaseNodeViewHolder<BookIndexBean> {
        MyHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, BookIndexBean itemView) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.activity_index_item, null, false);
            TextView tvValue = view.findViewById(R.id.node_value);
            ImageView image = view.findViewById(R.id.folder);
            TextView child_size = view.findViewById(R.id.child_size);


            String categoryDesc = itemView.getCategoryDesc(); //A 马列主义、毛泽东思想、邓小平理论
            final int childrenCount = Integer.parseInt(itemView.getChildrenCount());
            final int id = Integer.parseInt(itemView.getId());  //2
            String name = itemView.getName(); //A

            if (childrenCount != 0) {
                child_size.setTextColor(Color.parseColor("#00ff00"));
            }
            child_size.setText(String.valueOf(childrenCount));

            int index = name.length() - 1;
            if (index > 8) index = 8;
            view.setPadding(index * 50, 0, 0, 0);
            tvValue.setTextColor(Color.argb(255, index * 10, index * 20, index * 30));
            tvValue.setText(categoryDesc);

            return view;
        }
    }
}
