package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Output extends MyAppOutputActivity {
    private GridView o_grid_output;
    private GridOutputAdapter o_gridOAdt;

    private ArrayList<int[]> resList;

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


    protected void VoiceModeOn(){
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();

        // make objTree

        btn_prevW.setContentDescription("이전 단어");
        btn_nextW.setContentDescription("다음 단어");
        btn_prevO.setContentDescription("이전 출력");
        btn_nextO.setContentDescription("다음 출력");
        OT_root.addChildViewArr(new View[]{txt_str,btn_output,btn_nextO,btn_prevO});
        OT_root.getChildObjectOfIndex(0).addChildViewArr(new View[]{btn_nextW,btn_prevW});
        // make objTree
        MyFocusManager.viewArrFocusL(this, new View[]{txt_str,btn_output,btn_nextO,btn_prevO,btn_nextW,btn_prevW},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.output);
        super.onCreate(savedInstanceState);

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
            case 5: // 숫자
                txt_strType.setText("숫자 : ");
                tableName = DBManager.TABLE_NUM;
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
        resList = StringToBraille(dbManager.getBrailleOfWord(tableName,keyStr));
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
        btn_output = findViewById(R.id.btn_output);
        btn_nextO = findViewById(R.id.btn_nextOutput);
        btn_prevO = findViewById(R.id.btn_prevOutput);
        btn_reSpeak = findViewById(R.id.btn_listen);

        setWordBraille(keyStr, resList);

        btn_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = 3;
                send_idx = 0;
                if(max_idx == 0) {
                    cnt = rem;
                }
                sendBraille(resList, send_idx, cnt);
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
                sendBraille(resList, send_idx, cnt);
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
                sendBraille(resList, send_idx, cnt);
            }
        });

        btn_reSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTTS_import().speakOut(keyStr);
            }
        });

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

}
