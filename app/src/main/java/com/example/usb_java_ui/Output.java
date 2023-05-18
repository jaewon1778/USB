package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

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
        ArrayList<int[]> exmlist;
        switch (keyType){
//            case 0: // 일반 단어
//                exmlist = Hangul2Braille.text(keyStr);
//                break;
            case 1: // 초성
                exmlist = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("초성 : ");
                break;
            case 2: // 종성
                exmlist = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("종성 : ");
                break;
            case 3: // 중성
                exmlist = Hangul2BrailleSpecific.Learnig_hangul(keyStr);
                txt_strType.setText("중성 : ");
                break;
            case 4: // 약자
                exmlist = Hangul2BrailleSpecific.Learnig_Grammar(keyStr);
                txt_strType.setText("약자 : ");
                break;
            default:
                exmlist = Hangul2Braille.text(keyStr);
                txt_strType.setText("");
                break;
        }

        txt_str.setText(keyStr);
//        ArrayList<int[]> exmlist = Hangul2Braille.text(keyStr);

        o_grid_output = (GridView) findViewById(R.id.grdv_brailles);
        o_gridOAdt = new GridOutputAdapter(this);


//        int[][]exmlist = arrayList.toArray(new int[arrayList.size()][6]);

        for (int[] BItem : exmlist) {
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
        int numcol = exmlist.size();
        int line = 1;

        if (numcol > 6) {

            numcol = 6;
            line = 1 + (exmlist.size()-1)/6;

        }

        if (line >= 5){
            line = 5;
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
