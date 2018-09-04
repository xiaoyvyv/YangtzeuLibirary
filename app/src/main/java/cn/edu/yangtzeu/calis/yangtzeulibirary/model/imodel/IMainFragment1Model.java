package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public interface IMainFragment1Model {
    void searchData(Activity activity,SearchView searchView);

    void fitHotWord(Context context,SearchView searchView,LinearLayout layout1, LinearLayout layout2, LinearLayout layout3);

    void showHighSearch(Activity activity);

    void setSearchWay(RadioGroup radioGroup);

    void setOnlySavedLib(CheckBox checkBox);
}
