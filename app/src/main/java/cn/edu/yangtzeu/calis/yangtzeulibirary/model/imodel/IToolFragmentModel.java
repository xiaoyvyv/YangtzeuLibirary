package cn.edu.yangtzeu.calis.yangtzeulibirary.model.imodel;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import net.custom.MyGridView;

import cn.bingoogolapple.bgabanner.BGABanner;

public interface IToolFragmentModel {
    void fitGridView(AppCompatActivity context, MyGridView gridView);
    void fitBGABanner(AppCompatActivity context, BGABanner bgaBanner);
}
