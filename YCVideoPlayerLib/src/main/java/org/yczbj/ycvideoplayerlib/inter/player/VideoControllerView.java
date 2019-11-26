package org.yczbj.ycvideoplayerlib.inter.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : 视频控制器抽象视图
 *     revise: 定义一些滑动改变属性的抽象方法
 * </pre>
 */
public abstract class VideoControllerView extends FrameLayout {

    public VideoControllerView(@NonNull Context context) {
        super(context);
    }

    /**
     * 更新进度，包括更新网络加载速度
     */
    protected abstract void updateNetSpeedProgress();

    /**
     * 更新进度，包括进度条进度，展示的当前播放位置时长，总时长等。
     */
    protected abstract void updateProgress();

    /**
     * 手势左右滑动改变播放位置时，显示控制器中间的播放位置变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param duration            视频总时长ms
     * @param newPositionProgress 新的位置进度，取值0到100。
     */
    protected abstract void showChangePosition(long duration, int newPositionProgress);

    /**
     * 手势左右滑动改变播放位置后，手势up或者cancel时，隐藏控制器中间的播放位置变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    protected abstract void hideChangePosition();

    /**
     * 手势在右侧上下滑动改变音量时，显示控制器中间的音量变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param newVolumeProgress 新的音量进度，取值1到100。
     */
    protected abstract void showChangeVolume(int newVolumeProgress);

    /**
     * 手势在左侧上下滑动改变音量后，手势up或者cancel时，隐藏控制器中间的音量变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    protected abstract void hideChangeVolume();

    /**
     * 手势在左侧上下滑动改变亮度时，显示控制器中间的亮度变化视图，
     * 在手势滑动ACTION_MOVE的过程中，会不断调用此方法。
     *
     * @param newBrightnessProgress 新的亮度进度，取值1到100。
     */
    protected abstract void showChangeBrightness(int newBrightnessProgress);

    /**
     * 手势在左侧上下滑动改变亮度后，手势up或者cancel时，隐藏控制器中间的亮度变化视图，
     * 在手势ACTION_UP或ACTION_CANCEL时调用。
     */
    protected abstract void hideChangeBrightness();

    /**
     * 当电量发生变化的时候，在此方法中国你更新不同的电量状态的UI
     *
     * @param batterState 电量状态
     */
    public abstract void onBatterStateChanged(int batterState);

}
