package cn.edu.yangtzeu.calis.yangtzeulibirary.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.edu.yangtzeu.calis.yangtzeulibirary.tools.Utils_Tool;

public abstract class BaseActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static Utils_Tool UtilsTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initMethod();
        UtilsTool = new Utils_Tool(this);
    }

    public abstract void initViews();
    public abstract void initMethod();

}
