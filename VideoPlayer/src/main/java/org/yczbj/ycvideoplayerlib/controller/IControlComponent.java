package org.yczbj.ycvideoplayerlib.controller;

import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 自定义控制器接口
 *     revise:
 * </pre>
 */
public interface IControlComponent {

    /**
     * 这个是绑定视图操作
     * @param controlWrapper                自定义控制器包装类
     */
    void attach(@NonNull ControlWrapper controlWrapper);

    /**
     * 获取该自定义视图view对象
     * @return                              视图view对象
     */
    View getView();

    /**
     * 视图显示发生变化监听
     * @param isVisible                     是否可见
     * @param anim                          动画
     */
    void onVisibilityChanged(boolean isVisible, Animation anim);

    /**
     * 播放器状态变化监听
     * @param playState                     状态
     */
    void onPlayStateChanged(int playState);

    void onPlayerStateChanged(int playerState);

    /**
     * 设置进度操作
     * @param duration                      时间
     * @param position                      进度position
     */
    void setProgress(int duration, int position);

    /**
     * 锁屏状态监听
     * @param isLocked                      是否锁屏
     */
    void onLockStateChanged(boolean isLocked);

}
