package com.Spartan.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.Spartan.R;
import com.Spartan.ui.common.CommonActionBar;

/**
 * Created by melody
 * 2019/3/21 2:58 AM
 **/

public abstract class BaseActivity extends AppCompatActivity {
    private CommonActionBar commonActionBar;
    private ConstraintLayout root_view;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().hide();

        commonActionBar = (CommonActionBar) findViewById(R.id.common_action_bar);
        root_view = (ConstraintLayout) findViewById(R.id.root_view);

        setContentViewByRes(getLayoutById());
        initView();
        initData();


        setActionBar();
    }

    private void setContentViewByRes(int res){
        if(root_view != null){
            root_view.removeAllViews();
            LayoutInflater.from(this).inflate(res, root_view);
        }
    }

    /**
     *  子类通过调用此方法设置layout
     * **/
    public abstract int getLayoutById();

    /**
     *  子类样式初始化
     * **/
    public abstract void initView();

    /**
     *  子类数据初始化
     * **/
    public abstract void initData();

    public void setActionBar(){

    };

    public CommonActionBar getCommonActionBar(){
        return commonActionBar;
    }

    public ConstraintLayout getRootView(){
        return root_view;
    }


}