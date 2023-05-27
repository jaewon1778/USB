package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MyAppActivity extends AppCompatActivity {
    private SharedPreferences sp_setting;
    private TTS_Import tts_import;
    private Touchpad touchpad;
    private Toolbar toolbar;
    private Button tool_help;
    private Button tool_bluetooth;
    private Button tool_setting;

    protected SensorManager mSensorManager;
    protected Sensor mAccelerometer;
    protected ShakeDetector mShakeDetector;
    private Vibrator vibrator;

    public TTS_Import getTTS_import() {
        return tts_import;
    }

    public Touchpad getTouchpad() {
        return this.touchpad;
    }

    protected void VoiceModeOn(){

        ObjectTree OT_toolRoot = new ObjectTree().rootObject();
        Button[] toolViews = new Button[]{tool_help, tool_bluetooth, tool_setting};
        OT_toolRoot.addChildViewArr(toolViews);

        for (int i = 0; i<toolViews.length; i++){
            int finalI = i;
            toolViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onOptionsItemSelected(toolbar.getMenu().getItem(finalI));
                }
            });
        }
//        touchpad.setCurObj(OT_root.getChildObjectOfIndex(0));
        touchpad.setToolRootObjCurObj(OT_toolRoot);
//        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();
        touchpad.show();

    }
    @Override
    protected void onResume() {
        super.onResume();

        tts_import = new TTS_Import();
        tts_import.set_tts(new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts_import.onInit(i);
            }
        }));
        tts_import.setSpeed(sp_setting.getFloat("voiceSpeedFloat", 1.0f));


        touchpad = new Touchpad(this);
        touchpad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        touchpad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        touchpad.setCanceledOnTouchOutside(false);
        touchpad.setCancelable(false);

        if (sp_setting.getBoolean("voiceChecked", false)) {
            VoiceModeOn();

        }
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);

    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
        if (touchpad.isShowing()){
            touchpad.getRoot().deleteAllChildFocusable();
            touchpad.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts_import.ttsDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tool_help = findViewById(R.id.v_help);
        tool_bluetooth = findViewById(R.id.v_bluetooth);
        tool_setting = findViewById(R.id.v_setting);

        sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor spe_setting = sp_setting.edit();

        vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                if (mShakeDetector.getmShakeCount() >= 2){
                    vibrator.vibrate(VibrationEffect.createWaveform(new long[]{300,150, 200}, new int[]{80,0,80}, -1));
                    if (touchpad.isShowing()){
                        touchpad.getRoot().deleteAllChildFocusable();
                        touchpad.dismiss();
                        tts_import.speakOut("기본모드로 변경되었습니다");
                        spe_setting.putBoolean("voiceChecked", false);
                    }
                    else {
                        VoiceModeOn();
                        tts_import.speakOut("보이스모드로 변경되었습니다");
                        spe_setting.putBoolean("voiceChecked", true);
                    }
                    spe_setting.apply();
                }
            }
        });

    }

    public String braille2String(ArrayList<int[]> newBraille){
        StringBuilder resString = new StringBuilder();
        resString.append("[");
        for (int[] B : newBraille){
            resString.append("[");
            for (int i : B){
                resString.append(i);
            }
            resString.append("]");
        }
        resString.append("]");

        return resString.toString();
    }

    public SharedPreferences getSp_setting() {
        return sp_setting;
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
