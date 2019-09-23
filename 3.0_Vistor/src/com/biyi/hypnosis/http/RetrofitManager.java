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
import android.util.Log;
import android.util.SparseArray;


import com.biyi.hypnosis.http.apiservice.AppService;
import com.biyi.hypnosis.http.ssl.SSLSocketFactoryUtils;
import com.biyi.hypnosis.http.utils.Constans;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    
    private static String deviceKey ;
    
    
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
                            .addInterceptor(paramsInterceptor)
                            .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
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
                    .addQueryParameter("sign",encode(Constans.SIGN ))
                    .addQueryParameter("sys", String.valueOf(Constans.SYS))
                    .addQueryParameter(Constans.TIME, String.valueOf(System.currentTimeMillis()))
                    .addQueryParameter("ver",Constans.VER )
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
    public static String encode(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
            // if (type) {
            // return buf.toString(); // 32
            // } else {
            // return buf.toString().substring(8, 24);// 16
            // }
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
