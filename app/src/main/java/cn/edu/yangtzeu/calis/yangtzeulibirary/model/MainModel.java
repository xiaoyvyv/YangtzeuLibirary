package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.app.MyApplication;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IMainModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IHomeFragmentPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.LogActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.HomeFragment;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.MainFragment3;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.MineFragment;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.ToolFragment;

public class MainModel implements IMainModel{

    @Override
    public void addHomeFragment(AppCompatActivity activity, int container) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setUserVisibleHint(true);
        FragmentManager manger = activity.getSupportFragmentManager();
        FragmentUtils.add(manger,homeFragment,container,false);
    }

    @Override
    public void addToolFragment(AppCompatActivity context, int container) {
        ToolFragment toolFragment = new ToolFragment();
        FragmentManager manger = context.getSupportFragmentManager();
        FragmentUtils.add(manger,toolFragment,container,true);
    }

    @Override
    public void addMeFragment(AppCompatActivity context, int container) {
        MineFragment fragment = new MineFragment();
        FragmentManager manger = context.getSupportFragmentManager();
        FragmentUtils.add(manger,fragment,container,true);
    }

    @Override
    public void fitBottomView(final AppCompatActivity activity, BottomNavigationView bottomNavigationView) {
        final FragmentManager manger = activity.getSupportFragmentManager();
        BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                List<Fragment> list = FragmentUtils.getFragments(manger);
                int id = item.getItemId();
                switch (id) {
                    case R.id.bottom_home:
                        IHomeFragmentPresenter.IHomeFragmentView.getViewPager().setCurrentItem(0);
                        FragmentUtils.showHide(list.get(0),list.get(1),list.get(2));
                        break;
                    case R.id.bottom_tool:
                        list.get(1).setUserVisibleHint(true);
                        FragmentUtils.showHide(list.get(1),list.get(0),list.get(2));
                        break;
                    case R.id.bottom_me:
                        if (MyApplication.isLogin) {
                            list.get(2).setUserVisibleHint(true);
                            FragmentUtils.showHide(list.get(2), list.get(0), list.get(1));
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                                    .setTitle(R.string.trip)
                                    .setMessage("请您先登录")
                                    .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ActivityUtils.finishAllActivities();
                                            ActivityUtils.startActivity(LogActivity.class);
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);
                        }
                        break;
                }
                return true;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }

}
