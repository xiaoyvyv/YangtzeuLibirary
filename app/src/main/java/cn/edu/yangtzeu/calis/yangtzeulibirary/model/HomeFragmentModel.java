package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IHomeFragmentModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.MyUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.SetFragmentAdapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.MainFragment1;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.MainFragment2;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.MainFragment3;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments.MainFragment4;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHomeFragmentView;

public class HomeFragmentModel implements IHomeFragmentModel {
    @Override
    public void fitView(AppCompatActivity activity, final IHomeFragmentView iHomeFragmentView, final ViewPager viewPager) {
        FragmentManager manger = activity.getSupportFragmentManager();
        final SetFragmentAdapter setFragmentAdapter = new SetFragmentAdapter(manger);

        MainFragment1 mainFragment1 = new MainFragment1();
        MainFragment2 mainFragment2 = MainFragment2.newInstance(Urls.Url_Notice, 1);
        MainFragment2 mainFragment3 = MainFragment2.newInstance(Urls.Url_News, 2);
        MainFragment2 mainFragment4 = MainFragment2.newInstance(Urls.Url_Learn, 3);

        setFragmentAdapter.addFragment(activity.getResources().getString(R.string.lib_search), mainFragment1);
        setFragmentAdapter.addFragment(activity.getResources().getString(R.string.lib_notice), mainFragment2);
        setFragmentAdapter.addFragment(activity.getResources().getString(R.string.lib_news), mainFragment3);
        setFragmentAdapter.addFragment( activity.getResources().getString(R.string.lib_learn),mainFragment4);

        viewPager.setAdapter(setFragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);

        MyUtils.reflexTabLayout(iHomeFragmentView.getTabLayout());
        iHomeFragmentView.getTabLayout().setupWithViewPager(viewPager);
    }
}
