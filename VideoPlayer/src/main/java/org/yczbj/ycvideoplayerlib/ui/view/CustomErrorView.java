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

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.yczbj.ycvideoplayerlib.R;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.ControlWrapper;
import org.yczbj.ycvideoplayerlib.controller.IControlComponent;
import org.yczbj.ycvideoplayerlib.player.video.VideoPlayer;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 出错提示界面
 *     revise:
 * </pre>
 */
public class CustomErrorView extends LinearLayout implements IControlComponent {

    private Context mContext;
    private float mDownX;
    private float mDownY;
    private TextView mTvMessage;
    private TextView mTvRetry;

    private ControlWrapper mControlWrapper;

    public CustomErrorView(Context context) {
        this(context, null);
        init(context);
    }

    public CustomErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        this.mContext = context;
        setVisibility(GONE);
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.custom_video_player_error, this, true);
        initFindViewById(view);
        initListener();
        setClickable(true);
    }

    private void initFindViewById(View view) {
        mTvMessage = view.findViewById(R.id.tv_message);
        mTvRetry = view.findViewById(R.id.tv_retry);
    }

    private void initListener() {
        mTvRetry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                mControlWrapper.replay(false);
            }
        });
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
    public void onPlayStateChanged(int playState) {
        if (playState == ConstantKeys.CurrentState.STATE_ERROR) {
            bringToFront();
            setVisibility(VISIBLE);
        } else if (playState == ConstantKeys.CurrentState.STATE_IDLE) {
            setVisibility(GONE);
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLock) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float absDeltaX = Math.abs(ev.getX() - mDownX);
                float absDeltaY = Math.abs(ev.getY() - mDownY);
                if (absDeltaX > ViewConfiguration.get(getContext()).getScaledTouchSlop() ||
                        absDeltaY > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
