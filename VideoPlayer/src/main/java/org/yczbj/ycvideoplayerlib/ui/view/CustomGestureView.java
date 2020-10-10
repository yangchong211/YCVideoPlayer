/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.yczbj.ycvideoplayerlib.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.yczbj.ycvideoplayerlib.R;
import org.yczbj.ycvideoplayerlib.controller.ControlWrapper;
import org.yczbj.ycvideoplayerlib.controller.IGestureComponent;
import org.yczbj.ycvideoplayerlib.player.video.VideoView;
import org.yczbj.ycvideoplayerlib.tool.utils.PlayerUtils;

/**
 * 手势控制
 */

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 手势控制
 *     revise: 用于滑动改变亮度和音量的功能
 * </pre>
 */
public class CustomGestureView extends FrameLayout implements IGestureComponent {

    private Context mContext;
    private ControlWrapper mControlWrapper;
    private LinearLayout mLlCenterContainer;
    private ImageView mIvIcon;
    private TextView mTvPercent;
    private ProgressBar mProPercent;

    public CustomGestureView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomGestureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomGestureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        setVisibility(GONE);
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.custom_video_player_gesture, this, true);
        initFindViewById(view);
        initListener();
    }

    private void initFindViewById(View view) {
        mLlCenterContainer = view.findViewById(R.id.ll_center_container);
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvPercent = view.findViewById(R.id.tv_percent);
        mProPercent = view.findViewById(R.id.pro_percent);

    }

    private void initListener() {

    }


    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {

    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void onStartSlide() {
        mControlWrapper.hide();
        mLlCenterContainer.setVisibility(VISIBLE);
        mLlCenterContainer.setAlpha(1f);
    }

    @Override
    public void onStopSlide() {
        mLlCenterContainer.animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mLlCenterContainer.setVisibility(GONE);
                    }
                })
                .start();
    }

    @Override
    public void onPositionChange(int slidePosition, int currentPosition, int duration) {
        mProPercent.setVisibility(GONE);
        if (slidePosition > currentPosition) {
            mIvIcon.setImageResource(R.drawable.ic_player_fast_forward);
        } else {
            mIvIcon.setImageResource(R.drawable.ic_player_fast_rewind);
        }
        mTvPercent.setText(String.format("%s/%s", PlayerUtils.formatTime(slidePosition), PlayerUtils.formatTime(duration)));
    }

    @Override
    public void onBrightnessChange(int percent) {
        mProPercent.setVisibility(VISIBLE);
        mIvIcon.setImageResource(R.drawable.ic_palyer_brightness);
        mTvPercent.setText(percent + "%");
        mProPercent.setProgress(percent);
    }

    @Override
    public void onVolumeChange(int percent) {
        mProPercent.setVisibility(VISIBLE);
        if (percent <= 0) {
            mIvIcon.setImageResource(R.drawable.ic_player_volume_off);
        } else {
            mIvIcon.setImageResource(R.drawable.ic_player_volume_up);
        }
        mTvPercent.setText(percent + "%");
        mProPercent.setProgress(percent);
    }

    @Override
    public void onPlayStateChanged(int playState) {
        if (playState == VideoView.STATE_IDLE
                || playState == VideoView.STATE_START_ABORT
                || playState == VideoView.STATE_PREPARING
                || playState == VideoView.STATE_PREPARED
                || playState == VideoView.STATE_ERROR
                || playState == VideoView.STATE_PLAYBACK_COMPLETED) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLock) {

    }

}
