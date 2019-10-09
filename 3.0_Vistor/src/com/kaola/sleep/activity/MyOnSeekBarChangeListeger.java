package com.kaola.sleep.activity;

import android.os.Message;
import android.os.RemoteException;
import android.widget.SeekBar;

import com.kaola.sleep.services.Constant;

import java.lang.ref.WeakReference;

/**
 * Created by dingmouren on 2017/2/13.
 */

public class MyOnSeekBarChangeListeger implements SeekBar.OnSeekBarChangeListener {
    private WeakReference<HomeActivity> weakActivity;
    public MyOnSeekBarChangeListeger(HomeActivity activity) {
        weakActivity = new WeakReference<HomeActivity>(activity);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        HomeActivity activity = weakActivity.get();
        if (fromUser && null != activity) {//判断来自用户的滑动
            activity.mPercent = (float) progress * 100 / (float) activity.sb_progress.getMax();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //用户松开SeekBar，通知MediaPlayerService更新播放器的进度，解决拖动过程中卡顿的问题
        HomeActivity activity = weakActivity.get();
        if (null != activity) {
            Message msgToMediaPlayerService = Message.obtain();
            msgToMediaPlayerService.what = Constant.PLAYING_ACTIVITY_CUSTOM_PROGRESS;
            msgToMediaPlayerService.arg1 = (int) activity.mPercent;
            try {
                activity.mServiceMessenger.send(msgToMediaPlayerService);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
