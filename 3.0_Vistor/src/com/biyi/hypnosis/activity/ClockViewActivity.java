package com.biyi.hypnosis.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;


import com.biyi.hypnosis.R;
import com.biyi.hypnosis.loopview.LoopView;
import com.biyi.hypnosis.loopview.OnItemSelectedListener;
import com.biyi.hypnosis.utils.TimeUtil;

import java.util.ArrayList;

/**
 * 时间选择页面,第二种
 */
public class ClockViewActivity extends BaseActivity {

    private ImageView iv_rotate;
    LoopView loopView_year;
    LoopView mLoopView;
    LoopView loopView_day;

    ArrayList<String> list_year = new ArrayList<String>();
    ArrayList<String> list_mooth = new ArrayList<String>();
    ArrayList<String> list_day = new ArrayList<String>();

    int year;
    int mooth;
    int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data2);
        initView();
        initData();
//        initEvent();


        
    }
    
    @Override
    int getLayoutId() {
        return R.layout.activity_clock;
    }
    
    private void initView() {

        iv_rotate = findViewById(R.id.iv_rotate);
        mLoopView = (LoopView) findViewById(R.id.loopView);
//        loopView_day = (LoopView) findViewById(R.id.loopView_year);
        setTittle("定时关闭");
        ArrayList<String > list = new ArrayList<>();
        list.add("15");
        list.add("30");
        list.add("45");
        mLoopView.setScaleX(2f);
        mLoopView.setItems(list);
        mLoopView.setNotLoop();

    }

    private void initData() {
        Animation  operatingAnim = AnimationUtils.loadAnimation(this, R.anim.clock_bg);
        LinearInterpolator lin = new LinearInterpolator();
        if (operatingAnim != null) {
            iv_rotate.startAnimation(operatingAnim);
        }
        operatingAnim.setInterpolator(lin);
        operatingAnim.startNow();
        //设置是否不循环播放
//        loopView_year.setNotLoop();
//        year = getYear();
//        mooth = getMooth();
//        day = getDay();

        //年的时间
//        for (int i = 2000; i < 2031; i++) {
//            list_year.add("" + i);
//        }
//        //设置原始数据
//        loopView_year.setItems(list_year);
//        for (int i = 0; i < list_year.size(); i++) {
//            if (Integer.parseInt(list_year.get(i)) == getYear()) {
//                loopView_year.setCurrentPosition(i);
//            }
//        }
//
//
//        //月的时间
//        for (int i = 1; i < 13; i++) {
//            list_mooth.add("" + i);
//        }
//        //设置原始数据
//        mLoopView.setItems(list_mooth);
//        mLoopView.setCurrentPosition(getMooth() - 1);
//
//        //日的时间
//        for (int i = 1; i < 32; i++) {
//            list_day.add("" + i);
//        }
//        //设置原始数据
//        loopView_day.setItems(list_day);
//        loopView_day.setCurrentPosition(getDay() - 1);


    }

    private void initEvent() {
        //滚动监听
    
    }


    public void selectData(View view) {
        Toast.makeText(this, "你选中的时间是：" + year + "年" + mooth + "月" + day + "日", Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取得当前时间的天数
     *
     * @return
     */
    public int getDay() {
        return TimeUtil.getTimeInt("d");
    }


    /**
     * 获得当前时间的月份
     */
    public int getMooth() {
        return TimeUtil.getTimeInt("M");
    }

    /**
     * 获得当前时间的年份
     */
    public int getYear() {
        return TimeUtil.getTimeInt("yyyy");
    }

}