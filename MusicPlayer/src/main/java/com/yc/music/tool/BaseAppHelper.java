package com.yc.music.tool;

import android.annotation.SuppressLint;

import com.yc.music.model.AudioBean;
import com.yc.music.service.PlayService;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/03/22
 *     desc  : BaseAppHelper
 *     revise:
 * </pre>
 */
public class BaseAppHelper {

    /**
     * 播放音乐service
     */
    private PlayService mPlayService;
    /**
     * 本地歌曲列表
     */
    private final List<AudioBean> mMusicList = new ArrayList<>();

    private BaseAppHelper() {
        //这里可以做一些初始化的逻辑
    }

    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private final static BaseAppHelper INSTANCE = new BaseAppHelper();
    }

    public static BaseAppHelper get() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取PlayService对象
     * @return              返回PlayService对象
     */
    public PlayService getPlayService() {
        return mPlayService;
    }

    /**
     * 设置PlayService服务
     */
    public void setPlayService(PlayService service) {
        mPlayService = service;
    }

    /**
     * 获取扫描到的音乐数据集合
     * @return              返回list集合
     */
    public List<AudioBean> getMusicList() {
        return mMusicList;
    }


    /**
     * 设置音频结合
     * @param list              音频集合
     */
    public void setMusicList(List<AudioBean> list) {
        mMusicList.clear();
        mMusicList.addAll(list);
    }

    /**
     * 获取到播放音乐的服务
     * @return              PlayService对象
     */
    public PlayService getMusicService () {
        PlayService playService = BaseAppHelper.get().getPlayService();
        if (playService == null) {
            //待解决：当长期处于后台，如何保活？避免service被杀死……
            throw new NullPointerException("play service is null");
        }
        return playService;
    }

}
