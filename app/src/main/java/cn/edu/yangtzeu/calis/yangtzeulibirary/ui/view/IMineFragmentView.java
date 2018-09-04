package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;


import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public interface IMineFragmentView {
    SmartRefreshLayout getSmartRefreshLayout();

    ImageView getUserHeader();

    TextView getUserName();

    TextView getUserScore();

    TextView getUserNumber();

    List<TextView> getTextViews1();

    List<TextView> getTextViews2();
}
