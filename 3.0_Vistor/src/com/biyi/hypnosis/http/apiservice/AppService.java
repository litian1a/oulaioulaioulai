package com.biyi.hypnosis.http.apiservice;

import com.biyi.hypnosis.http.model.CheckVerModel;
import com.biyi.hypnosis.http.model.FeedbackModel;
import com.biyi.hypnosis.http.model.RequestBean;
import com.biyi.hypnosis.http.model.Result;
import com.biyi.hypnosis.http.model.TagListModel;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Description：
 * Time：2019/4/8 15:48
 *
 * @author：ltc
 */
public  interface AppService {
   
    @Headers({"Content-Type:application/json"})
    @POST("api/taglist")
    Observable<Result<TagListModel>> requestTagList();
    
    @Headers({"Content-Type:application/json"})
    @POST("api/checkver")
    Observable<Result<CheckVerModel>> requestCheckVer();
    @FormUrlEncoded
    @POST("api/feedback")
    Observable<Result<FeedbackModel>> requestFeedback(@Field("info") String info);
    @FormUrlEncoded
    @POST("api/musiclist")
    Observable<Result<TagListModel>> requestMusiclist(@Field("tagId") int tagId);
    
    
}
