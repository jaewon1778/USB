package com.example.usb_java_ui;

import static com.example.usb_java_ui.DBManager.TABLE_ABB;
import static com.example.usb_java_ui.DBManager.TABLE_FC;
import static com.example.usb_java_ui.DBManager.TABLE_IC;
import static com.example.usb_java_ui.DBManager.TABLE_NUM;
import static com.example.usb_java_ui.DBManager.TABLE_V;

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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Quiz_listenOutput extends AppCompatActivity {

    private TTS_Import tts_import;
    private ConnectedThread connectedThread;
    private GridView qzl_grid_output;
    private GridOutputAdapter qzl_gridOAdt;
    private String table_name;
    private int[] myBraille = new int[]{0, 0, 0, 0, 0, 0};
    private ArrayList<int[]> resList = new ArrayList<>();
    private ArrayList<int[]> BTresList = new ArrayList<>();
    private ArrayList<int[]> keyBraille = new ArrayList<>();
    private String keyStr;
    private int[] randomIndex;
    private int[][][] AnswerArr;
    private int curIndex;
    private NestedScrollView ns_qzl;

    private ImageButton btn_prevQz;
    private ImageButton btn_nextQz;
    private TextView txt_qzType;
    private TextView txt_qzStr;

    private Button btn_bluetoothInput;
    private boolean running;
    private Button btn_reSpeak;



    private ImageButton point1;
    private ImageButton point2;
    private ImageButton point3;
    private ImageButton point4;
    private ImageButton point5;
    private ImageButton point6;
    private ImageButton[] points;


    private ImageButton addA;
    private ImageButton backA;
    private ImageButton removeA;
    private ImageButton submitA;

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
            btn_bluetoothInput.setEnabled(true);
        } else {
            btn_bluetoothInput.setEnabled(false);
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
        setContentView(R.layout.quiz_listen_output);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        ns_qzl = findViewById(R.id.ns_qzl);
        qzl_grid_output = findViewById(R.id.grdv_qzlBrailles);
        updateAnswer();

        btn_prevQz = findViewById(R.id.btn_prevQz);
        btn_prevQz.setEnabled(false);
        btn_nextQz = findViewById(R.id.btn_nextQz);

        txt_qzType = findViewById(R.id.txt_qzlStrType);
        txt_qzStr = findViewById(R.id.txt_qzlStr);

        btn_bluetoothInput = findViewById(R.id.btn_bluetoothInput);
        btn_reSpeak = findViewById(R.id.btn_listen);

        point1 = findViewById(R.id.imgbtn_qzlPoint1);
        point2 = findViewById(R.id.imgbtn_qzlPoint2);
        point3 = findViewById(R.id.imgbtn_qzlPoint3);
        point4 = findViewById(R.id.imgbtn_qzlPoint4);
        point5 = findViewById(R.id.imgbtn_qzlPoint5);
        point6 = findViewById(R.id.imgbtn_qzlPoint6);
        points = new ImageButton[]{point1, point2, point3, point4, point5, point6};

        addA = findViewById(R.id.btn_addAnswer);
        backA = findViewById(R.id.btn_backspaceAnswer);
        removeA = findViewById(R.id.btn_removeAnswer);
        submitA = findViewById(R.id.btn_submitAnswer);

        dbManager = new DBManager();
        dbManager.checkDB(this);

        table_name = getIntent().getStringExtra("keyTableName");

        switch (table_name){
            case TABLE_IC:
                txt_qzType.setText("초성 : ");
                break;
            case TABLE_V:
                txt_qzType.setText("중성 : ");
                break;
            case TABLE_FC:
                txt_qzType.setText("종성 : ");
                break;
            case TABLE_NUM:
                txt_qzType.setText("숫자 : ");
                break;
            case TABLE_ABB:
                txt_qzType.setText("약자 : ");
                break;
            default:
                txt_qzType.setText("");
                break;
        }

        int[] indexes = dbManager.getIndexesOfWord(table_name,"더미");
        int maxIndex = indexes[1];
        randomIndex = generateShuffledArray(1, maxIndex);
        AnswerArr = new int[maxIndex][][];
        Arrays.fill(AnswerArr, new int[][]{});
        curIndex = 0;
        String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
        keyStr = info[0];
        txt_qzStr.setText(keyStr);
        keyBraille = StringToBraille(info[1]);


        btn_prevQz.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                curIndex--;
                if (curIndex == 0) btn_prevQz.setEnabled(false);
                if (curIndex == maxIndex-2) btn_nextQz.setEnabled(true);
                if (!Arrays.deepEquals(AnswerArr[curIndex], new int[][]{})) {
                    setButtonEnabled(false);
                    btn_bluetoothInput.setEnabled(false);
                    resList = new ArrayList<int[]>(Arrays.asList(AnswerArr[curIndex]));
                    updateAnswer();
                    qzl_grid_output.setBackground(getDrawable(R.drawable.border_green));
                } else {
                    setButtonEnabled(true);
                    SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
                    String deviceN = sp_bluetooth.getString("DN", "isNot");

                    if(!Objects.equals(deviceN, "isNot")) {
                        connectedThread = BluetoothConnection.connectedThread;
                        btn_bluetoothInput.setEnabled(true);
                    } else {
                        btn_bluetoothInput.setEnabled(false);
                    }
                    qzl_grid_output.setBackground(new ColorDrawable(Color.TRANSPARENT));
                    resList = new ArrayList<>();
                    updateAnswer();
                }
                setKeyValue();

            }
        });


        btn_nextQz.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                curIndex++;
                if (curIndex == 1) btn_prevQz.setEnabled(true);
                if (curIndex == maxIndex-1) btn_nextQz.setEnabled(false);
                if (!Arrays.deepEquals(AnswerArr[curIndex], new int[][]{})) {
                    setButtonEnabled(false);
                    btn_bluetoothInput.setEnabled(false);
                    resList = new ArrayList<int[]>(Arrays.asList(AnswerArr[curIndex]));                    updateAnswer();
                    qzl_grid_output.setBackground(getDrawable(R.drawable.border_green));
                } else {
                    setButtonEnabled(true);
                    SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
                    String deviceN = sp_bluetooth.getString("DN", "isNot");

                    if(!Objects.equals(deviceN, "isNot")) {
                        connectedThread = BluetoothConnection.connectedThread;
                        btn_bluetoothInput.setEnabled(true);
                    } else {
                        btn_bluetoothInput.setEnabled(false);
                    }
                    qzl_grid_output.setBackground(new ColorDrawable(Color.TRANSPARENT));
                    resList = new ArrayList<>();
                    updateAnswer();
                }
                setKeyValue();

            }
        });


        btn_bluetoothInput.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Fill in the code here
//                ArrayList<int[]> answer = Hangul2Braille.text("사과");
//                resList.clear();
                running = true;
                while (running){
                    quizresult();
                    updateAnswer();

                }


                // Fill in the code here



            }
        });


        btn_reSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts_import.speakOut(keyStr);
            }
        });


        addA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resList.add(myBraille);
                updateAnswer();
                resetBraille(points);
                ns_qzl.fullScroll(NestedScrollView.FOCUS_DOWN);
            }
        });
        backA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resList.size() != 0) {
                    resList.remove(resList.size() - 1);
                    updateAnswer();
                }
            }
        });
        removeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resList.clear();
                updateAnswer();
            }
        });
        submitA.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"제출버튼입니다.",Toast.LENGTH_SHORT).show();
                if(isItSameBraille(keyBraille, resList)){
                    setButtonEnabled(false);
                    btn_bluetoothInput.setEnabled(false);
                    AnswerArr[curIndex] = keyBraille.toArray(new int[0][]);
                    qzl_grid_output.setBackground(getDrawable(R.drawable.border_green));
                } else {
                    qzl_grid_output.setBackground(getDrawable(R.drawable.border_red));
//                    resList.clear();
                }

            }
        });

        point1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myBraille[0]==1){
                    myBraille[0] = 0;
                    point1.setImageResource(R.drawable.p0);
                }
                else {
                    myBraille[0] = 1;
                    point1.setImageResource(R.drawable.p1);
                }
            }
        });
        point2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myBraille[1]==1){
                    myBraille[1] = 0;
                    point2.setImageResource(R.drawable.p0);
                }
                else {
                    myBraille[1] = 1;
                    point2.setImageResource(R.drawable.p1);
                }
            }
        });
        point3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myBraille[2]==1){
                    myBraille[2] = 0;
                    point3.setImageResource(R.drawable.p0);
                }
                else {
                    myBraille[2] = 1;
                    point3.setImageResource(R.drawable.p1);
                }
            }
        });
        point4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myBraille[3]==1){
                    myBraille[3] = 0;
                    point4.setImageResource(R.drawable.p0);
                }
                else {
                    myBraille[3] = 1;
                    point4.setImageResource(R.drawable.p1);
                }
            }
        });
        point5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myBraille[4]==1){
                    myBraille[4] = 0;
                    point5.setImageResource(R.drawable.p0);
                }
                else {
                    myBraille[4] = 1;
                    point5.setImageResource(R.drawable.p1);
                }
            }
        });
        point6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myBraille[5]==1){
                    myBraille[5] = 0;
                    point6.setImageResource(R.drawable.p0);
                }
                else {
                    myBraille[5] = 1;
                    point6.setImageResource(R.drawable.p1);
                }
            }
        });


    }
    private void resetBraille(ImageButton[] points){
        myBraille = new int[]{0, 0, 0, 0, 0, 0};
        points[0].setImageResource(R.drawable.p0);
        points[1].setImageResource(R.drawable.p0);
        points[2].setImageResource(R.drawable.p0);
        points[3].setImageResource(R.drawable.p0);
        points[4].setImageResource(R.drawable.p0);
        points[5].setImageResource(R.drawable.p0);

    }

    public void updateAnswer(){
        qzl_gridOAdt = new GridOutputAdapter(this);

        for (int[] BItem : resList) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@drawable/b");
            for (int dot : BItem){
                stringBuilder.append(dot);
            }
            String resName = stringBuilder.toString();

            String packName = this.getPackageName();
            int resId = this.getResources().getIdentifier(resName, "drawable", packName);


            qzl_gridOAdt.setBItem(resId);
        }
        int numcol = resList.size();
        int line = 2;

        if (numcol > 12) {

            line = 1 + (numcol-1)/6;

        }

        qzl_grid_output.setAdapter(qzl_gridOAdt);

        ViewGroup.LayoutParams o_param = qzl_grid_output.getLayoutParams();
        if(o_param == null) {
            o_param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        o_param.height = 160 * line;
        qzl_grid_output.setLayoutParams(o_param);

    }

    private Boolean isItSameBraille(ArrayList<int[]> key, ArrayList<int[]> myAns){
        if(key.size() != myAns.size()) return false;
        for (int i = 0; i< key.size(); i++){
            if(!Arrays.equals(key.get(i),myAns.get(i))){
                return false;
            }
        }
        return true;
    }

    private void setButtonEnabled(Boolean bool){
        for (ImageButton p : points){
            p.setEnabled(bool);
        }
        addA.setEnabled(bool);
        backA.setEnabled(bool);
        removeA.setEnabled(bool);
        submitA.setEnabled(bool);

    }

    private void setKeyValue(){
        switch (table_name){
            case TABLE_IC:
                txt_qzType.setText("초성 : ");
                break;
            case TABLE_V:
                txt_qzType.setText("중성 : ");
                break;
            case TABLE_FC:
                txt_qzType.setText("종성 : ");
                break;
            case TABLE_ABB:
                txt_qzType.setText("약자 : ");
                break;
            default:
                txt_qzType.setText("");
                break;
        }

        String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
        keyStr = info[0];
        txt_qzStr.setText(keyStr);
        keyBraille = StringToBraille(info[1]);
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

    public void quizresult() {

        while(true) {
//            updateAnswer();
            String KeypadInput = connectedThread.KeypadInput();
            if(KeypadInput.length() == 1) {
                // 1개 지우기: $
                if(KeypadInput.charAt(0) == '$') {
                    if(resList.size() != 0) {
                        resList.remove(resList.size() - 1);
                        Log.d("quiz array remove", Arrays.deepToString(resList.toArray()));
//                        btn_bluetoothInput.callOnClick();
                        return;
//                        updateAnswer();
                    }
                }
                // 전체 지우기: #
                else if(KeypadInput.charAt(0) == '#') {
                    if(resList.size() != 0) {
                        resList.clear();
                        Log.d("quiz array clear", Arrays.deepToString(resList.toArray()));
//                        btn_bluetoothInput.callOnClick();
                        return;
//                        updateAnswer();
                    }
                }
                // 제출하기: @
                else if(KeypadInput.charAt(0) == '@') {
                    Log.d("quiz array submit", Arrays.deepToString(resList.toArray()));
                    running = false;
                    submitA.callOnClick();
                    return;
                }
            }

            // 1개 올리기: *
            else if(KeypadInput.length() == 6){
                int[] one = {0, 0, 0, 0, 0, 0};
                for(int i = 0; i < 6; i++) {
                    if(KeypadInput.charAt(i) == '1') {
                        one[i] = 1;
                    }
                }
                resList.add(one);
                Log.d("quiz array add", Arrays.deepToString(resList.toArray()));
//                btn_bluetoothInput.callOnClick();
                return;
//                updateAnswer();
            }

            //여기서 quizarray 이용해서 현재 입력된 점자 출력
            //coding here
//            Log.d("quiz array", Arrays.deepToString(resList.toArray()));
        }
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
