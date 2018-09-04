package cn.edu.yangtzeu.calis.yangtzeulibirary.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import cn.edu.yangtzeu.calis.yangtzeulibirary.database.DatabaseUtils;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.Urls;
import cn.edu.yangtzeu.calis.yangtzeulibirary.entity.UserInfoBean;
import cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel.IMineFragmentModel;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkHttp;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OkhttpError;
import cn.edu.yangtzeu.calis.yangtzeulibirary.network.OnResultListener;
import cn.edu.yangtzeu.calis.yangtzeulibirary.ui.view.IMineFragmentView;

public class MineFragmentModel implements IMineFragmentModel {
    @Override
    public void getUserInfo(final Activity activity, final IMineFragmentView iMineFragmentView) {
        OkHttp.do_Get(Urls.Url_Mine_Info, new OnResultListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                List<TextView> textViews1 = iMineFragmentView.getTextViews1();
                List<TextView> textViews2 =iMineFragmentView.getTextViews2();

                iMineFragmentView.getSmartRefreshLayout().finishRefresh();

                Document document = Jsoup.parse(response);
                UserInfoBean userInfoBean = new UserInfoBean();

                String canUse = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > font:nth-child(1)").text();
                String useData = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > font:nth-child(1)").text();
                String isActionReader = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(1) > font:nth-child(1)").text();
                String CanIn = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(1) > font:nth-child(1)").text();
                String CanInAction = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(2) > font:nth-child(1)").text();
                String score =document.select(".space_table > tbody:nth-child(1) > tr:nth-child(6) > td:nth-child(1) > font:nth-child(1)").text();
                String name = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(1)").text();
                String school = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2) > font:nth-child(1)").text();
                String notPay = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2) > font:nth-child(1)").text();
                String readyPay = document.select("font.space_font:nth-child(2)").text();
                String readerKind = document.select(".space_table > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2) > font:nth-child(1)").text();

                userInfoBean.setCanUse(canUse);
                userInfoBean.setUseData(useData);
                userInfoBean.setIsActionReader(isActionReader);
                userInfoBean.setCanIn(CanIn);
                userInfoBean.setCanInAction(CanInAction);
                userInfoBean.setScore(score);
                userInfoBean.setName(name);
                userInfoBean.setSchool(school);
                userInfoBean.setNotPay(notPay);
                userInfoBean.setReadyPay(readyPay);
                userInfoBean.setReaderKind(readerKind);

                DatabaseUtils.getHelper(activity, "UserInfo.db").save(userInfoBean);

                if (!activity.isDestroyed())
                    Glide.with(activity).load(Urls.Url_Default_Header).into(iMineFragmentView.getUserHeader());
                iMineFragmentView.getUserNumber().setText(userInfoBean.getNumber());
                iMineFragmentView.getUserScore().setText(score);
                iMineFragmentView.getUserName().setText(name);

                textViews1.get(0).setText("读者证号：" + userInfoBean.getNumber());
                textViews1.get(1).setText("证件状态：" + userInfoBean.getCanUse());
                textViews1.get(2).setText("证有效期：" + userInfoBean.getUseData());
                textViews1.get(3).setText("馆际读者：" + userInfoBean.getIsActionReader());
                textViews1.get(4).setText("当前借阅：" + userInfoBean.getCanIn());


                textViews2.get(0).setText("开户地点：" + userInfoBean.getSchool());
                textViews2.get(1).setText("服务欠款：" + userInfoBean.getNotPay());
                textViews2.get(2).setText("馆预付款：" + userInfoBean.getReadyPay());
                textViews2.get(3).setText("读者类型：" + userInfoBean.getReaderKind());
                textViews2.get(4).setText("馆际借阅：" + userInfoBean.getCanInAction());


            }
            @Override
            public void onFailure(String error) {
                iMineFragmentView.getSmartRefreshLayout().finishRefresh();
                if (!error.equals(OkhttpError.ERROR_INTERNET) && !error.equals(OkhttpError.ERROR_SCHOOL_NETWORK_URL))
                getUserInfo(activity, iMineFragmentView);
            }
        });
    }
}
