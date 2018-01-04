package org.yczbj.ycvideoplayer.ui.test2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test2.model.DataUtil;
import org.yczbj.ycvideoplayer.ui.test2.view.TestMyFirstActivity;
import org.yczbj.ycvideoplayer.ui.test2.view.TestMyFiveActivity;
import org.yczbj.ycvideoplayer.ui.test2.view.TestMyFourActivity;
import org.yczbj.ycvideoplayer.ui.test2.view.TestMySecondActivity;
import org.yczbj.ycvideoplayer.ui.test2.view.TestMySixActivity;
import org.yczbj.ycvideoplayer.ui.test2.view.TestMyThirdActivity;
import org.yczbj.ycvideoplayer.util.ImageUtils;
import org.yczbj.ycvideoplayer.util.LogUtils;
import org.yczbj.ycvideoplayerlib.VideoPlayer;
import org.yczbj.ycvideoplayerlib.VideoPlayerController;
import org.yczbj.ycvideoplayerlib.VideoPlayerManager;

import butterknife.Bind;
import butterknife.ButterKnife;

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


    @Bind(R.id.nice_video_player)
    VideoPlayer niceVideoPlayer;
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
    @Bind(R.id.btn_my_00)
    Button btnMy00;

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test_my;
    }


    @Override
    public void initView() {
        //设置播放类型
        niceVideoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE); // IjkPlayer or MediaPlayer
        //网络视频地址
        String videoUrl = DataUtil.getVideoListData().get(0).getVideoUrl();
        //设置视频地址和请求头部
        niceVideoPlayer.setUp(videoUrl, null);
        //创建视频控制器
        VideoPlayerController controller = new VideoPlayerController(this);
        controller.setTitle("办快来围观拉，自定义视频播放器可以播放视频拉");
        controller.setLength(98000);
        //controller.setImage(R.drawable.image_default);
        ImageUtils.loadImgByPicasso(this, R.drawable.image_default, R.drawable.image_default, controller.imageView());
        //设置视频控制器
        niceVideoPlayer.setController(controller);
        //是否从上一次的位置继续播放
        niceVideoPlayer.continueFromLastPosition(true);
        //设置播放速度
        niceVideoPlayer.setSpeed(1.0f);
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
            default:
                break;
        }
    }

    private void getData() {
        //获取最大音量值
        int maxVolume = niceVideoPlayer.getMaxVolume();
        LogUtils.e("最大音量值" + maxVolume);
        //获取音量值
        int volume = niceVideoPlayer.getVolume();
        LogUtils.e("获取音量值" + volume);
        //获取持续时长
        long duration = niceVideoPlayer.getDuration();
        LogUtils.e("获取持续时长" + duration);
        //获取播放位置
        long currentPosition = niceVideoPlayer.getCurrentPosition();
        LogUtils.e("获取播放位置" + duration);
        //获取缓冲区百分比
        int bufferPercentage = niceVideoPlayer.getBufferPercentage();
        LogUtils.e("获取缓冲区百分比" + duration);
        //获取播放速度
        float speed = niceVideoPlayer.getSpeed(1);
        LogUtils.e("获取播放速度" + duration);
    }


    /**
     * 下载视频
     */
    private void downloadVideo() {

    }

}
