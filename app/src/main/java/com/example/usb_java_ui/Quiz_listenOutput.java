package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import java.util.ArrayList;

public class Quiz_listenOutput extends AppCompatActivity {
    private int[] braille = new int[]{1,1,1,1,1,1};
    private final ArrayList<int[]> answerList = new ArrayList<>();


    private void resetBraille(ImageButton[] points){
        braille = new int[]{1, 1, 1, 1, 1, 1};
        points[0].setImageResource(R.drawable.p1);
        points[1].setImageResource(R.drawable.p1);
        points[2].setImageResource(R.drawable.p1);
        points[3].setImageResource(R.drawable.p1);
        points[4].setImageResource(R.drawable.p1);
        points[5].setImageResource(R.drawable.p1);

    }

    private void updateAnswer(){
        GridView qzl_grid_output = (GridView) findViewById(R.id.grdv_qzlBrailles);
        GridOutputAdapter qzl_gridOAdt = new GridOutputAdapter(this);


        for (int[] BItem : answerList) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@drawable/b");
            for (int dot : BItem){
                stringBuilder.append(dot);
            }
            String resName = stringBuilder.toString();

            String packName = this.getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packName);


            qzl_gridOAdt.setBItem(resId);
        }
        int numcol = answerList.size();
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

        updateAnswer();

        NestedScrollView ns_qzl = (NestedScrollView) findViewById(R.id.ns_qzl);


        ImageButton point1 = (ImageButton) findViewById(R.id.imgbtn_qzlPoint1);
        ImageButton point2 = (ImageButton) findViewById(R.id.imgbtn_qzlPoint2);
        ImageButton point3 = (ImageButton) findViewById(R.id.imgbtn_qzlPoint3);
        ImageButton point4 = (ImageButton) findViewById(R.id.imgbtn_qzlPoint4);
        ImageButton point5 = (ImageButton) findViewById(R.id.imgbtn_qzlPoint5);
        ImageButton point6 = (ImageButton) findViewById(R.id.imgbtn_qzlPoint6);

        ImageButton[] points = {point1,point2,point3,point4,point5,point6};

        point1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[0]==1){
                    braille[0] = 0;
                    point1.setImageResource(R.drawable.p0);
                }
                else {
                    braille[0] = 1;
                    point1.setImageResource(R.drawable.p1);
                }
            }
        });
        point2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[1]==1){
                    braille[1] = 0;
                    point2.setImageResource(R.drawable.p0);
                }
                else {
                    braille[1] = 1;
                    point2.setImageResource(R.drawable.p1);
                }
            }
        });
        point3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[2]==1){
                    braille[2] = 0;
                    point3.setImageResource(R.drawable.p0);
                }
                else {
                    braille[2] = 1;
                    point3.setImageResource(R.drawable.p1);
                }
            }
        });
        point4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[3]==1){
                    braille[3] = 0;
                    point4.setImageResource(R.drawable.p0);
                }
                else {
                    braille[3] = 1;
                    point4.setImageResource(R.drawable.p1);
                }
            }
        });
        point5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[4]==1){
                    braille[4] = 0;
                    point5.setImageResource(R.drawable.p0);
                }
                else {
                    braille[4] = 1;
                    point5.setImageResource(R.drawable.p1);
                }
            }
        });
        point6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(braille[5]==1){
                    braille[5] = 0;
                    point6.setImageResource(R.drawable.p0);
                }
                else {
                    braille[5] = 1;
                    point6.setImageResource(R.drawable.p1);
                }
            }
        });


        ImageButton addA = (ImageButton) findViewById(R.id.btn_addAnswer);
        ImageButton backA = (ImageButton) findViewById(R.id.btn_backspaceAnswer);
        ImageButton removeA = (ImageButton) findViewById(R.id.btn_removeAnswer);
        ImageButton submitA = (ImageButton) findViewById(R.id.btn_submitAnswer);

        addA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerList.add(braille);
                updateAnswer();
                resetBraille(points);
                ns_qzl.fullScroll(NestedScrollView.FOCUS_DOWN);
            }
        });
        backA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerList.size() != 0) {
                    answerList.remove(answerList.size() - 1);
                    updateAnswer();
                }
            }
        });
        removeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerList.clear();
                updateAnswer();
            }
        });
        submitA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"제출버튼입니다.",Toast.LENGTH_SHORT).show();
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
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
