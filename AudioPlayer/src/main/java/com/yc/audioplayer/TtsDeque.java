package com.yc.audioplayer;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TtsDeque {

    private Logger mLogger = LogService.getLogger(TtsDeque.class.getSimpleName());
    private Lock mLock = new ReentrantLock();
    private final Condition mNotEmpty = mLock.newCondition();
    private LinkedBlockingDeque<PlayData> mHighDeque = new LinkedBlockingDeque<>();
    private LinkedBlockingDeque<PlayData> mMiddleDeque = new LinkedBlockingDeque<>();
    private LinkedBlockingDeque<PlayData> mNormalDeque = new LinkedBlockingDeque<>();

    public void add(PlayData tts) {
        mLock.lock();
        try {
            switch (tts.mPriority) {
                case HIGH_PRIORITY:
                    mHighDeque.add(tts);
                    mLogger.debug("TTS queue add high: " + tts.getTts());
                    break;
                case MIDDLE_PRIORITY:
                    mMiddleDeque.add(tts);
                    mLogger.debug("TTS queue add  middle: " + tts.getTts());
                    break;
                case NORMAL_PRIORITY:
                    mNormalDeque.add(tts);
                    mLogger.debug("TTS queue add  normal: " + tts.getTts());
                    break;
            }
            mNotEmpty.signal();
        } finally {
            mLock.unlock();
        }
    }

    public PlayData get() throws InterruptedException {
        PlayData data;
        mLock.lock();
        try {
            while ((data = getTts()) == null) {
                mLogger.debug("TTS queue no data to play ");
                mNotEmpty.await();
            }
            mLogger.debug("TTS queue  will play is" + data.getTts() + " rawId " + data.getRawId());
        } finally {
            mLock.unlock();
        }
        return data;
    }

    public PlayData getTts() {
        PlayData tts = mHighDeque.poll();
        if (tts == null) {
            tts = mMiddleDeque.poll();
        }
        if (tts == null) {
            tts = mNormalDeque.poll();
        }
        mLogger.debug("TTS queue get data is " + tts);
        return tts;
    }

    public void clear() {
        mHighDeque.clear();
        mMiddleDeque.clear();
        mNormalDeque.clear();
    }
}
