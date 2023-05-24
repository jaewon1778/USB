package com.example.usb_java_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final long finishTime = 1000;
    private long pressTime = 0;
    private TTS_Import tts_import;

    private ChoiceVoiceMode CVMDialog;
    private Touchpad touchpad;

    private ConnectedThread connectedThread;
    private BluetoothConnection mBC;

    private TextView txt_study;
    private TextView txt_addWord;
    private Button btn_learning_word;
    private Button btn_quiz;
    private Button btn_typing;
    private Button btn_image_detection;
    private Button btn_stt;
    private ObjectTree OT_root;

    private Toolbar toolbar;
    private View tool_help;
    private View tool_bluetooth;
    private View tool_setting;
    private ObjectTree OT_toolRoot;

    private void VoiceModeOn(){
        OT_root = new ObjectTree().rootObject();
        ObjectTree OT_study = new ObjectTree().initObject(txt_study);
        ObjectTree OT_addWord = new ObjectTree().initObject(txt_addWord);
        OT_study.addChildViewArr(new View[]{btn_learning_word, btn_quiz});
        OT_addWord.addChildViewArr(new View[]{btn_typing, btn_image_detection, btn_stt});
        OT_root.addChildObjectArr(new ObjectTree[]{OT_study,OT_addWord});

        myFocusManager.txtFocusL(this, new TextView[]{txt_study, txt_addWord},tts_import);
//        myFocusManager.txtFocusL(this,txt_addWord,tts_import);

        OT_toolRoot = new ObjectTree().rootObject();
        OT_toolRoot.addChildViewArr(new View[]{tool_help, tool_bluetooth, tool_setting});

        tool_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(toolbar.getMenu().getItem(0));
            }
        });
        tool_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(toolbar.getMenu().getItem(1));
            }
        });
        tool_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(toolbar.getMenu().getItem(2));
            }
        });


        touchpad.setCurObj(OT_root.getChildObjectOfIndex(0));
        touchpad.setToolRootObjCurObj(OT_toolRoot);
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();
        touchpad.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
        tts_import = new TTS_Import();
        tts_import.set_tts(new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts_import.onInit(i);
            }
        }));
        tts_import.setSpeed(sp_setting.getFloat("voiceSpeedFloat",1.0f));


        touchpad = new Touchpad(this);
        touchpad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        touchpad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        touchpad.setCanceledOnTouchOutside(false);
        touchpad.setCancelable(false);

//        if(!sp_setting.getBoolean("voiceChecked", false)){
//            CVMDialog = new ChoiceVoiceMode(this);
////            CVMDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            CVMDialog.setCanceledOnTouchOutside(false);
////            CVMDialog.setCancelable(false);
//            CVMDialog.show();
//        }

        if(sp_setting.getBoolean("voiceChecked", false)){
            VoiceModeOn();

        }


        SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
        String deviceN = sp_bluetooth.getString("DN", "isNot");

        if(!Objects.equals(deviceN, "isNot")) {
            connectedThread = BluetoothConnection.connectedThread;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (touchpad.isShowing()){
            OT_root.deleteAllChildFocusable();
            touchpad.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        tts_import.onDestroy();
        tts_import.ttsDestroy();
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.deleteDatabase("USB_DB");

        DBManager mDBM = new DBManager();
        mDBM.InitDB(getApplicationContext());


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tool_help = findViewById(R.id.v_help);
        tool_bluetooth = findViewById(R.id.v_bluetooth);
        tool_setting = findViewById(R.id.v_setting);


        //DB TEST
        txt_study = (TextView) findViewById(R.id.txt_study);
        txt_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),apple.class);
                startActivity(intent);
            }
        });


        //DB TEST
        txt_addWord = findViewById(R.id.txt_addWord);
//        txt_addWord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                toolbar.getChildAt(2).callOnClick();
////                int n = toolbar.getChildCount();
////                Toast.makeText(MainActivity.this, ""+n, Toast.LENGTH_SHORT).show();
//                onOptionsItemSelected(toolbar.getMenu().getItem(1));
//
//            }
//        });

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



//        SharedPreferences sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
//        boolean is_voiceChecked = sp_setting.getBoolean("voiceChecked", false);
//
//        if (!is_voiceChecked){
//            CVMDialog = new ChoiceVoiceMode(this);
////            CVMDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            CVMDialog.setCanceledOnTouchOutside(false);
////            CVMDialog.setCancelable(false);
//            CVMDialog.show();
//        }

        // Blue
//        TextView txt_study = (TextView) findViewById(R.id.txt_study);
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
//        super.onBackPressed();
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