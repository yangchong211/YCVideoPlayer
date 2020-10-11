package org.yczbj.ycvideoplayer.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.ad.AdActivity;
import org.yczbj.ycvideoplayer.clarity.ClarityActivity;
import org.yczbj.ycvideoplayer.danmu.DanmuActivity;
import org.yczbj.ycvideoplayer.list.ListVideoActivity;
import org.yczbj.ycvideoplayer.list.TestListActivity;
import org.yczbj.ycvideoplayer.surface.TestSurfaceActivity;
import org.yczbj.ycvideoplayer.tiny.TestFullActivity;
import org.yczbj.ycvideoplayer.tiny.TinyScreenActivity;
import org.yczbj.ycvideoplayerlib.config.ConstantKeys;
import com.yc.kernel.impl.exo.ExoPlayerFactory;
import com.yc.kernel.impl.ijk.IjkPlayerFactory;
import com.yc.kernel.impl.media.MediaPlayerFactory;
import com.yc.kernel.factory.PlayerFactory;
import org.yczbj.ycvideoplayerlib.config.PlayerConfig;
import org.yczbj.ycvideoplayerlib.player.VideoViewManager;
import org.yczbj.ycvideoplayerlib.tool.BaseToast;
import org.yczbj.ycvideoplayerlib.tool.PlayerUtils;

import java.lang.reflect.Field;

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
    private TextView mTv43;
    private TextView mTv61;
    private TextView mTv62;
    private TextView mTv63;
    private TextView mTv64;
    private TextView mTv71;
    private TextView mTv81;
    private TextView mTv101;
    private TextView mTv111;
    private TextView mTv131;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mTv43 = findViewById(R.id.tv_4_3);
        mTv61 = findViewById(R.id.tv_6_1);
        mTv62 = findViewById(R.id.tv_6_2);
        mTv63 = findViewById(R.id.tv_6_3);
        mTv64 = findViewById(R.id.tv_6_4);
        mTv71 = findViewById(R.id.tv_7_1);
        mTv81 = findViewById(R.id.tv_8_1);
        mTv101 = findViewById(R.id.tv_10_1);
        mTv111 = findViewById(R.id.tv_11_1);
        mTv131 = findViewById(R.id.tv_13_1);
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
        mTv43.setOnClickListener(this);
        mTv61.setOnClickListener(this);
        mTv62.setOnClickListener(this);
        mTv63.setOnClickListener(this);
        mTv64.setOnClickListener(this);
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
        } else if (v == mTv43){
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
        } else if (v == mTv71){
            startActivity(new Intent(this, DanmuActivity.class));
        } else if (v == mTv81){
            startActivity(new Intent(this, AdActivity.class));
        } else if (v == mTv101){
            startActivity(new Intent(this, ListVideoActivity.class));
        } else if (v == mTv111){
            startActivity(new Intent(this, ClarityActivity.class));
        } else if (v == mTv131){
            startActivity(new Intent(this, TestSurfaceActivity.class));
        }
    }

    @SuppressLint("SetTextI18n")
    private void setChangeVideoType(@ConstantKeys.PlayerType int type){
        //切换播放核心，不推荐这么做，我这么写只是为了方便测试
        PlayerConfig config = VideoViewManager.getConfig();
        try {
            Field mPlayerFactoryField = config.getClass().getDeclaredField("mPlayerFactory");
            mPlayerFactoryField.setAccessible(true);
            PlayerFactory playerFactory = null;
            switch (type) {
                case ConstantKeys.VideoPlayerType.TYPE_IJK:
                    playerFactory = IjkPlayerFactory.create();
                    mTvTitle.setText("视频内核：" + " (IjkPlayer)");
                    break;
                case ConstantKeys.VideoPlayerType.TYPE_EXO:
                    playerFactory = ExoPlayerFactory.create();
                    mTvTitle.setText("视频内核：" + " (ExoPlayer)");
                    break;
                case ConstantKeys.VideoPlayerType.TYPE_NATIVE:
                    playerFactory = MediaPlayerFactory.create();
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
}
