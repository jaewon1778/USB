package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyFocusManager {

    public static void viewFocusL(Context context, View view, TTS_Import tts_import){

        if (view instanceof Button) btnFocusL((Button) view, tts_import);
        else if (view instanceof TextView) txtFocusL(context,(TextView) view, tts_import);
        else if (view instanceof ImageButton) imgBtnFocusL((ImageButton) view, tts_import);
    }

    public static void  viewArrFocusL(Context context, View[] views, TTS_Import tts_import){
        for(View view : views){
            viewFocusL(context, view, tts_import);
        }
    }

    public static void  wordItemFocusL(Context context, View[] items, TTS_Import tts_import){
        for(View item : items){
            item.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b){
                        view.setBackground(context.getDrawable(R.drawable.border_red));
                        TextView txt_index =  view.findViewById(R.id.txt_listItemIndex);
                        TextView txt_word =  view.findViewById(R.id.txt_listItemWord);
                        tts_import.speakOut("버튼 "+txt_index.getText().toString()+"번 "+txt_word.getText().toString());
                    }
                    else {
                        view.setBackground(new ColorDrawable(Color.TRANSPARENT));
                    }
                }
            });
        }
    }

    public static void  viewListFocusL(Context context, ArrayList<View> views, TTS_Import tts_import){
        for(View view : views){
            viewFocusL(context, view, tts_import);
        }
    }

    public static void txtFocusL(Context context ,TextView textView, TTS_Import tts_import){
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
    public static void txtListFocusL(Context context ,TextView[] textViews, TTS_Import tts_import){
        for(TextView textView: textViews){
            txtFocusL(context, textView, tts_import);
        }
    }
    public static void btnFocusL(Button button, TTS_Import tts_import){
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    Button btn = (Button)view;
                    tts_import.speakOut("버튼 "+btn.getText().toString());
                }
            }
        });
    }
    public static void btnListFocusL(Button[] buttons, TTS_Import tts_import){
        for (Button button : buttons){
            btnFocusL(button, tts_import);
        }
    }


    public static void imgBtnFocusL(ImageButton imageButton, TTS_Import tts_import){
        imageButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b){
//                    view.setBackground(context.getDrawable(R.drawable.border_red));
                ImageButton imgBtn = (ImageButton)view;

                tts_import.speakOut("버튼 "+imgBtn.getContentDescription().toString());
            }
//                else {
////                    view.setBackground(new ColorDrawable(Color.TRANSPARENT));
//                }
        }
    });
    }
    public static void imgBtnListFocusL(ImageButton[] imageButtons, TTS_Import tts_import){
        for (ImageButton imageButton : imageButtons){
            imgBtnFocusL(imageButton, tts_import);
        }
    }

    public static void setToolbarView(View[] views, TTS_Import tts_import) {

        for (int i = 0; i<views.length; i++){
            int finalI = i;
            views[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b){
                        switch (finalI){
                            case 0:
                                tts_import.speakOut("버튼 도움말");
                                break;
                            case 1:
                                tts_import.speakOut("버튼 블루투스");
                                break;
                            case 2:
                                tts_import.speakOut("버튼 설정");
                                break;

                        }
                    }
                }
            });
        }


    }


}
