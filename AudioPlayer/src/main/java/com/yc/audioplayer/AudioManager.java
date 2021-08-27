package com.yc.audioplayer;

import android.content.Context;


public class AudioManager extends AbstractAudio {

    private final IAudio mTtsEngine;
    private final IAudio mMediaPlayer;
    private PlayData mCurrentData;
    private IAudio mCurrentAudio;
    private PlayStateListener mPlayStateListener;

    public AudioManager() {
        mTtsEngine = IAudioService.getInstance();
        mMediaPlayer = new MediaAudio();
    }
    @Override
    public void init(IPlayListener next, Context context) {
        mTtsEngine.init(next, context);
        mMediaPlayer.init(next, context);
    }

    @Override
    public void play(PlayData data) {
        if (null != mPlayStateListener) {
            mPlayStateListener.onStartPlay();
        }
        this.mCurrentData = data;
        this.mCurrentAudio = data.mPlayTts ? mTtsEngine : mMediaPlayer;
        this.mCurrentAudio.play(data);
    }

    /**
     * 暂停播放内容
     */
    @Override
    public void stop() {
        if (mCurrentAudio != null) {
            mCurrentAudio.stop();
            mCurrentData = null;
            synchronized (mMutex) {
                mMutex.notifyAll();
            }
        }
    }

    @Override
    public void release() {
        mTtsEngine.release();
        mMediaPlayer.release();
    }

    @Override
    public void pause() {
        mCurrentAudio.pause();
    }

    @Override
    public void resumeSpeaking() {
        mCurrentAudio.resumeSpeaking();
    }

    @Override
    public boolean isPlaying() {
        return mCurrentAudio != null && mCurrentAudio.isPlaying();
    }

    @Override
    public void onCompleted() {
        if (mCurrentData != null && mCurrentData.getNext() != null) {
            mCurrentData = mCurrentData.getNext();
            play(mCurrentData);
        } else {
            synchronized (mMutex) {
                mMutex.notifyAll();
            }
            if (null != mPlayStateListener) {
                mPlayStateListener.onCompletePlay();
            }
        }
    }

    public void setPlayStateListener(PlayStateListener playStateListener) {
        this.mPlayStateListener = playStateListener;
    }

    public interface PlayStateListener {

        void onStartPlay();

        void onCompletePlay();
    }
}
