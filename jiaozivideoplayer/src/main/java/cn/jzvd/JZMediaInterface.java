package cn.jzvd;

import android.view.Surface;

/**
 * 自定义播放器
 */
public abstract class JZMediaInterface {

    //正在播放的当前url或uri
    protected Object currentDataSource;
    /**
     * 第一个是url的map
     * 第二个是loop
     * 第三个是header
     * 第四个是context
     */
    //包含了地址的map（多分辨率用），context，loop，header等
    Object[] dataSourceObjects;

    //开始播放
    public abstract void start();

    //准备播放
    public abstract void prepare();

    //暂停播放
    public abstract void pause();

    //是否播放
    public abstract boolean isPlaying();

    //滑动
    public abstract void seekTo(long time);

    //释放
    public abstract void release();

    //获取索引
    public abstract long getCurrentPosition();

    //获取时间
    public abstract long getDuration();

    //设置外观view
    public abstract void setSurface(Surface surface);
}
