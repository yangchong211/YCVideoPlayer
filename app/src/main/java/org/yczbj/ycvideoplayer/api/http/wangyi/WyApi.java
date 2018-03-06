package org.yczbj.ycvideoplayer.api.http.wangyi;


import org.yczbj.ycvideoplayer.ui.news.model.bean.NewsCommentBean;
import org.yczbj.ycvideoplayer.ui.video.model.bean.MultiNewsArticleBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 参考 :　https://github.com/hrscy/TodayNews/blob/master/news.json
 */

public interface WyApi {


    @GET("api/channelInfo?platformType=androidPhone&adapterNo=7.0.0&pid=&recommendNo=3,2&positionId=&pageSize=20&protocol=1.0.0")
    Observable<String> getTabs();

    @GET("api/homePageList?platformType=androidPhone&isNotModified=0&adapterNo=7.0.0&protocol=1.0.0")
    Observable<String> refreshVideos(@Query("channelId") int tabId,
                                     @Query("pageSize") int pageSize,
                                     @Query("requireTime") String requireTime);

    @GET("api/homePageList?platformType=androidPhone&isNotModified=0&adapterNo=7.0.0&protocol=1.0.0")
    Observable<String> loadMoreVideos(@Query("channelId") int tabId,
                                      @Query("pageSize") int pageSize,
                                      @Query("positionId") String positionId);

    /**
     * 网易视频
     */
    @GET("recommend/getChanListNews?channel=T1457068979049&fn=3&passport=h3o88AuDhdH7tlyrE3hlILX2WMCoMqapk08GhEzPqX4%3D&devId=DWT861zlolJo7mHnyynnGA%3D%3D&version=15.0&net=wifi&ts=1474185450&sign=JmMhXTnPo%2BqgTgwyxKstDgS9lmS5Pv%2BUCP5tZ%2FrWevV48ErR02zJ6%2FKXOnxX046I&encryption=1")
    Observable<String> getVideos(@Query("size") int size, @Query("offset") int offset);


    /**
     * 头条快报视频
     * http://video.toutiaokuaibao.com/app_video/getvideos
     */
    @Headers({/*"Content-Type: application/x-www-form-urlencoded;charset=UTF-8",*/
            "Accept-Encoding: gzip",
            "User-Agent: okhttp/3.2.0"})
    @FormUrlEncoded
    @POST("app_video/getvideos")
    Observable<String> getVideos(@FieldMap Map<String, String> fieldMap);
}
