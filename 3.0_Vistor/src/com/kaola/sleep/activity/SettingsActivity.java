package com.kaola.sleep.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaola.sleep.R;
import com.kaola.sleep.http.RetrofitManager;
import com.kaola.sleep.http.model.CheckVerModel;
import com.kaola.sleep.http.rxjava.TransformUtils;
import com.kaola.sleep.utils.SpUtils;
import com.kaola.sleep.utils.UpdateDialog;

import rx.Observer;

/**
 * 设置Activity
 */
public class SettingsActivity extends BaseActivity {

    private ImageView iv_back,iv_switchon;
    private TextView tvp1,tvp2;
    private LinearLayout ll_evaluate,ll_update;
    private RelativeLayout rl_riseup;
    public static void startActivity(Context context){
           Intent intent= new Intent(context,SettingsActivity.class);
           context.startActivity(intent);
           
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    @Override
    int getLayoutId() {
        return R.layout.activity_settings;
    }
    
    /**
     * 初始化控件
     */
    private void initView() {
        ll_evaluate = findViewById(R.id.ll_feeckback);
        ll_update = findViewById(R.id.ll_update);
        tvp1 = findViewById(R.id.tvp1);
        iv_switchon = findViewById(R.id.iv_switchon);
        iv_switchon.setSelected(SpUtils.getBoolean(SpUtils.KEY_TAG_CLOCKSWIFT,false));
        tvp1.setText(Html.fromHtml("<u>"+"用户条款 & 隐私协议"+"</u>"));
        tvp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://app111.upapp.cc/private.html"));
                intent.setAction(Intent.ACTION_VIEW);
                startActivity(intent);
            }
        });
//        tvp2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setData(Uri.parse("https://app111.upapp.cc/private.html"));
//                intent.setAction(Intent.ACTION_VIEW);
//                startActivity(intent);
//            }
//        });
        ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitManager.getAppApi(SettingsActivity.this)
                            .getAppStoreService()
                            .requestCheckVer()
                            .compose(TransformUtils.<CheckVerModel>defaultSchedulers())
                            .subscribe(new Observer<CheckVerModel>() {
                                @Override
                                public void onCompleted() {
        
                                }
    
                                @Override
                                public void onError(Throwable e) {
        
                                }
    
                                @Override
                                public void onNext(final CheckVerModel checkVerModel) {
                                    if (checkVerModel != null && checkVerModel.getUpdate() == 1) {
                                        String des = checkVerModel.getMessage();
                                        final UpdateDialog instance = UpdateDialog.getInstance(SettingsActivity.this, R.style.UpdateDialog, des, "以后再说", "立即更新", new UpdateDialog.OnCustomDialogListener() {
                                                    @Override
                                                    public void refuseUpdate(String name) {
    
                                                        String downloadUrl = checkVerModel.getDownloadUrl();
                                                        if (!downloadUrl.startsWith("http")){
                                                            downloadUrl =  "http://"+downloadUrl;
                                                        }
                                                        Uri uri = Uri.parse(downloadUrl);
                                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                        startActivity(intent);
                                                    }
                
                                                    @Override
                                                    public void doUpdate(String name) {
                                                    
                    
                                                    }
                                                }
    
                                        );
                                        instance.show();
                                    }else {
                                        com.kaola.sleep.utils.ToastUtils.getInstance().showToast(SettingsActivity.this,"已经是最新版本了,无需更新");
//                                        ToastUtils.show(SettingsActivity.this,"已经是最新版本了,无需更新");
                                    }
                                }
                            });
               
//                ToastUtils.getInstance().showToast(getApplicationContext(), "暂无最新版本");
            }
        });
        iv_switchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean switching = !iv_switchon.isSelected();
                iv_switchon.setSelected(switching);
                SpUtils.putBoolean(SpUtils.KEY_TAG_CLOCKSWIFT,switching);
            }
        });
        ll_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,FeedbackActivity.class);
                startActivity(intent);

            }
        });
        setTittle("设置");

    }




}
