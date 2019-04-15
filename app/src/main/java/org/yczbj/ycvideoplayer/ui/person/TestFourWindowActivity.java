package org.yczbj.ycvideoplayer.ui.person;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.base.mvp1.BaseActivity;
import org.yczbj.ycvideoplayer.ui.home.view.activity.EmptyActivity;
import org.yczbj.ycvideoplayerlib.manager.VideoPlayerManager;
import org.yczbj.ycvideoplayerlib.player.VideoPlayer;
import org.yczbj.ycvideoplayerlib.window.FloatPlayerView;
import org.yczbj.ycvideoplayerlib.window.FloatWindow;
import org.yczbj.ycvideoplayerlib.window.MoveType;
import org.yczbj.ycvideoplayerlib.window.WindowScreen;
import org.yczbj.ycvideoplayerlib.window.WindowUtil;

import butterknife.BindView;


/**
 * @author yc
 */
public class TestFourWindowActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.video_player)
    VideoPlayer videoPlayer;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;

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
        return R.layout.activity_test_window_four;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!WindowUtil.hasPermission(this)) {
                requestAlertWindowPermission();
            }
        }
    }

    @Override
    public void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                startWindow();
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, EmptyActivity.class));
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
                //.setWidth(WindowScreen.width, 0.4f)
                //.setHeight(WindowScreen.width, 0.3f)
                .setX(WindowScreen.width, 0.8f)             //这个是设置位置
                .setY(WindowScreen.height, 0.3f)
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
        if (Build.VERSION.SDK_INT >= 23){
            if (WindowUtil.hasPermission(this)) {

            } else {
                this.finish();
            }
        }
    }

}
