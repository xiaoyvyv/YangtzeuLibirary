package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public interface IBookDetailsView {
    Toolbar getToolBar();

    String getFromUrl();

    String getIsbn();

    String getRecno();

    ImageView getImageView();

    TextView getPagesView();

    TextView getSummaryView();

    TextView getLibSizeView();

    RelativeLayout getShowContainerView();

    LinearLayout getLibContainer();

    ProgressDialog getProgressDialog();
}
