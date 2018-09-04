package cn.edu.yangtzeu.calis.yangtzeulibirary.presenter;

import cn.edu.yangtzeu.calis.yangtzeulibirary.model.HistoryModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity.HistoryActivity;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IHistoryView;

public class IHistoryPresenter {
    private IHistoryView view;
    private HistoryModel model;

    public IHistoryPresenter(IHistoryView historyView) {
        this.view = historyView;
        model = new HistoryModel();
    }

    public void applyToolbar(HistoryActivity activity) {
        model.applyToolbar(activity,view.getToolBar());
    }

    public void setAdapter(HistoryActivity historyActivity) {
        model.setAdapter(historyActivity,view);
    }
}
