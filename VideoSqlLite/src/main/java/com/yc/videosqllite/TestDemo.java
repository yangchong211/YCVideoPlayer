package com.yc.videosqllite;

import android.content.Context;

import com.yc.videosqllite.manager.CacheConfig;
import com.yc.videosqllite.manager.LocationManager;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2020/6/16
 *     desc  : 使用代码案例
 *     revise:
 * </pre>
 */
public class TestDemo {

    private void init(Context context){
        CacheConfig cacheConfig = new CacheConfig();
        //设置配置是生效
        cacheConfig.setIsEffective(true);
        /*
         * 0，表示内存缓存
         * 1，表示磁盘缓存
         * 2，表示内存缓存+磁盘缓存
         */
        cacheConfig.setType(2);
        //设置上下文
        cacheConfig.setContext(context);
        //设置最大缓存
        cacheConfig.setCacheMax(1000);
        //设置是否打印log
        cacheConfig.setLog(false);
        //初始化
        LocationManager.getInstance().init(cacheConfig);


        //保存播放位置
//        VideoLocation location = new VideoLocation(url, currentPosition, duration);
//        LocationManager.getInstance().put(url,location);

        //获取播放位置
//        final long location = LocationManager.getInstance().get(url);

        //清除所有位置
//        LocationManager.getInstance().clearAll();
    }

}
