package com.Spartan;


import com.Spartan.ui.activity.BaseActivity;
import com.Spartan.ui.common.CommonActionBar;

public class MainActivity extends BaseActivity {

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void setActionBar() {
        super.setActionBar();
        CommonActionBar commonActionBar = getCommonActionBar();
        commonActionBar.setTitle("首页");
    }

    @Override
    public int getLayoutById() {
        return R.layout.activity_main;
    }

}
