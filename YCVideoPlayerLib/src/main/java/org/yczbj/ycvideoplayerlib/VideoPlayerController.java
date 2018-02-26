package org.yczbj.ycvideoplayerlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @author yc
 * @date 2017/9/4
 * 参考项目：
 * https://github.com/CarGuo/GSYVideoPlayer
 * https://github.com/danylovolokh/VideoPlayerManager
 * https://github.com/HotBitmapGG/bilibili-android-client
 * https://github.com/jjdxmashl/jjdxm_ijkplayer
 * https://github.com/JasonChow1989/JieCaoVideoPlayer-develop          2年前
 * https://github.com/open-android/JieCaoVideoPlayer                   1年前
 * https://github.com/lipangit/JiaoZiVideoPlayer                       4个月前
 * 个人感觉jiaozi这个播放器，与JieCaoVideoPlayer-develop有惊人的类同，借鉴了上面两个项目[JieCao]
 *
 *
 * 注意：在对应的播放Activity页面，清单文件中一定要添加
 * android:configChanges="orientation|keyboardHidden|screenSize"
 * android:screenOrientation="portrait"
 *
 * 关于我的github：https://github.com/yangchong211
 * 关于我的个人网站：www.ycbjie.cn或者www.yczbj.org
 *
 * 仿视频热点列表页播放器控制器
 * 播放控制界面上，播放、暂停、播放进度、缓冲动画、全屏/小屏等触发都是直接调用播放器对应的操作的。
 * 注意：建议先判断状态，再进行设置参数
 *
 * 会员制需求：
 * 1.是会员，可以全部观看
 * 2.非会员，可以试看，试看完后谈试看视图
 */

public class VideoPlayerController extends AbsVideoPlayerController implements View.OnClickListener{

    private Context mContext;
    private ImageView mImage;
    private ImageView mCenterStart;
    private LinearLayout mTop;
    private ImageView mBack;
    private TextView mTitle;
    private LinearLayout mBatteryTime;
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
    private TextView mRetry;
    private LinearLayout mCompleted;
    private TextView mReplay;
    private TextView mShare;
    private LinearLayout mLlTrySee;
    private TextView mTvSeeContent;
    private Button mBtnVip;
    private ImageView mIvTrySee;


    private boolean topBottomVisible;
    //倒计时器
    private CountDownTimer mDismissTopBottomCountDownTimer;
    private List<VideoClarity> clarities;
    private int defaultClarityIndex;
    private ChangeClarityDialog mClarityDialog;
    /**
     * 是否已经注册了电池广播
     */
    private boolean hasRegisterBatteryReceiver;
    //setMemberType 如果不设置该方法，那么默认视频都是可以看的
    //试看类型
    private int mType;
    //是否有观看权限
    private boolean mIsSee = true;
    //是否登录
    private boolean mIsLogin = true;
    private boolean mSeeEnd = true;
    //会员权限话术内容
    private ArrayList<String> mMemberContent;
    /**
     * 这个是time时间不操作界面，则自动隐藏顶部和底部视图布局
     */
    private long time;
    /**
     * 这个是设置试看时间，当然前提是设置试看权限后才能生效，如果不设置，默认为30秒
     */
    private long mTrySeeTime;


    public VideoPlayerController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.custom_video_player, this, true);
        initFindViewById();
        initListener();
    }

    private void initFindViewById() {
        mCenterStart = (ImageView) findViewById(R.id.center_start);
        mImage = (ImageView) findViewById(R.id.image);
        mTop = (LinearLayout) findViewById(R.id.top);
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mBatteryTime = (LinearLayout) findViewById(R.id.battery_time);
        mBattery = (ImageView) findViewById(R.id.battery);
        mTime = (TextView) findViewById(R.id.time);
        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mRestartPause = (ImageView) findViewById(R.id.restart_or_pause);
        mPosition = (TextView) findViewById(R.id.position);
        mDuration = (TextView) findViewById(R.id.duration);
        mSeek = (SeekBar) findViewById(R.id.seek);
        mFullScreen = (ImageView) findViewById(R.id.full_screen);
        mClarity = (TextView) findViewById(R.id.clarity);
        mLength = (TextView) findViewById(R.id.length);
        mLoading = (LinearLayout) findViewById(R.id.loading);
        pbLoadingRing = (ProgressBar)findViewById(R.id.pb_loading_ring);
        pbLoadingQq = (ProgressBar)findViewById(R.id.pb_loading_qq);

        mLoadText = (TextView) findViewById(R.id.load_text);
        mChangePosition = (LinearLayout) findViewById(R.id.change_position);
        mChangePositionCurrent = (TextView) findViewById(R.id.change_position_current);
        mChangePositionProgress = (ProgressBar) findViewById(R.id.change_position_progress);
        mChangeBrightness = (LinearLayout) findViewById(R.id.change_brightness);
        mChangeBrightnessProgress = (ProgressBar) findViewById(R.id.change_brightness_progress);
        mChangeVolume = (LinearLayout) findViewById(R.id.change_volume);
        mChangeVolumeProgress = (ProgressBar) findViewById(R.id.change_volume_progress);

        mError = (LinearLayout) findViewById(R.id.error);
        mRetry = (TextView) findViewById(R.id.retry);
        mCompleted = (LinearLayout) findViewById(R.id.completed);
        mReplay = (TextView) findViewById(R.id.replay);
        mShare = (TextView) findViewById(R.id.share);
        mLlTrySee = (LinearLayout) findViewById(R.id.ll_try_see);
        mTvSeeContent = (TextView)findViewById(R.id.tv_see_content);
        mBtnVip = (Button)findViewById(R.id.btn_vip);
        mIvTrySee = (ImageView) findViewById(R.id.iv_try_see);
    }


    private void initListener() {
        mCenterStart.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mRestartPause.setOnClickListener(this);
        mFullScreen.setOnClickListener(this);
        mClarity.setOnClickListener(this);
        mRetry.setOnClickListener(this);
        mReplay.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mBtnVip.setOnClickListener(this);
        mIvTrySee.setOnClickListener(this);
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
    public void setLoadingType(int type) {
        if(type==1){
            pbLoadingRing.setVisibility(GONE);
            pbLoadingQq.setVisibility(VISIBLE);
        }else if(type==2){
            pbLoadingRing.setVisibility(VISIBLE);
            pbLoadingQq.setVisibility(GONE);
        }else {
            pbLoadingRing.setVisibility(VISIBLE);
            pbLoadingQq.setVisibility(GONE);
        }
    }

    /**
     * 设置试看视频时间，让使用者自己定制
     * @param time                  时间
     */
    @Override
    public void setTrySeeTime(long time) {
        this.mTrySeeTime = time;
    }


    /**
     * 18年1月12号添加
     * 设置会员权限类型
     * @param isLogin   是否登录
     * @param isSee     是否有权限看[及时登录，如果没有成为会员也需区分权限的]
     * @param type      视频类型
     * @param isSaveProgress        是否保存观看位置
     */
    @Override
    public void setMemberType(boolean isLogin, boolean isSee, int type ,boolean isSaveProgress) {
        this.mIsSee = isSee;
        this.mIsLogin = isLogin;
        this.mType = type;
    }


    /**
     * 设置会员权限话术内容
     * @param memberContent         集合
     */
    @Override
    public void setMemberContent(ArrayList<String> memberContent) {
        this.mMemberContent = memberContent;
    }

    /**
     * 设置不操作后，多久自动隐藏头部和底部布局
     * @param time                  时间
     */
    @Override
    public void setHideTime(long time) {
        this.time = time;
    }


    /**
     * 设置视频标题
     * @param title             视频标题
     */
    @Override
    public void setTitle(String title) {
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
     * @param length            时长
     */
    @Override
    public void setLength(long length) {
        mLength.setText(VideoPlayerUtils.formatTime(length));
    }

    /**
     * 设置播放器
     * @param VideoPlayer   播放器
     */
    @Override
    public void setVideoPlayer(InterVideoPlayer VideoPlayer) {
        super.setVideoPlayer(VideoPlayer);
        // 给播放器配置视频链接地址
        if (clarities != null && clarities.size() > 1) {
            mVideoPlayer.setUp(clarities.get(defaultClarityIndex).getVideoUrl(), null);
        }
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
                    mVideoPlayer.releasePlayer();
                    mVideoPlayer.setUp(clarity.getVideoUrl(), null);
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
     *                  <ul>
     *                  <li>{@link VideoPlayer#STATE_IDLE}</li>
     *                  <li>{@link VideoPlayer#STATE_PREPARING}</li>
     *                  <li>{@link VideoPlayer#STATE_PREPARED}</li>
     *                  <li>{@link VideoPlayer#STATE_PLAYING}</li>
     *                  <li>{@link VideoPlayer#STATE_PAUSED}</li>
     *                  <li>{@link VideoPlayer#STATE_BUFFERING_PLAYING}</li>
     *                  <li>{@link VideoPlayer#STATE_BUFFERING_PAUSED}</li>
     *                  <li>{@link VideoPlayer#STATE_ERROR}</li>
     *                  <li>{@link VideoPlayer#STATE_COMPLETED}</li>
     */
    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoPlayer.STATE_IDLE:
                break;
            //播放准备中
            case VideoPlayer.STATE_PREPARING:
                mImage.setVisibility(View.GONE);
                mLoading.setVisibility(View.VISIBLE);
                mLoadText.setText("正在准备...");
                mError.setVisibility(View.GONE);
                mCompleted.setVisibility(View.GONE);
                mTop.setVisibility(View.GONE);
                mBottom.setVisibility(View.GONE);
                mCenterStart.setVisibility(View.GONE);
                mLength.setVisibility(View.GONE);
                break;
            //播放准备就绪
            case VideoPlayer.STATE_PREPARED:
                startUpdateProgressTimer();
                break;
            //正在播放
            case VideoPlayer.STATE_PLAYING:
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                startDismissTopBottomTimer();
                break;
            //暂停播放
            case VideoPlayer.STATE_PAUSED:
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                cancelDismissTopBottomTimer();
                break;
            //正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
            case VideoPlayer.STATE_BUFFERING_PLAYING:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                mLoadText.setText("正在缓冲...");
                startDismissTopBottomTimer();
                break;
            //正在缓冲
            case VideoPlayer.STATE_BUFFERING_PAUSED:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                mLoadText.setText("正在缓冲...");
                cancelDismissTopBottomTimer();
                break;
            //播放错误
            case VideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mTop.setVisibility(View.VISIBLE);
                mError.setVisibility(View.VISIBLE);
                break;
            //播放完成
            case VideoPlayer.STATE_COMPLETED:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mImage.setVisibility(View.VISIBLE);
                mCompleted.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    /**
     * 当播放器的播放模式发生变化时
     * @param playMode 播放器的模式：
     *                 <ul>
     *                 <li>{@link VideoPlayer#MODE_NORMAL}</li>
     *                 <li>{@link VideoPlayer#MODE_FULL_SCREEN}</li>
     *                 <li>{@link VideoPlayer#MODE_TINY_WINDOW}</li>
     */
    @Override
    protected void onPlayModeChanged(int playMode) {
        switch (playMode) {
            //普通模式
            case VideoPlayer.MODE_NORMAL:
                mBack.setVisibility(View.VISIBLE);
                mFullScreen.setImageResource(R.drawable.ic_player_enlarge);
                mFullScreen.setVisibility(View.VISIBLE);
                mClarity.setVisibility(View.GONE);
                mBatteryTime.setVisibility(View.GONE);
                if (hasRegisterBatteryReceiver) {
                    mContext.unregisterReceiver(mBatterReceiver);
                    hasRegisterBatteryReceiver = false;
                }
                break;
            //全屏模式
            case VideoPlayer.MODE_FULL_SCREEN:
                mBack.setVisibility(View.VISIBLE);
                mFullScreen.setVisibility(View.GONE);
                mFullScreen.setImageResource(R.drawable.ic_player_shrink);
                if (clarities != null && clarities.size() > 1) {
                    mClarity.setVisibility(View.VISIBLE);
                }
                mBatteryTime.setVisibility(View.VISIBLE);
                if (!hasRegisterBatteryReceiver) {
                    mContext.registerReceiver(mBatterReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    hasRegisterBatteryReceiver = true;
                }
                break;
            //小窗口模式
            case VideoPlayer.MODE_TINY_WINDOW:
                mBack.setVisibility(View.VISIBLE);
                mClarity.setVisibility(View.GONE);
                break;
            default:
                break;
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


    /**
     * 重新设置
     */
    @Override
    protected void reset() {
        topBottomVisible = false;
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        mSeek.setProgress(0);
        mSeek.setSecondaryProgress(0);


        //添加，试看视频
        if (!mIsSee || !mIsLogin) {
            mIvTrySee.setVisibility(VISIBLE);
            mCenterStart.setVisibility(GONE);
            mLength.setVisibility(View.GONE);
        }else{
            mIvTrySee.setVisibility(GONE);
            mCenterStart.setVisibility(VISIBLE);
            mLength.setVisibility(View.VISIBLE);
        }

        //mCenterStart.setVisibility(View.VISIBLE);
        mImage.setVisibility(View.VISIBLE);
        mBottom.setVisibility(View.GONE);
        mFullScreen.setImageResource(R.drawable.ic_player_enlarge);
        mTop.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);

        mLlTrySee.setVisibility(GONE);
        mLoading.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mCompleted.setVisibility(View.GONE);
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
                }
            }
        } else if (v == mRestartPause) {
            //重新播放或者暂停
            if (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying()) {
                mVideoPlayer.pause();
            } else if (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused()) {
                mVideoPlayer.restart();
            }
        } else if (v == mFullScreen) {
            //全屏模式
            if (mVideoPlayer.isNormal() || mVideoPlayer.isTinyWindow()) {
                mVideoPlayer.enterFullScreen();
            } else if (mVideoPlayer.isFullScreen()) {
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
            mVideoPlayer.restart();
        } else if (v == mReplay) {
            //重新播放
            mRetry.performClick();
        } else if (v == mShare) {
            //分享
            Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
        } else if (v == mIvTrySee){
            //点击试看按钮，也是播放
            mVideoPlayer.start();
            mIvTrySee.setVisibility(GONE);
        } else if (v == mBtnVip){
            //点击vip按钮，第一种情况是跳转登录，第二种情况是跳转购买会员页面
            if (mIsLogin) {
                //试看结束，观看全部内容请开通会员
                mClickListener.onClick(ConstantKeys.Gender.LOGIN);
            }else if(mType ==1){
                //试看结束，观看全部内容请开通会员/购买。已是会员/已购买可登陆观看
                mClickListener.onClick(ConstantKeys.Gender.MEMBER);
            }else if(mType == 2){
                //试看结束，观看全部内容请开通会员。已是会员/已购买可登陆观看
                mClickListener.onClick(ConstantKeys.Gender.MEMBER);
            }else if(mType == 3){
                //试看结束, 登录后即可观看全部免费课程。
                mClickListener.onClick(ConstantKeys.Gender.MEMBER);
            }
        } else if (v == this) {
            if (mVideoPlayer.isPlaying() || mVideoPlayer.isPaused()
                    || mVideoPlayer.isBufferingPlaying() || mVideoPlayer.isBufferingPaused()) {
                if(mTrySeeTime==0){
                    mTrySeeTime = 30000;
                }

                if(mVideoPlayer.getCurrentPosition()>mTrySeeTime){
                    //在试看3分钟外
                    //如果没有登录或者没有观看权限，则隐藏底部和顶部视图
                    if(mIsLogin || mIsSee){
                        setTopBottomVisible(!topBottomVisible);
                    }
                }else {
                    //在试看3分钟内，点击界面可以显示或者隐藏顶部底部视图
                    setTopBottomVisible(!topBottomVisible);
                }
            }
        }
    }


    /**
     * 设置top、bottom的显示和隐藏
     * @param visible true显示，false隐藏.
     */
    private void setTopBottomVisible(boolean visible) {
        //如果显示为立即登录或者购买会员的布局，则不隐藏<箭头
        if(mLlTrySee.getVisibility() == View.VISIBLE){
            mTop.setVisibility(View.VISIBLE);
            mTitle.setVisibility(View.VISIBLE);
        }else {
            mTop.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        mBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
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
        mPosition.setText(VideoPlayerUtils.formatTime(position));
        mDuration.setText(VideoPlayerUtils.formatTime(duration));
        // 更新时间
        mTime.setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date()));

        //添加，如果没有播放权限或者没有登录，并且播放时间大于3分钟，则试看结束
        if (!mIsSee || !mIsLogin) {
            if(mTrySeeTime==0){
                mTrySeeTime = 30000;
            }
            //避免更新中多次走这个方法
            if(position >= mTrySeeTime && mSeeEnd){
                // 试看结束
                setVideoTrySeeEnd();
                mSeeEnd = false;
            }
        }
    }


    /**
     * 设置试看播放结束
     */
    private void setVideoTrySeeEnd() {
        //先暂停播放
        mVideoPlayer.pause();
        mImage.setVisibility(View.VISIBLE);
        mImage.setBackgroundColor(Color.BLACK);
        //取消计时器
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        //隐藏底部面板
        mBottom.setVisibility(View.GONE);
        //设置顶部返回键可见
        mTop.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);

        mLlTrySee.setVisibility(VISIBLE);
        String seeContent = "";
        if (mIsLogin) {
            //试看结束，观看全部内容请开通会员
            if(mMemberContent!=null && mMemberContent.size()>0 && mMemberContent.get(0)!=null){
                seeContent = mMemberContent.get(0);
            }else {
                seeContent = ConstantKeys.LOGIN_TEXT;
            }
            mTvSeeContent.setClickable(false);
            mTvSeeContent.setEnabled(false);
        }else if(mType ==1){
            //试看结束，观看全部内容请开通会员/购买。已是会员/已购买可登陆观看
            if(mMemberContent!=null && mMemberContent.size()>1 && mMemberContent.get(1)!=null){
                seeContent = mMemberContent.get(1);
            }else {
                seeContent = ConstantKeys.NO_LOGIN_TEXT;
            }
            mBtnVip.setText("立即登录");
        }else if(mType == 2){
            //试看结束，观看全部内容请开通会员。已是会员/已购买可登陆观看
            if(mMemberContent!=null && mMemberContent.size()>2 && mMemberContent.get(2)!=null){
                seeContent = mMemberContent.get(2);
            }else {
                seeContent = ConstantKeys.NO_LOGIN_VIP_TEXT;
            }
            mBtnVip.setText("立即登录");
        }else if(mType == 3){
            //试看结束, 登录后即可观看全部免费课程。
            if(mMemberContent!=null && mMemberContent.size()>3 && mMemberContent.get(3)!=null){
                seeContent = mMemberContent.get(3);
            }else {
                seeContent = ConstantKeys.NO_LOGIN;
            }
            mBtnVip.setText("立即登录");
        }
        mTvSeeContent.setText(seeContent);
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
        mPosition.setText(VideoPlayerUtils.formatTime(newPosition));
    }


    /**
     * 隐藏视频播放位置
     */
    @Override
    protected void hideChangePosition() {
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
     * 让用户自己处理点击事件的逻辑
     * 点击事件，跳转登录或者购买会员页面
     * 欢迎同行交流：https://github.com/yangchong211
     * 如果你觉得项目可以，欢迎star
     */
    private OnMemberClickListener mClickListener;
    public void setOnMemberClickListener(OnMemberClickListener listener) {
        this.mClickListener = listener;
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


}
