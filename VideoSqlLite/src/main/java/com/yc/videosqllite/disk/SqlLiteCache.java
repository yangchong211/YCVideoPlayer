package com.yc.videosqllite.disk;

import com.yc.videosqllite.manager.CacheConfig;
import com.yc.videosqllite.model.VideoLocation;
import com.yc.videosqllite.utils.CacheLogUtils;
import com.yc.videosqllite.utils.VideoMd5Utils;

import java.io.File;
import java.io.IOException;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2020/8/6
 *     desc  : 磁盘缓存工具
 *     revise:
 * </pre>
 */
public class SqlLiteCache {

    private CacheConfig cacheConfig;
    private DiskLruCache diskLruCache;

    public SqlLiteCache(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
        File path = DiskFileUtils.getFilePath(cacheConfig.getContext());
        String pathString = path.getPath();
        CacheLogUtils.d("SqlLiteCache-----pathString-"+pathString);
        try {
            diskLruCache = DiskLruCache.open(path,1,1,cacheConfig.getCacheMax());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存数据
     * @param url                           链接
     * @param location                      视频数据
     */
    public synchronized void put(String url , VideoLocation location){
        if (url==null || url.length()==0){
            return;
        }
        if (location==null){
            return;
        }

        if (diskLruCache!=null){
            try {
                String key = VideoMd5Utils.encryptMD5ToString(url, cacheConfig.getSalt());
                CacheLogUtils.d("SqlLiteCache-----put--key--"+key);
                DiskLruCache.Editor editor = diskLruCache.edit(key);
                if (editor!=null){
                    String json = location.toJson();
                    editor.set(0,json);
                    CacheLogUtils.d("SqlLiteCache-----put--json--"+json);
                    editor.commit();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 取数据
     * @param url                           链接
     * @return
     */
    public synchronized long get(String url){
        if (url==null || url.length()==0){
            return -1;
        }
        if (diskLruCache!=null){
            String key = VideoMd5Utils.encryptMD5ToString(url, cacheConfig.getSalt());
            CacheLogUtils.d("SqlLiteCache-----get--key-"+key);
            try {
                DiskLruCache.Value value = diskLruCache.get(key);
                if (value != null) {
                    File file = value.getFile(0);
                    long length = value.getLength(0);
                    String string = value.getString(0);
                    VideoLocation location = VideoLocation.toObject(string);
                    long position = location.getPosition();
                    CacheLogUtils.d("SqlLiteCache-----get---"+file+"-------"+length+"-------"+string+"-----"+position);
                    return position;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    /**
     * 移除数据
     * @param url                           链接
     * @return
     */
    public synchronized boolean remove(String url){
        if (diskLruCache!=null){
            String key = VideoMd5Utils.encryptMD5ToString(url, cacheConfig.getSalt());
            try {
                boolean remove = diskLruCache.remove(key);
                return remove;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 是否包含
     * @param url                           链接
     * @return
     */
    public synchronized boolean containsKey(String url){
        if (diskLruCache!=null){
            String key = VideoMd5Utils.encryptMD5ToString(url, cacheConfig.getSalt());
            try {
                DiskLruCache.Value value = diskLruCache.get(key);
                return value != null;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    /**
     * 清楚所有数据
     * @return                              是否清楚完毕
     */
    public synchronized boolean clearAll(){
        if (diskLruCache!=null){
            try {
                diskLruCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


}
