package com.yc.ycvideoplayer.audio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.audioplayer.manager.AudioManager;
import com.yc.audioplayer.spi.AudioService;
import com.yc.audioplayer.bean.AudioPlayData;
import com.yc.audioplayer.bean.AudioTtsPriority;

import org.yc.ycvideoplayer.R;

public class AudioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnInit;
    private Button btnSpeakTts;
    private Button btnSpeakMedia;
    private Button btnMixPlay;
    private Button btnPause;
    private Button btnResume;
    private Button btnStop;
    private Button btnHighPriority;
    private Button btnRelease;
    private Button btnBrazil;
    private Button btnTts;
    private Button btnTtsDemo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        btnInit = findViewById(R.id.btn_init);
        btnSpeakTts = findViewById(R.id.btn_speak_tts);
        btnSpeakMedia = findViewById(R.id.btn_speak_media);
        btnMixPlay = findViewById(R.id.btn_mix_play);
        btnPause = findViewById(R.id.btn_pause);
        btnResume = findViewById(R.id.btn_resume);
        btnStop = findViewById(R.id.btn_stop);
        btnHighPriority = findViewById(R.id.btn_high_priority);
        btnRelease = findViewById(R.id.btn_release);
        btnBrazil = findViewById(R.id.btn_brazil);
        btnTts = findViewById(R.id.btn_tts);
        btnTtsDemo = findViewById(R.id.btn_tts_demo);

        btnInit.setOnClickListener(this);
        btnSpeakTts.setOnClickListener(this);
        btnSpeakMedia.setOnClickListener(this);
        btnMixPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnResume.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnHighPriority.setOnClickListener(this);
        btnRelease.setOnClickListener(this);
        btnBrazil.setOnClickListener(this);
        btnTts.setOnClickListener(this);
        btnTtsDemo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnInit){
            AudioService.getInstance().init(this);
            AudioService.getInstance().setPlayStateListener(new AudioManager.PlayStateListener() {
                @Override
                public void onStartPlay() {

                }

                @Override
                public void onCompletePlay() {

                }
            });
        } else if (v == btnSpeakTts){
            AudioPlayData playData = new AudioPlayData.Builder()
                    .tts("当前只有做国际版业务时，才需要调用此函数。别的业务线或其他情况下禁止调用此函数。2.若需要调用，请在初始化阶段")
                    .build();
            AudioService.getInstance().play(playData);
        } else if (v == btnSpeakMedia){
            AudioPlayData playData = new AudioPlayData.Builder()
                    .rawId(R.raw.timeout)
                    .build();
            AudioService.getInstance().play(playData);
        } else if (v == btnMixPlay){
            AudioPlayData data = new AudioPlayData.Builder(AudioTtsPriority.HIGH_PRIORITY)
                    .tts("我是一个混合的协议")
                    .rawId(R.raw.timeout)
                    .tts("Hello TTS Service").build();
            AudioService.getInstance().play(data);
        } else if (v == btnPause){
            AudioService.getInstance().pause();
        } else if (v == btnResume){
            AudioService.getInstance().resume();
        } else if (v == btnStop){
            AudioService.getInstance().stop();
        } else if (v == btnHighPriority){
            AudioPlayData playData =new AudioPlayData.Builder(AudioTtsPriority.HIGH_PRIORITY)
                    .tts("Fire in the home! Fire in the home ")
                    .build();
            AudioService.getInstance().play(playData);
        } else if (v == btnRelease){
            AudioService.getInstance().release();
        } else if (v == btnBrazil){
            AudioPlayData playData = new AudioPlayData.Builder()
                    .tts("vire à esquerda na parada de ônibus")
                    .build();
            AudioService.getInstance().play(playData);
        } else if (v == btnTts){
            AudioService.getInstance().playTts("逗比，这个是tts");
        } else if (v == btnTtsDemo){
            startActivity(new Intent(this,TTSAudioActivity.class));
        }
    }
}
