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
package com.yc.video.old.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.yc.kernel.utils.VideoLogUtils;
import com.yc.video.config.ConstantKeys;
import com.yc.video.old.controller.AbsVideoPlayerController;
import com.yc.video.tool.BaseToast;
import com.yc.video.tool.PlayerUtils;

import com.yc.video.old.other.VideoPlayerManager;

import java.util.Map;

import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/9/21
 *     desc  : 播放器
 *     revise: 注意：在对应的播放Activity页面，清单文件中一定要添加
 *             android:configChanges="orientation|keyboardHidden|screenSize"
 *             android:screenOrientation="portrait"
 * </pre>
 */
@Deprecated
public class OldVideoPlayer extends FrameLayout implements IVideoPlayer {

    /**
     * 播放类型
     * TYPE_IJK             基于IjkPlayer封装播放器
     * TYPE_NATIVE          基于原生自带的播放器控件
     **/
    public int mPlayerType = ConstantKeys.VideoPlayerType.TYPE_IJK;
    /**
     * 播放状态，错误，开始播放，暂停播放，缓存中等等状态
     **/
    private int mCurrentState = ConstantKeys.CurrentState.STATE_IDLE;
    /**
     * 播放模式，普通模式，小窗口模式，正常模式等等
     * 存在局限性：比如小窗口下的正在播放模式，那么mCurrentMode就是STATE_PLAYING，而不是MODE_TINY_WINDOW并存
     **/
    private int mCurrentMode = ConstantKeys.PlayMode.MODE_NORMAL;


    private Context mContext;
    private FrameLayout mContainer;
    private AbsVideoPlayerController mController;
    private String mUrl;
    private Map<String, String> mHeaders;
    private int mBufferPercentage;
    /**
     * 是否从上一次位置播放，默认是false
     */
    private boolean continueFromLastPosition = false;
    public long skipToPosition;
    private VideoMediaPlayer videoMediaPlayer;

    public OldVideoPlayer(Context context) {
        this(context, null);
    }

    public OldVideoPlayer(Context context, AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public OldVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        BaseToast.init(mContext.getApplicationContext());
        mContainer = new FrameLayout(mContext);
        //设置背景颜色，目前设置为纯黑色
        mContainer.setBackgroundColor(Color.BLACK);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        videoMediaPlayer = new VideoMediaPlayer(this);
        //将布局添加到该视图中
        this.addView(mContainer, params);
    }

    /**
     * 如果锁屏，则屏蔽返回键，这个地方设置无效，需要在activity中设置处理返回键逻辑
     * 后期找替代方案
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        VideoLogUtils.i("如果锁屏1，则屏蔽返回键onKeyDown"+event.getAction());
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            if(mController!=null && mController.getLock()){
                //如果锁屏，那就屏蔽返回键
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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
        if (mController!=null){
            mController.destroy();
        }
        //onDetachedFromWindow方法是在Activity destroy的时候被调用的，也就是act对应的window被删除的时候，
        //且每个view只会被调用一次，父view的调用在后，也不论view的visibility状态都会被调用，适合做最后的清理操作
        //防止开发者没有在onDestroy中没有做销毁视频的优化
        release();
    }

    /*--------------setUp为必须设置的方法，二选其一--------------------------------------*/
    /**
     * 设置，必须设置
     * @param url               视频地址，可以是本地，也可以是网络视频
     * @param headers           请求header.
     */
    @Override
    public final void setUp(String url, Map<String, String> headers) {
        if(url==null || url.length()==0){
            VideoLogUtils.d("设置参数-------设置的视频链接不能为空");
        }
        mUrl = url;
        mHeaders = headers;
    }


    /**
     * 设置视频控制器，必须设置
     * @param controller        AbsVideoPlayerController子类对象，可用VideoPlayerController，也可自定义
     */
    public void setController(@NonNull AbsVideoPlayerController controller) {
        //这里必须先移除
        mContainer.removeView(mController);
        mController = controller;
        mController.reset();
        mController.setVideoPlayer(this);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.addView(mController, params);
    }

    public AbsVideoPlayerController getController(){
        return mController;
    }

    public FrameLayout getContainer() {
        return mContainer;
    }

    public String getUrl(){
        return mUrl;
    }

    public Map<String, String> getHeaders(){
        return mHeaders;
    }

    /**
     * 设置播放器类型，必须设置
     * 注意：感谢某人建议，这里限定了传入值类型
     * 输入值：ConstantKeys.IjkPlayerType.TYPE_IJK   或者  ConstantKeys.IjkPlayerType.TYPE_NATIVE
     * @param playerType IjkPlayer or MediaPlayer.
     */
    public void setPlayerType(@ConstantKeys.PlayerType int playerType) {
        //默认是基于IjkPlayer封装播放器
        mPlayerType = playerType;
    }


    /**
     * 是否从上一次的位置继续播放，不必须
     *
     * @param continueFromLastPosition true从上一次的位置继续播放
     */
    @Override
    public void continueFromLastPosition(boolean continueFromLastPosition) {
        //默认是从上一次的位置继续播放
        this.continueFromLastPosition = continueFromLastPosition;
    }

    public boolean getContinueFromLastPosition(){
        return continueFromLastPosition;
    }

    public long getSkipToPosition() {
        return skipToPosition;
    }

    /**
     * 注意：MediaPlayer没有这个方法
     * 设置播放速度，不必须
     * @param speed                     播放速度
     */
    @Override
    public void setSpeed(float speed) {
        if (speed<0){
            VideoLogUtils.d("设置参数-------设置的视频播放速度不能小于0");
        }
        if (videoMediaPlayer.getMediaPlayer() instanceof IjkMediaPlayer) {
            ((IjkMediaPlayer) videoMediaPlayer.getMediaPlayer()).setSpeed(speed);
        } else if (videoMediaPlayer.getMediaPlayer() instanceof AndroidMediaPlayer){
            //((AndroidMediaPlayer) videoMediaPlayer.getMediaPlayer()).setSpeed(speed);
            VideoLogUtils.d("设置参数-------只有IjkPlayer才能设置播放速度");
        }else if(videoMediaPlayer.getMediaPlayer() instanceof MediaPlayer){
            //((MediaPlayer) videoMediaPlayer.getMediaPlayer()).setSpeed(speed);
            VideoLogUtils.d("设置参数-------只有IjkPlayer才能设置播放速度");
        } else {
            VideoLogUtils.d("设置参数-------只有IjkPlayer才能设置播放速度");
        }
    }

    /**
     * 开始播放
     */
    @Override
    public void start() {
        if (mController==null){
            //在调用start方法前，请先初始化视频控制器，调用setController方法
            throw new NullPointerException("Controller must not be null , please setController first");
        }
        if (mCurrentState == ConstantKeys.CurrentState.STATE_IDLE) {
            VideoPlayerManager.instance().setCurrentVideoPlayer(this);
            videoMediaPlayer.initAudioManager();
            videoMediaPlayer.initMediaPlayer();
            videoMediaPlayer.initTextureView();
        } else {
            VideoLogUtils.d("播放状态--------VideoPlayer只有在mCurrentState == STATE_IDLE时才能调用start方法.");
        }
    }


    /**
     * 开始播放
     * @param position                 播放位置
     */
    @Override
    public void start(long position) {
        if (position<0){
            VideoLogUtils.d("设置参数-------设置开始播放的播放位置不能小于0");
        }
        skipToPosition = position;
        start();
    }


    /**
     * 重新播放
     */
    @Override
    public void restart() {
        if (mCurrentState == ConstantKeys.CurrentState.STATE_PAUSED) {
            //如果是暂停状态，那么则继续播放
            videoMediaPlayer.getMediaPlayer().start();
            mCurrentState = ConstantKeys.CurrentState.STATE_PLAYING;
            mController.onPlayStateChanged(mCurrentState);
            VideoLogUtils.d("播放状态--------STATE_PLAYING");
        } else if (mCurrentState == ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED) {
            //如果是缓存暂停状态，那么则继续播放
            videoMediaPlayer.getMediaPlayer().start();
            mCurrentState = ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING;
            mController.onPlayStateChanged(mCurrentState);
            VideoLogUtils.d("播放状态--------STATE_BUFFERING_PLAYING");
        } else if (mCurrentState == ConstantKeys.CurrentState.STATE_COMPLETED
                || mCurrentState == ConstantKeys.CurrentState.STATE_ERROR) {
            //如果是完成播放或者播放错误，则重新播放
            videoMediaPlayer.getMediaPlayer().reset();
            videoMediaPlayer.openMediaPlayer();
            VideoLogUtils.d("播放状态--------完成播放或者播放错误，则重新播放");
        } else {
            VideoLogUtils.d("VideoPlayer在mCurrentState == " + mCurrentState + "时不能调用restart()方法.");
        }
    }


    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        if (mCurrentState == ConstantKeys.CurrentState.STATE_PLAYING) {
            //如果是播放状态，那么则暂停播放
            videoMediaPlayer.getMediaPlayer().pause();
            mCurrentState = ConstantKeys.CurrentState.STATE_PAUSED;
            mController.onPlayStateChanged(mCurrentState);
            VideoLogUtils.d("播放状态--------STATE_PAUSED");
        } else if (mCurrentState == ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING) {
            //如果是正在缓冲状态，那么则暂停暂停缓冲
            videoMediaPlayer.getMediaPlayer().pause();
            mCurrentState = ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED;
            mController.onPlayStateChanged(mCurrentState);
            VideoLogUtils.d("播放状态--------STATE_BUFFERING_PAUSED");
        }
    }


    /**
     * 设置播放位置
     * @param pos                   播放位置
     */
    @Override
    public void seekTo(long pos) {
        if (pos<0){
            VideoLogUtils.d("设置参数-------设置开始跳转播放位置不能小于0");
        }
        if (videoMediaPlayer.getMediaPlayer() != null) {
            videoMediaPlayer.getMediaPlayer().seekTo(pos);
        }
    }


    /**
     * 设置音量
     * @param volume                音量值
     */
    @Override
    public void setVolume(int volume) {
        if (videoMediaPlayer.getAudioManager() != null) {
            videoMediaPlayer.getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        }
    }

    /**
     * 判断是否开始播放
     * @return                      true表示播放未开始
     */
    @Override
    public boolean isIdle() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_IDLE;
    }


    /**
     * 判断视频是否播放准备中
     * @return                      true表示播放准备中
     */
    @Override
    public boolean isPreparing() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_PREPARING;
    }


    /**
     * 判断视频是否准备就绪
     * @return                      true表示播放准备就绪
     */
    @Override
    public boolean isPrepared() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_PREPARED;
    }


    /**
     * 判断视频是否正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
     * @return                      true表示正在缓冲
     */
    @Override
    public boolean isBufferingPlaying() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING;
    }


    /**
     * 判断是否是否缓冲暂停
     * @return                      true表示缓冲暂停
     */
    @Override
    public boolean isBufferingPaused() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED;
    }


    /**
     * 判断视频是否正在播放
     * @return                      true表示正在播放
     */
    @Override
    public boolean isPlaying() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_PLAYING;
    }


    /**
     * 判断视频是否暂停播放
     * @return                      true表示暂停播放
     */
    @Override
    public boolean isPaused() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_PAUSED;
    }


    /**
     * 判断视频是否播放错误
     * @return                      true表示播放错误
     */
    @Override
    public boolean isError() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_ERROR;
    }


    /**
     * 判断视频是否播放完成
     * @return                      true表示播放完成
     */
    @Override
    public boolean isCompleted() {
        return mCurrentState == ConstantKeys.CurrentState.STATE_COMPLETED;
    }


    /**
     * 判断视频是否播放全屏
     * @return                      true表示播放全屏
     */
    @Override
    public boolean isFullScreen() {
        return mCurrentMode == ConstantKeys.PlayMode.MODE_FULL_SCREEN;
    }


    /**
     * 判断视频是否播放小窗口
     * @return                      true表示播放小窗口
     */
    @Override
    public boolean isTinyWindow() {
        return mCurrentMode == ConstantKeys.PlayMode.MODE_TINY_WINDOW;
    }


    /**
     * 判断视频是否正常播放
     * @return                      true表示正常播放
     */
    @Override
    public boolean isNormal() {
        return mCurrentMode == ConstantKeys.PlayMode.MODE_NORMAL;
    }


    /**
     * 获取最大音量
     * @return                  音量值
     */
    @Override
    public int getMaxVolume() {
        if (videoMediaPlayer.getAudioManager() != null) {
            return videoMediaPlayer.getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }

    /**
     * 获取当前播放状态
     *
     * @return  播放状态
     */
    @Override
    public int getPlayType() {
        return mCurrentMode;
    }


    /**
     * 获取音量值
     * @return                  音量值
     */
    @Override
    public int getVolume() {
        if (videoMediaPlayer.getAudioManager() != null) {
            return videoMediaPlayer.getAudioManager().getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }


    /**
     * 获取持续时长
     * @return                  long时间值
     */
    @Override
    public long getDuration() {
        return videoMediaPlayer.getMediaPlayer() != null ? videoMediaPlayer.getMediaPlayer().getDuration() : 0;
    }


    /**
     * 获取播放位置
     * @return                  位置
     */
    @Override
    public long getCurrentPosition() {
        return videoMediaPlayer.getMediaPlayer() != null ? videoMediaPlayer.getMediaPlayer().getCurrentPosition() : 0;
    }


    /**
     * 获取缓冲区百分比
     * @return                  百分比
     */
    @Override
    public int getBufferPercentage() {
        return mBufferPercentage;
    }

    /**
     * 设置缓冲区百分比
     * @param bufferPercentage
     */
    public void setBufferPercentage(int bufferPercentage) {
        this.mBufferPercentage = bufferPercentage;
    }

    /**
     * 获取播放速度
     * @param speed             播放速度
     * @return                  速度
     */
    @Override
    public float getSpeed(float speed) {
        if (videoMediaPlayer.getMediaPlayer() instanceof IjkMediaPlayer) {
            return ((IjkMediaPlayer) videoMediaPlayer.getMediaPlayer()).getSpeed(speed);
        }
        return 0;
    }


    /**
     * 获取播放速度
     * @return                  速度
     */
    @Override
    public long getTcpSpeed() {
        if (videoMediaPlayer.getMediaPlayer() instanceof IjkMediaPlayer) {
            return ((IjkMediaPlayer) videoMediaPlayer.getMediaPlayer()).getTcpSpeed();
        }
        return 0;
    }

    /**
     * 获取当前播放模式
     * @return                  返回当前播放模式
     */
    public int getCurrentState(){
        return mCurrentState;
    }

    /**
     * 设置当前播放模式
     * @param state             当前播放模式
     */
    public void setCurrentState(@ConstantKeys.CurrentState int state){
        mCurrentState = state;
    }


    /**
     * 进入全屏模式
     * 全屏，将mContainer(内部包含mTextureView和mController)从当前容器中移除，并添加到android.R.content中.
     * 切换横屏时需要在manifest的activity标签下添加android:configChanges="orientation|keyboardHidden|screenSize"配置，
     * 以避免Activity重新走生命周期
     */
    @Override
    public void enterFullScreen() {
        if (mCurrentMode == ConstantKeys.PlayMode.MODE_FULL_SCREEN){
            return;
        }
        // 隐藏ActionBar、状态栏，并横屏
        PlayerUtils.hideActionBar(mContext);
        //设置更改此页面的所需方向。如果页面当前位于前台或以其他方式影响方向
        //则屏幕将立即更改(可能导致重新启动该页面)。否则，这将在下一次页面可见时使用。
        PlayerUtils.scanForActivity(mContext).setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //找到contentView
        ViewGroup contentView = PlayerUtils.scanForActivity(mContext)
                .findViewById(android.R.id.content);
        if (mCurrentMode == ConstantKeys.PlayMode.MODE_TINY_WINDOW) {
            contentView.removeView(mContainer);
        } else {
            this.removeView(mContainer);
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(mContainer, params);
        mCurrentMode = ConstantKeys.PlayMode.MODE_FULL_SCREEN;
        mController.onPlayModeChanged(mCurrentMode);
        VideoLogUtils.d("播放模式--------MODE_FULL_SCREEN");
    }


    /**
     * 进入竖屏的全屏模式
     */
    @Override
    public void enterVerticalScreenScreen() {
        if (mCurrentMode == ConstantKeys.PlayMode.MODE_FULL_SCREEN){
            return;
        }
        // 隐藏ActionBar、状态栏，并横屏
        PlayerUtils.hideActionBar(mContext);
        PlayerUtils.scanForActivity(mContext).
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup contentView = PlayerUtils.
                scanForActivity(mContext).findViewById(android.R.id.content);
        if (mCurrentMode == ConstantKeys.PlayMode.MODE_TINY_WINDOW) {
            contentView.removeView(mContainer);
        } else {
            this.removeView(mContainer);
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(mContainer, params);

        mCurrentMode = ConstantKeys.PlayMode.MODE_FULL_SCREEN;
        mController.onPlayModeChanged(mCurrentMode);
        VideoLogUtils.d("播放模式--------MODE_FULL_SCREEN");
    }


    /**
     * 退出全屏模式
     * 退出全屏，移除mTextureView和mController，并添加到非全屏的容器中。
     * 切换竖屏时需要在manifest的activity标签下添加
     * android:configChanges="orientation|keyboardHidden|screenSize"配置，
     * 以避免Activity重新走生命周期.
     *
     * @return true退出全屏.
     */
    @Override
    public boolean exitFullScreen() {
        if (mCurrentMode == ConstantKeys.PlayMode.MODE_FULL_SCREEN) {
            PlayerUtils.showActionBar(mContext);
            PlayerUtils.scanForActivity(mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            ViewGroup contentView = PlayerUtils.scanForActivity(mContext).findViewById(android.R.id.content);
            //将视图移除
            contentView.removeView(mContainer);
            //重新添加到当前视图
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(mContainer, params);
            mCurrentMode = ConstantKeys.PlayMode.MODE_NORMAL;
            mController.onPlayModeChanged(mCurrentMode);
            VideoLogUtils.d("播放模式--------MODE_NORMAL");
            this.setOnKeyListener(null);
            return true;
        }
        return false;
    }


    /**
     * 进入小窗口播放，小窗口播放的实现原理与全屏播放类似。
     * 注意：小窗口播放视频比例是        16：9
     */
    @Override
    public void enterTinyWindow() {
        //如果是小窗口模式，则不执行下面代码
        if (mCurrentMode == ConstantKeys.PlayMode.MODE_TINY_WINDOW) {
            return;
        }
        //先移除
        this.removeView(mContainer);
        ViewGroup contentView = PlayerUtils.scanForActivity(mContext).findViewById(android.R.id.content);
        // 小窗口的宽度为屏幕宽度的60%，长宽比默认为16:9，右边距、下边距为8dp。
        LayoutParams params = new LayoutParams(
                (int) (PlayerUtils.getScreenWidth(mContext) * 0.6f),
                (int) (PlayerUtils.getScreenWidth(mContext) * 0.6f * 9f / 16f));
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.rightMargin = PlayerUtils.dp2px(mContext, 8f);
        params.bottomMargin = PlayerUtils.dp2px(mContext, 8f);

        contentView.addView(mContainer, params);
        mCurrentMode = ConstantKeys.PlayMode.MODE_TINY_WINDOW;
        mController.onPlayModeChanged(mCurrentMode);
        VideoLogUtils.d("播放模式-------MODE_TINY_WINDOW");
    }

    /**
     * 退出小窗口播放
     */
    @Override
    public boolean exitTinyWindow() {
        if (mCurrentMode == ConstantKeys.PlayMode.MODE_TINY_WINDOW) {
            ViewGroup contentView = PlayerUtils.scanForActivity(mContext).findViewById(android.R.id.content);
            contentView.removeView(mContainer);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(mContainer, params);
            mCurrentMode = ConstantKeys.PlayMode.MODE_NORMAL;
            mController.onPlayModeChanged(mCurrentMode);
            VideoLogUtils.d("播放模式-------MODE_NORMAL");
            return true;
        }
        return false;
    }


    /**
     * 释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
     * 逻辑
     * 1.先保存播放位置
     * 2.退出全屏或小窗口，回复播放模式为正常模式
     * 3.释放播放器
     * 4.恢复控制器
     * 5.gc回收
     */
    @Override
    public void release() {
        // 保存播放位置，当正在播放时，缓冲时，缓冲暂停时，暂停时
        if (isPlaying() || isBufferingPlaying() || isBufferingPaused() || isPaused()) {
            PlayerUtils.savePlayPosition(mContext, mUrl, getCurrentPosition());
        } else if (isCompleted()) {
            //如果播放完成，则保存播放位置为0，也就是初始位置
            PlayerUtils.savePlayPosition(mContext, mUrl, 0);
        }
        // 退出全屏或小窗口
        if (isFullScreen()) {
            exitFullScreen();
        }
        if (isTinyWindow()) {
            exitTinyWindow();
        }
        mCurrentMode = ConstantKeys.PlayMode.MODE_NORMAL;

        // 释放播放器
        releasePlayer();

        // 恢复控制器
        if (mController != null) {
            mController.reset();
        }
        // gc回收
        Runtime.getRuntime().gc();
    }

    /**
     * 释放播放器，注意一定要判断对象是否为空，增强严谨性
     * 这样以便在当前播放器状态下可以方便的切换不同的清晰度的视频地址
     * 关于我的github：https://github.com/yangchong211
     * 杨充修改：
     *      17年12月23日，添加释放音频和TextureView
     */
    @Override
    public void releasePlayer() {
        videoMediaPlayer.setAudioManagerNull();
        if (videoMediaPlayer.getMediaPlayer() != null) {
            //释放视频焦点
            videoMediaPlayer.getMediaPlayer().release();
            videoMediaPlayer.setMediaPlayerNull();
        }
        if (mContainer!=null){
            //从视图中移除TextureView
            mContainer.removeView(videoMediaPlayer.getTextureView());
        }
        videoMediaPlayer.releaseSurface();
        //如果SurfaceTexture不为null，则释放
        videoMediaPlayer.releaseSurfaceTexture();
        mCurrentState = ConstantKeys.CurrentState.STATE_IDLE;
    }


}
