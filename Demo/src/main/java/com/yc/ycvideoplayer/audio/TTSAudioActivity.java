package com.yc.ycvideoplayer.audio;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.yc.videotool.VideoLogUtils;

import org.yc.ycvideoplayer.R;

import java.util.Locale;

public class TTSAudioActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnInit;
    private Button btnSpeakTts1;
    private Button btnSpeakTts2;
    private Button btnSpeakTts3;
    private Button btnSpeakTts4;
    private Button btnResume;
    private Button btnStop;
    private Button btnHighPriority;
    private Button btnRelease;
    private Button btnBrazil;
    private Button btnTts;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_tts);

        btnInit = findViewById(R.id.btn_init);
        btnSpeakTts1 = findViewById(R.id.btn_speak_tts1);
        btnSpeakTts2 = findViewById(R.id.btn_speak_tts2);
        btnSpeakTts3 = findViewById(R.id.btn_speak_tts3);
        btnSpeakTts4 = findViewById(R.id.btn_speak_tts4);
        btnResume = findViewById(R.id.btn_resume);
        btnStop = findViewById(R.id.btn_stop);
        btnHighPriority = findViewById(R.id.btn_high_priority);
        btnRelease = findViewById(R.id.btn_release);
        btnBrazil = findViewById(R.id.btn_brazil);
        btnTts = findViewById(R.id.btn_tts);

        btnInit.setOnClickListener(this);
        btnSpeakTts1.setOnClickListener(this);
        btnSpeakTts2.setOnClickListener(this);
        btnSpeakTts3.setOnClickListener(this);
        btnSpeakTts4.setOnClickListener(this);
        btnResume.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnHighPriority.setOnClickListener(this);
        btnRelease.setOnClickListener(this);
        btnBrazil.setOnClickListener(this);
        btnTts.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnInit){
            init();
        } else if (v == btnSpeakTts1){
            if (textToSpeech!=null){
                textToSpeech.speak("简单播放tts", TextToSpeech.QUEUE_FLUSH, null);
            }
        } else if (v == btnSpeakTts2){
            if (textToSpeech!=null){
                for (int i=0 ; i<5 ; i++){
                    textToSpeech.speak("简单播放tts，"+i, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        } else if (v == btnSpeakTts3){
            if (textToSpeech!=null){
                for (int i=0 ; i<5 ; i++){
                    if (!textToSpeech.isSpeaking()){
                        textToSpeech.speak("简单播放tts，"+i, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        } else if (v == btnSpeakTts4){
            if (textToSpeech!=null){
                for (int i=0 ; i<5 ; i++){
                    textToSpeech.speak("简单播放，"+i, TextToSpeech.QUEUE_ADD, null);
                }
            }
        } else if (v == btnResume){
        } else if (v == btnStop){
            if (textToSpeech!=null){
                textToSpeech.stop();
            }
        } else if (v == btnHighPriority){

        } else if (v == btnRelease){
        } else if (v == btnBrazil){
        } else if (v == btnTts){
        }
    }

    private TextToSpeech textToSpeech;
    private void init(){
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if ((TextToSpeech.SUCCESS == status) && textToSpeech != null) {
                    VideoLogUtils.i("Initialize TTS success");
                    //获取locale
                    final Locale locale = TTSAudioActivity.this.getApplicationContext()
                            .getResources().getConfiguration().locale;
                    if (locale != null) {
                        VideoLogUtils.i("tts isLanguageAvailable " + textToSpeech.isLanguageAvailable(locale) +
                                "; variant is " + locale.getVariant() +
                                "; locale is " + locale + " ; country  is " + locale.getCountry());
                    }
                    //设置朗读语言
                    int setLanguage = textToSpeech.setLanguage(null != locale ? locale : Locale.getDefault());
                    switch (setLanguage) {
                        case TextToSpeech.LANG_MISSING_DATA:
                            VideoLogUtils.i("TTS set language: Language missing data");
                            break;
                        case TextToSpeech.LANG_NOT_SUPPORTED:
                            VideoLogUtils.i("TTS set language: Language not supported");
                            break;
                        case TextToSpeech.LANG_AVAILABLE:
                            VideoLogUtils.i("TTS set language: Language available");
                            break;
                        case TextToSpeech.LANG_COUNTRY_AVAILABLE:
                            VideoLogUtils.i("TTS set language: Language country available");
                            break;
                        case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
                            VideoLogUtils.i("TTS set language: Language country var available");
                            break;
                        default:
                            VideoLogUtils.i("TTS set language: Unknown error");
                            break;
                    }
                } else if (TextToSpeech.ERROR == status) {
                    VideoLogUtils.i("Initialize TTS error");
                } else {
                    VideoLogUtils.i("Initialize TTS error");
                }
            }
        });
    }

}
