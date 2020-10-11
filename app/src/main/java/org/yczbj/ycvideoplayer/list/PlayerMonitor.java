package org.yczbj.ycvideoplayer.list;

import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;

import com.yc.kernel.utils.VideoLogUtils;

import org.yczbj.ycvideoplayerlib.controller.ControlWrapper;
import org.yczbj.ycvideoplayerlib.controller.IControlComponent;
import org.yczbj.ycvideoplayerlib.tool.PlayerUtils;


public class PlayerMonitor implements IControlComponent {

    private ControlWrapper mControlWrapper;

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {
        VideoLogUtils.d("onVisibilityChanged: " + isVisible);
    }

    @Override
    public void onPlayStateChanged(int playState) {
        VideoLogUtils.d("onPlayStateChanged: " + PlayerUtils.playState2str(playState));
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        VideoLogUtils.d("onPlayerStateChanged: " + PlayerUtils.playerState2str(playerState));
    }

    @Override
    public void setProgress(int duration, int position) {
        VideoLogUtils.d("setProgress: duration: " + duration + " position: " + position + " buffered percent: " + mControlWrapper.getBufferedPercentage());
        VideoLogUtils.d("network speed: " + mControlWrapper.getTcpSpeed());
    }

    @Override
    public void onLockStateChanged(boolean isLocked) {
        VideoLogUtils.d("onLockStateChanged: " + isLocked);
    }
}
