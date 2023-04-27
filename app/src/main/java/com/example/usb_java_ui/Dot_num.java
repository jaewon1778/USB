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

        String S_on = getString(R.string.tgl_on);
        String S_off = getString(R.string.tgl_off);

        ToggleButton tgl_p1 = findViewById(R.id.tgl_point_1);
        String S_p1 = getString(R.string.point_1);
        tgl_p1.setTextOn(S_p1 + "\n" + S_on);
        tgl_p1.setTextOff(S_p1 + "\n" + S_off);

        ToggleButton tgl_p2 = findViewById(R.id.tgl_point_2);
        String S_p2 = getString(R.string.point_2);
        tgl_p2.setTextOn(S_p2 + "\n" + S_on);
        tgl_p2.setTextOff(S_p2 + "\n" + S_off);

        ToggleButton tgl_p3 = findViewById(R.id.tgl_point_3);
        String S_p3 = getString(R.string.point_3);
        tgl_p3.setTextOn(S_p3 + "\n" + S_on);
        tgl_p3.setTextOff(S_p3 + "\n" + S_off);

        ToggleButton tgl_p4 = findViewById(R.id.tgl_point_4);
        String S_p4 = getString(R.string.point_4);
        tgl_p4.setTextOn(S_p4 + "\n" + S_on);
        tgl_p4.setTextOff(S_p4 + "\n" + S_off);

        ToggleButton tgl_p5 = findViewById(R.id.tgl_point_5);
        String S_p5 = getString(R.string.point_5);
        tgl_p5.setTextOn(S_p5 + "\n" + S_on);
        tgl_p5.setTextOff(S_p5 + "\n" + S_off);

        ToggleButton tgl_p6 = findViewById(R.id.tgl_point_6);
        String S_p6 = getString(R.string.point_6);
        tgl_p6.setTextOn(S_p6 + "\n" + S_on);
        tgl_p6.setTextOff(S_p6 + "\n" + S_off);

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
