package com.Spartan.ui.common;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Spartan.R;

/**
 * Created by melody
 * 2019/3/21 3:39 PM
 **/

public class CommonActionBar extends ConstraintLayout{
    private TextView title_text, left_text, right_text;
    private LinearLayout title_ll, left_ll, right_ll;
    private ImageView left_iv, right_iv;
    private String TAG;
    private View action_bar;


    public void initView(){
        final Context context = getContext();
        inflate(context, R.layout.common_actionbar,this);

        action_bar = findViewById(R.id.action_bar);

        TAG = this.getClass().getName();
        title_text = (TextView) findViewById(R.id.title_text);
        left_text = (TextView) findViewById(R.id.left_text);
        right_text = (TextView) findViewById(R.id.right_text);

        title_ll = (LinearLayout) findViewById(R.id.title_ll);
        left_ll = (LinearLayout) findViewById(R.id.left_ll);
        right_ll = (LinearLayout) findViewById(R.id.right_ll);

        left_iv = (ImageView) findViewById(R.id.left_icon);
        right_iv = (ImageView) findViewById(R.id.right_icon);

        if(context instanceof Activity){
            setLeftIconListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });
        }
    }


    public CommonActionBar(Context context){
        super(context);
        initView();

    }


    public void setLeftListener(OnClickListener cl){
        left_ll.setOnClickListener(cl);
    }
    public void setLeftIconListener(OnClickListener cl){
        left_iv.setOnClickListener(cl);
    }

    public void setRightListener(OnClickListener cl){
        right_ll.setOnClickListener(cl);
    }
    public void setRightIconListener(OnClickListener cl){
        right_iv.setOnClickListener(cl);
    }

    public void setTitle(CharSequence title){ title_text.setText(title); }
    public void setTitleColor(int color){ title_text.setTextColor(color); }
    public void setLeftText(CharSequence text){ left_text.setText(text); }
    public void setLeftTextColor(int color){ left_text.setTextColor(color); }
    public void setRightText(CharSequence text){ right_text.setText(text); }
    public void setRightTextColor(int color){ right_text.setTextColor(color); }

    public void setLeftView(int resId){ setView(R.id.left_icon, resId); }
    public void setLeftView(View v){ setView(R.id.left_icon, v); }
    public void setLeftViewVisiable(){ left_iv.setVisibility(VISIBLE); }

    public void setRightView(int resId){ setView(R.id.right_icon, resId); }
    public void setRightView(View v){ setView(R.id.right_icon, v); }
    public void setRightViewVisiable(){ right_iv.setVisibility(VISIBLE); }

    private void setView(int rootId, int layoutId){
        LinearLayout ll = findViewById(rootId);
        ll.removeAllViews();
        inflate(getContext(), layoutId, ll);
    }

    private void setView(int rootId, View v){
        LinearLayout ll = findViewById(rootId);
        ll.removeAllViews();
        ll.addView(v);
    }



}
