package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class Quiz_readOutput extends AppCompatActivity {
    private TTS_Import tts_import;
    private ConnectedThread connectedThread;
    private String keyStr;
    private ArrayList<int[]> resList;
    private int[] randomIndex;
    private String[] AnswerArr;
    private int curIndex;


    private ImageButton btn_nextQz;
    private ImageButton btn_prevQz;
    private Button btn_output;
    private ImageButton btn_nextO;
    private ImageButton btn_prevO;
    private int send_idx;
    private int max_idx;
    private int rem;
    private Button btn_reSpeak;
    private ImageButton btn_STT;
    private ImageButton btn_submit;
    private EditText edt_myAnswer;

    private GridView qzr_grid_output;
    private GridOutputAdapter qzr_gridOAdt;
    private DBManager dbManager;

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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_read_output);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_nextQz = findViewById(R.id.btn_nextQz);
        btn_prevQz = findViewById(R.id.btn_prevQz);
        btn_prevO = findViewById(R.id.btn_prevOutput);
        btn_output = findViewById(R.id.btn_output);
        btn_nextO = findViewById(R.id.btn_nextOutput);
        btn_reSpeak = findViewById(R.id.btn_listen);
        btn_reSpeak.setEnabled(false);
        btn_STT = findViewById(R.id.btn_speakAnswer);
        btn_submit = findViewById(R.id.btn_submitAnswer);
        edt_myAnswer = findViewById(R.id.edt_myAnswer);

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE) ;
        edt_myAnswer.setBackground(getDrawable(R.drawable.border_fill_gray));

        dbManager = new DBManager();
        dbManager.checkDB(this);

        qzr_grid_output = (GridView) findViewById(R.id.grdv_qzrBrailles);
        String table_name = getIntent().getStringExtra("keyTableName");

        int[] indexes = dbManager.getIndexesOfWord(table_name,"더미");
        int maxIndex = indexes[1];
        randomIndex = generateShuffledArray(1, maxIndex);
        AnswerArr = new String[maxIndex];
        Arrays.fill(AnswerArr, "");
        curIndex = 0;
        String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
        keyStr = info[0];
        resList = StringToBraille(info[1]);
        setQzBraille(resList);

        btn_prevQz.setEnabled(false);
        btn_prevQz.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                curIndex--;
                if (curIndex == 0) btn_prevQz.setEnabled(false);
                if (curIndex == maxIndex-2) btn_nextQz.setEnabled(true);
                if (!Objects.equals(AnswerArr[curIndex], "")) {
                    edt_myAnswer.setText(AnswerArr[curIndex]);
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_green));
                    edt_myAnswer.setFocusable(false);
                    edt_myAnswer.setFocusableInTouchMode(false);
                    btn_reSpeak.setEnabled(true);
                } else {
                    edt_myAnswer.setText("");
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_fill_gray));
                    edt_myAnswer.setFocusable(true);
                    edt_myAnswer.setFocusableInTouchMode(true);
                    btn_reSpeak.setEnabled(false);
                }
                String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
                keyStr = info[0];
                resList = StringToBraille(info[1]);
                setQzBraille(resList);
            }
        });
        btn_nextQz.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                curIndex++;
                if (curIndex == 1) btn_prevQz.setEnabled(true);
                if (curIndex == maxIndex-1) btn_nextQz.setEnabled(false);
                if (!Objects.equals(AnswerArr[curIndex], "")) {
                    edt_myAnswer.setText(AnswerArr[curIndex]);
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_green));
                    edt_myAnswer.setFocusable(false);
                    edt_myAnswer.setFocusableInTouchMode(false);
                    btn_reSpeak.setEnabled(true);
                } else {
                    edt_myAnswer.setText("");
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_fill_gray));
                    edt_myAnswer.setFocusable(true);
                    edt_myAnswer.setFocusableInTouchMode(true);
                    btn_reSpeak.setEnabled(false);
                }
                String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
                keyStr = info[0];
                resList = StringToBraille(info[1]);
                setQzBraille(resList);
            }
        });

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
                    Toast.makeText(getApplicationContext(), "마지막 출력입니다.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "첫 번째 출력입니다.", Toast.LENGTH_SHORT).show();
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

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                String myAnswer = edt_myAnswer.getText().toString();
                if (myAnswer.equals(keyStr)){
                    AnswerArr[curIndex] = myAnswer;
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_green));
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    edt_myAnswer.setFocusable(false);
                    edt_myAnswer.setFocusableInTouchMode(false);
                    btn_reSpeak.setEnabled(true);
                }
                else {
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_red));
                }
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

    public void setQzBraille(ArrayList<int[]> braille){
        qzr_gridOAdt = new GridOutputAdapter(this);

        for (int[] BItem : braille) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@drawable/b");
            for (int dot : BItem){
                stringBuilder.append(dot);
            }
            String resName = stringBuilder.toString();

            String packName = this.getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packName);

            qzr_gridOAdt.setBItem(resId);
        }
        int numcol = braille.size();
        int line = 1;

        if (numcol > 6) {

            numcol = 6;
            line = 1 + (braille.size()-1)/6;

        }

        qzr_grid_output.setNumColumns(numcol);
        qzr_grid_output.setAdapter(qzr_gridOAdt);

        ViewGroup.LayoutParams o_param = qzr_grid_output.getLayoutParams();
        if(o_param == null) {
            o_param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        o_param.height = 160 * line;
        qzr_grid_output.setLayoutParams(o_param);

        send_idx = 0;
        max_idx = (braille.size()-1) / 3;
        rem = braille.size() % 3 == 0 ? 3 : braille.size() % 3;
    }

    public static int[] generateShuffledArray(int start, int end) {
        int[] array = new int[end - start + 1];
        for (int i = start; i <= end; i++) {
            array[i - start] = i;
        }

        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        return array;
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
