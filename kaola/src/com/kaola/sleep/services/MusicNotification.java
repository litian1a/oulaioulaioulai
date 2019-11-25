package com.sleep.kaola.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.sleep.kaola.MyApplication;
import com.sleep.kaola.R;
import com.sleep.kaola.activity.HomeActivity;
import com.sleep.kaola.http.model.MusicListModel;
import com.sleep.kaola.http.utils.Constans;

/**
 * Created by dingmouren on 2017/1/20.
 * 音乐播放的自定义通知栏
 */

public class MusicNotification extends Notification {
    private static final String TAG = MusicNotification.class.getName();
    private static MusicNotification musicNotification = null;//饿汉式实现单例模式加载，加载比较慢，但运行时获取对象速度快，线程安全
    private final int NOTIFICATION_ID = 10001;//通知id
    private Notification notification = null;//通知对象，不知道做什么用的
    private NotificationManager notificationManager = null;//通知管理器
    private Builder builder = null;//建造者对象
    private Context context;//上下文对象
    private RemoteViews remoteViews;//远程视图对象
    private RemoteViews big_remoteViews;//远程视图对象
    private final int REQUEST_CODE = 30000;
    //给service发送的广播
    private final String MUSIC_NOTIFICATION_ACTION_PLAY = "musicnotificaion.To.PLAY";
    private final String MUSIC_NOTIFICATION_ACTION_NEXT = "musicnotificaion.To.NEXT";
    private final String MUSIC_NOTIFICATION_ACTION_CLOSE = "musicnotificaion.To.CLOSE";
    private final String MUSIC_NOTIFICATION_ACTION_LAST = "musicnotificaion.To.LAST";
    private final int MUSIC_NOTIFICATION_VALUE_PLAY = 30001;
    private final int MUSIC_NOTIFICATION_VALUE_NEXT = 30002;
    private final int MUSIC_NOTIFICATION_VALUE_CLOSE =30003;
    private final int MUSIC_NOTIFICATION_VALUE_Last =30004;
    private Intent playIntent = null,nextIntent = null,lastIntent = null,closeIntent = null,backIntent = null;//播放、下一首、关闭的意图对象
    private MusicService mService;

    public void setService(MusicService service){
        this.mService = service;
    }


    /**
     * 私有化构造函数
     */
    private MusicNotification(){
        Log.e(TAG,"创建MusicNotification对象");
        this.context = MyApplication.getAppContext();
        remoteViews = new RemoteViews(MyApplication.getAppContext().getPackageName(), R.layout.notification_layout);//初始化远程视图对象，使用自定义的通知布局
        big_remoteViews = new RemoteViews(MyApplication.getAppContext().getPackageName(), R.layout.remote_view_music_player);//初始化远程视图对象，使用自定义的通知布局
    
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Builder(context);//初始化建造者对象，
    
        if (Build.VERSION.SDK_INT  >= 26){
    
            NotificationChannel notificationChannel = new NotificationChannel(context.getPackageName(), context.getPackageName(), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setVibrationPattern(new long[]{0l});
            notificationChannel.setSound(null,null);
            if (notificationManager != null) notificationManager.createNotificationChannel(notificationChannel);
            builder = new Builder(context,context.getPackageName());//初始化建造者对象，
    
        }

        //初始化控制的意图intent
            playIntent = new Intent();
        playIntent.setAction(MUSIC_NOTIFICATION_ACTION_PLAY);
        nextIntent = new Intent();
        nextIntent.setAction(MUSIC_NOTIFICATION_ACTION_NEXT);
        lastIntent = new Intent();
        lastIntent.setAction(MUSIC_NOTIFICATION_ACTION_LAST);
        closeIntent = new Intent();
        closeIntent.setAction(MUSIC_NOTIFICATION_ACTION_CLOSE);
        

        backIntent = new Intent(MyApplication.getAppContext(), HomeActivity.class);
        backIntent.setAction(Constans.ACTION_MUSIC_NOTIFY);
    }

    /**
     * 获取自定义通知对象，饿汉式实现单例模式
     * @return
     */
    public static MusicNotification getMusicNotification(){
        Log.e(TAG,"获取MusicNotification对象");
        if (musicNotification == null){
            musicNotification = new MusicNotification();
        }
        return musicNotification;
    }

    /**
     * 初始化自定义通知
     */
    public void onCreateMusicNotification(){
        Log.e(TAG,"初始化MusicNotification对象");
        //注册点击事件
        //1.播放事件
        playIntent.putExtra("type",MUSIC_NOTIFICATION_VALUE_PLAY);
        PendingIntent pendingPlayIntent = PendingIntent.getBroadcast(context,REQUEST_CODE,playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.button_play_toggle,pendingPlayIntent);
        big_remoteViews.setOnClickPendingIntent(R.id.button_play_toggle,pendingPlayIntent);
        //2.下一首事件
        nextIntent.putExtra("type",MUSIC_NOTIFICATION_VALUE_NEXT);
        PendingIntent pendingNextIntent = PendingIntent.getBroadcast(context,REQUEST_CODE,nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.image_view_play_next,pendingNextIntent);
        big_remoteViews.setOnClickPendingIntent(R.id.image_view_play_next,pendingNextIntent);
        //2.上一首事件
        lastIntent.putExtra("type",MUSIC_NOTIFICATION_VALUE_Last);
        PendingIntent pendingLastIntent = PendingIntent.getBroadcast(context,REQUEST_CODE,lastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.image_view_play_last,pendingLastIntent);
        big_remoteViews.setOnClickPendingIntent(R.id.image_view_play_last,pendingLastIntent);
        //3.关闭通知事件
        closeIntent.putExtra("type",MUSIC_NOTIFICATION_VALUE_CLOSE);
        PendingIntent pendingCloseIntent = PendingIntent.getBroadcast(context,REQUEST_CODE,closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.image_view_close,pendingCloseIntent);
        big_remoteViews.setOnClickPendingIntent(R.id.image_view_close,pendingCloseIntent);
        //4.点击通知返回App
        PendingIntent pendingBackIntent = PendingIntent.getActivity(MyApplication.getAppContext(),REQUEST_CODE,backIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent big_r = PendingIntent.getActivity(MyApplication.getAppContext(),REQUEST_CODE,backIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.layout_root,pendingBackIntent);
        big_remoteViews.setOnClickPendingIntent(R.id.layout_root,pendingBackIntent);
        if (Build.VERSION.SDK_INT >24) {
            builder.setOngoing(true)//表示正在运行的通知，常用于音乐播放或者文件下载
                    .setWhen(System.currentTimeMillis())
                    .setCustomBigContentView(big_remoteViews)
                    .setCustomContentView(remoteViews)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setSmallIcon(R.mipmap.ic_laucnher);
        }else {
            builder.setContent(remoteViews)
                    .setOngoing(true)//表示正在运行的通知，常用于音乐播放或者文件下载
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_laucnher);
        }

        notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;//将此通知放到通知栏的"Ongoing"，“正在运行”组中
//        notificationManager.notify(NOTIFICATION_ID,notification);//弹出通知
        
        mService.startForeground(NOTIFICATION_ID,notification);

    }

    /**
     * 更新通知
     */
    public void onUpdateMusicNotification(String  bean, boolean isplay){
        if (null == bean) return;
        MusicListModel.TagListBean tagListBean = MusicService.map.get(bean);
        //更新歌曲名称
        remoteViews.setTextViewText(R.id.text_view_name,(tagListBean.getMusicName() == null ? "" : tagListBean.getMusicName()));
        big_remoteViews.setTextViewText(R.id.text_view_name,(tagListBean.getMusicName() == null ? "" : tagListBean.getMusicName()));


        //更新播放状态：播放或者暂停
        if (isplay){
            remoteViews.setImageViewResource(R.id.image_view_play_toggle,R.drawable.ic_remote_view_pause);
            big_remoteViews.setImageViewResource(R.id.image_view_play_toggle,R.drawable.ic_remote_view_pause);
        }else {
            remoteViews.setImageViewResource(R.id.image_view_play_toggle,R.drawable.ic_remote_view_play);
            big_remoteViews.setImageViewResource(R.id.image_view_play_toggle,R.drawable.ic_remote_view_play);
        }
        onCreateMusicNotification();//弹出更新的通知
    }

    /**
     * 取消通知栏
     */
    public void onCancelMusicNotification(){
        Log.e(TAG,"销毁通知" );
//        notificationManager.cancel(NOTIFICATION_ID);
        mService.stopForeground(true);
    }

}
