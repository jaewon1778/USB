package com.example.usb_java_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.usb_java_ui.TTS_Import;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final long finishTime = 1000;
    private long pressTime = 0;
    private TTS_Import tts_import;

    private ChoiceVoiceMode CVMDialog;

    private ConnectedThread connectedThread;
    private BluetoothConnection mBC;
    private Button btn_learning_word;
    private Button btn_quiz;
    private Button btn_typing;
    private Button btn_image_detection;
    private Button btn_stt;

    @Override
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
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        tts_import.onDestroy();
        tts_import.ttsDestroy();
        try {
            BluetoothConnection.btSocket.close();
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //TTS TEST
//        TextView txt_study = (TextView) findViewById(R.id.txt_study);
//        txt_study.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                tts_import.speakOut((String) txt_study.getText());
//            }
//        });
        //TTS TEST



        SharedPreferences sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
        boolean is_voiceChecked = sp_setting.getBoolean("voiceChecked", false);

        if (!is_voiceChecked){
            CVMDialog = new ChoiceVoiceMode(this);
//            CVMDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            CVMDialog.setCanceledOnTouchOutside(false);
            CVMDialog.setCancelable(false);
            CVMDialog.show();
        }

        // Blue
        TextView txt_study = (TextView) findViewById(R.id.txt_study);



//        txt_study.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String braille = "hello world from jaewon;";
////                if(connectedThread!=null){ connectedThread.write(braille); }
////                tts_import.speakOut((String) txt_study.getText());
//            }
//        });



        // Blue


        btn_learning_word = (Button) findViewById(R.id.learning_word);
        btn_quiz = (Button) findViewById(R.id.quiz);
        btn_typing = (Button) findViewById(R.id.typing);
        btn_image_detection = (Button) findViewById(R.id.image_detection);
        btn_stt = (Button) findViewById(R.id.STT);

        btn_learning_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String braille = "hello world from jaewon;";
//                if(connectedThread!=null){ connectedThread.write(braille); }

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
                long tempTime = System.currentTimeMillis();
                long intervalTime = tempTime - pressTime;

                if (0 <= intervalTime && finishTime >= intervalTime)
                {
                    SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
                    SharedPreferences.Editor spe_bluetooth = sp_bluetooth.edit();
                    spe_bluetooth.putString("DN", "isNot");
                    spe_bluetooth.apply();
                    finish();
                }
                else
                {
                    pressTime = tempTime;
                    Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
                }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - pressTime;

        if (0 <= intervalTime && finishTime >= intervalTime)
        {
            SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
            SharedPreferences.Editor spe_bluetooth = sp_bluetooth.edit();
            spe_bluetooth.putString("DN", "isNot");
            spe_bluetooth.apply();
            finish();
        }
        else
        {
            pressTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }

}