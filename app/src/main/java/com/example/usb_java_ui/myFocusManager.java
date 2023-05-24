package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class myFocusManager {

    public static void txtFocusL(Context context ,TextView[] textViews, TTS_Import tts_import){
        for(TextView textView: textViews){
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    view.setBackground(context.getDrawable(R.drawable.border_red));
                    TextView txt = (TextView)view;
                    tts_import.speakOut(txt.getText().toString());
                }
                else {
                    view.setBackground(new ColorDrawable(Color.TRANSPARENT));
                }
            }
        });
        }
    }
    public static void btnFocusL(Context context , Button[] buttons, TTS_Import tts_import){
        for (Button button : buttons){
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
//                    view.setBackground(context.getDrawable(R.drawable.border_red));
                    Button btn = (Button)view;
                    tts_import.speakOut("버튼 "+btn.getText().toString());
                }
//                else {
////                    view.setBackground(new ColorDrawable(Color.TRANSPARENT));
//                }
            }
        });
        }
    }
//    public static void imgBtnFocusL(Context context , ImageButton[] imageButtons, TTS_Import tts_import){
//        for (ImageButton imageButton : imageButtons){
//            imageButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b){
////                    view.setBackground(context.getDrawable(R.drawable.border_red));
//                    ImageButton imgBtn = (ImageButton)view;
//
//                    tts_import.speakOut("버튼 "+imgBtn.getText().toString());
//                }
////                else {
//////                    view.setBackground(new ColorDrawable(Color.TRANSPARENT));
////                }
//            }
//        });
//        }
//    }


}
