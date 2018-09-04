package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.animation.AnimationUtils;

import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.Utils_Tool;

/**
 * Created by Administrator on 2018/4/28.
 *
 * @author 王怀玉
 * @explain BaseFragment
 */
public abstract class BaseFragment extends Fragment {
    protected boolean isVisible;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible(){
        lazyLoad();
    }
    protected abstract void lazyLoad();
    protected void onInvisible(){}

    protected void openActivity(Object activity) {
        Intent intent = new Intent(getActivity(), (Class<?>) activity);
        startActivity(intent);
        AnimationUtils.makeInAnimation(getActivity(), true);
    }


}