package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;

public class MainActivity extends MyAppActivity {
    private final long finishTime = 1000;
    private long pressTime = 0;

    private TextView txt_study;
    private TextView txt_addWord;
    private Button btn_learning_word;
    private Button btn_quiz;
    private Button btn_typing;
    private Button btn_image_detection;
    private Button btn_stt;

    protected void VoiceModeOn(){
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();
        ObjectTree OT_study = new ObjectTree().initObject(txt_study);
        ObjectTree OT_addWord = new ObjectTree().initObject(txt_addWord);
        OT_study.addChildViewArr(new View[]{btn_learning_word, btn_quiz});
        OT_addWord.addChildViewArr(new View[]{btn_typing, btn_image_detection, btn_stt});
        OT_root.addChildObjectArr(new ObjectTree[]{OT_study,OT_addWord});

        MyFocusManager.viewArrFocusL(this, new View[]{txt_study,txt_addWord,btn_learning_word, btn_quiz,btn_typing, btn_image_detection, btn_stt}, getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();


    }

    @Override
    protected void onResume() {
//        if (!getSp_setting().getBoolean("voiceChecked", false)){
//            ChoiceVoiceMode CVM = new ChoiceVoiceMode(this,getTTS_import());
//            CVM.show();
//        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (BluetoothConnection.btSocket != null){
                if(BluetoothConnection.btSocket.isConnected()){
                    BluetoothConnection.btSocket.close();
                }
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

//        this.deleteDatabase("USB_DB");

        DBManager mDBM = new DBManager();
        mDBM.InitDB(getApplicationContext());

        SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
        SharedPreferences.Editor spe_bluetooth = sp_bluetooth.edit();
        spe_bluetooth.putString("DN", "isNot");
        spe_bluetooth.apply();




        txt_study = (TextView) findViewById(R.id.txt_study);
        txt_addWord = findViewById(R.id.txt_addWord);
        btn_learning_word = (Button) findViewById(R.id.learning_word);
        btn_quiz = (Button) findViewById(R.id.quiz);
        btn_typing = (Button) findViewById(R.id.typing);
        btn_image_detection = (Button) findViewById(R.id.image_detection);
        btn_stt = (Button) findViewById(R.id.STT);

        btn_learning_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Learning_word.class);
                startActivity(intent);
            }
        });

        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Quiz.class);
                startActivity(intent);
            }
        });

        btn_typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Typing.class);
                startActivity(intent);
            }
        });

        btn_image_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImageDetection.class);
                startActivity(intent);
            }
        });

        btn_stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), STT.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - pressTime;

            if (0 <= intervalTime && finishTime >= intervalTime) {

                finish();
            } else {
                pressTime = tempTime;
                Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - pressTime;

        if (0 <= intervalTime && finishTime >= intervalTime)
        {
            finish();
        }
        else
        {
            pressTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }

}