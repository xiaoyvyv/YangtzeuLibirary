package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.ToolFragmentModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IToolFragmentView;

public class IToolFragmentPresenter {
    //数据源
    public IToolFragmentView IToolFragmentView;
    //处理业务逻辑
    public ToolFragmentModel toolFragmentModel;

    public IToolFragmentPresenter(IToolFragmentView IToolFragmentView) {
        this.IToolFragmentView = IToolFragmentView;
        toolFragmentModel = new ToolFragmentModel();
    }

    //填充主页 Fragment 视图

    public void fitView(AppCompatActivity activity) {
        toolFragmentModel.fitGridView(activity, IToolFragmentView.getGridView());
        toolFragmentModel.fitBGABanner(activity, IToolFragmentView.getBanner());
    }

}
