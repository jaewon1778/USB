package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Vowel extends AppCompatActivity {
    private GridView m_grid_v;
    private GridAdapter m_gridAdt_v;
    private final String[] kor_v_list = {
            "ㅏ", "ㅑ", "ㅓ", "ㅕ", "ㅗ",
            "ㅛ", "ㅜ", "ㅠ", "ㅡ", "ㅣ",
            "ㅐ", "ㅔ", "ㅖ", "ㅘ", "ㅚ",
            "ㅝ", "ㅢ", "ㅒ", "ㅙ", "ㅞ",
            "ㅟ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vowel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        m_grid_v = (GridView) findViewById(R.id.grdv_vowel);
        m_gridAdt_v = new GridAdapter(this);

        for (String voStr : kor_v_list) {
            m_gridAdt_v.setItem(voStr);
        }
        m_gridAdt_v.setKeyType(3);

        m_grid_v.setAdapter(m_gridAdt_v);

        int btn_height = 550;
        ViewGroup.LayoutParams param_v = m_grid_v.getLayoutParams();
        if(param_v == null) {
            param_v = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_v.height = btn_height;
        m_grid_v.setLayoutParams(param_v);
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
