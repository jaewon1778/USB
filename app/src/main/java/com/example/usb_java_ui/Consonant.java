package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Consonant extends MyAppActivity {

    private TextView txt_ic, txt_idc, txt_fc, txt_fdc;
    private GridView m_grid_ic;
    private GridView m_grid_idc;
    private GridView m_grid_fc;
    private GridView m_grid_fdc;
    private GridAdapter m_gridAdt_ic;
    private GridAdapter m_gridAdt_idc;
    private GridAdapter m_gridAdt_fc;
    private GridAdapter m_gridAdt_fdc;

    private final String[] kor_ic_list = {"ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ",
                                          "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ",
                                          "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

    private final String[] kor_idc_list = {"ㄲ", "ㄸ", "ㅃ", "ㅆ", "ㅉ"};

    private final String[] kor_fc_list = {"ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ",
                                          "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ",
                                          "ㅋ", "ㅌ", "ㅍ", "ㅎ"};

    private final String[] kor_fdc_list = {"ㄲ", "ㄳ", "ㄵ", "ㄶ", "ㄺ",
                                           "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ",
                                           "ㅀ", "ㅄ", "ㅆ"};

    protected void VoiceModeOn(){
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();
        ObjectTree OT_ic = new ObjectTree().initObject(txt_ic);
        ObjectTree OT_idc = new ObjectTree().initObject(txt_idc);
        ObjectTree OT_fc = new ObjectTree().initObject(txt_fc);
        ObjectTree OT_fdc = new ObjectTree().initObject(txt_fdc);

        Button[] grid_ic_allView =new Button[m_grid_ic.getCount()];
//        Log.d("item", String.valueOf(m_grid_v.getCount()));
        for (int i = 0; i<m_grid_ic.getCount(); i++){
            grid_ic_allView[i] = m_gridAdt_ic.getView(i,new View(this), m_grid_ic).findViewById(R.id.btn_item_con);
        }
        Button[] grid_idc_allView =new Button[m_grid_idc.getCount()];
//        Log.d("item", String.valueOf(m_grid_v.getCount()));
        for (int i = 0; i<m_grid_idc.getCount(); i++){
            grid_idc_allView[i] = m_gridAdt_idc.getView(i,new View(this), m_grid_idc).findViewById(R.id.btn_item_con);
        }
        Button[] grid_fc_allView =new Button[m_grid_fc.getCount()];
//        Log.d("item", String.valueOf(m_grid_v.getCount()));
        for (int i = 0; i<m_grid_fc.getCount(); i++){
            grid_fc_allView[i] = m_gridAdt_fc.getView(i,new View(this), m_grid_fc).findViewById(R.id.btn_item_con);
        }
        Button[] grid_fdc_allView =new Button[m_grid_fdc.getCount()];
//        Log.d("item", String.valueOf(m_grid_v.getCount()));
        for (int i = 0; i<m_grid_fdc.getCount(); i++){
            grid_fdc_allView[i] = m_gridAdt_fdc.getView(i,new View(this), m_grid_fdc).findViewById(R.id.btn_item_con);
        }
        OT_ic.addChildViewArr(grid_ic_allView);
        OT_idc.addChildViewArr(grid_idc_allView);
        OT_fc.addChildViewArr(grid_fc_allView);
        OT_fdc.addChildViewArr(grid_fdc_allView);
        OT_root.addChildObjectArr(new ObjectTree[]{OT_ic, OT_idc, OT_fc, OT_fdc});

        MyFocusManager.viewArrFocusL(this, new View[]{txt_ic, txt_idc, txt_fc, txt_fdc},getTTS_import());
        MyFocusManager.btnListFocusL(grid_ic_allView, getTTS_import());
        MyFocusManager.btnListFocusL(grid_idc_allView, getTTS_import());
        MyFocusManager.btnListFocusL(grid_fc_allView, getTTS_import());
        MyFocusManager.btnListFocusL(grid_fdc_allView, getTTS_import());

        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.consonant);
        super.onCreate(savedInstanceState);

        txt_ic = findViewById(R.id.txt_initialCon);
        txt_idc = findViewById(R.id.txt_initialDblCon);
        txt_fc = findViewById(R.id.txt_finalCon);
        txt_fdc = findViewById(R.id.txt_finalDblCon);

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

        m_gridAdt_ic.setKeyType(1);
        m_gridAdt_idc.setKeyType(1);
        m_gridAdt_fc.setKeyType(2);
        m_gridAdt_fdc.setKeyType(2);


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

}