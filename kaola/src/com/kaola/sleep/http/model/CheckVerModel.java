package com.sleep.kaola.http.model;

/**
 * Description：
 * Time：2019-09-23 17:50
 *
 * @author：ltc
 */
public class CheckVerModel {
    
    /**
     * update : 1
     * version : 0.0.2
     * message : 新版本发布，欢迎下载最新版本
     * downloadUrl : xxxx
     */
    
    private int update;
    private String version;
    private String message;
    private String downloadUrl;
    
    public int getUpdate() {
        return update;
    }
    
    public void setUpdate(int update) {
        this.update = update;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getDownloadUrl() {
        return downloadUrl;
    }
    
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
