package org.yczbj.ycvideoplayer.api.http.wangyi;


import org.yczbj.ycvideoplayer.api.manager.RetrofitWrapper;
import org.yczbj.ycvideoplayer.ui.video.model.bean.MultiNewsArticleBean;

import io.reactivex.Observable;


/**
 * Created by PC on 2017/8/21.
 * 作者：PC
 */

public class WyModel {

    private static WyModel model;
    private WyApi mApiService;
    private static final String NETEASY_BASE_URL = "http://c.m.163.com/";
    private static final String TTKB_BASE_URL = "http://video.toutiaokuaibao.com/";
    private static final String IFENG_BASE_URL = "http://vcis.ifeng.com/";

    private WyModel(int type) {
        switch (type){
            case 1:
                mApiService = RetrofitWrapper
                        .getInstance(NETEASY_BASE_URL)
                        .create(WyApi.class);
                break;
            case 2:
                mApiService = RetrofitWrapper
                        .getInstance(TTKB_BASE_URL)
                        .create(WyApi.class);
                break;
            case 3:
                mApiService = RetrofitWrapper
                        .getInstance(IFENG_BASE_URL)
                        .create(WyApi.class);
                break;
            default:
                break;
        }
    }

    public static WyModel getInstance(int type){
        if(model == null) {
            model = new WyModel(type);
        }
        return model;
    }


}
