package com.yc.video.old.player;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.view.Surface;

import androidx.annotation.RequiresApi;

import com.yc.kernel.utils.VideoLogUtils;
import com.yc.video.config.ConstantKeys;
import com.yc.video.old.surface.VideoTextureView;
import com.yc.video.tool.BaseToast;
import com.yc.video.tool.PlayerUtils;

import java.io.IOException;

import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : MediaPlayer帮助累
 *     revise: 主要处理音视频player初始化操作和各种监听
 * </pre>
 */
@Deprecated
public class VideoMediaPlayer {

    private OldVideoPlayer videoPlayer;
    private IMediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private Surface mSurface;
    private VideoTextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;

    public VideoMediaPlayer(OldVideoPlayer videoPlayer) {
        this.videoPlayer = videoPlayer;
    }

    /**
     * 避免直接初始化
     */
    private VideoMediaPlayer(){}

    /**
     * 初始化音频管理器
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public AudioManager initAudioManager() {
        if (mAudioManager == null) {
            mAudioManager  = (AudioManager) videoPlayer.getContext()
                    .getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
        }
        return mAudioManager;
    }

    public IMediaPlayer getMediaPlayer() {
        initMediaPlayer();
        return mMediaPlayer;
    }

    public void setMediaPlayerNull(){
        if (mMediaPlayer!=null){
            mMediaPlayer = null;
        }
    }

    public AudioManager getAudioManager() {
        initAudioManager();
        return mAudioManager;
    }

    public void setAudioManagerNull(){
        if (mAudioManager!=null){
            //放弃音频焦点。使以前的焦点所有者(如果有的话)接收焦点。
            mAudioManager.abandonAudioFocus(null);
            //置空
            mAudioManager = null;
        }
    }

    public Surface getSurface() {
        return mSurface;
    }

    /**
     *
     */
    public void releaseSurface(){
        if (mSurface != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mSurface.release();
            }
            mSurface = null;
        }
    }

    public VideoTextureView getTextureView() {
        return mTextureView;
    }

    /**
     * 如果SurfaceTexture不为null，则释放
     */
    public void releaseSurfaceTexture(){
        if (mSurfaceTexture != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mSurfaceTexture.release();
            }
            mSurfaceTexture = null;
        }
    }

    /**
     * 初始化视频管理器
     */
    public void initMediaPlayer() {
        if (mMediaPlayer == null) {
            switch (videoPlayer.mPlayerType) {
                //AndroidMediaPlayer和IjkMediaPlayer都是实现AbstractMediaPlayer
                //MediaPlayer
                case ConstantKeys.VideoPlayerType.TYPE_NATIVE:
                    mMediaPlayer = new AndroidMediaPlayer();
                    break;
                //IjkMediaPlayer    基于Ijk
                case ConstantKeys.VideoPlayerType.TYPE_IJK:
                default:
                    mMediaPlayer = createIjkMediaPlayer();
                    break;
            }
            //设置音频流类型
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }

    /**
     * 打开MediaPlayer播放器
     */
    private IMediaPlayer createIjkMediaPlayer() {
        //创建IjkMediaPlayer对象
        mMediaPlayer = new IjkMediaPlayer();
        int player = IjkMediaPlayer.OPT_CATEGORY_PLAYER;
        int codec = IjkMediaPlayer.OPT_CATEGORY_CODEC;
        int format = IjkMediaPlayer.OPT_CATEGORY_FORMAT;

        //设置ijkPlayer播放器的硬件解码相关参数
        //设置播放前的最大探测时间
        ((IjkMediaPlayer)mMediaPlayer).setOption(format, "analyzemaxduration", 100L);
        //设置播放前的探测时间 1,达到首屏秒开效果
        ((IjkMediaPlayer)mMediaPlayer).setOption(format, "analyzeduration", 1L);
        //播放前的探测Size，默认是1M, 改小一点会出画面更快
        ((IjkMediaPlayer)mMediaPlayer).setOption(format, "probesize", 10240L);
        //设置是否开启变调isModifyTone?0:1
        ((IjkMediaPlayer)mMediaPlayer).setOption(player,"soundtouch",0);
        //每处理一个packet之后刷新io上下文
        ((IjkMediaPlayer)mMediaPlayer).setOption(format, "flush_packets", 1L);
        //是否开启预缓冲，一般直播项目会开启，达到秒开的效果，不过带来了播放丢帧卡顿的体验
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "packet-buffering", 0L);
        //播放重连次数
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "reconnect", 5);
        //最大缓冲大小,单位kb
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "max-buffer-size", 10240L);
        //跳帧处理,放CPU处理较慢时，进行跳帧处理，保证播放流程，画面和声音同步
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "framedrop", 1L);
        //最大fps
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "max-fps", 30L);
        //SeekTo设置优化
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "enable-accurate-seek", 1L);
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "opensles", 0);
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "overlay-format",
                IjkMediaPlayer.SDL_FCC_RV32);
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "framedrop", 1);
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "start-on-prepared", 0);
        ((IjkMediaPlayer)mMediaPlayer).setOption(format, "http-detect-range-support", 0);
        //设置是否开启环路过滤: 0开启，画面质量高，解码开销大，48关闭，画面质量差点，解码开销小
        ((IjkMediaPlayer)mMediaPlayer).setOption(codec, "skip_loop_filter", 48);

        //jkPlayer支持硬解码和软解码。
        //软解码时不会旋转视频角度这时需要你通过onInfo的what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED去获取角度，自己旋转画面。
        //或者开启硬解硬解码，不过硬解码容易造成黑屏无声（硬件兼容问题），下面是设置硬解码相关的代码
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "mediacodec", 0);
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "mediacodec-auto-rotate", 1);
        ((IjkMediaPlayer)mMediaPlayer).setOption(player, "mediacodec-handle-resolution-change", 1);
        return mMediaPlayer;
    }


    /**
     * 打开MediaPlayer播放器
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void openMediaPlayer() {
        // 屏幕常亮，这个很重要，如果不设置，则看视频一会儿，屏幕会变暗
        videoPlayer.getContainer().setKeepScreenOn(true);
        // 设置监听，可以查看ijk中的IMediaPlayer源码监听事件
        // 设置准备视频播放监听事件
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        // 设置视频播放完成监听事件
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        // 设置视频缓冲更新监听事件
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        // 设置视频seek完成监听事件
        mMediaPlayer.setOnSeekCompleteListener(mOnSeekCompleteListener);
        // 设置视频大小更改监听器
        mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        // 设置视频错误监听器
        mMediaPlayer.setOnErrorListener(mOnErrorListener);
        // 设置视频信息监听器
        mMediaPlayer.setOnInfoListener(mOnInfoListener);
        // 设置时间文本监听器
        mMediaPlayer.setOnTimedTextListener(mOnTimedTextListener);
        // 设置dataSource
        if(videoPlayer.getUrl()==null || videoPlayer.getUrl().length()==0){
            BaseToast.showRoundRectToast("视频链接不能为空");
            return;
        }
        Uri path = Uri.parse(videoPlayer.getUrl());
        try {
            mMediaPlayer.setDataSource(videoPlayer.getContext().getApplicationContext(), path, videoPlayer.getHeaders());
            if (mSurface == null) {
                mSurface = new Surface(mSurfaceTexture);
            }
            // 设置surface
            mMediaPlayer.setSurface(mSurface);
            // 设置播放时常亮
            mMediaPlayer.setScreenOnWhilePlaying(true);
            // 开始加载
            mMediaPlayer.prepareAsync();
            // 播放准备中
            videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_PREPARING);
            // 控制器，更新不同的播放状态的UI
            videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
            VideoLogUtils.d("STATE_PREPARING");
        } catch (IOException e) {
            e.printStackTrace();
            VideoLogUtils.e("打开播放器发生错误", e);
        }
    }


    /**
     * 初始化TextureView
     * 这个主要是用作视频的
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void initTextureView() {
        if (mTextureView == null) {
            mTextureView = new VideoTextureView(videoPlayer.getContext());
            mTextureView.setOnTextureListener(new VideoTextureView.OnTextureListener() {
                @Override
                public void onSurfaceAvailable(SurfaceTexture surface) {
                    if (mSurfaceTexture == null) {
                        mSurfaceTexture = surface;
                        openMediaPlayer();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mTextureView.setSurfaceTexture(mSurfaceTexture);
                        }
                    }
                }

                @Override
                public void onSurfaceSizeChanged(SurfaceTexture surface, int width, int height) {
                    VideoLogUtils.i("OnTextureListener----"+"onSurfaceSizeChanged");
                }

                @Override
                public boolean onSurfaceDestroyed(SurfaceTexture surface) {
                    VideoLogUtils.i("OnTextureListener----"+"onSurfaceDestroyed");
                    return mSurfaceTexture == null;
                }

                @Override
                public void onSurfaceUpdated(SurfaceTexture surface) {
                    VideoLogUtils.i("OnTextureListener----"+"onSurfaceUpdated");
                }
            });
        }
        mTextureView.addTextureView(videoPlayer.getContainer(),mTextureView);
    }



    /**
     * 设置视频播放完成监听事件
     */
    private IMediaPlayer.OnCompletionListener mOnCompletionListener =
            new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer mp) {
                    videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_COMPLETED);
                    videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
                    VideoLogUtils.d("listener---------onCompletion ——> STATE_COMPLETED");
                    // 清除屏幕常亮
                    videoPlayer.getContainer().setKeepScreenOn(false);
                }
            };


    /**
     * 设置准备视频播放监听事件
     */
    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_PREPARED);
            videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
            VideoLogUtils.d("listener---------onPrepared ——> STATE_PREPARED");
            mp.start();
            // 从上次的保存位置播放
            if (videoPlayer.getContinueFromLastPosition()) {
                long savedPlayPosition = PlayerUtils.getSavedPlayPosition(
                        videoPlayer.getContext(), videoPlayer.getUrl());
                mp.seekTo(savedPlayPosition);
            }
            // 跳到指定位置播放
            if (videoPlayer.getSkipToPosition() != 0) {
                mp.seekTo(videoPlayer.getSkipToPosition());
            }
        }
    };


    /**
     * 设置视频缓冲更新监听事件
     */
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener =
            new IMediaPlayer.OnBufferingUpdateListener() {
                final int MAX_PERCENT = 97;
                @Override
                public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                    videoPlayer.setBufferPercentage(percent);
                    //播放完成后再次播放getBufferPercentage获取的值也不准确，94、95，达不到100
                    if (percent>MAX_PERCENT){
                        videoPlayer.setBufferPercentage(100);
                    }
                    VideoLogUtils.d("listener---------onBufferingUpdate ——> " + percent);
                }
            };



    /**
     * 设置视频seek完成监听事件
     */
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener =
            new IMediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(IMediaPlayer iMediaPlayer) {

                }
            };



    /**
     * 设置视频大小更改监听器
     */
    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener =
            new IMediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(IMediaPlayer mp, int width, int height,
                                               int sar_num, int sar_den) {
                    mTextureView.adaptVideoSize(width, height);
                    VideoLogUtils.d("listener---------onVideoSizeChanged ——> WIDTH：" + width + "， HEIGHT：" + height);
                }
            };

    /**
     * 设置视频错误监听器
     * int MEDIA_INFO_VIDEO_RENDERING_START = 3;//视频准备渲染
     * int MEDIA_INFO_BUFFERING_START = 701;//开始缓冲
     * int MEDIA_INFO_BUFFERING_END = 702;//缓冲结束
     * int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;//视频选择信息
     * int MEDIA_ERROR_SERVER_DIED = 100;//视频中断，一般是视频源异常或者不支持的视频类型。
     * int MEDIA_ERROR_IJK_PLAYER = -10000,//一般是视频源有问题或者数据格式不支持，比如音频不是AAC之类的
     * int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;//数据错误没有有效的回收
     */
    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            // 直播流播放时去调用mediaPlayer.getDuration会导致-38和-2147483648错误，忽略该错误
            if (what != -38 && what != -2147483648 && extra != -38 && extra != -2147483648) {
                videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_ERROR);
                videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
            }
            VideoLogUtils.d("listener---------onError ——> STATE_ERROR ———— what：" + what + ", extra: " + extra);
            return true;
        }
    };


    /**
     * 设置视频信息监听器
     */
    private IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            if (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                // 播放器开始渲染
                videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_PLAYING);
                videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
                VideoLogUtils.d("listener---------onInfo ——> MEDIA_INFO_VIDEO_RENDERING_START：STATE_PLAYING");
            } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                // MediaPlayer暂时不播放，以缓冲更多的数据
                if (videoPlayer.getCurrentState() == ConstantKeys.CurrentState.STATE_PAUSED ||
                        videoPlayer.getCurrentState() == ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED) {
                    videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED);
                    VideoLogUtils.d("listener---------onInfo ——> MEDIA_INFO_BUFFERING_START：STATE_BUFFERING_PAUSED");
                } else {
                    videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING);
                    VideoLogUtils.d("listener---------onInfo ——> MEDIA_INFO_BUFFERING_START：STATE_BUFFERING_PLAYING");
                }
                videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
            } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                // 填充缓冲区后，MediaPlayer恢复播放/暂停
                if (videoPlayer.getCurrentState() == ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING) {
                    videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_PLAYING);
                    videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
                    VideoLogUtils.d("listener---------onInfo ——> MEDIA_INFO_BUFFERING_END： STATE_PLAYING");
                }
                if (videoPlayer.getCurrentState() == ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED) {
                    videoPlayer.setCurrentState(ConstantKeys.CurrentState.STATE_PAUSED);
                    videoPlayer.getController().onPlayStateChanged(videoPlayer.getCurrentState());
                    VideoLogUtils.d("listener---------onInfo ——> MEDIA_INFO_BUFFERING_END： STATE_PAUSED");
                }
            } else if (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED) {
                // 视频旋转了extra度，需要恢复
                if (mTextureView != null) {
                    mTextureView.setRotation(extra);
                    VideoLogUtils.d("listener---------视频旋转角度：" + extra);
                }
            } else if (what == IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE) {
                VideoLogUtils.d("listener---------视频不能seekTo，为直播视频");
            } else {
                VideoLogUtils.d("listener---------onInfo ——> what：" + what);
            }
            return true;
        }
    };


    /**
     * 设置时间文本监听器
     */
    private IMediaPlayer.OnTimedTextListener mOnTimedTextListener = new
            IMediaPlayer.OnTimedTextListener() {
                @Override
                public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {

                }
            };

}
