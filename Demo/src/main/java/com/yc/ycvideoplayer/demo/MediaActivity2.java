package com.yc.ycvideoplayer.demo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.ycvideoplayer.ConstantVideo;

import org.yc.ycvideoplayer.R;
import com.yc.video.tool.BaseToast;

public class MediaActivity2 extends AppCompatActivity {

    private SurfaceView mSvMainSurface;
    private SeekBar mSeekBar;
    private Button mStart;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player2);
        initView();
        initMedia();
        initSurfaceView();
    }


    private void initView() {
        mSvMainSurface = findViewById(R.id.sv_main_surface);
        mSeekBar = findViewById(R.id.seekBar);
        mStart = findViewById(R.id.start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStart.getText().toString().equals("暂停")){
                    mediaPlayer.start();
                    mStart.setText("播放");
                } else {
                    mediaPlayer.pause();
                    mStart.setText("暂停");
                }
            }
        });
    }

    private void initMedia() {
        mediaPlayer=new MediaPlayer();
        //设置类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        /* 得到文件路径 *//* 注：文件存放在SD卡的根目录，一定要进行prepare()方法，使硬件进行准备 */
        try{
            Uri uri = Uri.parse(ConstantVideo.VideoPlayerList[2]);
            /* 为MediaPlayer 设置数据源 */
            mediaPlayer.setDataSource(this,uri);
            /* 准备 */
            mediaPlayer.prepare();
            //将播放器捕捉的画面展示到SurfaceView画面上
            mediaPlayer.setDisplay(mSvMainSurface.getHolder());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        mediaPlayer.start();
        // 把图标变为暂停图标
        mStart.setText("暂停");
        //获取音乐的总时长
        int duration=mediaPlayer.getDuration();
        //设置进度条的最大值为音乐总时长
        mSeekBar.setMax(duration);

    }


    private void initSurfaceView() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //获取拖动结束之后的位置
                int progress=seekBar.getProgress();
                //跳转到某个位置播放
                mediaPlayer.seekTo(progress);
            }
        });
    }

}
