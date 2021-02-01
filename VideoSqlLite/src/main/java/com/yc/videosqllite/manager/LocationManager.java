package com.yc.videosqllite.manager;

import android.content.Context;
import android.os.Debug;

import com.yc.videosqllite.cache.VideoMapCache;
import com.yc.videosqllite.disk.DiskFileUtils;
import com.yc.videosqllite.disk.SqlLiteCache;
import com.yc.videosqllite.model.VideoLocation;
import com.yc.videosqllite.utils.CacheLogUtils;

import java.io.IOException;

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

    /**
     * 内存缓存
     */
    private VideoMapCache videoMapCache;
    /**
     * 磁盘缓存
     */
    private SqlLiteCache sqlLiteCache;
    private CacheConfig cacheConfig;


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
        CacheLogUtils.setIsLog(cacheConfig.isLog());
    }

    /**
     * 存数据
     * @param url                           链接
     * @param location                      视频数据
     */
    public synchronized void put(String url , VideoLocation location){
        /*
         * type
         * 0，表示内存缓存
         * 1，表示磁盘缓存
         * 2，表示内存缓存+磁盘缓存
         */
        if (cacheConfig.getType() ==1){
            //存储到磁盘中
            sqlLiteCache.put(url,location);
        } else if (cacheConfig.getType() ==2){
            //存储到内存中
            videoMapCache.put(url,location);
            //存储到磁盘中
            sqlLiteCache.put(url,location);
        } else if (cacheConfig.getType()==0){
            //存储到内存中
            videoMapCache.put(url,location);
        } else {
            //存储到内存中
            videoMapCache.put(url,location);
        }
    }

    /**
     * 取数据
     * @param url                           链接
     * @return
     */
    public synchronized long get(String url){
        /*
         * type
         * 0，表示内存缓存
         * 1，表示磁盘缓存
         * 2，表示内存缓存+磁盘缓存
         */
        long position;
        if (cacheConfig.getType() ==1){
            //从磁盘中查找
            position = sqlLiteCache.get(url);
        } else if (cacheConfig.getType() ==2){
            //先从内存中找
            position = videoMapCache.get(url);
            if (position<0){
                //内存找不到，则从磁盘中查找
                position = sqlLiteCache.get(url);
            }
        } else if (cacheConfig.getType()==0){
            //先从内存中找
            position = videoMapCache.get(url);
        } else {
            //先从内存中找
            position = videoMapCache.get(url);
        }
        return position;
    }

    /**
     * 移除数据
     * @param url                           链接
     * @return
     */
    public synchronized boolean remove(String url){
        /*
         * type
         * 0，表示内存缓存
         * 1，表示磁盘缓存
         * 2，表示内存缓存+磁盘缓存
         */
        if (cacheConfig.getType() ==1){
            return sqlLiteCache.remove(url);
        } else if (cacheConfig.getType() ==2){
            boolean remove = videoMapCache.remove(url);
            boolean removeSql = sqlLiteCache.remove(url);
            return remove || removeSql;
        } else if (cacheConfig.getType()==0){
            return videoMapCache.remove(url);
        } else {
            return videoMapCache.remove(url);
        }
    }

    /**
     * 是否包含
     * @param url                           链接
     * @return
     */
    public synchronized boolean containsKey(String url){
        /*
         * type
         * 0，表示内存缓存
         * 1，表示磁盘缓存
         * 2，表示内存缓存+磁盘缓存
         */
        boolean containsKey;
        if (cacheConfig.getType() ==1){
            containsKey = sqlLiteCache.containsKey(url);
        } else if (cacheConfig.getType() ==2){
            containsKey = videoMapCache.containsKey(url);
            if (!containsKey){
                containsKey = sqlLiteCache.containsKey(url);
                return containsKey;
            }
        } else if (cacheConfig.getType()==0){
            containsKey = videoMapCache.containsKey(url);
        } else {
            containsKey = videoMapCache.containsKey(url);
        }
        return containsKey;
    }

    /**
     * 清楚所有数据
     * @return                              是否清楚完毕
     */
    public synchronized boolean clearAll(){
        /*
         * type
         * 0，表示内存缓存
         * 1，表示磁盘缓存
         * 2，表示内存缓存+磁盘缓存
         */
        if (cacheConfig.getType() ==1){
            return sqlLiteCache.clearAll();
        } else if (cacheConfig.getType() ==2){
            boolean clearAll = videoMapCache.clearAll();
            boolean all = sqlLiteCache.clearAll();
            return clearAll && all;
        } else if (cacheConfig.getType()==0){
            return videoMapCache.clearAll();
        } else {
            return videoMapCache.clearAll();
        }
    }

    /**
     * 获取当前应用使用的内存
     * @return
     */
    public long getUseMemory(){
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        //long maxMemory = Runtime.getRuntime().maxMemory();
        long useMemory = totalMemory - freeMemory;
        return useMemory;
    }

    /**
     * 设定内存的阈值
     * @param proportion                    比例
     * @return
     */
    public long setMemoryThreshold(int proportion){
        if (proportion<0 || proportion>10){
            proportion = 2;
        }
        long totalMemory = Runtime.getRuntime().totalMemory();
        long threshold = totalMemory / proportion;
        return threshold;
    }

    /**
     * 获取Java内存快照文件
     * @param context
     */
    public void dumpHprofData(Context context){
        String dump = DiskFileUtils.getPath(context, "dump");
        try {
            Debug.dumpHprofData(dump);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
