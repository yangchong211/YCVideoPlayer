package org.yczbj.ycvideoplayerlib.player;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import org.yczbj.ycvideoplayerlib.player.VideoPlayer;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 音频焦点改变监听
 *     revise:
 * </pre>
 */
public final class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private WeakReference<VideoPlayer> mWeakVideoView;
    private AudioManager mAudioManager;
    private boolean mStartRequested = false;
    private boolean mPausedForLoss = false;
    private int mCurrentFocus = 0;

    public AudioFocusHelper(@NonNull VideoPlayer videoView) {
        mWeakVideoView = new WeakReference<>(videoView);
        mAudioManager = (AudioManager) videoView.getContext().getApplicationContext()
                .getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onAudioFocusChange(final int focusChange) {
        if (mCurrentFocus == focusChange) {
            return;
        }

        //由于onAudioFocusChange有可能在子线程调用，
        //故通过此方式切换到主线程去执行
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //处理音频焦点抢占
                handleAudioFocusChange(focusChange);
            }
        });
        mCurrentFocus = focusChange;
    }

    private void handleAudioFocusChange(int focusChange) {
        final VideoPlayer videoView = mWeakVideoView.get();
        if (videoView == null) {
            return;
        }
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                //获得焦点
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                //暂时获得焦点
                if (mStartRequested || mPausedForLoss) {
                    videoView.start();
                    mStartRequested = false;
                    mPausedForLoss = false;
                }
                if (!videoView.isMute())
                    //恢复音量
                    videoView.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                //焦点丢失
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                //焦点暂时丢失
                if (videoView.isPlaying()) {
                    mPausedForLoss = true;
                    videoView.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                //此时需降低音量
                if (videoView.isPlaying() && !videoView.isMute()) {
                    videoView.setVolume(0.1f, 0.1f);
                }
                break;
        }
    }

    /**
     * 请求获取音频焦点
     */
    public void requestFocus() {
        if (mCurrentFocus == AudioManager.AUDIOFOCUS_GAIN) {
            return;
        }
        if (mAudioManager == null) {
            return;
        }
        int status = mAudioManager.requestAudioFocus(this,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (AudioManager.AUDIOFOCUS_REQUEST_GRANTED == status) {
            mCurrentFocus = AudioManager.AUDIOFOCUS_GAIN;
            return;
        }
        mStartRequested = true;
    }

    /**
     * 请求系统放下音频焦点
     */
    public void abandonFocus() {
        if (mAudioManager == null) {
            return;
        }
        mStartRequested = false;
        mAudioManager.abandonAudioFocus(this);
    }

    /**
     * 销毁资源
     */
    public void release(){
        abandonFocus();
        if (mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

}