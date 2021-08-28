package com.yc.audioplayer.wrapper;


import com.yc.audioplayer.inter.InterAudio;
import com.yc.audioplayer.inter.InterPlayListener;

public abstract class AbstractAudioWrapper implements InterAudio, InterPlayListener {

    public final Object mMutex = new Object();

}
