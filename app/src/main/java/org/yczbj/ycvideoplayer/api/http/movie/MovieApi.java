package org.yczbj.ycvideoplayer.api.http.movie;

import org.yczbj.ycvideoplayer.ui.movie.model.MovieBean;
import org.yczbj.ycvideoplayer.ui.movie.model.MovieDetailBean;
import org.yczbj.ycvideoplayer.ui.video.model.bean.MultiNewsArticleBean;
import org.yczbj.ycvideoplayer.ui.video.model.bean.VideoContentBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface MovieApi {

    /**
     * 首页
     *
     * @return
     */
    @GET("homePageApi/homePage.do")
    Observable<MovieBean> getHomePage();

    /**
     * 影片详情
     *
     * @param mediaId 影片id
     * @return
     */
    @GET("videoDetailApi/videoDetail.do")
    Observable<MovieDetailBean> getVideoInfo(@Query("mediaId") String mediaId);

    /**
     * 影片分类列表
     *
     * @param catalogId
     * @param pnum
     * @return
     */
    @GET("columns/getVideoList.do")
    Observable<MovieBean> getVideoList(@Query("catalogId") String catalogId,
                                                         @Query("pnum") String pnum);

    /**
     * 影片搜索
     *
     * @param pnum
     * @return
     */
    @GET("searchKeyWordApi/getVideoListByKeyWord.do")
    Observable<MovieBean> getVideoListByKeyWord(@Query("keyword") String keyword,
                                                                  @Query("pnum") String pnum);

    /**
     * 获取评论列表
     * @param mediaId
     * @param pnum
     * @return
     */
    @GET("Commentary/getCommentList.do")
    Observable<MovieBean> getCommentList(@Query("mediaId") String mediaId,
                                                           @Query("pnum") String pnum);



}
