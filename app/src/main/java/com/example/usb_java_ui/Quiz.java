package com.example.usb_java_ui;

import static com.example.usb_java_ui.DBManager.TABLE_ABB;
import static com.example.usb_java_ui.DBManager.TABLE_FC;
import static com.example.usb_java_ui.DBManager.TABLE_IC;
import static com.example.usb_java_ui.DBManager.TABLE_V;
import static com.example.usb_java_ui.DBManager.TABLE_WORD;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Quiz extends AppCompatActivity {
    private boolean qzMode;
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
    protected void onPause() {
        super.onPause();
        SharedPreferences prev_mode = getSharedPreferences("qzMode", MODE_PRIVATE);
        SharedPreferences.Editor editor = prev_mode.edit();
        editor.putBoolean("modeChecked",qzMode);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prev_mode = getSharedPreferences("qzMode", MODE_PRIVATE);
        qzMode = prev_mode.getBoolean("modeChecked",false);
        if (qzMode){
            txt_qzs.setText("쓰기 퀴즈");
        }
        else{
            txt_qzs.setText("읽기 퀴즈");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences prev_mode = getSharedPreferences("qzMode", MODE_PRIVATE);

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
//        btn_num.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(createIntent());
//            }
//        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Help:
                startActivity(new Intent(this, Help.class));
                return true;

            case R.id.Bluetooth:
                startActivity(new Intent(this, Bluetooth.class));
                return true;

            case R.id.Setting:
                startActivity(new Intent(this, Setting.class));
                return true;

            case android.R.id.home:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
