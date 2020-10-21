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

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yc.video.tool.PlayerUtils;
import com.yc.video.old.player.OldVideoPlayer;
import com.yc.video.config.ConstantKeys;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 控制器抽象类
 *     revise:
 * </pre>
 */
@Deprecated
public abstract class AbsVideoPlayerController extends FrameLayout implements
        View.OnTouchListener, IVideoController {

    private Context mContext;
    protected OldVideoPlayer mVideoPlayer;
    //protected InterPropertyVideoPlayer mVideoPlayer;
    private Timer mUpdateProgressTimer;
    private TimerTask mUpdateProgressTimerTask;
    private Timer mUpdateNetSpeedTimer;
    private TimerTask mUpdateNetSpeedTask;
    private float mDownX;
    private float mDownY;
    /**
     * 是否需要改变播放的进度
     */
    private boolean mNeedChangePosition;
    /**
     * 是否需要改变播放的声音
     */
    private boolean mNeedChangeVolume;
    /**
     * 是否需要改变播放的亮度
     */
    private boolean mNeedChangeBrightness;
    private static final int THRESHOLD = 80;
    private long mGestureDownPosition;
    private float mGestureDownBrightness;
    private int mGestureDownVolume;
    private long mNewPosition;


    public AbsVideoPlayerController(Context context) {
        super(context);
        mContext = context;
        this.setOnTouchListener(this);
    }

    /**
     * 设置VideoPlayer
     * @param videoPlayer            VideoPlayer对象
     */
    public void setVideoPlayer(OldVideoPlayer videoPlayer) {
        mVideoPlayer = videoPlayer;
    }

    /**
     * 当正在缓冲或者播放准备中状态时，开启缓冲时更新网络加载速度
     */
    protected void startUpdateNetSpeedTimer() {
        cancelUpdateNetSpeedTimer();
        if (mUpdateNetSpeedTimer == null) {
            mUpdateNetSpeedTimer = new Timer();
        }
        if (mUpdateNetSpeedTask == null) {
            mUpdateNetSpeedTask = new TimerTask() {
                @Override
                public void run() {
                    //在子线程中更新进度，包括更新网络加载速度
                    AbsVideoPlayerController.this.post(new Runnable() {
                        @Override
                        public void run() {
                            updateNetSpeedProgress();
                        }
                    });
                }
            };
        }
        mUpdateNetSpeedTimer.schedule(mUpdateNetSpeedTask, 0, 100);
    }

    /**
     * 取消缓冲时更新网络加载速度
     */
    protected void cancelUpdateNetSpeedTimer() {
        if (mUpdateNetSpeedTimer != null) {
            mUpdateNetSpeedTimer.cancel();
            mUpdateNetSpeedTimer = null;
        }
        if (mUpdateNetSpeedTask != null) {
            mUpdateNetSpeedTask.cancel();
            mUpdateNetSpeedTask = null;
        }
    }

    /**
     * 开启更新进度的计时器。
     */
    protected void startUpdateProgressTimer() {
        cancelUpdateProgressTimer();
        if (mUpdateProgressTimer == null) {
            mUpdateProgressTimer = new Timer();
        }
        if (mUpdateProgressTimerTask == null) {
            mUpdateProgressTimerTask = new TimerTask() {
                @Override
                public void run() {
                    //在子线程中更新进度，包括进度条进度，展示的当前播放位置时长，总时长等。
                    AbsVideoPlayerController.this.post(new Runnable() {
                        @Override
                        public void run() {
                            updateProgress();
                        }
                    });
                }
            };
        }
        mUpdateProgressTimer.schedule(mUpdateProgressTimerTask, 0, 1000);
    }

    /**
     * 取消更新进度的计时器。
     */
    protected void cancelUpdateProgressTimer() {
        if (mUpdateProgressTimer != null) {
            mUpdateProgressTimer.cancel();
            mUpdateProgressTimer = null;
        }
        if (mUpdateProgressTimerTask != null) {
            mUpdateProgressTimerTask.cancel();
            mUpdateProgressTimerTask = null;
        }
    }

    /**
     * 滑动处理调节声音和亮度的逻辑
     * @param v                         v
     * @param event                     event
     * @return                          是否自己处理滑动事件
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //不能用这个做判断，如果是小窗口播放状态，那么这个返回时false
        //boolean tinyWindow = mVideoPlayer.isTinyWindow();
        int playType = mVideoPlayer.getPlayType();
        //如果是小窗口模式，则可以拖拽。其他情况则正常处理
        if(playType == ConstantKeys.PlayMode.MODE_FULL_SCREEN){
            //处理全屏播放时，滑动处理调节声音和亮度的逻辑
            return setOnTouch(v,event);
        }
        return false;
    }


    /**
     * 处理全屏播放时，滑动处理调节声音和亮度的逻辑
     * @param v                         v
     * @param event                     event
     * @return                          是否自己处理滑动事件
     */
    private boolean setOnTouch(View v, MotionEvent event) {
        // 只有全屏的时候才能拖动位置、亮度、声音
        if (!mVideoPlayer.isFullScreen()) {
            return false;
        }
        // 只有在播放、暂停、缓冲的时候能够拖动改变位置、亮度和声音
        if (mVideoPlayer.isIdle() || mVideoPlayer.isError() || mVideoPlayer.isPreparing()
                || mVideoPlayer.isPrepared() || mVideoPlayer.isCompleted()) {
            //势左右滑动改变播放位置后，手势up或者cancel时，隐藏控制器中间的播放位置变化视图
            hideChangePosition();
            //手势在左侧上下滑动改变亮度后，手势up或者cancel时，隐藏控制器中间的亮度变化视图，
            hideChangeBrightness();
            //手势在左侧上下滑动改变音量后，手势up或者cancel时，隐藏控制器中间的音量变化视图，
            hideChangeVolume();
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取起点时的坐标
                mDownX = x;
                mDownY = y;
                mNeedChangePosition = false;
                mNeedChangeVolume = false;
                mNeedChangeBrightness = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动过程中的x，y轴的绝对值
                float deltaX = x - mDownX;
                float deltaY = y - mDownY;
                float absDeltaX = Math.abs(deltaX);
                float absDeltaY = Math.abs(deltaY);
                if (!mNeedChangePosition && !mNeedChangeVolume && !mNeedChangeBrightness) {
                    // 只有在播放、暂停、缓冲的时候能够拖动改变位置、亮度和声音
                    if (absDeltaX >= THRESHOLD) {
                        cancelUpdateProgressTimer();
                        mNeedChangePosition = true;
                        mGestureDownPosition = mVideoPlayer.getCurrentPosition();
                    } else if (absDeltaY >= THRESHOLD) {
                        if (mDownX < getWidth() * 0.5f) {
                            // 左侧改变亮度
                            mNeedChangeBrightness = true;
                            mGestureDownBrightness = PlayerUtils.scanForActivity(mContext)
                                    .getWindow().getAttributes().screenBrightness;
                        } else {
                            // 右侧改变声音
                            mNeedChangeVolume = true;
                            mGestureDownVolume = mVideoPlayer.getVolume();
                        }
                    }
                }
                //是否需要改变播放的进度
                if (mNeedChangePosition) {
                    long duration = mVideoPlayer.getDuration();
                    long toPosition = (long) (mGestureDownPosition + duration * deltaX / getWidth());
                    mNewPosition = Math.max(0, Math.min(duration, toPosition));
                    int newPositionProgress = (int) (100f * mNewPosition / duration);
                    showChangePosition(duration, newPositionProgress);
                }
                //是否改变亮度
                if (mNeedChangeBrightness) {
                    deltaY = -deltaY;
                    float deltaBrightness = deltaY * 3 / getHeight();
                    float newBrightness = mGestureDownBrightness + deltaBrightness;
                    newBrightness = Math.max(0, Math.min(newBrightness, 1));
                    float newBrightnessPercentage = newBrightness;
                    WindowManager.LayoutParams params = PlayerUtils.scanForActivity(mContext)
                            .getWindow().getAttributes();
                    params.screenBrightness = newBrightnessPercentage;
                    PlayerUtils.scanForActivity(mContext).getWindow().setAttributes(params);
                    int newBrightnessProgress = (int) (100f * newBrightnessPercentage);
                    showChangeBrightness(newBrightnessProgress);
                }
                //是否改变音量
                if (mNeedChangeVolume) {
                    deltaY = -deltaY;
                    int maxVolume = mVideoPlayer.getMaxVolume();
                    int deltaVolume = (int) (maxVolume * deltaY * 3 / getHeight());
                    int newVolume = mGestureDownVolume + deltaVolume;
                    newVolume = Math.max(0, Math.min(maxVolume, newVolume));
                    mVideoPlayer.setVolume(newVolume);
                    int newVolumeProgress = (int) (100f * newVolume / maxVolume);
                    showChangeVolume(newVolumeProgress);
                }
                break;
            //滑动结束
            case MotionEvent.ACTION_CANCEL:
            //滑动手指抬起
            case MotionEvent.ACTION_UP:
                if (mNeedChangePosition) {
                    mVideoPlayer.seekTo(mNewPosition);
                    hideChangePosition();
                    startUpdateProgressTimer();
                    return true;
                }
                if (mNeedChangeBrightness) {
                    hideChangeBrightness();
                    return true;
                }
                if (mNeedChangeVolume) {
                    hideChangeVolume();
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

}
