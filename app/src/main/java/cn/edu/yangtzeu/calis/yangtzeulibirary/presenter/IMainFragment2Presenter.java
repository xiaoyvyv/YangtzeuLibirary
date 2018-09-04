package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.content.Context;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.MainFragment2Model;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.adapter.Fragment2Adapter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMainFragment2View;

public class IMainFragment2Presenter {
    //数据源
    private IMainFragment2View fragment2View;
    //处理业务逻辑
    private MainFragment2Model mainModel;

    public IMainFragment2Presenter(IMainFragment2View fragment2View) {
        this.fragment2View = fragment2View;
        mainModel = new MainFragment2Model();
    }

    public void getBanner(Context context) {
        mainModel.getBanner(context,fragment2View);
    }

    public void getNotice(final Context context, Fragment2Adapter adapter, boolean isNext) {
        mainModel.getNotice(context,adapter,fragment2View,isNext);
    }

}
