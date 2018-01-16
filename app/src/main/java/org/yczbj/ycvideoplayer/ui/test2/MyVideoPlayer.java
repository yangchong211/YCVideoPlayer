package org.yczbj.ycvideoplayer.ui.test2;

import android.content.Context;

import org.yczbj.ycvideoplayerlib.AbsVideoPlayerController;
import org.yczbj.ycvideoplayerlib.VideoPlayer;

import java.util.Map;

/**
 * Created by yc on 2018/1/16.
 */

public class MyVideoPlayer extends VideoPlayer {

    public MyVideoPlayer(Context context) {
        super(context);
    }

    @Override
    public void setUp(String url, Map<String, String> headers) {
        super.setUp(url, headers);
    }

    @Override
    public void setController(AbsVideoPlayerController controller) {
        super.setController(controller);
    }

    @Override
    public void setPlayerType(int playerType) {
        super.setPlayerType(playerType);
    }

    @Override
    public void continueFromLastPosition(boolean continueFromLastPosition) {
        super.continueFromLastPosition(continueFromLastPosition);
    }

    @Override
    public void setSpeed(float speed) {
        super.setSpeed(speed);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void start(long position) {
        super.start(position);
    }

    @Override
    public void restart() {
        super.restart();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void seekTo(long pos) {
        super.seekTo(pos);
    }

    @Override
    public void setVolume(int volume) {
        super.setVolume(volume);
    }

    @Override
    public boolean isIdle() {
        return super.isIdle();
    }

    @Override
    public boolean isPreparing() {
        return super.isPreparing();
    }

    @Override
    public boolean isPrepared() {
        return super.isPrepared();
    }

    @Override
    public boolean isBufferingPlaying() {
        return super.isBufferingPlaying();
    }

    @Override
    public boolean isBufferingPaused() {
        return super.isBufferingPaused();
    }

    @Override
    public boolean isPlaying() {
        return super.isPlaying();
    }

    @Override
    public boolean isPaused() {
        return super.isPaused();
    }

    @Override
    public boolean isError() {
        return super.isError();
    }
}
