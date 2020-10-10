package org.yczbj.ycvideoplayer.tiny;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.yczbj.ycvideoplayer.ConstantVideo;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.list.OnItemChildClickListener;
import org.yczbj.ycvideoplayer.list.VideoRecyclerViewAdapter;
import org.yczbj.ycvideoplayerlib.config.VideoInfoBean;
import org.yczbj.ycvideoplayerlib.kernel.view.VideoView;
import org.yczbj.ycvideoplayerlib.tool.utils.PlayerUtils;
import org.yczbj.ycvideoplayerlib.ui.view.BasisVideoController;

import java.util.List;

/**
 * 小窗播放
 */
public class TinyScreenActivity extends AppCompatActivity implements OnItemChildClickListener {

    private BasisVideoController mController;
    private List<VideoInfoBean> mVideos;
    private LinearLayoutManager mLinearLayoutManager;
    private VideoView mVideoPlayer;
    private int mCurPos = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_recycler_view);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoPlayer != null) {
            mVideoPlayer.resume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoPlayer != null) {
            mVideoPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        if (mVideoPlayer == null || !mVideoPlayer.onBackPressed()) {
            super.onBackPressed();
        }
    }


    protected void initView() {
        mVideoPlayer = new VideoView(this);
        mVideoPlayer.setOnStateChangeListener(new VideoView.SimpleOnStateChangeListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_PLAYBACK_COMPLETED) {
                    if (mVideoPlayer.isTinyScreen()) {
                        mVideoPlayer.stopTinyScreen();
                        releaseVideoView();
                    }
                }
            }
        });
        mController = new BasisVideoController(this);
        initRecyclerView();
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mVideos = ConstantVideo.getVideoList();
        VideoRecyclerViewAdapter adapter = new VideoRecyclerViewAdapter(mVideos);
        adapter.setOnItemChildClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                VideoRecyclerViewAdapter.VideoHolder holder = (VideoRecyclerViewAdapter.VideoHolder) view.getTag();
                int position = holder.mPosition;
                if (position == mCurPos) {
                    startPlay(position, false);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                VideoRecyclerViewAdapter.VideoHolder holder = (VideoRecyclerViewAdapter.VideoHolder) view.getTag();
                int position = holder.mPosition;
                if (position == mCurPos && !mVideoPlayer.isFullScreen()) {
                    mVideoPlayer.startTinyScreen();
                    mVideoPlayer.setVideoController(null);
                    mController.setPlayState(VideoView.STATE_IDLE);
                }
            }
        });
    }

    @Override
    public void onItemChildClick(int position) {
        startPlay(position, true);
    }

    /**
     * 开始播放
     *
     * @param position 列表位置
     */
    protected void startPlay(int position, boolean isRelease) {
        if (mVideoPlayer.isTinyScreen())
            mVideoPlayer.stopTinyScreen();
        if (mCurPos != -1 && isRelease) {
            releaseVideoView();
        }
        VideoInfoBean videoBean = mVideos.get(position);
        mVideoPlayer.setUrl(videoBean.getVideoUrl());
        View itemView = mLinearLayoutManager.findViewByPosition(position);
        if (itemView == null) return;
        //注意：要先设置控制才能去设置控制器的状态。
        mVideoPlayer.setVideoController(mController);
        mController.setPlayState(mVideoPlayer.getCurrentPlayState());

        VideoRecyclerViewAdapter.VideoHolder viewHolder = (VideoRecyclerViewAdapter.VideoHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.mPrepareView, true);
        PlayerUtils.removeViewFormParent(mVideoPlayer);
        viewHolder.mPlayerContainer.addView(mVideoPlayer, 0);
        mVideoPlayer.start();
        mCurPos = position;
    }

    private void releaseVideoView() {
        mVideoPlayer.release();
        if (mVideoPlayer.isFullScreen()) {
            mVideoPlayer.stopFullScreen();
        }
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }
}
