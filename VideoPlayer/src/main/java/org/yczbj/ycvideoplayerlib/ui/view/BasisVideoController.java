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
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.yczbj.ycvideoplayerlib.R;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.controller.GestureVideoController;
import org.yczbj.ycvideoplayerlib.tool.BaseToast;
import org.yczbj.ycvideoplayerlib.tool.PlayerUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 控制条界面。包括了顶部的标题栏，底部 的控制栏，锁屏按钮等等。
 *             是界面的主要组成部分。
 *     revise: 如果想定制ui，你可以直接继承GestureVideoController或者BaseVideoController实现
 * </pre>
 */
public class BasisVideoController extends GestureVideoController implements View.OnClickListener {

    private Context mContext;
    private ImageView mLockButton;
    private ProgressBar mLoadingProgress;
    private ImageView thumb;
    private CustomTitleView titleView;

    public BasisVideoController(@NonNull Context context) {
        this(context, null);
    }

    public BasisVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasisVideoController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.custom_video_player_standard;
    }

    @Override
    protected void initView(Context context) {
        super.initView(context);
        this.mContext = context;
        initFindViewById();
        initListener();
        initConfig();
    }

    private void initFindViewById() {
        mLockButton = findViewById(R.id.lock);
        mLoadingProgress = findViewById(R.id.loading);
    }

    private void initListener() {
        mLockButton.setOnClickListener(this);
    }

    private void initConfig() {
        //根据屏幕方向自动进入/退出全屏
        setEnableOrientation(true);
        //设置可以滑动调节进度
        setCanChangePosition(true);
        //竖屏也开启手势操作，默认关闭
        setEnableInNormal(true);
        //滑动调节亮度，音量，进度，默认开启
        setGestureEnabled(true);

        addDefaultControlComponent("",false);
    }



    /**
     * 快速添加各个组件
     * 需要注意各个层级
     * @param title                             标题
     * @param isLive                            是否为直播
     */
    public void addDefaultControlComponent(String title, boolean isLive) {
        //添加自动完成播放界面view
        CustomCompleteView completeView = new CustomCompleteView(mContext);
        completeView.setVisibility(GONE);
        this.addControlComponent(completeView);

        //添加错误界面view
        CustomErrorView errorView = new CustomErrorView(mContext);
        errorView.setVisibility(GONE);
        this.addControlComponent(errorView);

        //添加与加载视图界面view，准备播放界面
        CustomPrepareView prepareView = new CustomPrepareView(mContext);
        thumb = prepareView.getThumb();
        prepareView.setClickStart();
        this.addControlComponent(prepareView);

        //添加标题栏
        titleView = new CustomTitleView(mContext);
        titleView.setTitle(title);
        titleView.setVisibility(VISIBLE);
        this.addControlComponent(titleView);

        if (isLive) {
            //添加底部播放控制条
            CustomLiveControlView liveControlView = new CustomLiveControlView(mContext);
            this.addControlComponent(liveControlView);
        } else {
            //添加底部播放控制条
            CustomBottomView vodControlView = new CustomBottomView(mContext);
            //是否显示底部进度条。默认显示
            vodControlView.showBottomProgress(true);
            this.addControlComponent(vodControlView);
        }
        //添加滑动控制视图
        CustomGestureView gestureControlView = new CustomGestureView(mContext);
        this.addControlComponent(gestureControlView);
        setCanChangePosition(!isLive);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.lock) {
            mControlWrapper.toggleLockState();
        }
    }

    @Override
    protected void onLockStateChanged(boolean isLocked) {
        if (isLocked) {
            mLockButton.setSelected(true);
            String string = mContext.getResources().getString(R.string.locked);
            BaseToast.showRoundRectToast(string);
        } else {
            mLockButton.setSelected(false);
            String string = mContext.getResources().getString(R.string.unlocked);
            BaseToast.showRoundRectToast(string);
        }
    }

    @Override
    protected void onVisibilityChanged(boolean isVisible, Animation anim) {
        if (mControlWrapper.isFullScreen()) {
            if (isVisible) {
                if (mLockButton.getVisibility() == GONE) {
                    mLockButton.setVisibility(VISIBLE);
                    if (anim != null) {
                        mLockButton.startAnimation(anim);
                    }
                }
            } else {
                mLockButton.setVisibility(GONE);
                if (anim != null) {
                    mLockButton.startAnimation(anim);
                }
            }
        }
    }

    /**
     * 播放模式
     * 普通模式，小窗口模式，正常模式三种其中一种
     * MODE_NORMAL              普通模式
     * MODE_FULL_SCREEN         全屏模式
     * MODE_TINY_WINDOW         小屏模式
     * @param playerState                   播放模式
     */
    @Override
    protected void onPlayerStateChanged(int playerState) {
        super.onPlayerStateChanged(playerState);
        switch (playerState) {
            case ConstantKeys.PlayMode.MODE_NORMAL:
                setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mLockButton.setVisibility(GONE);
                break;
            case ConstantKeys.PlayMode.MODE_FULL_SCREEN:
                if (isShowing()) {
                    mLockButton.setVisibility(VISIBLE);
                } else {
                    mLockButton.setVisibility(GONE);
                }
                break;
        }

        if (mActivity != null && hasCutout()) {
            int orientation = mActivity.getRequestedOrientation();
            int dp24 = PlayerUtils.dp2px(getContext(), 24);
            int cutoutHeight = getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                FrameLayout.LayoutParams lblp = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                lblp.setMargins(dp24, 0, dp24, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24 + cutoutHeight, 0, dp24 + cutoutHeight, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLockButton.getLayoutParams();
                layoutParams.setMargins(dp24, 0, dp24, 0);
            }
        }

    }

    /**
     * 播放状态
     * -1               播放错误
     * 0                播放未开始
     * 1                播放准备中
     * 2                播放准备就绪
     * 3                正在播放
     * 4                暂停播放
     * 5                正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
     * 6                暂停缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，此时暂停播放器，继续缓冲，缓冲区数据足够后恢复暂停
     * 7                播放完成
     * 8                开始播放中止
     * @param playState                     播放状态，主要是指播放器的各种状态
     */
    @Override
    protected void onPlayStateChanged(int playState) {
        super.onPlayStateChanged(playState);
        switch (playState) {
            //调用release方法会回到此状态
            case ConstantKeys.CurrentState.STATE_IDLE:
                mLockButton.setSelected(false);
                mLoadingProgress.setVisibility(GONE);
                break;
            case ConstantKeys.CurrentState.STATE_PLAYING:
            case ConstantKeys.CurrentState.STATE_PAUSED:
            case ConstantKeys.CurrentState.STATE_PREPARED:
            case ConstantKeys.CurrentState.STATE_ERROR:
            case ConstantKeys.CurrentState.STATE_COMPLETED:
                mLoadingProgress.setVisibility(GONE);
                break;
            case ConstantKeys.CurrentState.STATE_PREPARING:
            case ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED:
                mLoadingProgress.setVisibility(VISIBLE);
                break;
            case ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING:
                mLoadingProgress.setVisibility(GONE);
                mLockButton.setVisibility(GONE);
                mLockButton.setSelected(false);
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (isLocked()) {
            show();
            String string = mContext.getResources().getString(R.string.lock_tip);
            BaseToast.showRoundRectToast(string);
            return true;
        }
        if (mControlWrapper.isFullScreen()) {
            return stopFullScreen();
        }
        return super.onBackPressed();
    }

    @Override
    public void destroy() {

    }

    public ImageView getThumb() {
        return thumb;
    }

    public void setTitle(String title) {
        if (titleView!=null){
            titleView.setTitle(title);
        }
    }
}
