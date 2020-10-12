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
package org.yczbj.ycvideoplayerlib.ui.pip;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.yczbj.ycvideoplayerlib.R;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.ControlWrapper;
import org.yczbj.ycvideoplayerlib.ui.view.IControlComponent;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 悬浮窗视图
 *     revise:
 * </pre>
 */
public class CustomFloatView extends FrameLayout implements IControlComponent, View.OnClickListener {

    private ControlWrapper mControlWrapper;
    private Context mContext;
    private ImageView mIvStartPlay;
    private ProgressBar mPbLoading;
    private ImageView mIvClose;
    private ImageView mIvSkip;

    public CustomFloatView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomFloatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomFloatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.custom_video_player_float, this, true);
        initFindViewById(view);
        initListener();
    }

    private void initFindViewById(View view) {
        mIvStartPlay = view.findViewById(R.id.iv_start_play);
        mPbLoading = view.findViewById(R.id.pb_loading);
        mIvClose = view.findViewById(R.id.iv_close);
        mIvSkip = view.findViewById(R.id.iv_skip);

    }

    private void initListener() {
        mIvStartPlay.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mIvSkip.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mIvClose) {
            FloatVideoManager.getInstance(mContext).stopFloatWindow();
            FloatVideoManager.getInstance(mContext).reset();
        } else if (v == mIvStartPlay) {
            mControlWrapper.togglePlay();
        } else if (v == mIvSkip) {
            if (FloatVideoManager.getInstance(mContext).getActClass() != null) {
                Intent intent = new Intent(getContext(), FloatVideoManager.getInstance(mContext).getActClass());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        }
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
        if (isVisible) {
            if (mIvStartPlay.getVisibility() == VISIBLE){
                return;
            }
            mIvStartPlay.setVisibility(VISIBLE);
            mIvStartPlay.startAnimation(anim);
        } else {
            if (mIvStartPlay.getVisibility() == GONE){
                return;
            }
            mIvStartPlay.setVisibility(GONE);
            mIvStartPlay.startAnimation(anim);
        }
    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case ConstantKeys.CurrentState.STATE_IDLE:
                mIvStartPlay.setSelected(false);
                mIvStartPlay.setVisibility(VISIBLE);
                mPbLoading.setVisibility(GONE);
                break;
            case ConstantKeys.CurrentState.STATE_PLAYING:
                mIvStartPlay.setSelected(true);
                mIvStartPlay.setVisibility(GONE);
                mPbLoading.setVisibility(GONE);
                break;
            case ConstantKeys.CurrentState.STATE_PAUSED:
                mIvStartPlay.setSelected(false);
                mIvStartPlay.setVisibility(VISIBLE);
                mPbLoading.setVisibility(GONE);
                break;
            case ConstantKeys.CurrentState.STATE_PREPARING:
                mIvStartPlay.setVisibility(GONE);
                mIvStartPlay.setVisibility(VISIBLE);
                break;
            case ConstantKeys.CurrentState.STATE_PREPARED:
                mIvStartPlay.setVisibility(GONE);
                mPbLoading.setVisibility(GONE);
                break;
            case ConstantKeys.CurrentState.STATE_ERROR:
                mPbLoading.setVisibility(GONE);
                mIvStartPlay.setVisibility(GONE);
                bringToFront();
                break;
            case ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED:
                mIvStartPlay.setVisibility(GONE);
                mPbLoading.setVisibility(VISIBLE);
                break;
            case ConstantKeys.CurrentState.STATE_COMPLETED:
                mIvStartPlay.setVisibility(GONE);
                mPbLoading.setVisibility(GONE);
                mIvStartPlay.setSelected(mControlWrapper.isPlaying());
                break;
            case ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING:
                bringToFront();
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLocked) {

    }

}
