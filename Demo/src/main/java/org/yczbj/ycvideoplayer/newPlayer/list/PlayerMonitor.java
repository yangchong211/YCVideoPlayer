package org.yczbj.ycvideoplayer.newPlayer.list;

import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;

import com.yc.kernel.utils.VideoLogUtils;

import org.yczbj.ycvideoplayerlib.controller.ControlWrapper;
import org.yczbj.ycvideoplayerlib.ui.view.InterControlView;
import org.yczbj.ycvideoplayerlib.tool.PlayerUtils;


public class PlayerMonitor implements InterControlView {

    private ControlWrapper mControlWrapper;
    private static final String TAG = "PlayerMonitor";

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
        VideoLogUtils.d(TAG+ "---" +"onVisibilityChanged: " + isVisible);
    }

    @Override
    public void onPlayStateChanged(int playState) {
        VideoLogUtils.d(TAG+ "---" +"onPlayStateChanged: " + PlayerUtils.playState2str(playState));
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        VideoLogUtils.d(TAG+ "---" +"onPlayerStateChanged: " + PlayerUtils.playerState2str(playerState));
    }

    @Override
    public void setProgress(int duration, int position) {
        VideoLogUtils.d(TAG+ "---" +"setProgress: duration: " + duration + " position: " + position + " buffered percent: " + mControlWrapper.getBufferedPercentage());
        VideoLogUtils.d(TAG+ "---" +"network speed: " + mControlWrapper.getTcpSpeed());
    }

    @Override
    public void onLockStateChanged(boolean isLocked) {
        VideoLogUtils.d(TAG+ "---" +"onLockStateChanged: " + isLocked);
    }
}
