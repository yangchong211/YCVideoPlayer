/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.yc.video.old.controller;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.yc.video.config.ConstantKeys;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : 视频控制器接口
 *     revise: 定义一些设置视图属性接口
 * </pre>
 */
@Deprecated
public interface IVideoController {

    /**
     * 设置top到顶部的距离
     * @param top                   top距离
     */
    void setTopPadding(float top);

    /**
     * 设置横屏播放时，tv和audio图标是否显示
     * @param isVisibility1                 tv图标是否显示
     * @param isVisibility2                 audio图标是否显示
     */
    void setTvAndAudioVisibility(boolean isVisibility1, boolean isVisibility2);

    /**
     * 视频底图
     *
     * @param resId 视频底图资源
     */
    void setImage(@DrawableRes int resId);

    /**
     * 设置总时长
     */
    void setLength(long length);

    /**
     * 设置总时长
     */
    void setLength(String length);

    /**
     * 视频底图ImageView控件，提供给外部用图片加载工具来加载网络图片
     *
     * @return 底图ImageView
     */
    ImageView imageView();

    /**
     * 获取是否是锁屏模式
     * @return                      true表示锁屏
     */
    boolean getLock();

    /**
     * 设置是否显示视频头部的下载，分享布局控件
     * @param isVisibility          是否可见
     */
    void setTopVisibility(boolean isVisibility);

    /**
     * 设置不操作后，多久自动隐藏头部和底部布局
     * @param time                  时间
     */
    void setHideTime(long time);

    /**
     * 设置加载loading类型
     *
     * @param type 加载loading的类型
     *             目前1，是仿腾讯加载loading
     *             2，是转圈加载loading
     */
    void setLoadingType(@ConstantKeys.LoadingType int type);

    /**
     * 设置播放的视频的标题
     *
     * @param title 视频标题
     */
    void setTitle(String title);

    /**
     * 当播放器的播放状态发生变化，在此方法中国你更新不同的播放状态的UI
     *
     * @param playState 播放状态：
     */
    void onPlayStateChanged(@ConstantKeys.CurrentState int playState);

    /**
     * 当播放器的播放模式发生变化，在此方法中更新不同模式下的控制器界面。
     *
     * @param playMode 播放器的模式：
     */
    void onPlayModeChanged(@ConstantKeys.PlayMode int playMode);

    /**
     * 重置控制器，将控制器恢复到初始状态。
     */
    void reset();

    /**
     * 控制器意外销毁，比如手动退出，意外崩溃等等
     */
    void destroy();



    /**
     * 更新进度，包括更新网络加载速度
     */
    void updateNetSpeedProgress();

    /**
     * 更新进度，包括进度条进度，展示的当前播放位置时长，总时长等。
     */
    void updateProgress();

    /**
     * 手势左右滑动改变播放位置时，显示控制器中间的播放位置变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param duration            视频总时长ms
     * @param newPositionProgress 新的位置进度，取值0到100。
     */
    void showChangePosition(long duration, int newPositionProgress);

    /**
     * 手势左右滑动改变播放位置后，手势up或者cancel时，隐藏控制器中间的播放位置变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    void hideChangePosition();

    /**
     * 手势在右侧上下滑动改变音量时，显示控制器中间的音量变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param newVolumeProgress 新的音量进度，取值1到100。
     */
    void showChangeVolume(int newVolumeProgress);

    /**
     * 手势在左侧上下滑动改变音量后，手势up或者cancel时，隐藏控制器中间的音量变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    void hideChangeVolume();

    /**
     * 手势在左侧上下滑动改变亮度时，显示控制器中间的亮度变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param newBrightnessProgress 新的亮度进度，取值1到100。
     */
    void showChangeBrightness(int newBrightnessProgress);

    /**
     * 手势在左侧上下滑动改变亮度后，手势up或者cancel时，隐藏控制器中间的亮度变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    void hideChangeBrightness();

    /**
     * 当电量发生变化的时候，在此方法中国你更新不同的电量状态的UI
     *
     * @param batterState 电量状态
     */
    void onBatterStateChanged(int batterState);

}
