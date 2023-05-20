package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.PrimitiveIterator;

public class Output extends AppCompatActivity {
    private GridView o_grid_output;
    private GridOutputAdapter o_gridOAdt;

    private ArrayList<int[]> resList;

    private TTS_Import tts_import;
    private ConnectedThread connectedThread;

    private Button btn_output;
    private ImageButton btn_nextO;
    private ImageButton btn_prevO;
    private Button btn_reSpeak;

    private int send_idx;
    private int max_idx;
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
    protected void onDestroy() {
        super.onDestroy();
        sendbraille(0, 0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txt_strType = (TextView) findViewById(R.id.txt_strType);
        TextView txt_str = (TextView)findViewById(R.id.txt_str);

        Intent recv_intent = getIntent();
        String keyStr = recv_intent.getStringExtra("keyStr");
        int keyType = recv_intent.getIntExtra("keyType",0);

        switch (keyType){
//            case 0: // 일반 단어
//                resList = Hangul2Braille.text(keyStr);
//                break;
            case 1: // 초성
                resList = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("초성 : ");
                break;
            case 2: // 종성
                resList = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("종성 : ");
                break;
            case 3: // 중성
                resList = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("중성 : ");
                break;
            case 4: // 약자
                resList = Hangul2BrailleSpecific.Learnig_Grammar(keyStr);
                txt_strType.setText("약자 : ");
                break;
            default:
                resList = Hangul2Braille.text(keyStr);
                txt_strType.setText("");
                break;
        }

        txt_str.setText(keyStr);
//        ArrayList<int[]> resList = Hangul2Braille.text(keyStr);

        o_grid_output = (GridView) findViewById(R.id.grdv_brailles);
        o_gridOAdt = new GridOutputAdapter(this);


//        int[][]resList = arrayList.toArray(new int[arrayList.size()][6]);

        for (int[] BItem : resList) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@drawable/b");
            for (int dot : BItem){
                stringBuilder.append(dot);
            }
            String resName = stringBuilder.toString();

            String packName = this.getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packName);


            o_gridOAdt.setBItem(resId);
        }
        int numcol = resList.size();
        int line = 1;

        if (numcol > 6) {

            numcol = 6;
            line = 1 + (resList.size()-1)/6;

        }

        if (line >= 4){
            line = 4;
        }

        o_grid_output.setNumColumns(numcol);
        o_grid_output.setAdapter(o_gridOAdt);

        ViewGroup.LayoutParams o_param = o_grid_output.getLayoutParams();
        if(o_param == null) {
            o_param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if(numcol < 6){
            o_param.width = 120*numcol;
        }

        o_param.height = 160 * line;
        o_grid_output.setLayoutParams(o_param);


        btn_output = findViewById(R.id.btn_output);
        btn_nextO = findViewById(R.id.btn_nextOutput);
        btn_prevO = findViewById(R.id.btn_prevOutput);
        btn_reSpeak = findViewById(R.id.btn_listen);

        if (numcol == 0){
            btn_output.setClickable(false);
            btn_nextO.setClickable(false);
            btn_prevO.setClickable(false);
            btn_reSpeak.setClickable(false);

            return;
        }

        send_idx = 0;
        max_idx = (resList.size()-1) / 3;
        int rem = resList.size() % 3 == 0 ? 3 : resList.size() % 3;

        btn_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = 3;
                send_idx = 0;
                if(max_idx == 0) {
                    cnt = rem;
                }
                sendbraille(send_idx, cnt);
            }
        });

        btn_nextO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(send_idx == max_idx) {
                    Toast.makeText(Output.this, "마지막 출력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                send_idx++;
                int cnt = 3;
                if(send_idx == max_idx){
                    cnt = rem;
                }
                sendbraille(send_idx, cnt);
            }
        });

        btn_prevO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(send_idx == 0) {
                    Toast.makeText(Output.this, "마지막 출력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                send_idx--;
                int cnt = 3;
                if(send_idx+max_idx == 0){
                    cnt = rem;
                }
                sendbraille(send_idx, cnt);
            }
        });

    }


    public void sendbraille(int send_idx, int cnt) {
        ArrayList<int[]> brailleList = new ArrayList<>();

        for(int j = 0; j < cnt; j++) {
            brailleList.add(resList.get(send_idx*3 + j));
        }

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
