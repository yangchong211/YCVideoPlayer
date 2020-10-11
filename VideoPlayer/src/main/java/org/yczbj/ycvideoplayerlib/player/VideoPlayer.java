package org.yczbj.ycvideoplayerlib.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.yczbj.ycvideoplayerlib.R;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.BaseVideoController;

import com.yc.kernel.inter.AbstractVideoPlayer;
import com.yc.kernel.factory.PlayerFactory;

import org.yczbj.ycvideoplayerlib.config.PlayerConfig;
import org.yczbj.ycvideoplayerlib.surface.ISurfaceView;
import org.yczbj.ycvideoplayerlib.surface.SurfaceFactory;
import org.yczbj.ycvideoplayerlib.tool.BaseToast;
import org.yczbj.ycvideoplayerlib.tool.PlayerUtils;
import org.yczbj.ycvideoplayerlib.tool.VideoException;

import com.yc.kernel.inter.VideoPlayerListener;
import com.yc.kernel.utils.VideoLogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 播放器具体实现类
 *     revise:
 * </pre>
 */
public class VideoPlayer<P extends AbstractVideoPlayer> extends FrameLayout
        implements InterVideoPlayer, VideoPlayerListener {

    private Context mContext;
    /**
     * 播放器
     */
    protected P mMediaPlayer;
    /**
     * 实例化播放核心
     */
    protected PlayerFactory<P> mPlayerFactory;
    /**
     * 控制器
     */
    @Nullable
    protected BaseVideoController mVideoController;
    /**
     * 真正承载播放器视图的容器
     */
    protected FrameLayout mPlayerContainer;

    protected ISurfaceView mRenderView;
    protected SurfaceFactory mRenderViewFactory;
    protected int mCurrentScreenScaleType;
    protected int[] mVideoSize = {0, 0};
    /**
     * 是否静音
     */
    protected boolean mIsMute;

    /**
     * 当前播放视频的地址
     */
    protected String mUrl;
    /**
     * 当前视频地址的请求头
     */
    protected Map<String, String> mHeaders;
    /**
     * assets文件
     */
    protected AssetFileDescriptor mAssetFileDescriptor;
    /**
     * 当前正在播放视频的位置
     */
    protected long mCurrentPosition;
    /**
     * 当前播放器的状态
     * 比如：错误，开始播放，暂停播放，缓存中等等状态
     */
    protected int mCurrentPlayState = ConstantKeys.CurrentState.STATE_IDLE;
    /**
     * 播放模式，普通模式，小窗口模式，正常模式等等
     * 存在局限性：比如小窗口下的正在播放模式，那么mCurrentMode就是STATE_PLAYING，而不是MODE_TINY_WINDOW并存
     **/
    protected int mCurrentPlayerState = ConstantKeys.PlayMode.MODE_NORMAL;
    /**
     * 是否处于全屏状态
     */
    protected boolean mIsFullScreen;
    /**
     * 是否处于小屏状态
     */
    protected boolean mIsTinyScreen;
    protected int[] mTinyScreenSize = {0, 0};
    /**
     * 监听系统中音频焦点改变
     */
    protected boolean mEnableAudioFocus;
    @Nullable
    protected AudioFocusHelper mAudioFocusHelper;

    /**
     * OnStateChangeListener集合，保存了所有开发者设置的监听器
     */
    protected List<OnVideoStateListener> mOnStateChangeListeners;

    /**
     * 进度管理器，设置之后播放器会记录播放进度，以便下次播放恢复进度
     */
    @Nullable
    protected ProgressManager mProgressManager;

    /**
     * 循环播放
     */
    protected boolean mIsLooping;

    /**
     * {@link #mPlayerContainer}背景色，默认黑色
     */
    private int mPlayerBackgroundColor;

    public VideoPlayer(@NonNull Context context) {
        this(context, null);
    }

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        BaseToast.init(mContext.getApplicationContext());

        //读取全局配置
        PlayerConfig config = VideoViewManager.getConfig();
        mEnableAudioFocus = config.mEnableAudioFocus;
        mProgressManager = config.mProgressManager;
        mPlayerFactory = config.mPlayerFactory;
        mCurrentScreenScaleType = config.mScreenScaleType;
        mRenderViewFactory = config.mRenderViewFactory;

        //读取xml中的配置，并综合全局配置
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.VideoPlayer);
        mEnableAudioFocus = a.getBoolean(R.styleable.VideoPlayer_enableAudioFocus, mEnableAudioFocus);
        mIsLooping = a.getBoolean(R.styleable.VideoPlayer_looping, false);
        mCurrentScreenScaleType = a.getInt(R.styleable.VideoPlayer_screenScaleType, mCurrentScreenScaleType);
        mPlayerBackgroundColor = a.getColor(R.styleable.VideoPlayer_playerBackgroundColor, Color.BLACK);
        a.recycle();

        initView();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        VideoLogUtils.d("onAttachedToWindow");
        //init();
        //在构造函数初始化时addView
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        VideoLogUtils.d("onDetachedFromWindow");
        if (mVideoController!=null){
            mVideoController.destroy();
        }
        //onDetachedFromWindow方法是在Activity destroy的时候被调用的，也就是act对应的window被删除的时候，
        //且每个view只会被调用一次，父view的调用在后，也不论view的visibility状态都会被调用，适合做最后的清理操作
        //防止开发者没有在onDestroy中没有做销毁视频的优化
        release();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && mIsFullScreen) {
            //重新获得焦点时保持全屏状态
            ViewGroup decorView = VideoPlayerHelper.instance().getDecorView(mContext, mVideoController);
            VideoPlayerHelper.instance().hideSysBar(decorView,mContext,mVideoController);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        VideoLogUtils.d("onSaveInstanceState: " + mCurrentPosition);
        //activity切到后台后可能被系统回收，故在此处进行进度保存
        saveProgress();
        return super.onSaveInstanceState();
    }

    /**
     * 初始化播放器视图
     */
    protected void initView() {
        mPlayerContainer = new FrameLayout(getContext());
        //设置背景颜色，目前设置为纯黑色
        mPlayerContainer.setBackgroundColor(mPlayerBackgroundColor);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //将布局添加到该视图中
        this.addView(mPlayerContainer, params);
    }

    /**
     * 设置控制器，传null表示移除控制器
     * @param mediaController                           controller
     */
    public void setVideoController(@Nullable BaseVideoController mediaController) {
        mPlayerContainer.removeView(mVideoController);
        mVideoController = mediaController;
        if (mediaController != null) {
            mediaController.setMediaPlayer(this);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mPlayerContainer.addView(mVideoController, params);
        }
    }

    /**
     * 开始播放，注意：调用此方法后必须调用{@link #release()}释放播放器，否则会导致内存泄漏
     */
    @Override
    public void start() {
        if (mVideoController==null){
            //在调用start方法前，请先初始化视频控制器，调用setController方法
            throw new VideoException(VideoException.CODE_NOT_SET_CONTROLLER,
                    "Controller must not be null , please setController first");
        }
        boolean isStarted = false;
        if (isInIdleState() || isInStartAbortState()) {
            isStarted = startPlay();
        } else if (isInPlaybackState()) {
            startInPlaybackState();
            isStarted = true;
        }
        if (isStarted) {
            mPlayerContainer.setKeepScreenOn(true);
            if (mAudioFocusHelper != null){
                mAudioFocusHelper.requestFocus();
            }
        }
    }

    /**
     * 第一次播放
     * @return 是否成功开始播放
     */
    protected boolean startPlay() {
        //如果要显示移动网络提示则不继续播放
        if (showNetWarning()) {
            //中止播放
            setPlayState(ConstantKeys.CurrentState.STATE_START_ABORT);
            return false;
        }
        //监听音频焦点改变
        if (mEnableAudioFocus) {
            mAudioFocusHelper = new AudioFocusHelper(this);
        }
        //读取播放进度
        if (mProgressManager != null) {
            mCurrentPosition = mProgressManager.getSavedProgress(mUrl);
        }
        initPlayer();
        addDisplay();
        startPrepare(false);
        return true;
    }


    /**
     * 初始化播放器
     */
    protected void initPlayer() {
        //通过工厂模式创建对象
        mMediaPlayer = mPlayerFactory.createPlayer(mContext);
        mMediaPlayer.setPlayerEventListener(this);
        setInitOptions();
        mMediaPlayer.initPlayer();
        setOptions();
    }

    /**
     * 是否显示移动网络提示，可在Controller中配置
     */
    protected boolean showNetWarning() {
        //播放本地数据源时不检测网络
        if (VideoPlayerHelper.instance().isLocalDataSource(mUrl,mAssetFileDescriptor)){
            return false;
        }
        return mVideoController != null && mVideoController.showNetWarning();
    }


    /**
     * 初始化之前的配置项
     */
    protected void setInitOptions() {

    }

    /**
     * 初始化之后的配置项
     */
    protected void setOptions() {
        //设置是否循环播放
        mMediaPlayer.setLooping(mIsLooping);
    }

    /**
     * 初始化视频渲染View
     */
    protected void addDisplay() {
        if (mRenderView != null) {
            mPlayerContainer.removeView(mRenderView.getView());
            mRenderView.release();
        }
        //创建TextureView对象
        mRenderView = mRenderViewFactory.createRenderView(mContext);
        //绑定mMediaPlayer对象
        mRenderView.attachToPlayer(mMediaPlayer);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        mPlayerContainer.addView(mRenderView.getView(), 0, params);
    }

    /**
     * 开始准备播放（直接播放）
     */
    protected void startPrepare(boolean reset) {
        if (reset) {
            mMediaPlayer.reset();
            //重新设置option，media player reset之后，option会失效
            setOptions();
        }
        if (prepareDataSource()) {
            mMediaPlayer.prepareAsync();
            setPlayState(ConstantKeys.CurrentState.STATE_PREPARING);
            setPlayerState(isFullScreen() ? ConstantKeys.PlayMode.MODE_FULL_SCREEN :
                    isTinyScreen() ? ConstantKeys.PlayMode.MODE_TINY_WINDOW : ConstantKeys.PlayMode.MODE_NORMAL);
        }
    }

    /**
     * 设置播放数据
     * @return 播放数据是否设置成功
     */
    protected boolean prepareDataSource() {
        if (mAssetFileDescriptor != null) {
            mMediaPlayer.setDataSource(mAssetFileDescriptor);
            return true;
        } else if (!TextUtils.isEmpty(mUrl)) {
            mMediaPlayer.setDataSource(mUrl, mHeaders);
            return true;
        }
        return false;
    }

    /**
     * 播放状态下开始播放
     */
    protected void startInPlaybackState() {
        mMediaPlayer.start();
        setPlayState(ConstantKeys.CurrentState.STATE_PLAYING);
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        if (isInPlaybackState() && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            setPlayState(ConstantKeys.CurrentState.STATE_PAUSED);
            if (mAudioFocusHelper != null) {
                mAudioFocusHelper.abandonFocus();
            }
            mPlayerContainer.setKeepScreenOn(false);
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (isInPlaybackState() && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            setPlayState(ConstantKeys.CurrentState.STATE_PLAYING);
            if (mAudioFocusHelper != null) {
                mAudioFocusHelper.requestFocus();
            }
            mPlayerContainer.setKeepScreenOn(true);
        }
    }

    /**
     * 释放播放器
     */
    public void release() {
        if (!isInIdleState()) {
            //释放播放器
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            //释放renderView
            if (mRenderView != null) {
                mPlayerContainer.removeView(mRenderView.getView());
                mRenderView.release();
                mRenderView = null;
            }
            //释放Assets资源
            if (mAssetFileDescriptor != null) {
                try {
                    mAssetFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭AudioFocus监听
            if (mAudioFocusHelper != null) {
                mAudioFocusHelper.abandonFocus();
                mAudioFocusHelper = null;
            }
            //关闭屏幕常亮
            mPlayerContainer.setKeepScreenOn(false);
            //保存播放进度
            saveProgress();
            //重置播放进度
            mCurrentPosition = 0;
            //切换转态
            setPlayState(ConstantKeys.CurrentState.STATE_IDLE);
        }
    }

    /**
     * 保存播放进度
     */
    protected void saveProgress() {
        if (mProgressManager != null && mCurrentPosition > 0) {
            VideoLogUtils.d("saveProgress: " + mCurrentPosition);
            mProgressManager.saveProgress(mUrl, mCurrentPosition);
        }
    }

    /**
     * 是否处于播放状态
     */
    protected boolean isInPlaybackState() {
        return mMediaPlayer != null
                && mCurrentPlayState != ConstantKeys.CurrentState.STATE_ERROR
                && mCurrentPlayState != ConstantKeys.CurrentState.STATE_IDLE
                && mCurrentPlayState != ConstantKeys.CurrentState.STATE_PREPARING
                && mCurrentPlayState != ConstantKeys.CurrentState.STATE_START_ABORT
                && mCurrentPlayState != ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING;
    }

    /**
     * 是否处于未播放状态
     */
    protected boolean isInIdleState() {
        return mCurrentPlayState == ConstantKeys.CurrentState.STATE_IDLE;
    }

    /**
     * 播放中止状态
     */
    private boolean isInStartAbortState() {
        return mCurrentPlayState == ConstantKeys.CurrentState.STATE_START_ABORT;
    }

    /**
     * 重新播放
     *
     * @param resetPosition 是否从头开始播放
     */
    @Override
    public void replay(boolean resetPosition) {
        if (resetPosition) {
            mCurrentPosition = 0;
        }
        addDisplay();
        startPrepare(true);
        mPlayerContainer.setKeepScreenOn(true);
    }

    /**
     * 获取视频总时长
     */
    @Override
    public long getDuration() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * 获取当前播放的位置
     */
    @Override
    public long getCurrentPosition() {
        if (isInPlaybackState()) {
            mCurrentPosition = mMediaPlayer.getCurrentPosition();
            return mCurrentPosition;
        }
        return 0;
    }

    /**
     * 调整播放进度
     */
    @Override
    public void seekTo(long pos) {
        if (isInPlaybackState()) {
            mMediaPlayer.seekTo(pos);
        }
    }

    /**
     * 是否处于播放状态
     */
    @Override
    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    /**
     * 获取当前缓冲百分比
     */
    @Override
    public int getBufferedPercentage() {
        return mMediaPlayer != null ? mMediaPlayer.getBufferedPercentage() : 0;
    }

    /**
     * 设置静音
     */
    @Override
    public void setMute(boolean isMute) {
        if (mMediaPlayer != null) {
            this.mIsMute = isMute;
            float volume = isMute ? 0.0f : 1.0f;
            mMediaPlayer.setVolume(volume, volume);
        }
    }

    /**
     * 是否处于静音状态
     */
    @Override
    public boolean isMute() {
        return mIsMute;
    }

    /**
     * 视频播放出错回调
     */
    @Override
    public void onError() {
        mPlayerContainer.setKeepScreenOn(false);
        setPlayState(ConstantKeys.CurrentState.STATE_ERROR);
    }

    /**
     * 视频播放完成回调
     */
    @Override
    public void onCompletion() {
        mPlayerContainer.setKeepScreenOn(false);
        mCurrentPosition = 0;
        if (mProgressManager != null) {
            //播放完成，清除进度
            mProgressManager.saveProgress(mUrl, 0);
        }
        setPlayState(ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING);
    }

    @Override
    public void onInfo(int what, int extra) {
        switch (what) {
            case AbstractVideoPlayer.MEDIA_INFO_BUFFERING_START:
                setPlayState(ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED);
                break;
            case AbstractVideoPlayer.MEDIA_INFO_BUFFERING_END:
                setPlayState(ConstantKeys.CurrentState.STATE_COMPLETED);
                break;
            case AbstractVideoPlayer.MEDIA_INFO_VIDEO_RENDERING_START: // 视频开始渲染
                setPlayState(ConstantKeys.CurrentState.STATE_PLAYING);
                if (mPlayerContainer.getWindowVisibility() != VISIBLE) {
                    pause();
                }
                break;
            case AbstractVideoPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                if (mRenderView != null)
                    mRenderView.setVideoRotation(extra);
                break;
        }
    }

    /**
     * 视频缓冲完毕，准备开始播放时回调
     */
    @Override
    public void onPrepared() {
        setPlayState(ConstantKeys.CurrentState.STATE_PREPARED);
        if (mCurrentPosition > 0) {
            seekTo(mCurrentPosition);
        }
    }

    /**
     * 获取当前播放器的状态
     */
    public int getCurrentPlayerState() {
        return mCurrentPlayerState;
    }

    /**
     * 获取当前的播放状态
     */
    public int getCurrentPlayState() {
        return mCurrentPlayState;
    }

    /**
     * 获取缓冲速度
     */
    @Override
    public long getTcpSpeed() {
        return mMediaPlayer != null ? mMediaPlayer.getTcpSpeed() : 0;
    }

    /**
     * 设置播放速度
     */
    @Override
    public void setSpeed(float speed) {
        if (isInPlaybackState()) {
            mMediaPlayer.setSpeed(speed);
        }
    }

    @Override
    public float getSpeed() {
        if (isInPlaybackState()) {
            return mMediaPlayer.getSpeed();
        }
        return 1f;
    }

    /**
     * 设置视频地址
     */
    public void setUrl(String url) {
        setUrl(url, null);
    }

    /**
     * 设置包含请求头信息的视频地址
     *
     * @param url     视频地址
     * @param headers 请求头
     */
    public void setUrl(String url, Map<String, String> headers) {
        mAssetFileDescriptor = null;
        mUrl = url;
        mHeaders = headers;
    }

    /**
     * 用于播放assets里面的视频文件
     */
    public void setAssetFileDescriptor(AssetFileDescriptor fd) {
        mUrl = null;
        this.mAssetFileDescriptor = fd;
    }

    /**
     * 设置音量 0.0f-1.0f 之间
     *
     * @param v1 左声道音量
     * @param v2 右声道音量
     */
    public void setVolume(float v1, float v2) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setVolume(v1, v2);
        }
    }

    /**
     * 设置进度管理器，用于保存播放进度
     */
    public void setProgressManager(@Nullable ProgressManager progressManager) {
        this.mProgressManager = progressManager;
    }

    /**
     * 循环播放， 默认不循环播放
     */
    public void setLooping(boolean looping) {
        mIsLooping = looping;
        if (mMediaPlayer != null) {
            mMediaPlayer.setLooping(looping);
        }
    }

    /**
     * 自定义播放核心，继承{@link PlayerFactory}实现自己的播放核心
     */
    public void setPlayerFactory(PlayerFactory<P> playerFactory) {
        if (playerFactory == null) {
            throw new IllegalArgumentException("PlayerFactory can not be null!");
        }
        mPlayerFactory = playerFactory;
    }

    /**
     * 自定义RenderView，继承{@link SurfaceFactory}实现自己的RenderView
     */
    public void setRenderViewFactory(SurfaceFactory renderViewFactory) {
        if (renderViewFactory == null) {
            throw new IllegalArgumentException("RenderViewFactory can not be null!");
        }
        mRenderViewFactory = renderViewFactory;
    }

    /**
     * 进入全屏
     */
    @Override
    public void startFullScreen() {
        if (mIsFullScreen){
            return;
        }
        ViewGroup decorView = VideoPlayerHelper.instance().getDecorView(mContext,mVideoController);
        if (decorView == null){
            return;
        }
        mIsFullScreen = true;
        //隐藏NavigationBar和StatusBar
        VideoPlayerHelper.instance().hideSysBar(decorView,mContext,mVideoController);
        //从当前FrameLayout中移除播放器视图
        this.removeView(mPlayerContainer);
        //将播放器视图添加到DecorView中即实现了全屏
        decorView.addView(mPlayerContainer);
        setPlayerState(ConstantKeys.PlayMode.MODE_FULL_SCREEN);
    }

    /**
     * 退出全屏
     */
    @Override
    public void stopFullScreen() {
        if (!mIsFullScreen){
            return;
        }
        ViewGroup decorView = VideoPlayerHelper.instance().getDecorView(mContext,mVideoController);
        if (decorView == null){
            return;
        }
        mIsFullScreen = false;
        //显示NavigationBar和StatusBar
        VideoPlayerHelper.instance().showSysBar(decorView,mContext,mVideoController);

        //把播放器视图从DecorView中移除并添加到当前FrameLayout中即退出了全屏
        decorView.removeView(mPlayerContainer);
        this.addView(mPlayerContainer);

        setPlayerState(ConstantKeys.PlayMode.MODE_NORMAL);
    }



    /**
     * 判断是否处于全屏状态
     */
    @Override
    public boolean isFullScreen() {
        return mIsFullScreen;
    }

    /**
     * 开启小屏
     */
    public void startTinyScreen() {
        if (mIsTinyScreen) return;
        ViewGroup contentView = VideoPlayerHelper.instance().getContentView(mContext,mVideoController);
        if (contentView == null) return;
        this.removeView(mPlayerContainer);
        int width = mTinyScreenSize[0];
        if (width <= 0) {
            width = PlayerUtils.getScreenWidth(getContext(), false) / 2;
        }
        int height = mTinyScreenSize[1];
        if (height <= 0) {
            height = width * 9 / 16;
        }
        LayoutParams params = new LayoutParams(width, height);
        params.gravity = Gravity.BOTTOM | Gravity.END;
        contentView.addView(mPlayerContainer, params);
        mIsTinyScreen = true;
        setPlayerState(ConstantKeys.PlayMode.MODE_TINY_WINDOW);
    }

    /**
     * 退出小屏
     */
    public void stopTinyScreen() {
        if (!mIsTinyScreen) return;

        ViewGroup contentView = VideoPlayerHelper.instance().getContentView(mContext,mVideoController);
        if (contentView == null) return;
        contentView.removeView(mPlayerContainer);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mPlayerContainer, params);

        mIsTinyScreen = false;
        setPlayerState(ConstantKeys.PlayMode.MODE_NORMAL);
    }

    public boolean isTinyScreen() {
        return mIsTinyScreen;
    }

    @Override
    public void onVideoSizeChanged(int videoWidth, int videoHeight) {
        mVideoSize[0] = videoWidth;
        mVideoSize[1] = videoHeight;

        if (mRenderView != null) {
            mRenderView.setScaleType(mCurrentScreenScaleType);
            mRenderView.setVideoSize(videoWidth, videoHeight);
        }
    }

    /**
     * 设置视频比例
     */
    @Override
    public void setScreenScaleType(@ConstantKeys.ScreenScaleType int screenScaleType) {
        mCurrentScreenScaleType = screenScaleType;
        if (mRenderView != null) {
            mRenderView.setScaleType(screenScaleType);
        }
    }

    /**
     * 设置镜像旋转，暂不支持SurfaceView
     */
    @Override
    public void setMirrorRotation(boolean enable) {
        if (mRenderView != null) {
            mRenderView.getView().setScaleX(enable ? -1 : 1);
        }
    }

    /**
     * 截图，暂不支持SurfaceView
     */
    @Override
    public Bitmap doScreenShot() {
        if (mRenderView != null) {
            return mRenderView.doScreenShot();
        }
        return null;
    }

    /**
     * 获取视频宽高,其中width: mVideoSize[0], height: mVideoSize[1]
     */
    @Override
    public int[] getVideoSize() {
        return mVideoSize;
    }

    /**
     * 旋转视频画面
     *
     * @param rotation 角度
     */
    @Override
    public void setRotation(float rotation) {
        if (mRenderView != null) {
            mRenderView.setVideoRotation((int) rotation);
        }
    }

    /**
     * 向Controller设置播放状态，用于控制Controller的ui展示
     */
    protected void setPlayState(@ConstantKeys.CurrentStateType int playState) {
        mCurrentPlayState = playState;
        if (mVideoController != null) {
            mVideoController.setPlayState(playState);
        }
        if (mOnStateChangeListeners != null) {
            for (OnVideoStateListener l : PlayerUtils.getSnapshot(mOnStateChangeListeners)) {
                if (l != null) {
                    l.onPlayStateChanged(playState);
                }
            }
        }
    }

    /**
     * 向Controller设置播放器状态，包含全屏状态和非全屏状态
     */
    protected void setPlayerState(@ConstantKeys.PlayModeType int playerState) {
        mCurrentPlayerState = playerState;
        if (mVideoController != null) {
            mVideoController.setPlayerState(playerState);
        }
        if (mOnStateChangeListeners != null) {
            for (OnVideoStateListener l : PlayerUtils.getSnapshot(mOnStateChangeListeners)) {
                if (l != null) {
                    l.onPlayerStateChanged(playerState);
                }
            }
        }
    }

    /**
     * OnStateChangeListener的空实现。用的时候只需要重写需要的方法
     */
    public static class SimpleOnStateChangeListener implements OnVideoStateListener {
        @Override
        public void onPlayerStateChanged(@ConstantKeys.PlayModeType int playerState) {}
        @Override
        public void onPlayStateChanged(int playState) {}
    }

    /**
     * 添加一个播放状态监听器，播放状态发生变化时将会调用。
     */
    public void addOnStateChangeListener(@NonNull OnVideoStateListener listener) {
        if (mOnStateChangeListeners == null) {
            mOnStateChangeListeners = new ArrayList<>();
        }
        mOnStateChangeListeners.add(listener);
    }

    /**
     * 移除某个播放状态监听
     */
    public void removeOnStateChangeListener(@NonNull OnVideoStateListener listener) {
        if (mOnStateChangeListeners != null) {
            mOnStateChangeListeners.remove(listener);
        }
    }

    /**
     * 设置一个播放状态监听器，播放状态发生变化时将会调用，
     * 如果你想同时设置多个监听器，推荐 {@link #addOnStateChangeListener(OnVideoStateListener)}。
     */
    public void setOnStateChangeListener(@NonNull OnVideoStateListener listener) {
        if (mOnStateChangeListeners == null) {
            mOnStateChangeListeners = new ArrayList<>();
        } else {
            mOnStateChangeListeners.clear();
        }
        mOnStateChangeListeners.add(listener);
    }

    /**
     * 移除所有播放状态监听
     */
    public void clearOnStateChangeListeners() {
        if (mOnStateChangeListeners != null) {
            mOnStateChangeListeners.clear();
        }
    }

    /**
     * 改变返回键逻辑，用于activity
     */
    public boolean onBackPressed() {
        return mVideoController != null && mVideoController.onBackPressed();
    }


    /**-----------------------------暴露api方法--------------------------------------**/
    /**-----------------------------暴露api方法--------------------------------------**/


    public void setVideoBuilder(VideoPlayerBuilder videoBuilder){
        if (mPlayerContainer==null){
            return;
        }
        //设置视频播放器的背景色
        mPlayerContainer.setBackgroundColor(videoBuilder.mColor);
        //设置小屏的宽高
        if (videoBuilder.mTinyScreenSize.length>0){
            mTinyScreenSize = videoBuilder.mTinyScreenSize;
        }
        //一开始播放就seek到预先设置好的位置
        if (videoBuilder.mCurrentPosition>0){
            this.mCurrentPosition = videoBuilder.mCurrentPosition;
        }
        //是否开启AudioFocus监听， 默认开启
        this.mEnableAudioFocus = videoBuilder.mEnableAudioFocus;
    }



}
