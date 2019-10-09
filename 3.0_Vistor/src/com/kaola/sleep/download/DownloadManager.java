package com.kaola.sleep.download;

import android.content.Context;
import android.support.annotation.NonNull;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.UnifiedListenerManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Description：
 * Time：2019/4/11 14:19
 *
 * @author：ltc
 */
public class DownloadManager {
    
  
    
    
    private volatile static DownloadManager instance = null;
    
    
    private Map<String, DownloadTask> mTaskMap = new HashMap<>();
    private Map<String, UnifiedListenerManager> unifiedMap = new HashMap<>();

//    private UnifiedListenerManager mManager = new UnifiedListenerManager();
    
    
    private DownloadManager() {
    }
    
    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                    
                }
            }
        }
        return instance;
    }
    
    /**
     * 初始化
     *
     * @param context
     */
    public void init(final Context context) {
    
        
    }
    
    
    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件全路径 /0/cache/aa.apk
     * @param callBack 回调
     */
    public void downloadUrl(String url, String path, @NonNull final DownLoadCallback callBack) {
        DownloadTask task = getBuild(url, path);
        UnifiedListenerManager manager = new UnifiedListenerManager();
        task.setTag(url);
        manager.enqueueTaskWithUnifiedListener(task,callBack);
        mTaskMap.put(url,task);
        unifiedMap.put(url,manager);
    }
    
    public void pause(String url){
        DownloadTask task = mTaskMap.get(url);
        if (task != null){
            task.cancel();
        }
    }
    
    public void cancel(String url){
        DownloadTask task = mTaskMap.get(url);
        if (task != null){
            task.cancel();
            OkDownload.with().breakpointStore().remove(task.getId());
        }
    }
    
   
    
    public void binCallback(String url, String path, DownLoadCallback callBack) {
        UnifiedListenerManager unifiedListenerManager = unifiedMap.get(url);
        DownloadTask task = mTaskMap.get(url);
        if (unifiedListenerManager !=null && task != null) {
            unifiedListenerManager.attachListener(task,callBack);
        }
    }
    
    
    public DownloadTask getBuild(String url, String path) {
        return new DownloadTask.Builder(url, new File(path))
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(200)
                // ignore the same task has already completed in the past.
                .build();
    }
    
}
