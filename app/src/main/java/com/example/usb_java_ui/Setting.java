package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

public class Setting extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


//        ImageButton setting = findViewById(R.id.Setting);
//        setting.setImageResource(0);




        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch swbtn_vm = findViewById(R.id.swbtn_voiceMode);
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



        Button saveSetting = (Button) findViewById(R.id.btn_saveSetting);
        saveSetting.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {

                SharedPreferences sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor spe_setting = sp_setting.edit();
                int rdbtn_id = radioGroup.getCheckedRadioButtonId();
                float voiceSpeedFloat;
                switch (rdbtn_id){
                    case R.id.rdbtn_speed_0_5:
                        voiceSpeedFloat = 0.5f;
                        break;
                    case R.id.rdbtn_speed_1:
                        voiceSpeedFloat = 1.0f;
                        break;
                    case R.id.rdbtn_speed_1_5:
                        voiceSpeedFloat = 1.5f;
                        break;
                    case R.id.rdbtn_speed_2:
                        voiceSpeedFloat = 2.0f;
                        break;
                    case R.id.rdbtn_speed_3:
                        voiceSpeedFloat = 3.0f;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + rdbtn_id);
                }
                spe_setting.putBoolean("voiceChecked",vm_checked[0]);
                spe_setting.putInt("voiceSpeed", rdbtn_id);
                spe_setting.putFloat("voiceSpeedFloat", voiceSpeedFloat);
                spe_setting.apply();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);

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
