package com.yc.audioplayer.deque;

import com.yc.audioplayer.bean.AudioPlayData;
import com.yc.videotool.VideoLogUtils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AudioTtsDeque {

    private final Lock mLock = new ReentrantLock();
    private final Condition mNotEmpty = mLock.newCondition();
    private final LinkedBlockingDeque<AudioPlayData> mHighDeque = new LinkedBlockingDeque<>();
    private final LinkedBlockingDeque<AudioPlayData> mMiddleDeque = new LinkedBlockingDeque<>();
    private final LinkedBlockingDeque<AudioPlayData> mNormalDeque = new LinkedBlockingDeque<>();

    public void add(AudioPlayData tts) {
        mLock.lock();
        try {
            switch (tts.mPriority) {
                case HIGH_PRIORITY:
                    mHighDeque.add(tts);
                    VideoLogUtils.d("TTS queue add high: " + tts.getTts());
                    break;
                case MIDDLE_PRIORITY:
                    mMiddleDeque.add(tts);
                    VideoLogUtils.d("TTS queue add  middle: " + tts.getTts());
                    break;
                case NORMAL_PRIORITY:
                    mNormalDeque.add(tts);
                    VideoLogUtils.d("TTS queue add  normal: " + tts.getTts());
                    break;
            }
            mNotEmpty.signal();
        } finally {
            mLock.unlock();
        }
    }

    public AudioPlayData get() throws InterruptedException {
        AudioPlayData data;
        mLock.lock();
        try {
            while ((data = getTts()) == null) {
                VideoLogUtils.d("TTS queue no data to play ");
                mNotEmpty.await();
            }
            VideoLogUtils.d("TTS queue  will play is" + data.getTts() + " rawId " + data.getRawId());
        } finally {
            mLock.unlock();
        }
        return data;
    }

    public AudioPlayData getTts() {
        AudioPlayData tts = mHighDeque.poll();
        if (tts == null) {
            tts = mMiddleDeque.poll();
        }
        if (tts == null) {
            tts = mNormalDeque.poll();
        }
        VideoLogUtils.d("TTS queue get data is " + tts);
        return tts;
    }

    public void clear() {
        mHighDeque.clear();
        mMiddleDeque.clear();
        mNormalDeque.clear();
    }
}
