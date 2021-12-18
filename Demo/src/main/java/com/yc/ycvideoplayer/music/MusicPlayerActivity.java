package com.yc.ycvideoplayer.music;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.yc.music.inter.OnPlayerEventListener;
import com.yc.music.model.AudioBean;
import com.yc.music.tool.BaseAppHelper;
import com.yc.ycvideoplayer.R;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTvStart;
    private TextView mTvStop;
    private TextView mTvNext;
    private TextView mTvPre;
    private FrameLayout mFlPlayBar;
    private ImageView mIvPlayBarCover;
    private TextView mTvPlayBarTitle;
    private TextView mTvPlayBarArtist;
    private ImageView mIvPlayBarList;
    private ImageView mIvPlayBarPlay;
    private ImageView mIvPlayBarNext;
    private ProgressBar mPbPlayBar;
    private boolean isPlayFragmentShow = false;
    private PlayMusicFragment mPlayFragment;

    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        initFindViewById();
        initListener();
        mTv1.postDelayed(new Runnable() {
            @Override
            public void run() {
                initPlayServiceListener();
            }
        },1000);
    }

    private void initFindViewById() {
        mToolbar = findViewById(R.id.toolbar);
        mTv1 = findViewById(R.id.tv_1);
        mTv2 = findViewById(R.id.tv_2);
        mTv3 = findViewById(R.id.tv_3);
        mTvStart = findViewById(R.id.tv_start);
        mTvStop = findViewById(R.id.tv_stop);
        mTvNext = findViewById(R.id.tv_next);
        mTvPre = findViewById(R.id.tv_pre);
        mFlPlayBar = findViewById(R.id.fl_play_bar);
        mIvPlayBarCover = findViewById(R.id.iv_play_bar_cover);
        mTvPlayBarTitle = findViewById(R.id.tv_play_bar_title);
        mTvPlayBarArtist = findViewById(R.id.tv_play_bar_artist);
        mIvPlayBarList = findViewById(R.id.iv_play_bar_list);
        mIvPlayBarPlay = findViewById(R.id.iv_play_bar_play);
        mIvPlayBarNext = findViewById(R.id.iv_play_bar_next);
        mPbPlayBar = findViewById(R.id.pb_play_bar);

    }

    private void initListener() {
        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTvStart.setOnClickListener(this);
        mTvStop.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvPre.setOnClickListener(this);
        mIvPlayBarPlay.setOnClickListener(this);
        mFlPlayBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                BaseAppHelper.get().getMusicService().play(0);
                break;
            case R.id.tv_2:

                break;
            case R.id.tv_3:

                break;
            case R.id.tv_start:
                BaseAppHelper.get().getMusicService().start();
                break;
            case R.id.tv_stop:
                BaseAppHelper.get().getMusicService().stop();
                break;
            case R.id.tv_next:
                BaseAppHelper.get().getMusicService().next();
                break;
            case R.id.tv_pre:
                BaseAppHelper.get().getMusicService().prev();
                break;
            case R.id.fl_play_bar:
                showPlayingFragment();
                break;
            case R.id.iv_play_bar_play:
                if (BaseAppHelper.get().getMusicService().isDefault()) {
                    if (BaseAppHelper.get().getMusicList().size() > 0) {
                        int mPlayPosition;
                        if (BaseAppHelper.get().getMusicService().getPlayingMusic() != null ) {
                            mPlayPosition = BaseAppHelper.get().getMusicService().getPlayingPosition();
                        } else {
                            mPlayPosition = 0;
                        }
                        BaseAppHelper.get().getMusicService().play(BaseAppHelper.get()
                                .getMusicList().get(mPlayPosition));
                    }
                } else {
                    BaseAppHelper.get().getMusicService().playPause();
                }
                break;
        }
    }

    /**
     * 初始化服务播放音频播放进度监听器
     * 这个是要是通过监听即时更新主页面的底部控制器视图
     * 同时还要同步播放详情页面mPlayFragment的视图
     */
    public void initPlayServiceListener() {
        if (BaseAppHelper.get().getMusicService() == null) {
            return;
        }
        BaseAppHelper.get().getMusicService().setOnPlayEventListener(new OnPlayerEventListener() {
            /**
             * 切换歌曲
             * 主要是切换歌曲的时候需要及时刷新界面信息
             */
            @Override
            public void onChange(AudioBean music) {
                onChangeImpl(music);
            }

            /**
             * 继续播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerStart() {
                mIvPlayBarPlay.setSelected(true);
            }

            /**
             * 暂停播放
             * 主要是切换歌曲的时候需要及时刷新界面信息，比如播放暂停按钮
             */
            @Override
            public void onPlayerPause() {
                mIvPlayBarPlay.setSelected(false);
            }

            /**
             * 更新进度
             * 主要是播放音乐或者拖动进度条时，需要更新进度
             */
            @Override
            public void onUpdateProgress(int progress) {
                mPbPlayBar.setProgress(progress);
            }

            @Override
            public void onBufferingUpdate(int percent) {

            }

            /**
             * 更新定时停止播放时间
             */
            @Override
            public void onTimer(long remain) {

            }
        });
    }

    /**
     * 当在播放音频详细页面切换歌曲的时候，需要刷新底部控制器，和音频详细页面的数据
     * 之前关于activity，Fragment，service之间用EventBus通信
     * 案例：https://github.com/yangchong211/LifeHelper
     * 本项目中直接通过定义接口来实现功能，尝试中……
     *
     * @param music LocalMusic
     */
    private void onChangeImpl(AudioBean music) {
        if (music == null) {
            return;
        }
        mTvPlayBarTitle.setText(music.getTitle());
        mIvPlayBarPlay.setSelected(BaseAppHelper.get().getMusicService().isPlaying() || BaseAppHelper.get().getMusicService().isPreparing());
        //更新进度条
        mPbPlayBar.setMax((int) music.getDuration());
        mPbPlayBar.setProgress((int) BaseAppHelper.get().getMusicService().getCurrentPosition());
    }


    /**
     * 展示页面
     */
    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = PlayMusicFragment.newInstance("OnLine");
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;
    }


    /**
     * 隐藏页面
     */
    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;
    }


}
