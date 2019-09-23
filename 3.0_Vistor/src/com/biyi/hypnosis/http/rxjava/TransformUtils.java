/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
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
package com.biyi.hypnosis.http.rxjava;

import com.biyi.hypnosis.MyApplication;
import com.biyi.hypnosis.http.model.Result;
import com.biyi.hypnosis.http.utils.NetUtils;
import com.biyi.hypnosis.http.utils.ToastUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by ltc
 */
public class TransformUtils {
    public static <T> Observable.Transformer<Result<T>, T> defaultSchedulers() {
        return new Observable.Transformer<Result<T>, T>() {
    
            @Override
            public Observable<T> call(Observable<Result<T>> resultObservable) {
                return resultObservable
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                      
                        .map(new ServerResultFunc<T>())
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
                            @Override
                            public Observable<? extends T> call(Throwable throwable) {
                                
                                return Observable.error(throwable);
                            }
                        });
            }
    
        };
    }
    
    private static class ServerResultFunc<T> implements Func1<Result<T>, T> {
        @Override
        public T call(Result<T> httpResult) {
            if (httpResult.code != 0) {
                if (NetUtils.isConnected(MyApplication.getAppContext())) {
                    ToastUtils.show(MyApplication.getAppContext(),"无网络");
                }else {
                    ToastUtils.show(MyApplication.getAppContext(),httpResult.msg);
    
        
                }
                throw new RuntimeException(httpResult.msg);
            }
            return httpResult.data;
        }
    }
}
