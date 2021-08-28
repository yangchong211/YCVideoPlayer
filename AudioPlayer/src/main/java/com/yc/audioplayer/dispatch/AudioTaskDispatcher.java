package com.yc.audioplayer.dispatch;

import android.os.Process;

import com.yc.audioplayer.deque.AudioTtsDeque;
import com.yc.audioplayer.manager.AudioManager;
import com.yc.audioplayer.wrapper.AbstractAudioWrapper;
import com.yc.audioplayer.bean.AudioPlayData;
import com.yc.audioplayer.inter.InterPlayListener;
import com.yc.videotool.VideoLogUtils;


public class AudioTaskDispatcher implements InterPlayListener {

    private AudioTtsDeque mTaskDeque ;
    private AudioPlayData mCurrentPlayData;
    private AbstractAudioWrapper mAudioManager;
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

    @Override
    public void onError(String error) {

    }

    public void initialize(final AudioManager manager) {
        this.mAudioManager = manager;
        this.mTaskDeque = new AudioTtsDeque();
        this.mRunning = true;
        VideoLogUtils.d("AudioTaskDispatcher initialize: ");
        this.mTtsThread = new Thread() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
                while (mRunning) {
                    try {
                        VideoLogUtils.d("AudioTaskDispatcher is running ");
                        mCurrentPlayData = mTaskDeque.get();
                        mAudioManager.play(mCurrentPlayData);
                        synchronized (manager.mMutex) {
                            VideoLogUtils.d("AudioTaskDispatcher is wait  " + mCurrentPlayData.getTts());
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

    public void addTask(AudioPlayData data) {
        if (data == null) {
            return;
        }
        if (mCurrentPlayData != null && data.mPriority.ordinal() > mCurrentPlayData.mPriority.ordinal()) {
            mAudioManager.stop();
        }
        VideoLogUtils.d("AudioTaskDispatcher data: " + data.getTts() + data.mPriority);
        mTaskDeque.add(data);
    }

    public void release() {
        mRunning = false;
        mTaskDeque.clear();
        mTtsThread.interrupt();
    }

}
