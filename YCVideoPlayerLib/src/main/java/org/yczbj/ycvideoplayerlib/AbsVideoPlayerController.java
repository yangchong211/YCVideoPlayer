package org.yczbj.ycvideoplayerlib;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author yc
 * @date 2017/12/4
 * 参考项目：
 * https://github.com/CarGuo/GSYVideoPlayer
 * https://github.com/danylovolokh/VideoPlayerManager
 * https://github.com/HotBitmapGG/bilibili-android-client
 * https://github.com/jjdxmashl/jjdxm_ijkplayer
 * https://github.com/JasonChow1989/JieCaoVideoPlayer-develop          2年前
 * https://github.com/open-android/JieCaoVideoPlayer                   1年前
 * https://github.com/lipangit/JiaoZiVideoPlayer                       4个月前
 * 个人感觉jiaozi这个播放器，与JieCaoVideoPlayer-develop有惊人的类同，借鉴了上面两个项目[JieCao]
 *
 *
 * 注意：在对应的播放Activity页面，清单文件中一定要添加
 * android:configChanges="orientation|keyboardHidden|screenSize"
 * android:screenOrientation="portrait"
 *
 * 关于我的github：https://github.com/yangchong211
 * 关于我的个人网站：www.ycbjie.cn或者www.yczbj.org
 *
 * 控制器抽象类
 */
public abstract class AbsVideoPlayerController extends FrameLayout implements View.OnTouchListener {

    private final DisplayMetrics dm;
    private Context mContext;
    protected InterVideoPlayer mVideoPlayer;
    private Timer mUpdateProgressTimer;
    private TimerTask mUpdateProgressTimerTask;
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
    private ScheduledExecutorService pool;


    public AbsVideoPlayerController(Context context) {
        super(context);
        mContext = context;
        this.setOnTouchListener(this);
        dm = new DisplayMetrics();//获取屏幕宽高，处理越界的时候用到
    }

    public void setVideoPlayer(InterVideoPlayer videoPlayer) {
        mVideoPlayer = videoPlayer;
    }

    /**
     * 设置是否显示视频头部的下载，分享布局控件
     * @param isVisibility          是否可见
     */
    public abstract void setTopVisibility(boolean isVisibility);

    /**
     * 设置试看视频时间，让使用者自己定制
     * @param time                  时间
     */
    public abstract void setTrySeeTime(boolean isSee , long time);

    /**
     * 设置不操作后，多久自动隐藏头部和底部布局
     * @param time                  时间
     */
    public abstract void setHideTime(long time);

    /**
     * 设置会员权限话术内容
     * @param memberContent         集合
     */
    public abstract void setMemberContent(ArrayList<String> memberContent);


    /**
     * 设置会员权限类型
     * @param isLogin   是否登录
     * @param type      视频类型
     */
    public abstract void setMemberType(boolean isLogin ,int type);


    /**
     * 设置加载loading类型
     *
     * @param type 加载loading的类型
     *             目前1，是仿腾讯加载loading
     *             2，是转圈加载loading
     */
    public abstract void setLoadingType(int type);

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
     *                  <ul>
     *                  <li>{@link VideoPlayer#STATE_IDLE}</li>
     *                  <li>{@link VideoPlayer#STATE_PREPARING}</li>
     *                  <li>{@link VideoPlayer#STATE_PREPARED}</li>
     *                  <li>{@link VideoPlayer#STATE_PLAYING}</li>
     *                  <li>{@link VideoPlayer#STATE_PAUSED}</li>
     *                  <li>{@link VideoPlayer#STATE_BUFFERING_PLAYING}</li>
     *                  <li>{@link VideoPlayer#STATE_BUFFERING_PAUSED}</li>
     *                  <li>{@link VideoPlayer#STATE_ERROR}</li>
     *                  <li>{@link VideoPlayer#STATE_COMPLETED}</li>
     *                  </ul>
     */
    protected abstract void onPlayStateChanged(int playState);

    /**
     * 当播放器的播放模式发生变化，在此方法中更新不同模式下的控制器界面。
     *
     * @param playMode 播放器的模式：
     *                 <ul>
     *                 <li>{@link VideoPlayer#MODE_NORMAL}</li>
     *                 <li>{@link VideoPlayer#MODE_FULL_SCREEN}</li>
     *                 <li>{@link VideoPlayer#MODE_TINY_WINDOW}</li>
     *                 </ul>
     */
    protected abstract void onPlayModeChanged(int playMode);

    /**
     * 重置控制器，将控制器恢复到初始状态。
     */
    protected abstract void reset();

    /**
     * 开启更新进度的计时器。
     */
    protected void startUpdateProgressTimer() {
        cancelUpdateProgressTimer();
        // Java并发，Timer的缺陷，用ScheduledExecutorService替代
        /*if(pool==null){
            pool = Executors.newScheduledThreadPool(1);
            //pool = Executors.newSingleThreadScheduledExecutor();
        }
        if (mUpdateProgressTimerTask == null) {
            mUpdateProgressTimerTask = new TimerTask() {
                @Override
                public void run() {
                    AbsVideoPlayerController.this.post(new Runnable() {
                        @Override
                        public void run() {
                            updateProgress();
                        }
                    });
                }
            };
        }
        pool.scheduleWithFixedDelay(mUpdateProgressTimerTask,0,1000, TimeUnit.MILLISECONDS);*/


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
     * 更新进度，包括进度条进度，展示的当前播放位置时长，总时长等。
     */
    protected abstract void updateProgress();


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
        if(playType == VideoPlayer.PlayMode.MODE_TINY_WINDOW){
            return setTinyWindowTouch(v,event);
        }else {
            //处理全屏播放时，滑动处理调节声音和亮度的逻辑
            return setOnTouch(v,event);
        }
    }


    /**
     * 如果是小窗口模式，则可以拖拽。其他情况则正常处理
     * 问题：
     * 1.按钮拖动的界限限定。
     * 2.按钮单击和拖动之间的冲突。
     * 3.在界面未显示之前，获得View的高/宽。
     * @param v                         v
     * @param event                     event
     * @return                          是否自己处理滑动事件
     */
    private boolean setTinyWindowTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(v,event);
                break;
            case MotionEvent.ACTION_UP:
                isOver(v);
                break;
        }
        //v.invalidate();
        return true;
    }

    //相对于父控件的触摸位置，用于处理拖拽
    private float xDown,yDown,xUp,yUp;
    private int extra;

    /** 按下 **/
    private void onTouchDown(MotionEvent event){
        xDown = event.getX();
        yDown = event.getY();
        xUp = event.getRawX();
        yUp = event.getRawY();
        extra = (int) (yUp - yDown - getTop());
        VideoLogUtil.i("AbsPlayer"+"onTouchDown"+xDown+"----"+yDown+"----"+xUp+"----"+yUp+"----"+extra);
    }


    /** 拖拽 **/
    private void onTouchMove(View view, MotionEvent event) {
        int left, right, top, bottom;
        top = (int) (yUp - yDown) - extra;
        bottom = top + getHeight();
        left = (int) (xUp - xDown);
        right = left + getWidth();
        //Top position, relative to parent这是该方法其中top参数的官方解释，反正我是被误导了，我不知道这个父亲到底是怎样定义的
        // 我目前的理解是它的所有参数位置是相对于该view放置的那个xml布局的位置，那个xml布局最外面的那个layout才是父view。
        //为什么要说的这么绕，就是它的位置不是相对屏幕的，因为你的应用可能有tab占了位置，那块位置就不能算。如果理解了这些话就能知道为什么会有extra了。
        view.layout(left, top, right, bottom);
        xUp = event.getRawX();
        yUp = event.getRawY();
        VideoLogUtil.i("AbsPlayer"+"onTouchMove"+left+"----"+top+"----"+right+"----"+bottom);
    }

    /**
     * 拖拽时判断是否越界
     */
    private void isOver(View v) {
        int width = this.getWidth()/3;
        int height = this.getHeight()/3;
        int left = getLeft(),right=getRight(),top=getTop(),bottom=getBottom();
        //针对整个可用屏幕的越界,必须还能看到控件的1/3
        if (this.getBottom() < height){
            bottom = height;
            top = bottom - getHeight();
        }
        if (this.getRight() <  width){
            right = width;
            left = right - getWidth();
        }
        if (this.getTop() > dm.heightPixels - extra - height){
            top = dm.heightPixels - extra - height;
            bottom = top + getHeight();
        }
        if (this.getLeft() > dm.widthPixels - width){
            left = dm.widthPixels - width;
            right = left + getWidth();
        }
        if (this.getBottom() < height || this.getLeft() < - width || this.getRight() > dm.widthPixels - width || this.getTop() > dm.heightPixels - extra - height) {
            v.layout(left, top, right, bottom);
        }
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
}
