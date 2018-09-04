package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHistoryView;

public interface IHistoryModel {
    void applyToolbar(AppCompatActivity activity, Toolbar toolbar);
    void setAdapter(AppCompatActivity activity, IHistoryView recyclerView);
}
