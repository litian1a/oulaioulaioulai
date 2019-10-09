package com.kaola.sleep.http.model;

/**
 * Description：
 * Time：2019-09-23 16:17
 *
 * @author：ltc
 */
public class RequestBean {
    public String sign = "e6f47e91396a133f35ebee2fbe4d409a";
    public int sys = 1;
    public long time ;
    public String ver = "1.01";
    public String ch = "baidu";
    public RequestBean(){
        time =System.currentTimeMillis();
    }
}
