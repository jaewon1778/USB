package com.example.usb_java_ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Abbreviation extends AppCompatActivity {

    private GridView m_grid_ca;
    private GridView m_grid_va;
    private GridView m_grid_ua;
    private GridAdapter m_gridAdt_ca;
    private GridAdapter m_gridAdt_va;
    private GridAdapter m_gridAdt_ua;
    private String[] kor_ca_list = {"가", "나", "다", "라", "마",
                                    "바", "사", "아", "자", "차",
                                    "카", "타", "파", "하"};
    private String[] kor_va_list = {"억", "언", "얼", "연", "열",
                                    "영", "옥", "온", "옹", "운",
                                    "울", "은", "을", "인"};
    private String[] kor_ua_list = {"것", "그래서", "그러나",
                                    "그러면", "그러므로", "그런데",
                                    "그리고", "그리하여"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abbreviation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        m_grid_ca = (GridView) findViewById(R.id.grdv_conAbb);
        m_gridAdt_ca = new GridAdapter(this);

        for (String caStr : kor_ca_list) {
            m_gridAdt_ca.setItem(caStr);
        }

        m_grid_va = (GridView) findViewById(R.id.grdv_voAbb);
        m_gridAdt_va = new GridAdapter(this);

        for (String vaStr : kor_va_list) {
            m_gridAdt_va.setItem(vaStr);
        }

        m_grid_ua = (GridView) findViewById(R.id.grdv_uAbb);
        m_gridAdt_ua = new GridAdapter(this);

        for (String uaStr : kor_ua_list) {
            m_gridAdt_ua.setItem(uaStr);
        }

        m_grid_ca.setAdapter(m_gridAdt_ca);
        m_grid_va.setAdapter(m_gridAdt_va);
        m_grid_ua.setAdapter(m_gridAdt_ua);

        int btn_height = 350;
        ViewGroup.LayoutParams param_ca = m_grid_ca.getLayoutParams();
        if(param_ca == null) {
            param_ca = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_ca.height = btn_height;
        m_grid_ca.setLayoutParams(param_ca);

        ViewGroup.LayoutParams param_va = m_grid_va.getLayoutParams();
        if(param_va == null) {
            param_va = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_va.height = btn_height;
        m_grid_va.setLayoutParams(param_va);

        ViewGroup.LayoutParams param_ua = m_grid_ua.getLayoutParams();
        if(param_ua == null) {
            param_ua = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_ua.height = btn_height;
        m_grid_ua.setLayoutParams(param_ua);

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
