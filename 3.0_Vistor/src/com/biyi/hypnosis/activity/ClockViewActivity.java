package com.biyi.hypnosis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.biyi.hypnosis.R;
import com.biyi.hypnosis.loopview.LoopView;
import com.biyi.hypnosis.loopview.OnItemSelectedListener;
import com.biyi.hypnosis.utils.SpUtils;
import com.biyi.hypnosis.utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 时间选择页面,第二种
 */
public class ClockViewActivity extends BaseActivity {

    private ImageView iv_rotate;
    LoopView loopView_minu,loopViewtime;
    LoopView loopView_hour;
    private Button btn_clock;
    private RelativeLayout rl_rotatetime;
    private View ll_clock;

    private Button btn_clock2;
    ArrayList<String> list_minu1 = new ArrayList<String>();
    ArrayList<String> list_hour = new ArrayList<String>();
    ArrayList<String> list_minu = new ArrayList<String>();
    private Toast toast;
    int minu1;
    int hour;
    int minu;
    private String TAG = "ClockViewActivity";
    private TextView tvTime,tv_timetoast;
    private Timer mTimer;
    
    
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
        tv_timetoast = findViewById(R.id.tv_timetoast);
        ll_clock = findViewById(R.id.ll_getupsetting);//闹钟的UI
        rl_rotatetime = findViewById(R.id.rl_rotatetime);
        btn_clock2 = findViewById(R.id.btn_clock2);
        btn_clock = findViewById(R.id.btn_clock);
        iv_rotate = findViewById(R.id.iv_rotate);
        tvTime = findViewById(R.id.tv_time);
        loopViewtime = (LoopView) findViewById(R.id.loopViewtime);
        loopView_minu = (LoopView) findViewById(R.id.loopView_minu);
        loopView_hour = (LoopView) findViewById(R.id.loopView_hour);
        setTittle("定时关闭");


    }

    private void initData() {

//        if (SpUtils.getBoolean(SpUtils.KEY_TAG_CLOCKSWIFT,false)){
//            ll_clock.setVisibility(View.VISIBLE);
//        }else
//            ll_clock.setVisibility(View.GONE);
        btn_clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 闹钟显示时间
                if (btn_clock2.getText().equals("取消")){
                    btn_clock2.setText("开启");
                    tv_timetoast.setVisibility(View.INVISIBLE);
                    tv_timetoast.setText("");
                }else{
                    btn_clock2.setText("取消");
                    tv_timetoast.setVisibility(View.VISIBLE);
                    tv_timetoast.setText("闹钟将在1小时1分钟后唤醒");
                }

            }
        });
        btn_clock.setOnClickListener(new View.OnClickListener() {
    
            private Animation mOperatingAnim;
    
            @Override
            public void onClick(View view) {
                if (btn_clock.getText().equals("倒计时")){
                    mOperatingAnim = AnimationUtils.loadAnimation(ClockViewActivity.this, R.anim.clock_bg);
    
                    LinearInterpolator lin = new LinearInterpolator();
                    if (mOperatingAnim != null) {
                        iv_rotate.startAnimation(mOperatingAnim);
                    }
                    mOperatingAnim.setInterpolator(lin);
//        开始动画
                    mOperatingAnim.startNow();
                    rl_rotatetime.setVisibility(View.GONE);
                    int selectedItem = loopViewtime.getSelectedItem();
                    Log.i(TAG, "onClick: " +list_minu1.get(selectedItem));
                    //TODO  倒计时功能
                    tvTime.setVisibility(View.VISIBLE);
    
                    long countTime = System.currentTimeMillis() + Long.valueOf(list_minu1.get(selectedItem))*60*1000;
                    SpUtils.putLong(SpUtils.KEY_COUNT_DOWN_TIME, countTime);
                    try {
                        mTimer = new Timer();
                        mTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        long countTime = SpUtils.getLong(SpUtils.KEY_COUNT_DOWN_TIME);
                                        long time = countTime - System.currentTimeMillis() ;
                                        Log.i(TAG, "run: "+countTime  +" time = "+time);
                    
                                        if (time >0){
                                            String format = TimeUtil.DateFormatToString(time);
                                            Log.i(TAG, "run: "+format);
    
                                            tvTime.setVisibility(View.VISIBLE);

                                            tvTime.setText(format);
                        
                        
                                        }else {
                                            mTimer.cancel();
                                            iv_rotate.clearAnimation();
                                            rl_rotatetime.setVisibility(View.VISIBLE);
                                            tvTime.setVisibility(View.GONE);
                                        }
                                    }
                                });
            
            
                            }
                        },0,1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                  
                    btn_clock.setText("结束");
                }else{
                    mTimer.cancel();
                    iv_rotate.clearAnimation();
                    rl_rotatetime.setVisibility(View.VISIBLE);
                    tvTime.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mTimer != null)
//            mTimer.cancel();
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