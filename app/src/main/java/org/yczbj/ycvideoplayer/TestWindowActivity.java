package org.yczbj.ycvideoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import org.yczbj.ycvideoplayer.tiny.TestFullActivity;
import org.yczbj.ycvideoplayerlib.old.other.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.ui.window.FloatPlayerView;
import org.yczbj.ycvideoplayerlib.old.player.OldVideoPlayer;
import org.yczbj.ycvideoplayerlib.ui.window.FloatWindow;
import org.yczbj.ycvideoplayerlib.ui.window.MoveType;
import org.yczbj.ycvideoplayerlib.ui.window.WindowScreen;
import org.yczbj.ycvideoplayerlib.ui.window.WindowUtil;


/**
 * @author yc
 */
public class TestWindowActivity extends BaseActivity implements View.OnClickListener {

    private OldVideoPlayer mVideoPlayer;
    private Button mBtn1;
    private Button mBtn2;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
         * 这里在返回主页的时候销毁了，因为不想和DEMO中其他页面冲突
         */
        VideoPlayerManager.instance().releaseVideoPlayer();
        FloatWindow.destroy();
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
        return R.layout.activity_test_window;
    }

    @Override
    public void initView() {
        mVideoPlayer = (OldVideoPlayer) findViewById(R.id.video_player);
        mBtn1 = (Button) findViewById(R.id.btn_1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn_2);
        mBtn2.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!WindowUtil.hasPermission(this)) {
                requestAlertWindowPermission();
            }
        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                startWindow();
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, TestFullActivity.class));
                break;
            default:
                break;
        }
    }

    private void startWindow() {
        if (FloatWindow.get() != null) {
            return;
        }
        String url = "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super";
        FloatPlayerView.setUrl(url);
        FloatPlayerView floatPlayerView = new FloatPlayerView(getApplicationContext());
        floatPlayerView.setCompletedListener(new FloatPlayerView.CompletedListener() {
            @Override
            public void Completed() {
                FloatWindow.get().hide();
            }
        });
        FloatWindow
                .with(getApplicationContext())
                .setView(floatPlayerView)
                //.setWidth(WindowScreen.WIDTH, 0.4f)
                //.setHeight(WindowScreen.WIDTH, 0.3f)
                //这个是设置位置
                .setX(WindowScreen.WIDTH, 0.8f)
                .setY(WindowScreen.HEIGHT, 0.3f)
                .setMoveType(MoveType.slide)
                .setFilter(false)
                //.setFilter(true, WindowActivity.class, EmptyActivity.class)
                .setMoveStyle(500, new BounceInterpolator())
                .build();
        FloatWindow.get().show();
    }


    @RequiresApi(api = 23)
    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 1);
    }

    @RequiresApi(api = 23)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= 23) {
            if (WindowUtil.hasPermission(this)) {

            } else {
                this.finish();
            }
        }
    }


}
