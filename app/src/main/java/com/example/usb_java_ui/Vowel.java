package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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

import java.util.ArrayList;

public class Vowel extends MyAppActivity {
    TextView txt_pt;
    private GridView m_grid_v;
    private GridAdapter m_gridAdt_v;
    private final String[] kor_v_list = {
            "ㅏ", "ㅑ", "ㅓ", "ㅕ", "ㅗ",
            "ㅛ", "ㅜ", "ㅠ", "ㅡ", "ㅣ",
            "ㅐ", "ㅔ", "ㅖ", "ㅘ", "ㅚ",
            "ㅝ", "ㅢ", "ㅒ", "ㅙ", "ㅞ",
            "ㅟ"};

    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();

        Button[] grid_v_allView =new Button[m_grid_v.getCount()];
        for (int i = 0; i<m_grid_v.getCount(); i++){
            grid_v_allView[i] = m_gridAdt_v.getView(i,new View(this), m_grid_v).findViewById(R.id.btn_item_con);
        }
        // make objTree
        ObjectTree OT_pt = new ObjectTree().initObject(txt_pt);
        OT_pt.addChildViewArr(grid_v_allView);
        OT_root.addChild(OT_pt);
        // make objTree

        MyFocusManager.txtFocusL(this, txt_pt, getTTS_import());
        MyFocusManager.btnListFocusL(grid_v_allView,getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.vowel);
        super.onCreate(savedInstanceState);

        txt_pt = findViewById(R.id.txt_vowel);
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
}
