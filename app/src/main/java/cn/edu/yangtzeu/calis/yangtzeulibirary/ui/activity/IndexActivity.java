
package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import cn.edu.yangtzeu.calis.yangtzeulibirary.R;
import cn.edu.yangtzeu.calis.yangtzeulibirary.presenter.IIndexPresenter;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IIndexView;

public class IndexActivity extends BaseActivity implements IIndexView {

    private Toolbar toolbar;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_index);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        container= findViewById(R.id.container);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void initMethod() {
        IIndexPresenter iIndexPresenter = new IIndexPresenter(this);
        iIndexPresenter.applyToolbar(this);
        iIndexPresenter.getAllKind(this, 0);
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }


    @Override
    public LinearLayout getContainer() {
        return container;
    }
}
