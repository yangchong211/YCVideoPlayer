package com.yc.ycvideoplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.yc.ycvideoplayer.R;

import com.yc.music.model.AudioBean;
import com.yc.music.service.PlayService;
import com.yc.music.tool.BaseAppHelper;
import com.yc.videotool.VideoLogUtils;
import com.yc.videoview.FloatWindow;
import com.yc.videoview.MoveType;
import com.yc.videoview.WindowScreen;
import com.yc.videoview.WindowUtil;
import com.yc.ycvideoplayer.audio.AudioActivity;
import com.yc.ycvideoplayer.demo.DemoActivity;
import com.yc.ycvideoplayer.m3u8.M3u8Activity;
import com.yc.ycvideoplayer.music.MusicPlayerActivity;
import com.yc.ycvideoplayer.newPlayer.activity.TypeActivity;
import com.yc.ycvideoplayer.oldPlayer.OldActivity;

import java.util.List;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/11/18
 * 描    述：Main主页面
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private PlayServiceConnection mPlayServiceConnection;


    @Override
    protected void onDestroy() {
        if (mPlayServiceConnection != null) {
            unbindService(mPlayServiceConnection);
        }
        super.onDestroy();
        FloatWindow.destroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StateAppBar.setStatusBarLightMode(this, Color.WHITE);
        initView();
    }

    private void initView() {
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mTv6 = (TextView) findViewById(R.id.tv_6);
        mTv7 = findViewById(R.id.tv_7);

        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
        mTv3.setOnClickListener(this);
        mTv4.setOnClickListener(this);
        mTv5.setOnClickListener(this);
        mTv6.setOnClickListener(this);
        mTv7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                startActivity(DemoActivity.class);
                break;
            case R.id.tv_2:
                startActivity(TypeActivity.class);
                break;
            case R.id.tv_3:
                startActivity(OldActivity.class);
                break;
            case R.id.tv_4:
                startCheckService();
                startActivity(MusicPlayerActivity.class);
                break;
            case R.id.tv_5:
                startActivity(M3u8Activity.class);
                break;
            case R.id.tv_6:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!WindowUtil.hasPermission(this)) {
                        requestAlertWindowPermission();
                    } else {
                        windowDialog();
                    }
                }
                break;
            case R.id.tv_7:
                startActivity(AudioActivity.class);
                break;
        }
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


    private void windowDialog() {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.ic_play_btn_loved);
        FloatWindow
                .with(getApplicationContext())
                .setView(imageView)
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FloatWindow.get()!=null){
            FloatWindow.get().hide();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (FloatWindow.get()!=null){
            FloatWindow.get().show();
        }
    }

    private void startActivity(Class c){
        startActivity(new Intent(this,c));
    }



    /**
     * 检测服务
     */
    private void startCheckService() {
        if (BaseAppHelper.get().getPlayService() == null) {
            startService();
            mTv1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            },500);
        }
    }

    /**
     * 开启服务
     */
    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }


    /**
     * 绑定服务
     * 注意对于绑定服务一定要解绑
     */
    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(this, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }


    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            VideoLogUtils.e("onServiceConnected"+name);
            final PlayService playService = ((PlayService.PlayBinder) service).getService();
            BaseAppHelper.get().setPlayService(playService);
            List<AudioBean> musicList = BaseAppHelper.get().getMusicList();
            AudioBean audioBean1 = new AudioBean();
            audioBean1.setPath("http://img.zhugexuetang.com/lleXB2SNF5UFp1LfNpPI0hsyQjNs");
            audioBean1.setId("1");
            audioBean1.setTitle("音频1");
            musicList.add(audioBean1);
            AudioBean audioBean2 = new AudioBean();
            audioBean2.setPath("http://img.zhugexuetang.com/ljUa-X-oDbLHu7n9AhkuMLu2Yz3k");
            audioBean2.setId("2");
            audioBean2.setTitle("音频2");
            musicList.add(audioBean2);
            AudioBean audioBean3 = new AudioBean();
            audioBean3.setPath("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4");
            audioBean3.setId("3");
            audioBean3.setTitle("音频3");
            musicList.add(audioBean3);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            VideoLogUtils.e("onServiceDisconnected"+name);
        }
    }



}
