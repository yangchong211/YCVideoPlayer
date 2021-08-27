package com.yc.audioplayer;


public abstract class AbstractAudio implements IAudio, IPlayListener {

    public final Object mMutex = new Object();

}
