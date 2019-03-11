package org.yczbj.ycvideoplayerlib.controller;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.yczbj.ycvideoplayerlib.dialog.ChangeClarityDialog;
import org.yczbj.ycvideoplayerlib.R;
import org.yczbj.ycvideoplayerlib.dialog.VideoClarity;
import org.yczbj.ycvideoplayerlib.utils.VideoLogUtil;
import org.yczbj.ycvideoplayerlib.utils.VideoPlayerUtils;
import org.yczbj.ycvideoplayerlib.constant.ConstantKeys;
import org.yczbj.ycvideoplayerlib.inter.InterVideoPlayer;
import org.yczbj.ycvideoplayerlib.inter.listener.OnClarityChangedListener;
import org.yczbj.ycvideoplayerlib.inter.listener.OnCompletedListener;
import org.yczbj.ycvideoplayerlib.inter.listener.OnPlayOrPauseListener;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoBackListener;
import org.yczbj.ycvideoplayerlib.inter.listener.OnVideoControlListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/9/29
 *     desc  : 播放器控制器，主要是处理UI操作逻辑
 *     revise: 注意：建议先判断状态，再进行设置参数
 * </pre>
 */
public class VideoPlayerController extends AbsVideoPlayerController implements View.OnClickListener{

    private Context mContext;
    private ImageView mImage;
    private ImageView mCenterStart;
    private LinearLayout mTop;
    private ImageView mBack;
    private TextView mTitle;

    private LinearLayout mLlTopOther;
    private ImageView mIvDownload;
    private ImageView mIvAudio;
    private ImageView mIvShare;
    private ImageView mIvMenu;

    private LinearLayout mLlHorizontal;
    private ImageView mIvHorAudio;
    private ImageView mIvHorTv;
    private ImageView mBattery;
    private TextView mTime;

    private LinearLayout mBottom;
    private ImageView mRestartPause;
    private TextView mPosition;
    private TextView mDuration;
    private SeekBar mSeek;
    private TextView mClarity;
    private ImageView mFullScreen;
    private TextView mLength;
    private LinearLayout mLoading;
    private ProgressBar pbLoadingRing;
    private ProgressBar pbLoadingQq;
    private TextView mLoadText;
    private LinearLayout mChangePosition;
    private TextView mChangePositionCurrent;
    private ProgressBar mChangePositionProgress;
    private LinearLayout mChangeBrightness;
    private ProgressBar mChangeBrightnessProgress;
    private LinearLayout mChangeVolume;
    private ProgressBar mChangeVolumeProgress;
    private LinearLayout mError;
    private TextView mTvError;
    private TextView mRetry;
    private LinearLayout mCompleted;
    private TextView mReplay;
    private TextView mShare;
    private FrameLayout mFlLock;
    private ImageView mIvLock;
    private LinearLayout mLine;
    private ProgressBar mPbPlayBar;

    private boolean topBottomVisible;
    /**
     * 倒计时器
     */
    private CountDownTimer mDismissTopBottomCountDownTimer;
    private List<VideoClarity> clarities;
    private int defaultClarityIndex;
    private ChangeClarityDialog mClarityDialog;
    /**
     * 是否已经注册了电池广播
     */
    private boolean hasRegisterBatteryReceiver;
    /**
     * 是否已经注册了网络监听广播，添加这个判断可以避免崩溃
     */
    private boolean hasRegisterNetReceiver;
    /**
     * 是否锁屏
     */
    private boolean mIsLock = false;
    /**
     * 这个是time时间不操作界面，则自动隐藏顶部和底部视图布局
     */
    private long time;
    /**
     * 顶部的布局，下载，切换音频，分享布局是否显示。
     * 默认为false，不显示
     */
    private boolean mIsTopAndBottomVisibility = false;
    /**
     * 设置视频播放器中间的播放键是否显示
     * 默认为false，不显示
     */
    private boolean mIsCenterPlayerVisibility = false;
    /**
     * 设置横屏播放时，tv图标是否显示
     * 默认为false，不显示
     */
    private boolean mIsTvIconVisibility = false;
    /**
     * 设置横屏播放时，audio图标是否显示
     * 默认为false，不显示
     */
    private boolean mIsAudioIconVisibility = false;
    /**
     * 网络变化监听广播，在网络变更时进行对应处理
     */
    private NetChangedReceiver netChangedReceiver;
    private class NetChangedReceiver extends BroadcastReceiver {
        private String getConnectionType(int type) {
            String connType = "";
            if (type == ConnectivityManager.TYPE_MOBILE) {
                connType = "3G，4G网络数据";
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                connType = "WIFI网络";
            }
            return connType;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                //获取联网状态的NetworkInfo对象
                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    //如果当前的网络连接成功并且网络连接可用
                    if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                        if (info.getType() == ConnectivityManager.TYPE_WIFI ||
                                info.getType() == ConnectivityManager.TYPE_MOBILE) {
                            VideoLogUtil.i(getConnectionType(info.getType()) + "连上");
                        }
                    } else {
                        VideoLogUtil.i(getConnectionType(info.getType()) + "断开");
                        if(mVideoPlayer.isIdle()){
                            mVideoPlayer.pause();
                        }else {
                            onPlayStateChanged(ConstantKeys.CurrentState.STATE_ERROR);
                        }
                    }
                }
            }
        }
    }


    /**
     * 电池状态即电量变化广播接收器
     */
    private BroadcastReceiver mBatterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                // 充电中
                mBattery.setImageResource(R.drawable.battery_charging);
            } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                // 充电完成
                mBattery.setImageResource(R.drawable.battery_full);
            } else {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int percentage = (int) (((float) level / scale) * 100);
                if (percentage <= 10) {
                    mBattery.setImageResource(R.drawable.battery_10);
                } else if (percentage <= 20) {
                    mBattery.setImageResource(R.drawable.battery_20);
                } else if (percentage <= 50) {
                    mBattery.setImageResource(R.drawable.battery_50);
                } else if (percentage <= 80) {
                    mBattery.setImageResource(R.drawable.battery_80);
                } else if (percentage <= 100) {
                    mBattery.setImageResource(R.drawable.battery_100);
                }
            }
        }
    };


    public VideoPlayerController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * 注册网络监听广播
     */
    private void registerNetChangedReceiver() {
        if (!hasRegisterNetReceiver) {
            if (netChangedReceiver == null) {
                netChangedReceiver = new NetChangedReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                mContext.registerReceiver(netChangedReceiver, filter);
                VideoLogUtil.i("注册网络监听广播");
            }
            hasRegisterNetReceiver = true;
        }
    }


    /**
     * 当播放完成或者意外销毁，都需要解绑注册网络监听广播
     */
    private void unRegisterNetChangedReceiver() {
        if (hasRegisterNetReceiver) {
            if (netChangedReceiver != null) {
                mContext.unregisterReceiver(netChangedReceiver);
                VideoLogUtil.i("解绑注册网络监听广播");
            }
            hasRegisterNetReceiver = false;
        }
    }

    /**
     * 注册电池监听广播
     */
    private void registerBatterReceiver() {
        if (!hasRegisterBatteryReceiver) {
            mContext.registerReceiver(mBatterReceiver, new IntentFilter(
                    Intent.ACTION_BATTERY_CHANGED));
            hasRegisterBatteryReceiver = true;
            VideoLogUtil.i("注册电池监听广播");
        }
    }


    /**
     * 当播放完成或者意外销毁，都需要解绑注册电池监听广播
     */
    private void unRegisterBatterReceiver() {
        if (hasRegisterBatteryReceiver) {
            mContext.unregisterReceiver(mBatterReceiver);
            hasRegisterBatteryReceiver = false;
        }
    }

    /**
     * 初始化操作
     */
    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.custom_video_player,
                this, true);
        initFindViewById();
        initListener();
        registerNetChangedReceiver();
    }

    private void initFindViewById() {
        mCenterStart = findViewById(R.id.center_start);
        mImage = findViewById(R.id.image);

        mTop = findViewById(R.id.top);
        mBack = findViewById(R.id.back);
        mTitle = findViewById(R.id.title);
        mLlTopOther = findViewById(R.id.ll_top_other);
        mIvDownload = findViewById(R.id.iv_download);
        mIvAudio =  findViewById(R.id.iv_audio);
        mIvShare = findViewById(R.id.iv_share);
        mIvMenu = findViewById(R.id.iv_menu);

        mLlHorizontal = findViewById(R.id.ll_horizontal);
        mIvHorAudio = findViewById(R.id.iv_hor_audio);
        mIvHorTv = findViewById(R.id.iv_hor_tv);
        mBattery = findViewById(R.id.battery);
        mTime = findViewById(R.id.time);

        mBottom = findViewById(R.id.bottom);
        mRestartPause = findViewById(R.id.restart_or_pause);
        mPosition = findViewById(R.id.position);
        mDuration = findViewById(R.id.duration);
        mSeek = findViewById(R.id.seek);
        mFullScreen = findViewById(R.id.full_screen);
        mClarity = findViewById(R.id.clarity);
        mLength = findViewById(R.id.length);
        mLoading = findViewById(R.id.loading);
        pbLoadingRing = findViewById(R.id.pb_loading_ring);
        pbLoadingQq = findViewById(R.id.pb_loading_qq);

        mLoadText = findViewById(R.id.load_text);
        mChangePosition = findViewById(R.id.change_position);
        mChangePositionCurrent = findViewById(R.id.change_position_current);
        mChangePositionProgress = findViewById(R.id.change_position_progress);
        mChangeBrightness = findViewById(R.id.change_brightness);
        mChangeBrightnessProgress = findViewById(R.id.change_brightness_progress);
        mChangeVolume = findViewById(R.id.change_volume);
        mChangeVolumeProgress = findViewById(R.id.change_volume_progress);

        mError = findViewById(R.id.error);
        mTvError = findViewById(R.id.tv_error);
        mRetry = findViewById(R.id.retry);
        mCompleted = findViewById(R.id.completed);
        mReplay = findViewById(R.id.replay);
        mShare = findViewById(R.id.share);
        mFlLock = findViewById(R.id.fl_lock);
        mIvLock = findViewById(R.id.iv_lock);

        mLine = findViewById(R.id.line);
        mPbPlayBar = findViewById(R.id.pb_play_bar);

        setTopVisibility(mIsTopAndBottomVisibility);
    }


    private void initListener() {
        mCenterStart.setOnClickListener(this);
        mBack.setOnClickListener(this);

        mIvDownload.setOnClickListener(this);
        mIvShare.setOnClickListener(this);
        mIvAudio.setOnClickListener(this);
        mIvMenu.setOnClickListener(this);

        mIvHorAudio.setOnClickListener(this);
        mIvHorTv.setOnClickListener(this);

        mRestartPause.setOnClickListener(this);
        mFullScreen.setOnClickListener(this);
        mClarity.setOnClickListener(this);
        mRetry.setOnClickListener(this);
        mReplay.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mFlLock.setOnClickListener(this);
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mVideoPlayer.isBufferingPaused() || mVideoPlayer.isPaused()) {
                    mVideoPlayer.restart();
                }
                long position = (long) (mVideoPlayer.getDuration() * seekBar.getProgress() / 100f);
                mVideoPlayer.seekTo(position);
                startDismissTopBottomTimer();
            }
        });
        this.setOnClickListener(this);
    }


    /**
     * 18年3月15日添加
     * 设置是否显示视频头部的下载，分享布局控件
     * @param isVisibility          是否可见
     */
    @Override
    public void setTopVisibility(boolean isVisibility) {
        this.mIsTopAndBottomVisibility = isVisibility;
        if(isVisibility){
            mLlTopOther.setVisibility(VISIBLE);
        }else {
            mLlTopOther.setVisibility(GONE);
        }
    }

    /**
     * 设置横屏播放时，tv和audio图标是否显示
     * @param isVisibility1                 tv图标是否显示
     * @param isVisibility2                 audio图标是否显示
     */
    @Override
    public void setTvAndAudioVisibility(boolean isVisibility1, boolean isVisibility2) {
        this.mIsTvIconVisibility = isVisibility1;
        this.mIsAudioIconVisibility = isVisibility2;
        mIvHorTv.setVisibility(mIsTvIconVisibility?VISIBLE:GONE);
        mIvHorAudio.setVisibility(mIsAudioIconVisibility?VISIBLE:GONE);
    }


    /**
     * 18年1月12号添加
     * 设置加载loading类型
     *
     * @param type 加载loading的类型
     *             目前1，是仿腾讯加载loading
     *             2，是转圈加载loading
     *             默认是2，后期想让用户自定义loading加载视图，不过暂时没实现
     *             更多可以关注我的GitHub：https://github.com/yangchong211
     */
    @Override
    public void setLoadingType(@ConstantKeys.LoadingType int type) {
        switch (type){
            case ConstantKeys.Loading.LOADING_RING:
                pbLoadingRing.setVisibility(VISIBLE);
                pbLoadingQq.setVisibility(GONE);
                break;
            case ConstantKeys.Loading.LOADING_QQ:
                pbLoadingRing.setVisibility(GONE);
                pbLoadingQq.setVisibility(VISIBLE);
                break;
            default:
                pbLoadingRing.setVisibility(VISIBLE);
                pbLoadingQq.setVisibility(GONE);
                break;
        }
    }


    /**
     * 设置不操作后，多久自动隐藏头部和底部布局
     * 添加值范围注释，限定时间为1秒到10秒之间
     * @param time                  时间
     */
    @Override
    public void setHideTime(@IntRange(from = 1000 , to = 10000) long time) {
        this.time = time;
    }


    /**
     * 设置视频标题
     * @param title             视频标题
     */
    @Override
    public void setTitle(@NonNull String title) {
        mTitle.setText(title);
    }


    /**
     * 获取ImageView的对象
     * @return                  对象
     */
    @Override
    public ImageView imageView() {
        return mImage;
    }


    /**
     * 设置图片
     * @param resId             视频底图资源
     */
    @Override
    public void setImage(@DrawableRes int resId) {
        mImage.setImageResource(resId);
    }


    /**
     * 设置视频时长
     * @param length            时长，long类型
     */
    @Override
    public void setLength(long length) {
        mLength.setText(VideoPlayerUtils.formatTime(length));
    }


    /**
     * 设置视频时长
     * @param length            时长，String类型
     */
    @Override
    public void setLength(String length) {
        mLength.setText(length);
    }


    /**
     * 设置播放器
     * @param videoPlayer   播放器
     */
    @Override
    public void setVideoPlayer(InterVideoPlayer videoPlayer) {
        super.setVideoPlayer(videoPlayer);
        // 给播放器配置视频链接地址
        if (clarities != null && clarities.size() > 1) {
            mVideoPlayer.setUp(clarities.get(defaultClarityIndex).getVideoUrl(), null);
        }
    }

    /**
     * 设置中间播放按钮是否显示，并且支持设置自定义图标
     * @param isVisibility          是否可见，默认不可见
     * @param image                 image
     */
    @Override
    public void setCenterPlayer(boolean isVisibility, @DrawableRes int image) {
        this.mIsCenterPlayerVisibility = isVisibility;
        if(isVisibility){
            if(image==0){
                mCenterStart.setImageResource(R.drawable.ic_player_center_start);
            }else {
                mCenterStart.setImageResource(image);
            }
        }
    }

    /**
     * 设置top到顶部的距离
     * @param top                   top
     */
    @Override
    public void setTopPadding(float top) {
        //如果设置0，则模式是10dp
        if (top==0){
            top = 10.0f;
        }
        mTop.setPadding(VideoPlayerUtils.dp2px(mContext,10.0f),
                VideoPlayerUtils.dp2px(mContext,top),
                VideoPlayerUtils.dp2px(mContext,10.0f), 0);
        mTop.invalidate();
    }


    /**
     * 获取是否是锁屏模式
     * @return              true表示锁屏
     */
    @Override
    public boolean getLock() {
        return mIsLock;
    }

    /**
     * 如果锁屏，则屏蔽滑动事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VideoLogUtil.i("如果锁屏2，则屏蔽返回键");
        //如果锁屏了，那就就不需要处理滑动的逻辑
        return !getLock() && super.onTouchEvent(event);
    }


    /**
     * 设置视频清晰度
     * @param clarities                         清晰度
     * @param defaultClarityIndex               默认清晰度
     */
    public void setClarity(final List<VideoClarity> clarities, int defaultClarityIndex) {
        if (clarities != null && clarities.size() > 1) {
            this.clarities = clarities;
            this.defaultClarityIndex = defaultClarityIndex;
            List<String> clarityGrades = new ArrayList<>();
            for (VideoClarity clarity : clarities) {
                clarityGrades.add(clarity.getGrade() + " " + clarity.getP());
            }
            mClarity.setText(clarities.get(defaultClarityIndex).getGrade());
            // 初始化切换清晰度对话框
            mClarityDialog = new ChangeClarityDialog(mContext);
            mClarityDialog.setClarityGrade(clarityGrades, defaultClarityIndex);
            mClarityDialog.setOnClarityCheckedListener(new OnClarityChangedListener() {
                @Override
                public void onClarityChanged(int clarityIndex) {
                    // 根据切换后的清晰度索引值，设置对应的视频链接地址，并从当前播放位置接着播放
                    VideoClarity clarity = clarities.get(clarityIndex);
                    mClarity.setText(clarity.getGrade());
                    long currentPosition = mVideoPlayer.getCurrentPosition();
                    //释放播放器
                    mVideoPlayer.releasePlayer();
                    //设置视频Url，以及headers
                    mVideoPlayer.setUp(clarity.getVideoUrl(), null);
                    //开始从此位置播放
                    mVideoPlayer.start(currentPosition);
                }

                @Override
                public void onClarityNotChanged() {
                    // 清晰度没有变化，对话框消失后，需要重新显示出top、bottom
                    setTopBottomVisible(true);
                }
            });
            // 给播放器配置视频链接地址
            if (mVideoPlayer != null) {
                mVideoPlayer.setUp(clarities.get(defaultClarityIndex).getVideoUrl(), null);
            }
        }
    }


    /**
     * 当播放状态发生改变时
     * @param playState 播放状态：
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case ConstantKeys.CurrentState.STATE_IDLE:
                break;
            //播放准备中
            case ConstantKeys.CurrentState.STATE_PREPARING:
                startPreparing();
                break;
            //播放准备就绪
            case ConstantKeys.CurrentState.STATE_PREPARED:
                startUpdateProgressTimer();
                //取消缓冲时更新网络加载速度
                cancelUpdateNetSpeedTimer();
                break;
            //正在播放
            case ConstantKeys.CurrentState.STATE_PLAYING:
                mLoading.setVisibility(View.GONE);
                mCenterStart.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                startDismissTopBottomTimer();
                cancelUpdateNetSpeedTimer();
                break;
            //暂停播放
            case ConstantKeys.CurrentState.STATE_PAUSED:
                mLoading.setVisibility(View.GONE);
                mCenterStart.setVisibility(mIsCenterPlayerVisibility?View.VISIBLE:View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                cancelDismissTopBottomTimer();
                cancelUpdateNetSpeedTimer();
                break;
            //正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
            case ConstantKeys.CurrentState.STATE_BUFFERING_PLAYING:
                mLoading.setVisibility(View.VISIBLE);
                mCenterStart.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                mLoadText.setText("正在准备...");
                startDismissTopBottomTimer();
                cancelUpdateNetSpeedTimer();
                break;
            //正在缓冲
            case ConstantKeys.CurrentState.STATE_BUFFERING_PAUSED:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                mLoadText.setText("正在准备...");
                cancelDismissTopBottomTimer();
                //开启缓冲时更新网络加载速度
                startUpdateNetSpeedTimer();
                break;
            //播放错误
            case ConstantKeys.CurrentState.STATE_ERROR:
                stateError();
                break;
            //播放完成
            case ConstantKeys.CurrentState.STATE_COMPLETED:
                stateCompleted();
                break;
            default:
                break;
        }
    }


    /**
     * 播放准备中
     */
    private void startPreparing() {
        mImage.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
        mLoadText.setText("正在准备...");
        mError.setVisibility(View.GONE);
        mCompleted.setVisibility(View.GONE);
        mTop.setVisibility(View.GONE);
        mBottom.setVisibility(View.GONE);
        mCenterStart.setVisibility(View.GONE);
        mLength.setVisibility(View.GONE);
        //开启缓冲时更新网络加载速度
        startUpdateNetSpeedTimer();
    }


    /**
     * 播放错误
     */
    private void stateError() {
        setTopBottomVisible(false);
        mTop.setVisibility(View.VISIBLE);
        mError.setVisibility(View.VISIBLE);
        mLine.setVisibility(GONE);
        cancelUpdateProgressTimer();
        cancelUpdateNetSpeedTimer();
        if (!VideoPlayerUtils.isConnected(mContext)){
            mTvError.setText("没有网络，请链接网络");
        } else {
            mTvError.setText("播放错误，请重试");
        }
    }

    /**
     * 播放完成
     */
    private void stateCompleted() {
        cancelUpdateProgressTimer();
        setTopBottomVisible(false);
        mImage.setVisibility(View.VISIBLE);
        mCompleted.setVisibility(View.VISIBLE);
        //设置播放完成的监听事件
        if(mOnCompletedListener!=null){
            mOnCompletedListener.onCompleted();
        }
        //当播放完成就解除广播
        unRegisterNetChangedReceiver();
        cancelUpdateNetSpeedTimer();
    }


    /**
     * 当播放器的播放模式发生变化时
     * @param playMode 播放器的模式：
     */
    @Override
    public void onPlayModeChanged(int playMode) {
        switch (playMode) {
            //普通模式
            case ConstantKeys.PlayMode.MODE_NORMAL:
                mFlLock.setVisibility(View.GONE);
                mBack.setVisibility(View.VISIBLE);
                mFullScreen.setImageResource(R.drawable.ic_player_enlarge);
                mFullScreen.setVisibility(View.VISIBLE);
                mClarity.setVisibility(View.GONE);
                setTopVisibility(mIsTopAndBottomVisibility);
                mLlHorizontal.setVisibility(View.GONE);
                unRegisterBatterReceiver();
                mIsLock = false;
                break;
            //全屏模式
            case ConstantKeys.PlayMode.MODE_FULL_SCREEN:
                mFlLock.setVisibility(View.VISIBLE);
                mBack.setVisibility(View.VISIBLE);
                mFullScreen.setVisibility(View.GONE);
                mFullScreen.setImageResource(R.drawable.ic_player_shrink);
                if (clarities != null && clarities.size() > 1) {
                    mClarity.setVisibility(View.VISIBLE);
                }
                mLlTopOther.setVisibility(GONE);
                if (mIsTopAndBottomVisibility){
                    mLlHorizontal.setVisibility(View.VISIBLE);
                    mIvHorTv.setVisibility(mIsTvIconVisibility?VISIBLE:GONE);
                    mIvHorAudio.setVisibility(mIsAudioIconVisibility?VISIBLE:GONE);
                }else {
                    mLlHorizontal.setVisibility(View.GONE);
                }
                registerBatterReceiver();
                break;
            //小窗口模式
            case ConstantKeys.PlayMode.MODE_TINY_WINDOW:
                mFlLock.setVisibility(View.GONE);
                mBack.setVisibility(View.VISIBLE);
                mClarity.setVisibility(View.GONE);
                mIsLock = false;
                break;
            default:
                break;
        }
    }


    /**
     * 重新设置
     */
    @Override
    public void reset() {
        topBottomVisible = false;
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        mSeek.setProgress(0);
        mSeek.setSecondaryProgress(0);
        mPbPlayBar.setProgress(0);
        mCenterStart.setVisibility(VISIBLE);
        mLength.setVisibility(View.VISIBLE);
        mFlLock.setVisibility(View.GONE);
        mImage.setVisibility(View.VISIBLE);
        mBottom.setVisibility(View.GONE);
        mFullScreen.setImageResource(R.drawable.ic_player_enlarge);
        mTop.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);

        mLoading.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mCompleted.setVisibility(View.GONE);
    }

    /**
     * 注意：跟重置有区别
     * 控制器意外销毁，比如手动退出，意外崩溃等等
     */
    @Override
    public void destroy() {
        //当播放完成就解除广播
        unRegisterNetChangedReceiver();
        unRegisterBatterReceiver();
        //结束timer
        cancelUpdateProgressTimer();
        cancelUpdateNetSpeedTimer();
    }


    /**
     * 尽量不要在onClick中直接处理控件的隐藏、显示及各种UI逻辑。
     * UI相关的逻辑都尽量到{@link #onPlayStateChanged}和{@link #onPlayModeChanged}中处理.
     */
    @Override
    public void onClick(View v) {
        if (v == mCenterStart) {
            //开始播放
            if (mVideoPlayer.isIdle()) {
                mVideoPlayer.start();
            }else if (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying()) {
                mVideoPlayer.pause();
            } else if (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused()) {
                mVideoPlayer.restart();
            }
        } else if (v == mBack) {
            //退出，执行返回逻辑
            //如果是全屏，则先退出全屏
            if (mVideoPlayer.isFullScreen()) {
                mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                //如果是小窗口，则退出小窗口
                mVideoPlayer.exitTinyWindow();
            }else {
                //如果两种情况都不是，执行逻辑交给使用者自己实现
                if(mBackListener!=null){
                    mBackListener.onBackClick();
                }else {
                    VideoLogUtil.d("返回键逻辑，如果是全屏，则先退出全屏；如果是小窗口，则退出小窗口；如果两种情况都不是，执行逻辑交给使用者自己实现");
                }
            }
        } else if (v == mRestartPause) {
            if(VideoPlayerUtils.isConnected(mContext)){
                //重新播放或者暂停
                if (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying()) {
                    mVideoPlayer.pause();
                    if(mOnPlayOrPauseListener!=null){
                        mOnPlayOrPauseListener.onPlayOrPauseClick(true);
                    }
                } else if (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused()) {
                    mVideoPlayer.restart();
                    if(mOnPlayOrPauseListener!=null){
                        mOnPlayOrPauseListener.onPlayOrPauseClick(false);
                    }
                }
            }else {
                Toast.makeText(mContext,"请检测是否有网络",Toast.LENGTH_SHORT).show();
            }
        } else if (v == mFullScreen) {
            //全屏模式，重置锁屏，设置为未选中状态
            if (mVideoPlayer.isNormal() || mVideoPlayer.isTinyWindow()) {
                mFlLock.setVisibility(VISIBLE);
                mIsLock = false;
                mIvLock.setImageResource(R.drawable.player_unlock_btn);
                mVideoPlayer.enterFullScreen();
            } else if (mVideoPlayer.isFullScreen()) {
                mFlLock.setVisibility(GONE);
                mVideoPlayer.exitFullScreen();
            }
        } else if (v == mClarity) {
            //设置清晰度
            //隐藏top、bottom
            setTopBottomVisible(false);
            //显示清晰度对话框
            mClarityDialog.show();
        } else if (v == mRetry) {
            //点击重试
            //不论是否记录播放位置，都是从零开始播放
            if(VideoPlayerUtils.isConnected(mContext)){
                mVideoPlayer.restart();
            }else {
                Toast.makeText(mContext,"请检测是否有网络",Toast.LENGTH_SHORT).show();
            }
        } else if (v == mReplay) {
            //重新播放
            if(VideoPlayerUtils.isConnected(mContext)){
                mRetry.performClick();
            }else {
                Toast.makeText(mContext,"请检测是否有网络",Toast.LENGTH_SHORT).show();
            }
        } else if (v == mShare) {
            //分享
            Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
        } else if(v == mFlLock){
            //点击锁屏按钮，则进入锁屏模式
            setLock(mIsLock);
        } else if(v == mIvDownload){
            if(mVideoControlListener==null){
                VideoLogUtil.d("请在初始化的时候设置下载监听事件");
                return;
            }
            //点击下载
            mVideoControlListener.onVideoControlClick(ConstantKeys.VideoControl.DOWNLOAD);
        } else if(v == mIvAudio){
            if(mVideoControlListener==null){
                VideoLogUtil.d("请在初始化的时候设置切换监听事件");
                return;
            }
            //点击切换音频
            mVideoControlListener.onVideoControlClick(ConstantKeys.VideoControl.AUDIO);
        }else if(v == mIvShare){
            if(mVideoControlListener==null){
                VideoLogUtil.d("请在初始化的时候设置分享监听事件");
                return;
            }
            //点击分享
            mVideoControlListener.onVideoControlClick(ConstantKeys.VideoControl.SHARE);
        }else if(v == mIvMenu){
            if(mVideoControlListener==null){
                VideoLogUtil.d("请在初始化的时候设置分享监听事件");
                return;
            }
            //点击菜单
            mVideoControlListener.onVideoControlClick(ConstantKeys.VideoControl.MENU);
        }else if(v == mIvHorAudio){
            if(mVideoControlListener==null){
                VideoLogUtil.d("请在初始化的时候设置横向音频监听事件");
                return;
            }
            //点击横向音频
            mVideoControlListener.onVideoControlClick(ConstantKeys.VideoControl.HOR_AUDIO);
        }else if(v == mIvHorTv){
            if(mVideoControlListener==null){
                VideoLogUtil.d("请在初始化的时候设置横向Tv监听事件");
                return;
            }
            //点击横向TV
            mVideoControlListener.onVideoControlClick(ConstantKeys.VideoControl.TV);
        }  else if (v == this) {
            if (mVideoPlayer.isPlaying() || mVideoPlayer.isPaused()
                    || mVideoPlayer.isBufferingPlaying() || mVideoPlayer.isBufferingPaused()) {
                setTopBottomVisible(!topBottomVisible);
            }
        }
    }


    /**
     * 设置top、bottom的显示和隐藏
     * @param visible true显示，false隐藏.
     */
    private void setTopBottomVisible(boolean visible) {
        mTop.setVisibility(visible ? View.VISIBLE : View.GONE);
        mBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
        mLine.setVisibility(visible ? View.GONE : View.VISIBLE);
        topBottomVisible = visible;
        if (visible) {
            if (!mVideoPlayer.isPaused() && !mVideoPlayer.isBufferingPaused()) {
                startDismissTopBottomTimer();
            }
        } else {
            cancelDismissTopBottomTimer();
        }
    }


    /**
     * 开启top、bottom自动消失的timer
     * 比如，视频常用功能，当用户5秒不操作后，自动隐藏头部和顶部
     */
    private void startDismissTopBottomTimer() {
        if(time==0){
            time = 8000;
        }
        cancelDismissTopBottomTimer();
        if (mDismissTopBottomCountDownTimer == null) {
            mDismissTopBottomCountDownTimer = new CountDownTimer(time, time) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setTopBottomVisible(false);
                }
            };
        }
        mDismissTopBottomCountDownTimer.start();
    }


    /**
     * 取消top、bottom自动消失的timer
     */
    private void cancelDismissTopBottomTimer() {
        if (mDismissTopBottomCountDownTimer != null) {
            mDismissTopBottomCountDownTimer.cancel();
        }
    }

    /**
     * 设置锁屏模式，默认是未锁屏的
     * 当为true时，则锁屏；否则为未锁屏
     * @param isLock            是否锁屏
     */
    private void setLock(boolean isLock){
        if(isLock){
            mIsLock = false;
            mIvLock.setImageResource(R.drawable.player_unlock_btn);
        }else {
            mIsLock = true;
            mIvLock.setImageResource(R.drawable.player_locked_btn);
        }
        /*
         * 设置锁屏时的布局
         * 1.横屏全屏时显示，其他不展示；
         * 2.锁屏时隐藏控制面板除锁屏按钮外其他所有控件
         * 3.当从全屏切换到正常或者小窗口时，则默认不锁屏
         */
        setTopBottomVisible(!mIsLock);
    }

    /**
     * 更新进度，包括更新网络加载速度
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void updateNetSpeedProgress() {
        //获取网络加载速度
        long tcpSpeed = mVideoPlayer.getTcpSpeed();
        VideoLogUtil.i("获取网络加载速度++++++++"+tcpSpeed);
        if (tcpSpeed>0){
            int speed = (int) (tcpSpeed/1024);
            //显示网速
            mLoading.setVisibility(View.VISIBLE);
            mLoadText.setText("网速"+speed+"kb");
        }
    }


    /**
     * 更新播放进度
     */
    @Override
    protected void updateProgress() {
        //获取当前播放的位置，毫秒
        long position = mVideoPlayer.getCurrentPosition();
        //获取办法给总时长，毫秒
        long duration = mVideoPlayer.getDuration();
        //获取视频缓冲百分比
        int bufferPercentage = mVideoPlayer.getBufferPercentage();
        mSeek.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeek.setProgress(progress);
        mPbPlayBar.setProgress(progress);
        mPosition.setText(VideoPlayerUtils.formatTime(position));
        mDuration.setText(VideoPlayerUtils.formatTime(duration));
        // 更新时间
        mTime.setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date()));

        long tcpSpeed = mVideoPlayer.getTcpSpeed();
        VideoLogUtil.i("获取网络加载速度---------"+tcpSpeed);
    }

    /**
     * 显示视频播放位置
     * @param duration            视频总时长ms
     * @param newPositionProgress 新的位置进度，取值0到100。
     */
    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {
        mChangePosition.setVisibility(View.VISIBLE);
        long newPosition = (long) (duration * newPositionProgress / 100f);
        mChangePositionCurrent.setText(VideoPlayerUtils.formatTime(newPosition));
        mChangePositionProgress.setProgress(newPositionProgress);
        mSeek.setProgress(newPositionProgress);
        mPbPlayBar.setProgress(newPositionProgress);
        mPosition.setText(VideoPlayerUtils.formatTime(newPosition));
    }


    /**
     * 隐藏视频播放位置
     */
    @Override
    protected void hideChangePosition() {
        //隐藏
        mChangePosition.setVisibility(View.GONE);
    }


    /**
     * 展示视频播放音量
     * @param newVolumeProgress 新的音量进度，取值1到100。
     */
    @Override
    protected void showChangeVolume(int newVolumeProgress) {
        mChangeVolume.setVisibility(View.VISIBLE);
        mChangeVolumeProgress.setProgress(newVolumeProgress);
    }


    /**
     * 隐藏视频播放音量
     */
    @Override
    protected void hideChangeVolume() {
        mChangeVolume.setVisibility(View.GONE);
    }


    /**
     * 展示视频播放亮度
     * @param newBrightnessProgress 新的亮度进度，取值1到100。
     */
    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {
        mChangeBrightness.setVisibility(View.VISIBLE);
        mChangeBrightnessProgress.setProgress(newBrightnessProgress);
    }


    /**
     * 隐藏视频播放亮度
     */
    @Override
    protected void hideChangeBrightness() {
        mChangeBrightness.setVisibility(View.GONE);
    }

    /**
     * 当视频退出全屏或者退出小窗口后，再次点击返回键
     * 让用户自己处理返回键事件的逻辑
     * 欢迎同行交流：https://github.com/yangchong211
     * 如果你觉得项目可以，欢迎star
     */
    private OnVideoBackListener mBackListener;
    public void setOnVideoBackListener(OnVideoBackListener listener) {
        this.mBackListener = listener;
    }

    /**
     * 设置视频分享，下载，音视频转化点击事件
     */
    private OnVideoControlListener mVideoControlListener;
    public void setOnVideoControlListener(OnVideoControlListener listener){
        this.mVideoControlListener = listener;
    }

    /**
     * 播放暂停监听事件
     */
    private OnPlayOrPauseListener mOnPlayOrPauseListener;
    public void setOnPlayOrPauseListener(OnPlayOrPauseListener listener){
        this.mOnPlayOrPauseListener = listener;
    }

    /**
     * 监听视频播放完成事件
     */
    private OnCompletedListener mOnCompletedListener;
    public void setOnCompletedListener(OnCompletedListener listener){
        this.mOnCompletedListener = listener;
    }


}
