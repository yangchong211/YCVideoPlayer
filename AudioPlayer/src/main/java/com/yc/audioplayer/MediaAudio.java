package com.yc.audioplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;


public class MediaAudio extends AbstractAudio {

    private IPlayListener mPlayListener;
    private Context mContext;
    private Logger mLogger = LogService.getLogger(MediaAudio.class.getSimpleName());

    private MediaPlayer mMediaPlayer;
    private boolean mPause = false;

    /**
     * 完成/出错时的监听接口
     */
    private OnCompletionListener mOnCompletionListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer player) {
            if (mMediaPlayer != null && player != null && mMediaPlayer == player) {
                try {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } finally {
                    mMediaPlayer = null;
                }
            }
            onCompleted();
        }
    };

    MediaAudio() {
    }

    @Override
    public void init(IPlayListener next, Context context) {
        this.mPlayListener = next;
        this.mContext = context;
    }

    /**
     * 播放raw资源
     */
    @Override
    public void play(PlayData data) {
        mLogger.debug("MediaPlay: play resourceId is" + data.getRawId());
        if (data.getRawId() <= 0) {
            return;
        }
        if (mMediaPlayer == null) {
            try {
                mMediaPlayer = new MediaPlayer();
                AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(data.getRawId());

//                Uri sound = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + data.getRawId());//  res/raw文件中的url地址。
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); //set streaming according to ur needs
                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
//                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        try {
                            mMediaPlayer.start();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });
                mMediaPlayer.prepare();
            } catch (Throwable e) {
                e.printStackTrace();
                mLogger.debug("MediaPlay: play fail");
                onCompleted();
            }
        }
    }

    /**
     * 停止播放
     */
    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            synchronized (mMediaPlayer) {
                try {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } finally {
                    mMediaPlayer = null;
                }
            }
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPause = true;
        }
    }

    @Override
    public void resumeSpeaking() {
        if (mMediaPlayer != null && mPause) {
            mMediaPlayer.start();
            mPause = false;
        }
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    public void onCompleted() {
        if (mPlayListener != null) {
            mPlayListener.onCompleted();
        }
    }

}
