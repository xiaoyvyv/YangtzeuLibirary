package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.LogUserModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IMineFragmentPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.AlipayUtil;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.ShareUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.AboutActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookAskActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.BookRenewActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.CollectionActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.HistoryActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.PayListActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.SearchHistoryActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.SettingActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMineFragmentView;


/**
 * Created by Administrator on 2018/3/6.
 */

public class MineFragment extends BaseFragment implements IMineFragmentView {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared=false;
    private boolean isLoadFinish = false;
    private View RootView;
    private TextView userName;
    private TextView userScore;
    private TextView userClass;
    private ImageView userHeader;
    private LinearLayout jiuMo;
    private LinearLayout history;
    private LinearLayout myBook;
    private LinearLayout info_Details;
    private LinearLayout searchHistory;
    private LinearLayout changePsd;
    private LinearLayout logOut;
    private SmartRefreshLayout smartRefreshLayout;
    private IMineFragmentPresenter iMineFragmentPresenter;
    private LinearLayout moreLayout;
    private ImageView moreImg;
    private LinearLayout share;
    private LinearLayout renew;
    private LinearLayout ask;
    private LinearLayout payList;
    private LinearLayout setting;

    private List<TextView> textViews1 = new ArrayList<>();
    private List<TextView> textViews2 = new ArrayList<>();
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_mine, container, false);
        FindView();
        isPrepared = true;
        lazyLoad();
        return RootView;
    }

    private void FindView() {
        toolbar=RootView.findViewById(R.id.toolbar);
        userName = RootView.findViewById(R.id.userName);
        userScore = RootView.findViewById(R.id.userScore);
        userClass = RootView.findViewById(R.id.userClass);
        userHeader = RootView.findViewById(R.id.userHeader);
        jiuMo = RootView.findViewById(R.id.jiuMo);
        history = RootView.findViewById(R.id.history);
        myBook = RootView.findViewById(R.id.myBook);
        info_Details = RootView.findViewById(R.id.info_Details);
        searchHistory = RootView.findViewById(R.id.searchHistory);
        changePsd = RootView.findViewById(R.id.changePsd);
        smartRefreshLayout = RootView.findViewById(R.id.smartRefreshLayout);
        moreImg= RootView.findViewById(R.id.moreImg);
        moreLayout = RootView.findViewById(R.id.moreLayout);
        logOut = RootView.findViewById(R.id.logOut);
        share = RootView.findViewById(R.id.share);
        payList = RootView.findViewById(R.id.payList);
        ask = RootView.findViewById(R.id.ask);
        renew = RootView.findViewById(R.id.renew);
        setting = RootView.findViewById(R.id.setting);


        LinearLayout moreLayout1 = RootView.findViewById(R.id.moreLayout1);
        LinearLayout moreLayout2 = RootView.findViewById(R.id.moreLayout2);

        for (int i = 0; i <5; i++) {
            final TextView textView1 = (TextView) moreLayout1.getChildAt(i);
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort(textView1.getText());
                }
            });
            final TextView textView2 = (TextView) moreLayout2.getChildAt(i);
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort(textView2.getText());
                }
            });
            textViews1.add(textView1);
            textViews2.add(textView2);
        }
    }

    private void SetUp() {
        toolbar.inflateMenu(R.menu.mine_fragment_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about:
                        ActivityUtils.startActivity(AboutActivity.class);
                        break;
                    case R.id.bag:
                        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                                .setTitle("发红包啦")
                                .setMessage("支付宝双重红包，最多可领99元！")
                                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (AlipayUtil.hasInstalledAlipayClient(Objects.requireNonNull(getActivity()))) {
                                            //第二个参数代表要给被支付的二维码code  可以在用草料二维码在线生成
                                            AlipayUtil.startAlipayClient(getActivity(), getString(R.string.apply_redbag_code_key));
                                        } else {
                                            ToastUtils.showLong(R.string.no_apply_app);
                                        }
                                    }
                                })
                                .create();
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        break;
                }
                return true;
            }
        });

        iMineFragmentPresenter = new IMineFragmentPresenter(this);

        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                iMineFragmentPresenter.getUserInfo(getActivity());

            }
        });
        smartRefreshLayout.autoRefresh();



        info_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible = moreLayout.getVisibility();
                if (visible == View.GONE) {
                    moreLayout.setVisibility(View.VISIBLE);
                    moreImg.setImageDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_bottom));
                } else {
                    moreLayout.setVisibility(View.GONE);
                    moreImg.setImageDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.ic_right));
                }

               // MyUtils.openUrl(Objects.requireNonNull(getActivity()), Urls.Url_Mine_Info);
            }
        });

        jiuMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.openUrl(Objects.requireNonNull(getActivity()), Urls.Url_JiuMo);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(HistoryActivity.class);
            }
        });

        searchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(SearchHistoryActivity.class);
            }
        });

        myBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(CollectionActivity.class);
            }
        });

        changePsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogUserModel().changePsd(getActivity());
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.trip)
                    .setMessage("退出登录？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new LogUserModel().logout(getActivity());
                        }
                    })
                    .create();
                alertDialog.show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareMsg(getActivity(), getString(R.string.app_name), getString(R.string.share_text), null);
            }
        });


        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(BookRenewActivity.class);
            }
        });
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(BookAskActivity.class);
            }
        });
        payList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(PayListActivity.class);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(SettingActivity.class);
            }
        });


    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            SetUp();
            LogUtils.e("加载第三个Fragment");
        }
        isLoadFinish = true;
    }


    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    @Override
    public ImageView getUserHeader() {
        return userHeader;
    }

    @Override
    public TextView getUserName() {
        return userName;
    }

    @Override
    public TextView getUserScore() {
        return userScore;
    }

    @Override
    public TextView getUserNumber() {
        return userClass;
    }

    @Override
    public List<TextView> getTextViews1() {
        return textViews1;
    }

    @Override
    public List<TextView> getTextViews2() {
        return textViews2;
    }
}