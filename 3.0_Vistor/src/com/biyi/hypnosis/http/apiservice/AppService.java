package com.biyi.hypnosis.http.apiservice;

import com.biyi.hypnosis.http.model.Result;
import com.biyi.hypnosis.http.model.TagListModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Description：
 * Time：2019/4/8 15:48
 *
 * @author：ltc
 */
public  interface AppService {
    @POST("api/taglist")
    Observable<Result<TagListModel>> requestTagList();
}
