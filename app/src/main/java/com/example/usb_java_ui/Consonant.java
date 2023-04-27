package com.example.usb_java_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Consonant extends AppCompatActivity {
    private GridView m_grid_ic;
    private GridView m_grid_idc;
    private GridView m_grid_fc;
    private GridView m_grid_fdc;
    private GridAdapter m_gridAdt_ic;
    private GridAdapter m_gridAdt_idc;
    private GridAdapter m_gridAdt_fc;
    private GridAdapter m_gridAdt_fdc;
    private String[] kor_ic_list = {"ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ",
                                    "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ",
                                    "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

    private final String[] kor_idc_list = {"ㄲ", "ㄸ", "ㅃ", "ㅆ", "ㅉ"};

    private final String[] kor_fc_list = {"ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ",
                                          "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ",
                                          "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

    private final String[] kor_fdc_list = {"ㄲ", "ㄳ", "ㄵ", "ㄶ", "ㄺ",
                                           "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ",
                                           "ㅀ", "ㅄ", "ㅆ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consonant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        m_grid_ic = (GridView) findViewById(R.id.grdv_initialCon);
        m_gridAdt_ic = new GridAdapter(this);

        for (String conStr : kor_ic_list) {
            m_gridAdt_ic.setItem(conStr);
        }


        m_grid_idc = (GridView) findViewById(R.id.grdv_initialDblCon);
        m_gridAdt_idc = new GridAdapter(this);

        for (String conStr : kor_idc_list) {
            m_gridAdt_idc.setItem(conStr);
        }


        m_grid_fc = (GridView) findViewById(R.id.grdv_finalCon);
        m_gridAdt_fc = new GridAdapter(this);

        for (String conStr : kor_fc_list) {
            m_gridAdt_fc.setItem(conStr);
        }

        m_grid_fdc = (GridView) findViewById(R.id.grdv_finalDblCon);
        m_gridAdt_fdc = new GridAdapter(this);

        for (String conStr : kor_fdc_list) {
            m_gridAdt_fdc.setItem(conStr);
        }
        m_grid_ic.setAdapter(m_gridAdt_ic);
        m_grid_idc.setAdapter(m_gridAdt_idc);
        m_grid_fc.setAdapter(m_gridAdt_fc);
        m_grid_fdc.setAdapter(m_gridAdt_fdc);

        int btn_height = 320;
        ViewGroup.LayoutParams param_ic = m_grid_ic.getLayoutParams();
        if(param_ic == null) {
            param_ic = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_ic.height = btn_height;
        m_grid_ic.setLayoutParams(param_ic);

        ViewGroup.LayoutParams param_idc = m_grid_idc.getLayoutParams();
        if(param_idc == null) {
            param_idc = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_idc.height = 100;
        m_grid_idc.setLayoutParams(param_idc);

        ViewGroup.LayoutParams param_fc = m_grid_fc.getLayoutParams();
        if(param_fc == null) {
            param_fc = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_fc.height = btn_height;
        m_grid_fc.setLayoutParams(param_fc);

        ViewGroup.LayoutParams param_fdc = m_grid_fdc.getLayoutParams();
        if(param_fdc == null) {
            param_fdc = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_fdc.height = btn_height;
        m_grid_fdc.setLayoutParams(param_fdc);


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