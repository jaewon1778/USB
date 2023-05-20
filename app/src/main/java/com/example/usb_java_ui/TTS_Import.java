package com.example.usb_java_ui;

import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TTS_Import implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private float speed;


    public void set_tts(TextToSpeech tts) {
        this.tts = tts;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS)  {
            int result = tts.setLanguage(Locale.KOREA);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }

        } else {
            Log.e("TTS", "Initialization Failed!");
        }
    }

    public void speakOut(String readThis) {
        tts.setPitch((float) 1.0);
        tts.setSpeechRate(speed);
        tts.speak(readThis,TextToSpeech.QUEUE_FLUSH,null,"id1");

    }

//    @Override
//    protected void onDestroy() {
//        if (tts != null)  {
//            tts.stop();
//            tts.shutdown();
//            tts=null;
//        }
//        super.onDestroy();
//    }
    public void ttsDestroy(){
        if (tts != null)  {
            tts.stop();
            tts.shutdown();
            tts=null;
        }
    }

}
