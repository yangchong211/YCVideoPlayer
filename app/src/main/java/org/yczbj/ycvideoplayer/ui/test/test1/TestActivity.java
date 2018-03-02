package org.yczbj.ycvideoplayer.ui.test.test1;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.view.first.TestFirstActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.view.five.TestFiveActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.view.four.TestFourActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.view.second.TestSecondActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.view.six.TestSixActivity;
import org.yczbj.ycvideoplayer.ui.test.test1.view.three.TestThreeActivity;

import butterknife.Bind;
import cn.jzvd.CustomView.MyJZVideoPlayerStandard;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author yc
 * @date 2017/12/29
 * 参考项目：
 *          https://github.com/CarGuo/GSYVideoPlayer
 *          https://github.com/HotBitmapGG/bilibili-android-client
 *          https://github.com/jjdxmashl/jjdxm_ijkplayer
 *          https://github.com/JasonChow1989/JieCaoVideoPlayer-develop          2年前
 *          https://github.com/open-android/JieCaoVideoPlayer                   1年前
 *          https://github.com/lipangit/JiaoZiVideoPlayer                       4个月前
 *          个人感觉jiaozi这个播放器，与JieCaoVideoPlayer-develop有惊人的类同，借鉴了上面两个项目[JieCao]
 */
public class TestActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.jz_video)
    MyJZVideoPlayerStandard jzVideo;
    @Bind(R.id.btn_1)
    Button btn1;
    @Bind(R.id.btn_2)
    Button btn2;
    @Bind(R.id.btn_3)
    Button btn3;
    @Bind(R.id.btn_4)
    Button btn4;
    @Bind(R.id.btn_5)
    Button btn5;
    @Bind(R.id.btn_6)
    Button btn6;

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public int getContentView() {
        return R.layout.activity_test;
    }


    @Override
    public void initView() {
        jzVideo.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子快长大");
        Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo.thumbImageView);
        JZVideoPlayer.setJzUserAction(new MyUserActionStandard());
    }


    @Override
    public void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                startActivity(TestFirstActivity.class);
                break;
            case R.id.btn_2:
                startActivity(TestSecondActivity.class);
                break;
            case R.id.btn_3:
                startActivity(TestThreeActivity.class);
                break;
            case R.id.btn_4:
                startActivity(TestFourActivity.class);
                break;
            case R.id.btn_5:
                startActivity(TestFiveActivity.class);
                break;
            case R.id.btn_6:
                startActivity(TestSixActivity.class);
                break;
            default:
                break;
        }
    }


    /**
     * 这只是给埋点统计用户数据用的，不能写和播放相关的逻辑，监听事件请参考MyJZVideoPlayerStandard，复写函数取得相应事件
     */
    class MyUserActionStandard implements JZUserActionStandard {
        @Override
        public void onEvent(int type, Object url, int screen, Object... objects) {
            switch (type) {
                case JZUserAction.ON_CLICK_START_ICON:
                    Log.i("TEST_USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_START_ERROR:
                    Log.i("TEST_USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("TEST_USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_PAUSE:
                    Log.i("TEST_USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_RESUME:
                    Log.i("TEST_USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_SEEK_POSITION:
                    Log.i("TEST_USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_AUTO_COMPLETE:
                    Log.i("TEST_USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("TEST_USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("TEST_USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("TEST_USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("TEST_USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("TEST_USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("TEST_USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("TEST_USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserActionStandard.ON_CLICK_BLANK:
                    Log.i("TEST_USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("TEST_USER_EVENT", "unknow");
                    break;
            }
        }
    }


    /**
     * 关于代码分析
     * 1.博客连接：https://www.jianshu.com/p/308e4a59908c?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
     *
     *
     * 关于JiaoZiVideoPlayer项目存在的问题
     * 1.小窗播放比较常用情景是在列表中，如果这个Item已经滑动出界面为不可见状态，这是需要小窗播放，并且小窗能左划删除等操作，
     *   这个问题调试过几个月，一直没解决，就是列表滑动的时候如何准确的判断视频的Item滑动出列表，列表的onScroll函数，
     *   他的回调是有延迟的，如果列表滑的快回调次数少滑动慢回调次数多，如果快速滑动，在onScroll回调之前列表就会复用，视频就会重置
     *
     * 2.全屏播放是否可以定制上一部，下一部视频功能
     *
     * 3.视频播放过程中状态：加载中，播放失败，没网呢，重新加载等状态如何切换。而不是一直转圈
     */


}
