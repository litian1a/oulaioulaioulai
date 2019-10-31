package com.sleep.kaola.download;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sleep.kaola.MyApplication;
import com.sleep.kaola.http.utils.ToastUtils;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.util.List;
import java.util.Map;

/**
 * Description：
 * Time：2019/4/11 18:08
 *
 * @author：ltc
 */
public abstract class DownLoadCallback extends DownloadListener4WithSpeed {
    private long mTotalLength;
    private String TAG = "DownLoadCallback";
    
    @Override
    public void taskStart(@NonNull DownloadTask task) {
    
    }
    
    @Override
    public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
    
    }
    
    @Override
    public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
    
    }
    
    @Override
    public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
        mTotalLength = info.getTotalLength();
    }
    
    @Override
    public void progressBlock(@NonNull DownloadTask task, int blockIndex,
                              long currentBlockOffset,
                              @NonNull SpeedCalculator blockSpeed) {
    }
    
    
    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
        onProgress(currentOffset, mTotalLength);
        
        Log.i(TAG, "task：" + task.getUrl() + "     progress: " + currentOffset + "    total:" + mTotalLength);
    }
    
    @Override
    public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {
    
    }
    
    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
        Log.i(TAG, "taskEnd:     cause:" + cause + "   Exception" + realCause);
        if (realCause == null) {
            if (cause.equals(EndCause.COMPLETED)) {
                onSuccess(task.getUrl());
            }
        } else {
            ToastUtils.show(MyApplication.getAppContext(),"下载失败");
            onFail();
        }
        
    }
    
    public abstract void onProgress(long currentOffset, long mTotalLength);
    
    public abstract void onSuccess(String url);
    
    public abstract void onFail();
    
}
