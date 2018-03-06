package org.yczbj.ycvideoplayer.ui.test.test4;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.util.binding.BindView;
import org.yczbj.ycvideoplayer.util.binding.ViewBinder;


/**
 * Created by yc on 2016/3/5.
 * 仅用于练习和分析
 */

public class MediaPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    @BindView(R.id.btnplay)
    TextView btnplay;
    @BindView(R.id.btnpause)
    TextView btnpause;
    @BindView(R.id.btnstop)
    TextView btnstop;
    @BindView(R.id.btnplay2)
    TextView btnplay2;
    @BindView(R.id.btnpause2)
    TextView btnpause2;
    @BindView(R.id.btnstop2)
    TextView btnstop2;

    private MediaPlayer mediaPlayer;
    private int position;
    private String url1 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
    //音频url
    private String url4 = "http://61.129.89.191/ThroughTrain/download.html?id=4035&flag=-org-";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        ViewBinder.bind(this);


        btnstop.setOnClickListener(this);
        btnplay.setOnClickListener(this);
        btnpause.setOnClickListener(this);

        btnstop2.setOnClickListener(this);
        btnplay2.setOnClickListener(this);
        btnpause2.setOnClickListener(this);

        mediaPlayer = new MediaPlayer();
        initSurface();
    }


    private void initSurface() {
        // 设置SurfaceView自己不管理的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (position > 0) {
                    try {
                        // 开始播放
                        play();
                        // 并直接从指定位置开始播放
                        mediaPlayer.seekTo(position);
                        position = 0;
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnplay:
                play();
                break;
            case R.id.btnpause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Toast.makeText(this, "暂停播放！", Toast.LENGTH_LONG).show();
                } else {
                    mediaPlayer.start();
                    Toast.makeText(this, "继续播放！", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnstop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    Toast.makeText(this, "停止播放！", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnplay2:
                play2();
                break;
            case R.id.btnpause2:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Toast.makeText(this, "暂停播放！", Toast.LENGTH_LONG).show();
                } else {
                    mediaPlayer.start();
                    Toast.makeText(this, "继续播放！", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnstop2:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    Toast.makeText(this, "停止播放！", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        // 先判断是否正在播放
        if (mediaPlayer.isPlaying()) {
            // 如果正在播放我们就先保存这个播放位置
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
        super.onPause();
    }


    private void play() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置需要播放的视频
            Uri uri = Uri.parse(url1);
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            // 把视频画面输出到SurfaceView
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepare();
            // 播放
            mediaPlayer.start();
            Toast.makeText(this, "开始播放！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }


    private void play2() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置需要播放的视频
            Uri uri = Uri.parse(url4);
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();
            // 播放
            mediaPlayer.start();
            Toast.makeText(this, "开始播放！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

}
