package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class Setting extends MyAppActivity {

    TextView txt_pt, txt_voiceMode, txt_speed;
    Switch swbtn_vm;
    RadioButton rdb_05, rdb_1,rdb_15,rdb_2,rdb_3;
    Button saveSetting;
    protected void VoiceModeOn(){
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();

        // make objTree
        ObjectTree OT_pt = new ObjectTree().initObject(txt_pt);
        ObjectTree OT_vm = new ObjectTree().initObject(txt_voiceMode);
        ObjectTree OT_speed = new ObjectTree().initObject(txt_speed);
        OT_vm.addChild(new ObjectTree().initObject(swbtn_vm));
        OT_speed.addChildViewArr(new View[]{rdb_05,rdb_1,rdb_15,rdb_2,rdb_3});
        OT_pt.addChildObjectArr(new ObjectTree[]{OT_vm,OT_speed});
        OT_root.addChild(OT_pt);
        OT_root.addChild(new ObjectTree().initObject(saveSetting));
        // make objTree
        MyFocusManager.viewArrFocusL(this, new View[]{txt_pt,txt_voiceMode,txt_speed,swbtn_vm,rdb_05,rdb_1,rdb_15,rdb_2,rdb_3,saveSetting},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.setting);
        super.onCreate(savedInstanceState);

        txt_pt = findViewById(R.id.pageTitle);
        txt_voiceMode = findViewById(R.id.txt_voiceMode);
        txt_speed =findViewById(R.id.txt_speedName);
        rdb_05 = findViewById(R.id.rdbtn_speed_0_5);
        rdb_1 = findViewById(R.id.rdbtn_speed_1);
        rdb_15 = findViewById(R.id.rdbtn_speed_1_5);
        rdb_2 = findViewById(R.id.rdbtn_speed_2);
        rdb_3 = findViewById(R.id.rdbtn_speed_3);
        saveSetting = findViewById(R.id.btn_saveSetting);


        swbtn_vm = findViewById(R.id.swbtn_voiceMode);

        swbtn_vm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swbtn_vm.setChecked(!swbtn_vm.isChecked());
            }
        });
        RadioButton[] rdbs = new RadioButton[]{rdb_05, rdb_1, rdb_15, rdb_2, rdb_3};
        for (RadioButton rdb : rdbs){
            rdb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rdb.setChecked(true);
                    String speed = "1.0";
                    float vos = 1.0f;
                    if (rdb_05.equals(rdb)) {
                        speed = "0.5";
                        vos = 0.5f;
                    } else if (rdb_1.equals(rdb)) {
                        speed = "1.0";
                    } else if (rdb_15.equals(rdb)) {
                        speed = "1.5";
                        vos = 1.5f;
                    } else if (rdb_2.equals(rdb)) {
                        speed = "2.0";
                        vos = 2.0f;
                    } else if (rdb_3.equals(rdb)) {
                        speed = "3.0";
                        vos = 3.0f;
                    }
                    getTTS_import().setSpeed(vos);
                    getTTS_import().speakOut("말하기 속도 "+speed+" 배속 입니다.");
                }
            });
        }

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdg_speed);
        int default_rdbtn_id = R.id.rdbtn_speed_1;

        SeekBar skbr_vol = findViewById(R.id.skbr_volume);

        SharedPreferences prev_settings = getSharedPreferences("setting", MODE_PRIVATE);
        boolean is_voiceChecked = prev_settings.getBoolean("voiceChecked", false);
        swbtn_vm.setChecked(is_voiceChecked);
        skbr_vol.setEnabled(is_voiceChecked);
        for(int i=0; i<5;i++) {
            radioGroup.getChildAt(i).setEnabled(is_voiceChecked);
        }
        RadioButton radioButton = radioGroup.findViewById(prev_settings.getInt("voiceSpeed", default_rdbtn_id));
        radioButton.setChecked(true);

        final boolean[] vm_checked = {is_voiceChecked};
        swbtn_vm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vm_checked[0] = isChecked;
                skbr_vol.setEnabled(isChecked);
                for(int i=0; i<5;i++) {
                    radioGroup.getChildAt(i).setEnabled(isChecked);
                }
            }
        });

        AudioManager m_audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVol = m_audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVol = m_audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        skbr_vol.setMax(maxVol);
        skbr_vol.setProgress(curVol);
        TextView txt_vol = findViewById(R.id.txt_volume);
        txt_vol.setText(100*curVol/maxVol + "%");
        skbr_vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_vol.setText(100*progress/maxVol + "%");
                m_audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        saveSetting.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {

                SharedPreferences sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor spe_setting = sp_setting.edit();
                int rdbtn_id = radioGroup.getCheckedRadioButtonId();
                float voiceSpeedFloat;
//                String speed = "1.0";
                switch (rdbtn_id){
                    case R.id.rdbtn_speed_0_5:
                        voiceSpeedFloat = 0.5f;
//                        speed = "0.5";
                        break;
                    case R.id.rdbtn_speed_1:
                        voiceSpeedFloat = 1.0f;
//                        speed = "1.0";
                        break;
                    case R.id.rdbtn_speed_1_5:
                        voiceSpeedFloat = 1.5f;
//                        speed = "1.5";
                        break;
                    case R.id.rdbtn_speed_2:
                        voiceSpeedFloat = 2.0f;
//                        speed = "2.0";
                        break;
                    case R.id.rdbtn_speed_3:
                        voiceSpeedFloat = 3.0f;
//                        speed = "3.0";
                        break;
                    default:
                        rdbtn_id = R.id.rdbtn_speed_1;
                        voiceSpeedFloat = 1.0f;
//                        speed = "1.0";
//                        throw new IllegalStateException("Unexpected value: " + rdbtn_id);
                }
//                getTTS_import().speakOut("말하기 속도 "+speed+" 배속 입니다.");
                spe_setting.putBoolean("voiceChecked",vm_checked[0]);
                spe_setting.putInt("voiceSpeed", rdbtn_id);
                spe_setting.putFloat("voiceSpeedFloat", voiceSpeedFloat);
                spe_setting.apply();
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        AudioManager m_audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        SeekBar skbr_vol = findViewById(R.id.skbr_volume);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP :
                m_audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_SHOW_UI);

                skbr_vol.setProgress(skbr_vol.getProgress()+1);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                m_audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_SHOW_UI);
                skbr_vol.setProgress(skbr_vol.getProgress()-1);
                return true;
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
        }

        return false;
    }

}
