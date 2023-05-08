package com.example.usb_java_ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class Dot_num extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dot_num);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        int[] braille = {1,1,1,1,1,1};

        ImageButton point1 = (ImageButton) findViewById(R.id.imgbtn_point1);
        ImageButton point2 = (ImageButton) findViewById(R.id.imgbtn_point2);
        ImageButton point3 = (ImageButton) findViewById(R.id.imgbtn_point3);
        ImageButton point4 = (ImageButton) findViewById(R.id.imgbtn_point4);
        ImageButton point5 = (ImageButton) findViewById(R.id.imgbtn_point5);
        ImageButton point6 = (ImageButton) findViewById(R.id.imgbtn_point6);

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
