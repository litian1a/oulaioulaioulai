package com.biyi.hypnosis.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyi.hypnosis.R;

/**
 * Description：
 * Time：2019-09-23 21:03
 *
 * @author：ltc
 */
public abstract class BaseActivity extends AppCompatActivity {
    
    private ImageView mBack;
    private TextView mTextView;
    
    private static float sNoncomapatDensity;
    /**
     * 字体的缩放因子，正常情况下和density相等
     */
    private static float sNoncomapatScaledDensity;
    /**
     * 屏幕密度
     */
    private static int sNoncomapatDensityDpi;
    /**
     * 参考屏幕宽度dp值
     * 参考 720 * 1280 的m屏幕宽度
     */
    private static int CONTRASTD = 400;
    public static int targetDensity;
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setCustomDensity(this,getApplication());
        setContentView(getLayoutId());
        
        mBack = findViewById(R.id.iv_back);
        mTextView = findViewById(R.id.tv_title);
    
        if (mBack != null){
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
        
                }
            });
        }
    
    
    
    
    
    }
    
    /**
     * 设置自定义dpi值
     * @param application
     */
    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        DisplayMetrics activityDisplayMetrice = activity.getResources().getDisplayMetrics();
        // 获取通常的屏幕参数
        if (sNoncomapatDensity == 0) {
            sNoncomapatDensity = appDisplayMetrics.density;
            sNoncomapatScaledDensity = appDisplayMetrics.scaledDensity;
            sNoncomapatDensityDpi = appDisplayMetrics.densityDpi;
        }
        
        if (activityDisplayMetrice.density != sNoncomapatDensity) return;
        
        // 设备真实宽度 / 360dp ,得出为期望1dp所展示的px值
        targetDensity = appDisplayMetrics.widthPixels / CONTRASTD;
        /*
            根据公式 :  px = dp * ( dpi / 160);
            1dp所展示px值为 : targetDensity = dpi /160 ;
            所期望dpi为 如下 :
         */
        int targetDensityDpi = (int) (targetDensity * 160);
        /*
            sd/d = tsd/td
            tsd = td * (sd/d)
         */
        float targetScaledDensity = targetDensity * (sNoncomapatScaledDensity / sNoncomapatDensity);

//        appDisplayMetrics.density = targetDensity;
//        appDisplayMetrics.scaledDensity = targetScaledDensity;
//        appDisplayMetrics.densityDpi = targetDensityDpi;
        
        
        activityDisplayMetrice.densityDpi = targetDensityDpi;
        activityDisplayMetrice.density = targetDensity;
        activityDisplayMetrice.scaledDensity = targetScaledDensity;
    }
    
    abstract int  getLayoutId();
    
    public  void  setTittle (String tittle){
        if (mTextView != null){
            mTextView.setText(tittle);
        }
    }
}
