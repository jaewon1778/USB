package com.example.usb_java_ui;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Dot_num extends MyAppOutputActivity {

    private TextView txt_pt, txt_dnd;
    private int[] braille;
    private ImageButton point1;
    private ImageButton point2;
    private ImageButton point3;
    private ImageButton point4;
    private ImageButton point5;
    private ImageButton point6;

    private Button btn_output;

    protected void VoiceModeOn(){
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();

        // make objTree
        point1.setContentDescription("1점");
        point2.setContentDescription("2점");
        point3.setContentDescription("3점");
        point4.setContentDescription("4점");
        point5.setContentDescription("5점");
        point6.setContentDescription("6점");
        OT_root.addChildViewArr(new View[]{txt_pt, btn_output});
        OT_root.getChildObjectOfIndex(0).addChildViewArr(new View[]{txt_dnd ,point1, point2, point3, point4, point5, point6});
        // make objTree
        MyFocusManager.viewArrFocusL(this, new View[]{txt_pt, txt_dnd, btn_output, point1, point2, point3, point4, point5, point6},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dot_num);
        super.onCreate(savedInstanceState);

        txt_pt = findViewById(R.id.pageTitle);
        txt_dnd = findViewById(R.id.txt_dotNumDes);
        braille = new int[]{0, 0, 0, 0, 0, 0};

        point1 = (ImageButton) findViewById(R.id.imgbtn_point1);
        point2 = (ImageButton) findViewById(R.id.imgbtn_point2);
        point3 = (ImageButton) findViewById(R.id.imgbtn_point3);
        point4 = (ImageButton) findViewById(R.id.imgbtn_point4);
        point5 = (ImageButton) findViewById(R.id.imgbtn_point5);
        point6 = (ImageButton) findViewById(R.id.imgbtn_point6);
        btn_output = findViewById(R.id.btn_output);


        btn_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBraille(setResList(braille),0, 1);
            }
        });

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

    }
    private ArrayList<int[]> setResList(int[] B){
        ArrayList<int[]> resList = new ArrayList<int[]>();
        resList.add(B);
        return resList;
    }


}
