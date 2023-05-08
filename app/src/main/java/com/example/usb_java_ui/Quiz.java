package com.example.usb_java_ui;

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
    boolean qzMode;
    TextView txt_qzs;
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

        txt_qzs = (TextView) findViewById(R.id.txt_qzSelect);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch swbtn_qzs = findViewById(R.id.swbtn_qzSelect);
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

        Button button1 = (Button) findViewById(R.id.btn_qzKorean);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Quiz_readOutput.class);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_qzNumber);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Quiz_listenOutput.class);
                startActivity(intent);
            }
        });

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
