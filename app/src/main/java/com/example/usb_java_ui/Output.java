package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Output extends AppCompatActivity {
    private GridView o_grid_output;
    private GridOutputAdapter o_gridOAdt;

    private ArrayList<int[]> resList;

    private TTS_Import tts_import;
    private ConnectedThread connectedThread;

    private String keyStr;
    private TextView txt_str;
    private ImageButton btn_nextW;
    private ImageButton btn_prevW;
    private Button btn_output;
    private ImageButton btn_nextO;
    private ImageButton btn_prevO;
    private Button btn_reSpeak;


    private String tableName;

    private DBManager dbManager;
    private int curIndexOfDB;
    private int maxIndexOfDB;

    private int send_idx;
    private int max_idx;
    private int rem;
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
        tts_import.ttsDestroy();
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
        txt_str = (TextView)findViewById(R.id.txt_str);

        Intent recv_intent = getIntent();
        keyStr = recv_intent.getStringExtra("keyStr");
        int keyType = recv_intent.getIntExtra("keyType",0);

        switch (keyType){
//            case 0: // 일반 단어
//                resList = Hangul2Braille.text(keyStr);
//                break;
            case 1: // 초성
//                resList = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("초성 : ");
                tableName = DBManager.TABLE_IC;
                break;
            case 2: // 종성
//                resList = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("종성 : ");
                tableName = DBManager.TABLE_FC;
                break;
            case 3: // 중성
//                resList = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("중성 : ");
                tableName = DBManager.TABLE_V;
                break;
            case 4: // 약자
//                resList = Hangul2BrailleSpecific.Learnig_Grammar(keyStr);
                txt_strType.setText("약자 : ");
                tableName = DBManager.TABLE_ABB;
                break;
            default:
//                resList = Hangul2Braille.text(keyStr);
                txt_strType.setText("");
                tableName = DBManager.TABLE_WORD;
                break;
        }
        dbManager = new DBManager();
        dbManager.checkDB(getApplicationContext());
        int[] indexesInDB = dbManager.getIndexesOfWord(tableName,keyStr);
        curIndexOfDB = indexesInDB[0];
        maxIndexOfDB = indexesInDB[1];
//        Log.d("index", String.valueOf(curIndexOfDB));
//        Log.d("index", String.valueOf(maxIndexOfDB));
        resList = StringToBraille(dbManager.getBrailleOfWord(tableName,keyStr));
//        Log.d("resList", String.valueOf(resList));
        btn_prevW = findViewById(R.id.btn_prevWord);
        btn_nextW = findViewById(R.id.btn_nextWord);

        if(curIndexOfDB == 1) {
            Log.d("index", String.valueOf(curIndexOfDB));
//            btn_prevW.setClickable(false);
            btn_prevW.setEnabled(false);
        }
        if(curIndexOfDB == maxIndexOfDB) {
//            btn_nextW.setClickable(false);
            btn_nextW.setEnabled(false);
        }
        btn_prevW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curIndexOfDB--;
                if(curIndexOfDB == 1) btn_prevW.setEnabled(false);
                if(curIndexOfDB == maxIndexOfDB-1) btn_nextW.setEnabled(true);
                String[] info = dbManager.getInfoOfIndex(tableName, curIndexOfDB);
                setWordBraille(info[0], StringToBraille(info[1]));
            }
        });

        btn_nextW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curIndexOfDB++;
                if(curIndexOfDB == 2) btn_prevW.setEnabled(true);
                if(curIndexOfDB == maxIndexOfDB) btn_nextW.setEnabled(false);
                String[] info = dbManager.getInfoOfIndex(tableName, curIndexOfDB);
                setWordBraille(info[0], StringToBraille(info[1]));
            }
        });

        o_grid_output = (GridView) findViewById(R.id.grdv_brailles);
        o_gridOAdt = new GridOutputAdapter(this);

        setWordBraille(keyStr, resList);

//        send_idx = 0;
//        max_idx = (resList.size()-1) / 3;
//        rem = resList.size() % 3 == 0 ? 3 : resList.size() % 3;

        btn_output = findViewById(R.id.btn_output);
        btn_nextO = findViewById(R.id.btn_nextOutput);
        btn_prevO = findViewById(R.id.btn_prevOutput);
        btn_reSpeak = findViewById(R.id.btn_listen);
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
                    Toast.makeText(Output.this, "첫 번째 출력입니다.", Toast.LENGTH_SHORT).show();
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

        btn_reSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts_import.speakOut(keyStr);
            }
        });

    }
    private ArrayList<int[]> StringToBraille(String str){
        String[] strArr = str.split("");
//        Log.d("strArr", Arrays.toString(strArr));
        String[] strArrFor = Arrays.copyOfRange(strArr, 2, strArr.length-1);
//        Log.d("strArrFor", Arrays.toString(strArrFor));
        ArrayList<int[]> braille = new ArrayList<>();
        int[] newB = {0,0,0,0,0,0};
        int indexB = 0;
        for (String one : strArrFor){

            if(Objects.equals(one, "1")){
                newB[indexB] = 1;
                indexB++;
            }
            else if (Objects.equals(one, "0")){
                indexB++;
            }
            else if (Objects.equals(one, "]")) {
                braille.add(newB);
                newB = new int[]{0, 0, 0, 0, 0, 0};
                indexB = 0;
            }
        }
//        Log.d("strArrBra", String.valueOf(braille));
        return braille;
    }

    private void setWordBraille(String word, ArrayList<int[]> brailles){
        keyStr = word;
        resList = brailles;
        txt_str.setText(word);
        o_gridOAdt = new GridOutputAdapter(this);
//        int[][]resList = arrayList.toArray(new int[arrayList.size()][6]);

        for (int[] BItem : brailles) {
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
        int numcol = brailles.size();
        Log.d("numCol", String.valueOf(numcol));
        int line = 1;

        if (numcol > 6) {

            numcol = 6;
            line = 1 + (brailles.size()-1)/6;

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
//        ViewGroup.LayoutParams o_param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(numcol < 6){
            o_param.width = 120*numcol;
        }
        else {
            o_param.width = 650;
        }

        o_param.height = 160 * line;
        o_grid_output.setLayoutParams(o_param);


        if (numcol == 0){
            btn_output.setClickable(false);
            btn_nextO.setClickable(false);
            btn_prevO.setClickable(false);
            btn_reSpeak.setClickable(false);

        }
        else{
            send_idx = 0;
            max_idx = (brailles.size()-1) / 3;
            rem = resList.size() % 3 == 0 ? 3 : resList.size() % 3;
        }

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
