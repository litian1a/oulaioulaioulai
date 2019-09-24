package com.biyi.hypnosis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    LoopView loopView_minu,loopViewtime;
    LoopView loopView_hour;
    private Button btn_clock;
    private RelativeLayout rl_rotatetime;
    private LinearLayout ll_getupsetting;

    private Button btn_clock2;
    ArrayList<String> list_minu1 = new ArrayList<String>();
    ArrayList<String> list_hour = new ArrayList<String>();
    ArrayList<String> list_minu = new ArrayList<String>();
    private Toast toast;
    int minu1;
    int hour;
    int minu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data2);
        initView();
        initData();
        initEvent();


        
    }
    
    @Override
    int getLayoutId() {
        return R.layout.activity_clock;
    }
    
    private void initView() {
//        ll_getupsetting = findViewById(R.id.ll_getupsetting);
        rl_rotatetime = findViewById(R.id.rl_rotatetime);
        btn_clock2 = findViewById(R.id.btn_clock2);
        btn_clock = findViewById(R.id.btn_clock);
        iv_rotate = findViewById(R.id.iv_rotate);
        loopViewtime = (LoopView) findViewById(R.id.loopViewtime);
        loopView_minu = (LoopView) findViewById(R.id.loopView_minu);
        loopView_hour = (LoopView) findViewById(R.id.loopView_hour);
        loopViewtime.bringToFront();
        setTittle("定时关闭");

    }

    private void initData() {
        btn_clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 闹钟显示时间
                btn_clock2.setBackgroundResource(R.drawable.cancle_clock);
            }
        });
        btn_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation  operatingAnim = AnimationUtils.loadAnimation(ClockViewActivity.this, R.anim.clock_bg);
                if (btn_clock.getText().equals("倒计时")){
                    LinearInterpolator lin = new LinearInterpolator();
                    if (operatingAnim != null) {
                        iv_rotate.startAnimation(operatingAnim);
                    }
                    operatingAnim.setInterpolator(lin);
//        开始动画
                    operatingAnim.startNow();
                    rl_rotatetime.setVisibility(View.GONE);
                    //TODO  倒计时功能
                    btn_clock.setText("结束");
                }else{
                    operatingAnim.cancel();
                    rl_rotatetime.setVisibility(View.VISIBLE);
                    btn_clock.setText("倒计时");
                }


            }
        });

        //设置是否不循环播放
//        loopView_year.setNotLoop();
//        year = getYear();
//        mooth = getMooth();
//        day = getDay();
//        minu1 = getMinu();

//        分的时间
        for (int i = 1; i <= 180; i++) {
            list_minu1.add(i+"");
        }
        //设置原始数据
        loopViewtime.setItems(list_minu1);
        loopViewtime.setCurrentPosition(0);
//        for (int i = 0; i < list_minu1.size(); i++) {
//            if (Integer.parseInt(list_minu1.get(i)) == getMinu()) {
//                loopViewtime.setCurrentPosition(i);
//            }
//        }
//
//
//        //月的时间
        for (int i = 1; i <= 24; i++) {
            list_hour.add("" + i);
        }
        //设置原始数据
        loopView_hour.setItems(list_hour);
        loopView_hour.setCurrentPosition(getMooth() - 1);

        //日的时间
        for (int i = 1; i <= 60; i++) {
            list_minu.add("" + i);
        }
//        //设置原始数据
        loopView_minu.setItems(list_minu);
        loopView_minu.setCurrentPosition(1);


    }

    private void initEvent() {
        //滚动监听
        loopViewtime.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
//                minu1 = Integer.parseInt(list_minu1.get(index));
//                if (toast == null) {
//                    toast = Toast.makeText(ClockViewActivity.this, minu1 + " min", Toast.LENGTH_SHORT);
//                }
                String minu = list_minu1.get(index);
//                toast.setText(min);
//                toast.show();
            }
        });

//        loopView_mooth.setListener(new OnItemSelectedListener() {
//            public void onItemSelected(int index) {
//                mooth = Integer.parseInt(list_mooth.get(index));
//            }
//        });
//
//        loopView_day.setListener(new OnItemSelectedListener() {
//            public void onItemSelected(int index) {
//                day = Integer.parseInt(list_day.get(index));
//            }
//        });
    }


    public void selectData(View view) {
//        Toast.makeText(this, "你选中的时间是：" + year + "年" + mooth + "月" + day + "日", Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取得当前时间的分
     *
     * @return
     */
    public int getMinu() {
        return TimeUtil.getTimeInt("m");
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