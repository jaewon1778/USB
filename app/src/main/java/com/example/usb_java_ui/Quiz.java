package com.example.usb_java_ui;

import static com.example.usb_java_ui.DBManager.TABLE_ABB;
import static com.example.usb_java_ui.DBManager.TABLE_FC;
import static com.example.usb_java_ui.DBManager.TABLE_IC;
import static com.example.usb_java_ui.DBManager.TABLE_NUM;
import static com.example.usb_java_ui.DBManager.TABLE_V;
import static com.example.usb_java_ui.DBManager.TABLE_WORD;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Quiz extends MyAppActivity {
    private boolean qzMode;
    private SharedPreferences prev_mode;
    private TextView txt_qzs;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch swbtn_qzs;

    private Button btn_initialC;
    private Button btn_vowel;
    private Button btn_finalC;
    private Button btn_abb;
    private Button btn_num;
    private Button btn_word;

    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();

        ObjectTree OT_root = new ObjectTree().rootObject();
        // make objTree
        swbtn_qzs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swbtn_qzs.setChecked(!swbtn_qzs.isChecked());
            }
        });
        ObjectTree OT_swM = new ObjectTree().initObject(swbtn_qzs);
        ObjectTree OT_pt = new ObjectTree().initObject(txt_qzs);
        OT_pt.addChildViewArr(new View[]{btn_initialC, btn_vowel, btn_finalC, btn_abb, btn_num, btn_word});
        OT_root.addChildObjectArr(new ObjectTree[]{OT_pt, OT_swM});

        // make objTree
        MyFocusManager.viewArrFocusL(this, new View[]{swbtn_qzs,txt_qzs, btn_initialC, btn_vowel, btn_finalC, btn_abb, btn_num, btn_word},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @Override
    protected void onResume() {
        super.onResume();
        qzMode = prev_mode.getBoolean("modeChecked",false);
        if (qzMode){
            txt_qzs.setText("쓰기 퀴즈");
        }
        else{
            txt_qzs.setText("읽기 퀴즈");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = prev_mode.edit();
        editor.putBoolean("modeChecked",qzMode);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.quiz);
        super.onCreate(savedInstanceState);

        prev_mode = getSharedPreferences("qzMode", MODE_PRIVATE);

        txt_qzs = findViewById(R.id.txt_qzSelect);
        btn_initialC = findViewById(R.id.btn_qzInitialCon);
        btn_vowel = findViewById(R.id.btn_qzVowel);
        btn_finalC = findViewById(R.id.btn_qzFinalCon);
        btn_abb = findViewById(R.id.btn_qzAbb);
        btn_num = findViewById(R.id.btn_qzNumber);
        btn_word = findViewById(R.id.btn_qzWord);

        swbtn_qzs = findViewById(R.id.swbtn_qzSelect);
        swbtn_qzs.setChecked(prev_mode.getBoolean("modeChecked", false));
        swbtn_qzs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean mode) {
                if (mode){
                    txt_qzs.setText("쓰기 퀴즈");
                }
                else{
                    txt_qzs.setText("읽기 퀴즈");
                }
                qzMode = mode;
            }
        });

        btn_initialC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createIntent(TABLE_IC));
            }
        });
        btn_vowel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createIntent(TABLE_V));
            }
        });
        btn_finalC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createIntent(TABLE_FC));
            }
        });
        btn_abb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createIntent(TABLE_ABB));
            }
        });
        btn_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createIntent(TABLE_NUM));
            }
        });
        btn_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createIntent(TABLE_WORD));
            }
        });
    }

    public Intent createIntent(String table_name){
        Intent intent;
        if(qzMode) intent = new Intent(getApplicationContext(), Quiz_listenOutput.class);
        else intent = new Intent(getApplicationContext(), Quiz_readOutput.class);

        intent.putExtra("keyTableName", table_name);
        return intent;
    }
}
