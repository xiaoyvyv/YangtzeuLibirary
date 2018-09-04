package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.MainFragment1Model;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainFragment1View;

public class IMainFragment1Presenter {
    //数据源
    private IMainFragment1View fragment1View;
    //处理业务逻辑
    private MainFragment1Model mainModel;

    public IMainFragment1Presenter(IMainFragment1View fragment1View) {
        this.fragment1View = fragment1View;
        mainModel = new MainFragment1Model();
    }

    public void searchData(Activity activity ) {
        mainModel.searchData(activity,fragment1View.getSearchView());
    }

    public void fitHotWord(Context context,SearchView searchView, LinearLayout layout1, LinearLayout layout2, LinearLayout layout3) {
        mainModel.fitHotWord(context,searchView,layout1, layout2, layout3);
    }
    public void showHighSearch(Activity activity) {
        mainModel.showHighSearch(activity);
    }
    public void setOnlySavedLib() {
        mainModel.setOnlySavedLib(fragment1View.getCheckBox());
    }

    public void setSearchWay() {
        mainModel.setSearchWay(fragment1View.getRadioGroup());
    }
}
