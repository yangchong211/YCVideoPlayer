package org.yczbj.ycvideoplayer.ui.test.test2;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.ConstantVideo;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.person.MeLoginActivity;
import org.yczbj.ycvideoplayer.ui.person.MeMemberActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyFirstActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyFiveActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyFourActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMySecondActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMySixActivity;
import org.yczbj.ycvideoplayer.ui.test.test2.view.TestMyThirdActivity;
import org.yczbj.ycvideoplayer.util.ImageUtil;
import org.yczbj.ycvideoplayer.util.LogUtils;
import org.yczbj.ycvideoplayerlib.ConstantKeys;
import org.yczbj.ycvideoplayerlib.listener.OnMemberClickListener;
import org.yczbj.ycvideoplayerlib.listener.OnVideoBackListener;
import org.yczbj.ycvideoplayerlib.listener.OnVideoControlListener;
import org.yczbj.ycvideoplayerlib.VideoLogUtil;
import org.yczbj.ycvideoplayerlib.VideoPlayer;
import org.yczbj.ycvideoplayerlib.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.VideoPlayerUtils;

import java.util.ArrayList;

import butterknife.Bind;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * @author yc
 * @date 2017/12/29
 * 参考项目：
 * https://github.com/CarGuo/GSYVideoPlayer
 * https://github.com/danylovolokh/VideoPlayerManager
 * https://github.com/HotBitmapGG/bilibili-android-client
 * https://github.com/jjdxmashl/jjdxm_ijkplayer
 * https://github.com/JasonChow1989/JieCaoVideoPlayer-develop          2年前
 * https://github.com/open-android/JieCaoVideoPlayer                   1年前
 * https://github.com/lipangit/JiaoZiVideoPlayer                       4个月前
 * 个人感觉jiaozi这个播放器，与JieCaoVideoPlayer-develop有惊人的类同，借鉴了上面两个项目[JieCao]
 * <p>
 * <p>
 * 注意：在对应的播放Activity页面，清单文件中一定要添加
 * android:configChanges="orientation|keyboardHidden|screenSize"
 * android:screenOrientation="portrait"
 */

public class TestMyActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.video_player_1)
    VideoPlayer videoPlayer1;

    @Bind(R.id.video_player)
    VideoPlayer videoPlayer;
    @Bind(R.id.btn_my_1)
    Button btnMy1;
    @Bind(R.id.btn_my_2)
    Button btnMy2;
    @Bind(R.id.btn_my_3)
    Button btnMy3;
    @Bind(R.id.btn_my_4)
    Button btnMy4;
    @Bind(R.id.btn_my_5)
    Button btnMy5;
    @Bind(R.id.btn_my_6)
    Button btnMy6;
    @Bind(R.id.btn_my_0)
    Button btnMy0;
    @Bind(R.id.btn_my_6_1)
    Button btnMy61;
    @Bind(R.id.btn_my_00)
    Button btnMy00;
    @Bind(R.id.btn_my_7)
    Button btnMy7;
    @Bind(R.id.btn_my_8)
    Button btnMy8;
    @Bind(R.id.btn_my_9)
    Button btnMy9;

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_my;
    }


    @Override
    public void initView() {
        YCAppBar.setStatusBarLightMode(this, Color.WHITE);
        //原始封装视频播放，没有设置登录状态和权限
        initVideo();
    }

    private void initVideo() {
        //如果不想打印库中的日志，可以设置
        VideoLogUtil.isLog = false;

        //设置播放类型
        // IjkPlayer or MediaPlayer
        videoPlayer1.setPlayerType(VideoPlayer.TYPE_NATIVE);
        //网络视频地址
        String videoUrl = ConstantVideo.VideoPlayerList[0];
        //设置视频地址和请求头部
        videoPlayer1.setUp(videoUrl, null);
        //是否从上一次的位置继续播放
        videoPlayer1.continueFromLastPosition(true);
        //设置播放速度
        videoPlayer1.setSpeed(1.0f);
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setOnVideoBackListener(new OnVideoBackListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }
        });
        controller.setOnMemberClickListener(new OnMemberClickListener() {
            @Override
            public void onClick(int type) {
                switch (type){
                    case ConstantKeys.Gender.LOGIN:
                        ToastUtil.showToast(TestMyActivity.this,"登录");
                        break;
                    case ConstantKeys.Gender.MEMBER:
                        ToastUtil.showToast(TestMyActivity.this,"犊子");
                        break;
                    default:
                        break;
                }
            }
        });
        controller.setOnVideoControlListener(new OnVideoControlListener() {
            @Override
            public void onVideoControlClick(int type) {
                switch (type){
                    case ConstantKeys.VideoControl.DOWNLOAD:
                        ToastUtil.showToast(TestMyActivity.this,"下载音视频");
                        break;
                    case ConstantKeys.VideoControl.AUDIO:
                        ToastUtil.showToast(TestMyActivity.this,"切换音频");
                        break;
                    case ConstantKeys.VideoControl.SHARE:
                        ToastUtil.showToast(TestMyActivity.this,"分享内容");
                        break;
                    default:
                        break;
                }
            }
        });
        controller.setTitle("办快来围观拉，自定义视频播放器可以播放视频拉");
        //设置视频时长
        controller.setLength(98000);
        //设置5秒不操作后则隐藏头部和底部布局视图
        controller.setHideTime(5000);
        //controller.setImage(R.drawable.image_default);
        ImageUtil.loadImgByPicasso(this, R.drawable.image_default, R.drawable.image_default, controller.imageView());
        //设置视频控制器
        videoPlayer1.setController(controller);
    }


    @Override
    public void initListener() {
        btnMy00.setOnClickListener(this);
        btnMy0.setOnClickListener(this);
        btnMy1.setOnClickListener(this);
        btnMy2.setOnClickListener(this);
        btnMy3.setOnClickListener(this);
        btnMy4.setOnClickListener(this);
        btnMy5.setOnClickListener(this);
        btnMy6.setOnClickListener(this);
        btnMy61.setOnClickListener(this);
        btnMy7.setOnClickListener(this);
        btnMy8.setOnClickListener(this);
        btnMy9.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_my_00:
                downloadVideo();
                break;
            case R.id.btn_my_0:
                getData();
                break;
            case R.id.btn_my_1:
                startActivity(TestMyFirstActivity.class);
                break;
            case R.id.btn_my_2:
                startActivity(TestMySecondActivity.class);
                break;
            case R.id.btn_my_3:
                startActivity(TestMyThirdActivity.class);
                break;
            case R.id.btn_my_4:
                startActivity(TestMyFourActivity.class);
                break;
            case R.id.btn_my_5:
                startActivity(TestMyFiveActivity.class);
                break;
            case R.id.btn_my_6:
                startActivity(TestMySixActivity.class);
                break;
            case R.id.btn_my_6_1:
                VideoPlayerUtils.clearPlayPosition(this);
                break;
            case R.id.btn_my_7:
                initVideo7();
                break;
            case R.id.btn_my_8:
                initVideo8();
                break;
            case R.id.btn_my_9:
                initVideo9();
                break;
            default:
                break;
        }
    }


    private void getData() {
        //获取最大音量值
        int maxVolume = videoPlayer.getMaxVolume();
        LogUtils.e("最大音量值" + maxVolume);
        //获取音量值
        int volume = videoPlayer.getVolume();
        LogUtils.e("获取音量值" + volume);
        //获取持续时长
        long duration = videoPlayer.getDuration();
        LogUtils.e("获取持续时长" + duration);
        //获取播放位置
        long currentPosition = videoPlayer.getCurrentPosition();
        LogUtils.e("获取播放位置" + duration);
        //获取缓冲区百分比
        int bufferPercentage = videoPlayer.getBufferPercentage();
        LogUtils.e("获取缓冲区百分比" + duration);
        //获取播放速度
        float speed = videoPlayer.getSpeed(1);
        LogUtils.e("获取播放速度" + duration);
    }


    /**
     * 下载视频
     */
    private void downloadVideo() {

    }

    /**
     * 没有登录没有权限，一键设置
     */
    private void initVideo7() {
        videoPlayer.release();
        //设置播放类型
        // MediaPlayer
        //videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
        // IjkPlayer
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //网络视频地址
        String videoUrl = ConstantVideo.VideoPlayerList[1];
        //设置视频地址和请求头部
        videoPlayer.setUp(videoUrl, null);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(false);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
        //设置播放位置
        //videoPlayer.seekTo(3000);
        //设置音量
        videoPlayer.setVolume(50);


        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        //设置视频标题
        controller.setTitle("高仿优酷视频播放页面");
        //设置视频时长
        //controller.setLength(98000);
        //设置视频加载缓冲时加载窗的类型，多种类型
        controller.setLoadingType(2);
        ArrayList<String> content = new ArrayList<>();
        content.add("试看结束，观看全部内容请开通会员1111。");
        content.add("试看结束，观看全部内容请开通会员2222。");
        content.add("试看结束，观看全部内容请开通会员3333。");
        content.add("试看结束，观看全部内容请开通会员4444。");
        //设置会员权限话术内容
        controller.setMemberContent(content);
        //设置不操作后，5秒自动隐藏头部和底部布局
        controller.setHideTime(5000);
        //设置设置会员权限类型，第一个参数是否登录，第二个参数是否有权限看，第三个参数试看完后展示的文字内容，第四个参数是否保存进度位置
        controller.setMemberType(false,false,3);
        //设置背景图片
        controller.imageView().setBackgroundResource(R.color.blackText);
        //ImageUtil.loadImgByPicasso(this, R.color.blackText, R.drawable.image_default, controller.imageView());
        //设置试看结束后，登录或者充值会员按钮的点击事件
        controller.setOnMemberClickListener(new OnMemberClickListener() {
            @Override
            public void onClick(int type) {
                switch (type){
                    case ConstantKeys.Gender.LOGIN:
                        //调到用户登录也米娜
                        startActivity(MeLoginActivity.class);
                        break;
                    case ConstantKeys.Gender.MEMBER:
                        //调到用户充值会员页面
                        startActivity(MeMemberActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
        controller.setOnVideoBackListener(new OnVideoBackListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }
        });
        //设置视频清晰度
        //videoPlayer.setClarity(list,720);
        //设置视频控制器
        videoPlayer.setController(controller);
    }


    /**
     * 登录了，但不是会员没有权限，一键设置
     * 并且设置试看时间为1分钟
     */
    private void initVideo8() {
        videoPlayer.release();
        //设置播放类型
        // MediaPlayer
        //videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
        // IjkPlayer
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //网络视频地址
        String videoUrl = ConstantVideo.VideoPlayerList[2];
        //设置视频地址和请求头部
        videoPlayer.setUp(videoUrl, null);
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("高仿优酷视频播放页面");
        //controller.setLength(98000);
        controller.setLoadingType(1);
        controller.setTrySeeTime(60000);
        controller.setMemberType(true,false,0);
        controller.imageView().setBackgroundResource(R.color.blackText);
        //ImageUtil.loadImgByPicasso(this, R.color.blackText, R.drawable.image_default, controller.imageView());
        controller.setOnMemberClickListener(new OnMemberClickListener() {
            @Override
            public void onClick(int type) {
                switch (type){
                    case ConstantKeys.Gender.LOGIN:
                        startActivity(MeLoginActivity.class);
                        break;
                    case ConstantKeys.Gender.MEMBER:
                        startActivity(MeMemberActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
        //设置视频控制器
        videoPlayer.setController(controller);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(false);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
    }


    /**
     * 登录了，是会员，有所有权限，一键设置
     */
    private void initVideo9() {
        videoPlayer.release();
        //设置播放类型
        // MediaPlayer
        //videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
        // IjkPlayer
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //网络视频地址
        String videoUrl = ConstantVideo.VideoPlayerList[3];
        //设置视频地址和请求头部
        videoPlayer.setUp(videoUrl, null);
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("高仿优酷视频播放页面");
        //controller.setLength(98000);
        controller.setLoadingType(2);
        controller.setMemberType(true,true,1);
        controller.imageView().setBackgroundResource(R.color.blackText);
        //ImageUtil.loadImgByPicasso(this, R.color.blackText, R.drawable.image_default, controller.imageView());
        controller.setOnMemberClickListener(new OnMemberClickListener() {
            @Override
            public void onClick(int type) {
                switch (type){
                    case ConstantKeys.Gender.LOGIN:
                        startActivity(MeLoginActivity.class);
                        break;
                    case ConstantKeys.Gender.MEMBER:
                        startActivity(MeMemberActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
        //设置视频控制器
        videoPlayer.setController(controller);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(false);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
    }



    /**
     * 只是作用于写博客使用
     */
    private void initVideo0() {
        videoPlayer.release();
        //设置播放类型
        // MediaPlayer
        videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
        // IjkPlayer
        videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
        //网络视频地址
        String videoUrl = ConstantVideo.VideoPlayerList[4];
        //设置视频地址和请求头部
        videoPlayer.setUp(videoUrl, null);
        //是否从上一次的位置继续播放
        videoPlayer.continueFromLastPosition(false);
        //设置播放速度
        videoPlayer.setSpeed(1.0f);
        //设置播放位置
        //videoPlayer.seekTo(3000);
        //设置音量
        videoPlayer.setVolume(50);

        //设置全屏播放
        videoPlayer.enterFullScreen();
        //设置小屏幕播放
        videoPlayer.enterTinyWindow();
        //退出全屏
        videoPlayer.exitFullScreen();
        //退出小窗口播放
        videoPlayer.exitTinyWindow();
        //释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
        videoPlayer.release();
        //释放播放器，注意一定要判断对象是否为空，增强严谨性
        videoPlayer.releasePlayer();


        //是否从上一次的位置继续播放，不必须
        videoPlayer.continueFromLastPosition(false);
        //获取最大音量
        int maxVolume = videoPlayer.getMaxVolume();
        //获取音量值
        int volume = videoPlayer.getVolume();
        //获取持续时长
        long duration = videoPlayer.getDuration();
        //获取播放位置
        long currentPosition = videoPlayer.getCurrentPosition();
        //获取缓冲区百分比
        int bufferPercentage = videoPlayer.getBufferPercentage();
        //获取播放速度
        float speed = videoPlayer.getSpeed(1);


        //开始播放
        videoPlayer.start();
        //开始播放，从某位置播放
        videoPlayer.start(3000);
        //重新播放
        videoPlayer.restart();
        //暂停播放
        videoPlayer.pause();


        //判断是否开始播放
        boolean idle = videoPlayer.isIdle();
        //判断视频是否播放准备中
        boolean preparing = videoPlayer.isPreparing();
        //判断视频是否准备就绪
        boolean prepared = videoPlayer.isPrepared();
        //判断视频是否正在缓冲
        boolean bufferingPlaying = videoPlayer.isBufferingPlaying();
        //判断是否是否缓冲暂停
        boolean bufferingPaused = videoPlayer.isBufferingPaused();
        //判断视频是否暂停播放
        boolean paused = videoPlayer.isPaused();
        //判断视频是否正在播放
        boolean playing = videoPlayer.isPlaying();
        //判断视频是否播放错误
        boolean error = videoPlayer.isError();
        //判断视频是否播放完成
        boolean completed = videoPlayer.isCompleted();
        //判断视频是否播放全屏
        boolean fullScreen = videoPlayer.isFullScreen();
        //判断视频是否播放小窗口
        boolean tinyWindow = videoPlayer.isTinyWindow();
        //判断视频是否正常播放
        boolean normal = videoPlayer.isNormal();


        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("高仿优酷视频播放页面");
        //controller.setLength(98000);
        //设置视频加载缓冲时加载窗的类型，多种类型
        controller.setLoadingType(2);
        ArrayList<String> content = new ArrayList<>();
        content.add("试看结束，观看全部内容请开通会员1111。");
        content.add("试看结束，观看全部内容请开通会员2222。");
        content.add("试看结束，观看全部内容请开通会员3333。");
        content.add("试看结束，观看全部内容请开通会员4444。");
        controller.setMemberContent(content);
        controller.setHideTime(5000);
        //设置设置会员权限类型，第一个参数是否登录，第二个参数是否有权限看，第三个参数试看完后展示的文字内容，第四个参数是否保存进度位置
        controller.setMemberType(false,false,3);
        controller.imageView().setBackgroundResource(R.color.blackText);
        //ImageUtil.loadImgByPicasso(this, R.color.blackText, R.drawable.image_default, controller.imageView());
        //设置试看结束后，登录或者充值会员按钮的点击事件
        controller.setOnMemberClickListener(new OnMemberClickListener() {
            @Override
            public void onClick(int type) {
                switch (type){
                    case ConstantKeys.Gender.LOGIN:
                        //调到用户登录也米娜
                        startActivity(MeLoginActivity.class);
                        break;
                    case ConstantKeys.Gender.MEMBER:
                        //调到用户充值会员页面
                        startActivity(MeMemberActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
        //设置视频控制器
        videoPlayer.setController(controller);
    }





}
