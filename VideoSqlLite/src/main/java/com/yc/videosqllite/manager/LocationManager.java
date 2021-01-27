package com.yc.videosqllite.manager;

import com.yc.videosqllite.cache.VideoMapCache;
import com.yc.videosqllite.dao.SqlLiteCache;
import com.yc.videosqllite.model.VideoLocation;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2020/8/6
 *     desc  : 音视频播放记录本地缓存
 *     revise:
 * </pre>
 */
public class LocationManager {

    /**
     * 终极目标
     * 1.开发者可以自由切换缓存模式
     * 2.可以设置内存缓存最大值，设置磁盘缓存的路径
     * 3.能够有增删改查基础方法
     * 4.多线程下安全和脏数据避免
     * 5.代码体积小
     * 6.一键打印存取表结构日志
     * 7.如何一键将本地记录数据上传
     * 8.拓展性和封闭性
     * 9.性能，插入和获取数据，超1000条数据测试
     * 10.将sql执行sql语句给简化，避免手写sql语句，因为特别容易出问题。而且存取bean如果比较复杂那很难搞
     */

    private CacheConfig cacheConfig;
    /**
     * 内存缓存
     */
    private VideoMapCache videoMapCache;
    /**
     * 磁盘缓存
     */
    private SqlLiteCache sqlLiteCache;

    private static class ManagerHolder {
        private static final LocationManager INSTANCE = new LocationManager();
    }

    public static LocationManager getInstance() {
        return ManagerHolder.INSTANCE;
    }

    public void init(CacheConfig cacheConfig){
        this.cacheConfig = cacheConfig;
        videoMapCache = new VideoMapCache(cacheConfig);
        sqlLiteCache = new SqlLiteCache(cacheConfig);
    }

    /**
     * 存数据
     * @param url                           链接
     * @param location                      视频数据
     */
    public synchronized void put(String url , VideoLocation location){
        videoMapCache.put(url,location);
    }

    /**
     * 取数据
     * @param url                           链接
     * @return
     */
    public synchronized long get(String url){
        long position = videoMapCache.get(url);
        return position;
    }

    /**
     * 移除数据
     * @param url                           链接
     * @return
     */
    public synchronized boolean remove(String url){
        boolean remove = videoMapCache.remove(url);
        return remove;
    }

    /**
     * 是否包含
     * @param url                           链接
     * @return
     */
    public synchronized boolean containsKey(String url){
        boolean containsKey = videoMapCache.containsKey(url);
        return containsKey;
    }

    /**
     * 清楚所有数据
     * @return                              是否清楚完毕
     */
    public synchronized boolean clearAll(){
        boolean clearAll = videoMapCache.clearAll();
        return clearAll;
    }

}
