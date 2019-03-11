package org.yczbj.ycvideoplayerlib.controller;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import org.yczbj.ycvideoplayerlib.utils.VideoPlayerUtils;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.inter.InterVideoPlayer;

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
public abstract class AbsVideoPlayerController extends FrameLayout implements View.OnTouchListener {

    private Context mContext;
    protected InterVideoPlayer mVideoPlayer;
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

    public void setVideoPlayer(InterVideoPlayer videoPlayer) {
        mVideoPlayer = videoPlayer;
    }

    /**
     * 获取是否是锁屏模式
     * @return                      true表示锁屏
     */
    public abstract boolean getLock();

    /**
     * 设置视频播放器中间的播放键是否显示，设置自定义图片
     * @param isVisibility          是否可见
     * @param image                 image
     */
    public abstract void setCenterPlayer(boolean isVisibility ,@DrawableRes int image);

    /**
     * 设置是否显示视频头部的下载，分享布局控件
     * @param isVisibility          是否可见
     */
    public abstract void setTopVisibility(boolean isVisibility);

    /**
     * 设置top到顶部的距离
     * @param top                   top距离
     */
    public abstract void setTopPadding(float top);

    /**
     * 设置横屏播放时，tv和audio图标是否显示
     * @param isVisibility1                 tv图标是否显示
     * @param isVisibility2                 audio图标是否显示
     */
    public abstract void setTvAndAudioVisibility(boolean isVisibility1 , boolean isVisibility2);

    /**
     * 设置不操作后，多久自动隐藏头部和底部布局
     * @param time                  时间
     */
    public abstract void setHideTime(long time);


    /**
     * 设置加载loading类型
     *
     * @param type 加载loading的类型
     *             目前1，是仿腾讯加载loading
     *             2，是转圈加载loading
     */
    public abstract void setLoadingType(@ConstantKeys.LoadingType int type);

    /**
     * 设置播放的视频的标题
     *
     * @param title 视频标题
     */
    public abstract void setTitle(String title);

    /**
     * 视频底图
     *
     * @param resId 视频底图资源
     */
    public abstract void setImage(@DrawableRes int resId);

    /**
     * 视频底图ImageView控件，提供给外部用图片加载工具来加载网络图片
     *
     * @return 底图ImageView
     */
    public abstract ImageView imageView();

    /**
     * 设置总时长
     */
    public abstract void setLength(long length);

    /**
     * 设置总时长
     */
    public abstract void setLength(String length);

    /**
     * 当播放器的播放状态发生变化，在此方法中国你更新不同的播放状态的UI
     *
     * @param playState 播放状态：
     */
    public abstract void onPlayStateChanged(int playState);

    /**
     * 当播放器的播放模式发生变化，在此方法中更新不同模式下的控制器界面。
     *
     * @param playMode 播放器的模式：
     */
    public abstract void onPlayModeChanged(int playMode);

    /**
     * 重置控制器，将控制器恢复到初始状态。
     */
    public abstract void reset();

    /**
     * 控制器意外销毁，比如手动退出，意外崩溃等等
     */
    public abstract void destroy();


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
                            mGestureDownBrightness = VideoPlayerUtils.scanForActivity(mContext)
                                    .getWindow().getAttributes().screenBrightness;
                        } else {
                            // 右侧改变声音
                            mNeedChangeVolume = true;
                            mGestureDownVolume = mVideoPlayer.getVolume();
                        }
                    }
                }
                if (mNeedChangePosition) {
                    long duration = mVideoPlayer.getDuration();
                    long toPosition = (long) (mGestureDownPosition + duration * deltaX / getWidth());
                    mNewPosition = Math.max(0, Math.min(duration, toPosition));
                    int newPositionProgress = (int) (100f * mNewPosition / duration);
                    showChangePosition(duration, newPositionProgress);
                }
                if (mNeedChangeBrightness) {
                    deltaY = -deltaY;
                    float deltaBrightness = deltaY * 3 / getHeight();
                    float newBrightness = mGestureDownBrightness + deltaBrightness;
                    newBrightness = Math.max(0, Math.min(newBrightness, 1));
                    float newBrightnessPercentage = newBrightness;
                    WindowManager.LayoutParams params = VideoPlayerUtils.scanForActivity(mContext).getWindow().getAttributes();
                    params.screenBrightness = newBrightnessPercentage;
                    VideoPlayerUtils.scanForActivity(mContext).getWindow().setAttributes(params);
                    int newBrightnessProgress = (int) (100f * newBrightnessPercentage);
                    showChangeBrightness(newBrightnessProgress);
                }
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
