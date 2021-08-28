package com.yc.ycvideoplayer.newPlayer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.yc.ycvideoplayer.R;
import com.yc.ycvideoplayer.newPlayer.ad.AdActivity;
import com.yc.ycvideoplayer.newPlayer.clarity.ClarityActivity;
import com.yc.ycvideoplayer.newPlayer.danmu.DanmuActivity;
import com.yc.ycvideoplayer.newPlayer.list.ContinuousVideoActivity;
import com.yc.ycvideoplayer.newPlayer.list.TestListActivity;
import com.yc.ycvideoplayer.newPlayer.pip.PipActivity;
import com.yc.ycvideoplayer.newPlayer.pip.PipListActivity;
import com.yc.ycvideoplayer.newPlayer.surface.TestSurfaceActivity;
import com.yc.ycvideoplayer.newPlayer.tiny.TestFullActivity;
import com.yc.ycvideoplayer.newPlayer.tiny.TinyScreenActivity;
import com.yc.video.config.ConstantKeys;

import com.yc.kernel.impl.exo.ExoMediaPlayer;
import com.yc.kernel.impl.exo.ExoPlayerFactory;
import com.yc.kernel.impl.ijk.IjkPlayerFactory;
import com.yc.kernel.impl.ijk.IjkVideoPlayer;
import com.yc.kernel.impl.media.AndroidMediaPlayer;
import com.yc.kernel.impl.media.MediaPlayerFactory;
import com.yc.kernel.factory.PlayerFactory;

import com.yc.video.config.VideoPlayerConfig;
import com.yc.video.player.VideoViewManager;
import com.yc.video.tool.BaseToast;
import com.yc.video.tool.PlayerUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTvTitle;
    private TextView mTv11;
    private TextView mTv12;
    private TextView mTv13;
    private TextView mTv21;
    private TextView mTv22;
    private TextView mTv23;
    private TextView mTv31;
    private TextView mTv32;
    private TextView mTv33;
    private TextView mTv41;
    private TextView mTv42;
    private TextView mTv43;
    private TextView mTv61;
    private TextView mTv62;
    private TextView mTv63;
    private TextView mTv64;
    private TextView mTv65;
    private TextView mTv66;
    private TextView mTv71;
    private TextView mTv81;
    private TextView mTv101;
    private TextView mTv111;
    private TextView mTv131;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        initFindViewById();
        initListener();

        //检测当前是用的哪个播放器
        Object factory = PlayerUtils.getCurrentPlayerFactory();
        if (factory instanceof ExoPlayerFactory) {
            mTvTitle.setText("视频内核：" + " (ExoPlayer)");
            setTitle(getResources().getString(R.string.app_name) + " (ExoPlayer)");
        } else if (factory instanceof IjkPlayerFactory) {
            mTvTitle.setText("视频内核：" + " (IjkPlayer)");
        } else if (factory instanceof MediaPlayerFactory) {
            mTvTitle.setText("视频内核：" + " (MediaPlayer)");
        } else {
            mTvTitle.setText("视频内核：" + " (unknown)");
        }
    }

    private void initFindViewById() {
        mToolbar = findViewById(R.id.toolbar);
        mTvTitle = findViewById(R.id.tv_title);
        mTv11 = findViewById(R.id.tv_1_1);
        mTv12 = findViewById(R.id.tv_1_2);
        mTv13 = findViewById(R.id.tv_1_3);
        mTv21 = findViewById(R.id.tv_2_1);
        mTv22 = findViewById(R.id.tv_2_2);
        mTv23 = findViewById(R.id.tv_2_3);
        mTv31 = findViewById(R.id.tv_3_1);
        mTv32 = findViewById(R.id.tv_3_2);
        mTv33 = findViewById(R.id.tv_3_3);
        mTv41 = findViewById(R.id.tv_4_1);
        mTv42 = findViewById(R.id.tv_4_2);
        mTv43 = findViewById(R.id.tv_4_3);
        mTv61 = findViewById(R.id.tv_6_1);
        mTv62 = findViewById(R.id.tv_6_2);
        mTv63 = findViewById(R.id.tv_6_3);
        mTv64 = findViewById(R.id.tv_6_4);
        mTv65 = findViewById(R.id.tv_6_5);
        mTv66 = findViewById(R.id.tv_6_6);
        mTv71 = findViewById(R.id.tv_7_1);
        mTv81 = findViewById(R.id.tv_8_1);
        mTv101 = findViewById(R.id.tv_10_1);
        mTv111 = findViewById(R.id.tv_11_1);
        mTv131 = findViewById(R.id.tv_13_1);


        String time = getTime(20);
        mTv11.setText(time);
    }

    private void initListener() {
        mTv11.setOnClickListener(this);
        mTv12.setOnClickListener(this);
        mTv13.setOnClickListener(this);
        mTv21.setOnClickListener(this);
        mTv22.setOnClickListener(this);
        mTv23.setOnClickListener(this);
        mTv31.setOnClickListener(this);
        mTv32.setOnClickListener(this);
        mTv33.setOnClickListener(this);
        mTv41.setOnClickListener(this);
        mTv42.setOnClickListener(this);
        mTv43.setOnClickListener(this);
        mTv61.setOnClickListener(this);
        mTv62.setOnClickListener(this);
        mTv63.setOnClickListener(this);
        mTv64.setOnClickListener(this);
        mTv65.setOnClickListener(this);
        mTv66.setOnClickListener(this);
        mTv71.setOnClickListener(this);
        mTv81.setOnClickListener(this);
        mTv101.setOnClickListener(this);
        mTv111.setOnClickListener(this);
        mTv131.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mTv11){
            //切换ijk
            setChangeVideoType(ConstantKeys.VideoPlayerType.TYPE_IJK);
        } else if (v == mTv12){
            //切换exo
            setChangeVideoType(ConstantKeys.VideoPlayerType.TYPE_EXO);
        } else if (v == mTv13){
            //切换原生
            setChangeVideoType(ConstantKeys.VideoPlayerType.TYPE_NATIVE);
        } else if (v == mTv21){
            BaseToast.showRoundRectToast("待完善");
        } else if (v == mTv22){
            BaseToast.showRoundRectToast("待完善");
        } else if (v == mTv23){
            BaseToast.showRoundRectToast("待完善");
        } else if (v == mTv31){
            startActivity(new Intent(this,NormalActivity.class));
        } else if (v == mTv32){
            startActivity(new Intent(this, TestFullActivity.class));
        } else if (v == mTv33){
            startActivity(new Intent(this,MultipleActivity.class));
        } else if (v == mTv41){
            startActivity(new Intent(this, PipActivity.class));
        } else if (v == mTv42){
            startActivity(new Intent(this, PipListActivity.class));
        }   else if (v == mTv43){
            startActivity(new Intent(this, TinyScreenActivity.class));
        } else if (v == mTv61){
            Intent intent = new Intent(this, TestListActivity.class);
            intent.putExtra("type",0);
            startActivity(intent);
        } else if (v == mTv62){
            Intent intent = new Intent(this, TestListActivity.class);
            intent.putExtra("type",1);
            startActivity(intent);
        } else if (v == mTv63){
            Intent intent = new Intent(this, TestListActivity.class);
            intent.putExtra("type",2);
            startActivity(intent);
        } else if (v == mTv64){
            Intent intent = new Intent(this, TestListActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        } else if (v == mTv65){
            Intent intent = new Intent(this, TestListActivity.class);
            intent.putExtra("type",4);
            startActivity(intent);
        } else if (v == mTv66){
            Intent intent = new Intent(this, TestListActivity.class);
            intent.putExtra("type",5);
            startActivity(intent);
        } else if (v == mTv71){
            startActivity(new Intent(this, DanmuActivity.class));
        } else if (v == mTv81){
            startActivity(new Intent(this, AdActivity.class));
        } else if (v == mTv101){
            startActivity(new Intent(this, ContinuousVideoActivity.class));
        } else if (v == mTv111){
            startActivity(new Intent(this, ClarityActivity.class));
        } else if (v == mTv131){
            startActivity(new Intent(this, TestSurfaceActivity.class));
        }
    }

    @SuppressLint("SetTextI18n")
    private void setChangeVideoType(@ConstantKeys.PlayerType int type){
        //切换播放核心，不推荐这么做，我这么写只是为了方便测试
        VideoPlayerConfig config = VideoViewManager.getConfig();
        try {
            Field mPlayerFactoryField = config.getClass().getDeclaredField("mPlayerFactory");
            mPlayerFactoryField.setAccessible(true);
            PlayerFactory playerFactory = null;
            switch (type) {
                case ConstantKeys.VideoPlayerType.TYPE_IJK:
                    playerFactory = IjkPlayerFactory.create();
                    IjkVideoPlayer ijkVideoPlayer = (IjkVideoPlayer) playerFactory.createPlayer(this);
                    mTvTitle.setText("视频内核：" + " (IjkPlayer)");
                    break;
                case ConstantKeys.VideoPlayerType.TYPE_EXO:
                    playerFactory = ExoPlayerFactory.create();
                    ExoMediaPlayer exoMediaPlayer = (ExoMediaPlayer) playerFactory.createPlayer(this);
                    mTvTitle.setText("视频内核：" + " (ExoPlayer)");
                    break;
                case ConstantKeys.VideoPlayerType.TYPE_NATIVE:
                    playerFactory = MediaPlayerFactory.create();
                    AndroidMediaPlayer androidMediaPlayer = (AndroidMediaPlayer) playerFactory.createPlayer(this);
                    mTvTitle.setText("视频内核：" + " (MediaPlayer)");
                    break;
                case ConstantKeys.VideoPlayerType.TYPE_RTC:
                    break;
            }
            mPlayerFactoryField.set(config, playerFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取时间
     * @param seconds                           分钟
     * @return
     */
    public static String getTime(long seconds){
        //获取当前时间戳
        long l = System.currentTimeMillis();
        long total = l + seconds * 60 * 1000;
        String time = formatDate(total);
        return time;
    }

    /**
     * 将千毫秒格式化mm:ss格式
     * @param millis                            毫秒值
     * @return
     */
    public static String formatDate(long millis) {
        Date date = new Date(millis);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }



    /**
     * 再点一次退出程序时间设置
     */
    private static final long WAIT_TIME = 2000L;
    private long touchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //双击返回桌面
            if (System.currentTimeMillis() - touchTime < WAIT_TIME) {
                //finish();
                //可将activity 退到后台，注意不是finish()退出。
                //判断Activity是否是task根
                //使用moveTaskToBack是为了让app退出时，不闪屏，退出柔和一些
                if (this.isTaskRoot()){
                    //参数为false——代表只有当前activity是task根，指应用启动的第一个activity时，才有效;
                    moveTaskToBack(false);
                } else {
                    //参数为true——则忽略这个限制，任何activity都可以有效。
                    //使用此方法，便不会执行Activity的onDestroy()方法
                    moveTaskToBack(true);
                }
                //注意这里是finish所有activity，然后杀死进程
                //ActivityManageUtils.getInstance().appExit(this);
            } else {
                touchTime = System.currentTimeMillis();
                //参考易车，抖音自定义吐司
                BaseToast.showRoundRectToast("再按一次退出");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
