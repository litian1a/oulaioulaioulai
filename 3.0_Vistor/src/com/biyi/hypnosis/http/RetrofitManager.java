/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.biyi.hypnosis.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;


import com.biyi.hypnosis.MyApplication;
import com.biyi.hypnosis.http.apiservice.AppService;
import com.biyi.hypnosis.http.ssl.SSLSocketFactoryUtils;
import com.biyi.hypnosis.http.utils.Constans;
import com.biyi.hypnosis.http.utils.NetUtils;
import com.biyi.hypnosis.utils.SpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.biyi.hypnosis.http.HostType.KAOLA;


/**
 * Created by ltc
 */
public class RetrofitManager {
    private static int CONNECTED_TIMEOUT = 30;
    private static int READ_TIMEOUT = 30;
    private static int WRITE_TIMEOUT = 30;
    private static Context context;
    private final Retrofit mRetrofit;
    private static ArrayList<Integer> cert = new ArrayList();
    
    private static String deviceKey;
    
    
    private String getHost(int hostType) {
        if (hostType == KAOLA) {
            return Constans.KAOLA;
        }
        return Constans.KAOLA;
    }
    
    
    private static volatile OkHttpClient sOkHttpClient;
    
    private static SparseArray<RetrofitManager> sRetrofitManager = new SparseArray<>();
    
    public RetrofitManager(@HostType.HostTypeChecker int hostType) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(getHost(hostType))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        
    }
    
    public AppService getAppStoreService() {
        
        return mRetrofit.create(AppService.class);
    }
    
    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                
                if (sOkHttpClient == null) {
    
    
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            try {
                                String text = URLDecoder.decode(message, "utf-8");
                                Log.i("OKHttp-----", text);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                Log.i("OKHttp-----", message);
                            }
                        }
                    });
                    sOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(CONNECTED_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory())
                            .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                            .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request request = chain.request();
                                    if ("POST".equals(request.method())) {
                                        JSONObject jsonObject = new JSONObject();
                                        int tagId = -1 ;
                                        String feekInfo = null;
                                        if (request.body() instanceof FormBody ){
                                            FormBody formBody = (FormBody) request.body();
                                            for (int i = 0; i < formBody.size(); i++) {
                                                if (formBody.encodedName(i).equals("tagId")){
                                                    tagId = Integer.parseInt(formBody.encodedValue(i));
                                                }
                                                if (formBody.encodedName(i).equals("info")){
                                                    feekInfo = formBody.encodedValue(i);
                                                }
                                            }
                                        }
                                        try {
                                            long time = System.currentTimeMillis() / 1000;
                                            jsonObject.put("sys", Constans.SYS);
                                            if (tagId != -1){
                                                jsonObject.put("tagId",tagId);
                                            }
                                            jsonObject.put("time", time);
                                            jsonObject.put("ver", Constans.VER);
                                            String string;
                                            if (tagId != -1) {
                                                 string = "8A164f54BcF" + "sys=" + Constans.SYS + "&tagId=" + tagId + "&time=" + time + "&ver=" + Constans.VER + "50B57478cd07FC8d3";
                                            }else {
                                                string = "8A164f54BcF" + "sys=" + Constans.SYS + "&time=" + time + "&ver=" + Constans.VER + "50B57478cd07FC8d3";
    
                                            }
                                            Log.i("OKHttp", "intercept: " + string);
                                            String sign = md5Hex(string);
                                            Log.i("OKHttp", "sign: " + sign);
    
                                            jsonObject.put("ch", MyApplication.instance.mChannel);
    
                                            jsonObject.put("sign", sign);
                                            if (!TextUtils.isEmpty(feekInfo)){
                                                jsonObject.put("info", feekInfo);
                                            }
                                            String key = request.url().toString();
    
    
                                            String value = SpUtils.getString(key);
                                            if (!NetUtils.isConnected(MyApplication.getAppContext())&&!TextUtils.isEmpty(value)){
                                                //构建一个新的response响应结果
                                                int maxStale = 60 * 60 * 24 * 28;
    
                                                return new Response.Builder()
                                                        .removeHeader("Pragma")
                                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                                        .body(ResponseBody.create(MediaType.parse("application/json"), value.getBytes()))
                                                        .message("111")
                                                        .request(request)
                                                        .protocol(Protocol.HTTP_1_1)
                                                        .code(200)
                                                        .build();
                                            }
    
                                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    
                                            request = request.newBuilder().post(body).build();
                                            Response proceed = chain.proceed(request);
                                            
                                            
                                            if (proceed !=null && proceed.code()%200 <10){
                                                ResponseBody responseBody = proceed.peekBody(1024 * 1024);
    
                                                SpUtils.putString(key,new String(responseBody.bytes()));
                                                
                                            }
                                            return proceed ;
    
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
    
                                    }
                                    return null ;
                                }
                            })
                            .build();
                }
            }
        }
        return sOkHttpClient;
    }
    
    
    private static final Interceptor paramsInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
    
            HttpUrl url;
            url = request.url().newBuilder() //请求尾部链接
                    .addQueryParameter("sys", String.valueOf(Constans.SYS))
                    .addQueryParameter("sign", Constans.SIGN2)
                    .addQueryParameter(Constans.TIME, String.valueOf(System.currentTimeMillis()))
                    .addQueryParameter("ver", Constans.VER)
                    .addQueryParameter("ch", "baidu")
                    .build();
            request = request.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        }
    };
    
    public static RetrofitManager getAppApi(Context context) {
        RetrofitManager.context = context.getApplicationContext();
        return getInstance(KAOLA);
    }
    
    private static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = sRetrofitManager.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(hostType);
            sRetrofitManager.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }
    
    /**
     * HexUtil类实现Hex(16进制字符串和)和字节数组的互转
     */
    @SuppressWarnings("unused")
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return new String(new HexUtil().encode(digest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "";
        }
    }
    
   
    
    public static class HexUtil {
        /**
         * 字节流转成十六进制表示
         */
        public static String encode(byte[] src) {
            String strHex = "";
            StringBuilder sb = new StringBuilder("");
            for (int n = 0; n < src.length; n++) {
                strHex = Integer.toHexString(src[n] & 0xFF);
                // 每个字节由两个字符表示，位数不够，高位补0
                sb.append((strHex.length() == 1) ? "0" + strHex : strHex);
            }
            return sb.toString().trim();
        }
    
        /**
         * 字符串转成字节流
         */
        public static byte[] decode(String src) {
            int m = 0, n = 0;
            int byteLen = src.length() / 2; // 每两个字符描述一个字节
            byte[] ret = new byte[byteLen];
            for (int i = 0; i < byteLen; i++) {
                m = i * 2 + 1;
                n = m + 1;
                int intVal = Integer.decode("0x" + src.substring(i * 2, m) +
                        src.substring(m, n));
                ret[i] = Byte.valueOf((byte) intVal);
            }
            return ret;
        }
    
    
    }
    }
