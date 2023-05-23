package com.example.usb_java_ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Dot_num extends AppCompatActivity {
    private TTS_Import tts_import;
    private ConnectedThread connectedThread;

    private int[] braille;
    private ImageButton point1;
    private ImageButton point2;
    private ImageButton point3;
    private ImageButton point4;
    private ImageButton point5;
    private ImageButton point6;

    private Button btn_output;

    protected void onResume() {
        super.onResume();
        SharedPreferences prev_settings = getSharedPreferences("setting", MODE_PRIVATE);
        tts_import = new TTS_Import();
        tts_import.set_tts(new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts_import.onInit(i);
            }
        }));
        tts_import.setSpeed(prev_settings.getFloat("voiceSpeedFloat",1.0f));


        SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
        String deviceN = sp_bluetooth.getString("DN", "isNot");

        if(!Objects.equals(deviceN, "isNot")) {
            connectedThread = BluetoothConnection.connectedThread;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts_import.ttsDestroy();
        sendbraille(0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dot_num);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        braille = new int[]{0, 0, 0, 0, 0, 0};

        point1 = (ImageButton) findViewById(R.id.imgbtn_point1);
        point2 = (ImageButton) findViewById(R.id.imgbtn_point2);
        point3 = (ImageButton) findViewById(R.id.imgbtn_point3);
        point4 = (ImageButton) findViewById(R.id.imgbtn_point4);
        point5 = (ImageButton) findViewById(R.id.imgbtn_point5);
        point6 = (ImageButton) findViewById(R.id.imgbtn_point6);
        btn_output = findViewById(R.id.btn_output);


        btn_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendbraille(0, 1);
            }
        });

        point1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[0]==1){
                    braille[0] = 0;
                    point1.setImageResource(R.drawable.p0);
                }
                else {
                    braille[0] = 1;
                    point1.setImageResource(R.drawable.p1);
                }
            }
        });
        point2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[1]==1){
                    braille[1] = 0;
                    point2.setImageResource(R.drawable.p0);
                }
                else {
                    braille[1] = 1;
                    point2.setImageResource(R.drawable.p1);
                }
            }
        });
        point3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[2]==1){
                    braille[2] = 0;
                    point3.setImageResource(R.drawable.p0);
                }
                else {
                    braille[2] = 1;
                    point3.setImageResource(R.drawable.p1);
                }
            }
        });
        point4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[3]==1){
                    braille[3] = 0;
                    point4.setImageResource(R.drawable.p0);
                }
                else {
                    braille[3] = 1;
                    point4.setImageResource(R.drawable.p1);
                }
            }
        });
        point5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[4]==1){
                    braille[4] = 0;
                    point5.setImageResource(R.drawable.p0);
                }
                else {
                    braille[4] = 1;
                    point5.setImageResource(R.drawable.p1);
                }
            }
        });
        point6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[5]==1){
                    braille[5] = 0;
                    point6.setImageResource(R.drawable.p0);
                }
                else {
                    braille[5] = 1;
                    point6.setImageResource(R.drawable.p1);
                }
            }
        });

    }
    public void sendbraille(int send_idx, int cnt) {
        ArrayList<int[]> brailleList = new ArrayList<>();

        if(send_idx+cnt == 0){
            braille = new int[]{0,0,0,0,0,0};
        }
        brailleList.add(braille);

        String row = String.valueOf(brailleList.size());
        String braille = Arrays.deepToString(brailleList.toArray());
        braille = row + braille + ";";  //끝에 ; 추가
        Log.d("braille", braille);
        if(connectedThread!=null){ Log.d("braille", braille);connectedThread.write(braille); }
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
