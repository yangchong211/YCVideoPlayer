package com.yc.audioplayer;

import android.os.Process;

import com.didi.foundation.sdk.log.LogService;
import com.didi.sdk.logging.Logger;

/**
 * @author nate
 * @since 2018/8/28.
 */
public class AudioTaskDispatcher implements IPlayListener {

    private Logger mLogger = LogService.getLogger(AudioTaskDispatcher.class.getSimpleName());
    private TtsDeque mTaskDeque ;
    private PlayData mCurrentPlayData;
    private AbstractAudio mAudioManager;

    private boolean mRunning = true;
    private Thread mTtsThread;

    private static class Holder {
        private static final AudioTaskDispatcher INSTANCE = new AudioTaskDispatcher();
    }

    private AudioTaskDispatcher() {
    }

    public static AudioTaskDispatcher getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void onCompleted() {
        mAudioManager.onCompleted();
    }

    public void initialize(final AudioManager manager) {
        this.mAudioManager = manager;
        this.mTaskDeque = new TtsDeque();
        this.mRunning = true;
        mLogger.debug("AudioTaskDispatcher initialize: ");
        this.mTtsThread = new Thread() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
                while (mRunning) {
                    try {
                        mLogger.debug("AudioTaskDispatcher is running ");
                        mCurrentPlayData = mTaskDeque.get();
                        mAudioManager.play(mCurrentPlayData);
                        synchronized (manager.mMutex) {
                            mLogger.debug("AudioTaskDispatcher is wait  " + mCurrentPlayData.getTts());
                            manager.mMutex.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };
        this.mTtsThread.start();
    }

    public void addTask(PlayData data) {
        if (data == null) {
            return;
        }
        if (mCurrentPlayData != null && data.mPriority.ordinal() > mCurrentPlayData.mPriority.ordinal()) {
            mAudioManager.stop();
        }
        mLogger.debug("AudioTaskDispatcher data: " + data.getTts() + data.mPriority);
        mTaskDeque.add(data);
    }

    public void release() {
        mRunning = false;
        mTaskDeque.clear();
        mTtsThread.interrupt();
    }

}
