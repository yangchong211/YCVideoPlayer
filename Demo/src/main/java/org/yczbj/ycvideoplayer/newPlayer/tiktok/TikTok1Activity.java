package org.yczbj.ycvideoplayer.newPlayer.tiktok;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.yc.kernel.utils.VideoLogUtils;
import com.yc.pagerlib.recycler.OnPagerListener;
import com.yc.pagerlib.recycler.PagerLayoutManager;
import com.yc.videocache.cache.PreloadManager;
import com.yc.videocache.cache.ProxyVideoCacheManager;

import org.yczbj.ycvideoplayer.ConstantVideo;
import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.newPlayer.list.OnItemChildClickListener;
import org.yczbj.ycvideoplayer.newPlayer.list.VideoRecyclerViewAdapter;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import org.yczbj.ycvideoplayerlib.config.VideoInfoBean;
import org.yczbj.ycvideoplayerlib.player.SimpleStateListener;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.player.VideoViewManager;
import org.yczbj.ycvideoplayerlib.tool.PlayerUtils;
import org.yczbj.ycvideoplayerlib.ui.view.BasisVideoController;

import java.util.ArrayList;
import java.util.List;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;


/**
 * 模仿抖音短视频，使用RecyclerView，实现了预加载功能
 */

public class TikTok1Activity extends AppCompatActivity {


    protected List<VideoInfoBean> mVideos = new ArrayList<>();
    protected VideoRecyclerViewAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected PagerLayoutManager mLinearLayoutManager;
    protected VideoPlayer mVideoView;
    protected BasisVideoController mController;

    /**
     * 当前播放的位置
     */
    protected int mCurPos = -1;
    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    protected int mLastPos = mCurPos;
    private static final String KEY_INDEX = "index";

    public static void start(Context context, int index) {
        Intent i = new Intent(context, TikTok2Activity.class);
        i.putExtra(KEY_INDEX, index);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_recycler_view);
        mCurPos = getIntent().getIntExtra(KEY_INDEX, 0);
        initView();
        initData();
    }

    protected void initView() {
        initVideoView();
        mRecyclerView = findViewById(R.id.recyclerView);
        mLinearLayoutManager= new PagerLayoutManager(this, OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new VideoRecyclerViewAdapter(mVideos);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(int position) {
                startPlay(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mLinearLayoutManager.setOnViewPagerListener(new OnPagerListener() {
            @Override
            public void onInitComplete() {
                System.out.println("OnPagerListener---onInitComplete--"+"初始化完成");
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                System.out.println("OnPagerListener---onPageRelease--"+position+"-----"+isNext);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                System.out.println("OnPagerListener---onPageSelected--"+position+"-----"+isBottom);

            }
        });
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                FrameLayout playerContainer = view.findViewById(R.id.player_container);
                View v = playerContainer.getChildAt(0);
                if (v != null && v == mVideoView && !mVideoView.isFullScreen()) {
                    releaseVideoView();
                }
            }
        });
    }

    protected void initVideoView() {
        mVideoView = new VideoPlayer(this);
        mVideoView.setOnStateChangeListener(new SimpleStateListener() {
            @Override
            public void onPlayStateChanged(int playState) {
                //监听VideoViewManager释放，重置状态
                if (playState == ConstantKeys.CurrentState.STATE_IDLE) {
                    PlayerUtils.removeViewFormParent(mVideoView);
                    mLastPos = mCurPos;
                    mCurPos = -1;
                }
            }
        });
        mController = new BasisVideoController(this);
        mVideoView.setController(mController);
    }

    protected void initData() {
        List<VideoInfoBean> videoList = ConstantVideo.getVideoList();
        mVideos.addAll(videoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    protected void pause() {
        releaseVideoView();
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    protected void resume() {
        if (mLastPos == -1)
            return;
        //恢复上次播放的位置
        startPlay(mLastPos);
    }

    /**
     * 开始播放
     * @param position 列表位置
     */
    protected void startPlay(int position) {
        if (mCurPos == position) return;
        if (mCurPos != -1) {
            releaseVideoView();
        }
        VideoInfoBean videoBean = mVideos.get(position);
        mVideoView.setUrl(videoBean.getVideoUrl());
        View itemView = mLinearLayoutManager.findViewByPosition(position);
        if (itemView == null) return;
        VideoRecyclerViewAdapter.VideoHolder viewHolder = (VideoRecyclerViewAdapter.VideoHolder) itemView.getTag();
        //把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.mPrepareView, true);
        PlayerUtils.removeViewFormParent(mVideoView);
        viewHolder.mPlayerContainer.addView(mVideoView, 0);
        //播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        VideoViewManager.instance().add(mVideoView, "list");
        mVideoView.start();
        mCurPos = position;
    }

    private void releaseVideoView() {
        mVideoView.release();
        if (mVideoView.isFullScreen()) {
            mVideoView.stopFullScreen();
        }
        if(this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mCurPos = -1;
    }

}
