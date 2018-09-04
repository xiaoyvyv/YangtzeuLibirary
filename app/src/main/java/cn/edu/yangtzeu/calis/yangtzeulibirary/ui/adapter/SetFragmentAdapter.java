package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2016 on 2017/12/24.
 *
 */

public class SetFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mFragments_name;
    public SetFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragments_name = new ArrayList<>();
    }

    public void addFragment(String title,Fragment fragment) {
        mFragments.add(fragment);
        mFragments_name.add(title);
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments_name.get(position);
    }
}