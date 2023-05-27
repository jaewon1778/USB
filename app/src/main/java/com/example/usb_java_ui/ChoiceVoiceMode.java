package com.example.usb_java_ui;

import static android.content.Context.MODE_PRIVATE;
import static com.example.usb_java_ui.MyTouchEvent.DOUBLE_TAP;
import static com.example.usb_java_ui.MyTouchEvent.FLING_DOWN;
import static com.example.usb_java_ui.MyTouchEvent.FLING_LEFT;
import static com.example.usb_java_ui.MyTouchEvent.FLING_RIGHT;
import static com.example.usb_java_ui.MyTouchEvent.FLING_UP;
import static com.example.usb_java_ui.MyTouchEvent.LONG_PRESS;
import static com.example.usb_java_ui.MyTouchEvent.SINGLE_TAP;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;

public class ChoiceVoiceMode extends Dialog {

    private MyTouchEvent myTouchEvent;
    private TTS_Import ttsImport;

    private View cvmV;
    private TextView readthis;

    @Override
    public void show() {
        super.show();
//        ttsImport.speakOut((String) readthis.getText());
        ttsImport.speakOut("아니 왜 안돼");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ttsImport.ttsDestroy();
    }

    @SuppressLint("MissingInflatedId")
    public ChoiceVoiceMode(@NonNull Context context, TTS_Import tts_import) {
        super(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choice_mode);


        myTouchEvent = new MyTouchEvent();
        myTouchEvent.mDetector = new GestureDetectorCompat(getContext(),myTouchEvent);
        myTouchEvent.mDetector.setOnDoubleTapListener(myTouchEvent);
        SharedPreferences sp_setting = context.getSharedPreferences("setting", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor spe_setting = sp_setting.edit();
//        boolean is_voiceChecked = sp_setting.getBoolean("voiceChecked", false);

        ttsImport = tts_import;
        ttsImport.setSpeed(1.0f);

        cvmV = findViewById(R.id.ll_CVM);
        readthis = findViewById(R.id.txt_choiceModeDescription);

//        ttsImport.speakOut((String) readthis.getText());

        cvmV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(!myTouchEvent.mDetector.onTouchEvent(motionEvent)){
                    return true;
                }
                int gesture_n = myTouchEvent.getGesture_n();
                switch(gesture_n) {
                    case SINGLE_TAP:
                        ttsImport.speakOut((String) readthis.getText());
                        break;
                    case DOUBLE_TAP:
                        spe_setting.putBoolean("voiceChecked",true);
                        spe_setting.apply();
                        dismiss();
                        break;
                    case FLING_UP:
                    case FLING_DOWN:
                    case FLING_LEFT:
                    case FLING_RIGHT:
                        spe_setting.putBoolean("voiceChecked",false);
                        spe_setting.apply();
                        dismiss();
                        break;
                }

                return true;
            }
        });
    }


}
