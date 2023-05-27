package com.example.usb_java_ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class Abbreviation extends MyAppActivity {

    private GridView m_grid_ca;
    private GridView m_grid_va;
    private GridView m_grid_ua;
    private GridAdapter m_gridAdt_ca;
    private GridAdapter m_gridAdt_va;
    private GridAdapter m_gridAdt_ua;
    private String[] kor_ca_list = {"가", "나", "다", "마", "바",
                                    "사", "자", "카", "타", "파",
                                    "하"};
    private String[] kor_va_list = {"억", "언", "얼", "연", "열",
                                    "영", "옥", "온", "옹", "운",
                                    "울", "은", "을", "인"};
    private String[] kor_ua_list = {"것", "그래서", "그러나",
                                    "그러면", "그러므로", "그런데",
                                    "그리고", "그리하여"};

    private TextView txt_ca;
    private TextView txt_va;
    private TextView txt_ua;

    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();
        ObjectTree OT_ca = new ObjectTree().initObject(txt_ca);
        ObjectTree OT_va = new ObjectTree().initObject(txt_va);
        ObjectTree OT_ua = new ObjectTree().initObject(txt_ua);

        Button[] grid_ca_allView =new Button[m_grid_ca.getCount()];
//        Log.d("item", String.valueOf(m_grid_v.getCount()));
        for (int i = 0; i<m_grid_ca.getCount(); i++){
            grid_ca_allView[i] = m_gridAdt_ca.getView(i,new View(this), m_grid_ca).findViewById(R.id.btn_item_con);
        }
        Button[] grid_va_allView =new Button[m_grid_va.getCount()];
//        Log.d("item", String.valueOf(m_grid_v.getCount()));
        for (int i = 0; i<m_grid_va.getCount(); i++){
            grid_va_allView[i] = m_gridAdt_va.getView(i,new View(this), m_grid_va).findViewById(R.id.btn_item_con);
        }
        Button[] grid_ua_allView =new Button[m_grid_ua.getCount()];
//        Log.d("item", String.valueOf(m_grid_v.getCount()));
        for (int i = 0; i<m_grid_ua.getCount(); i++){
            grid_ua_allView[i] = m_gridAdt_ua.getView(i,new View(this), m_grid_ua).findViewById(R.id.btn_item_con);
        }
        OT_ca.addChildViewArr(grid_ca_allView);
        OT_va.addChildViewArr(grid_va_allView);
        OT_ua.addChildViewArr(grid_ua_allView);
        OT_root.addChildObjectArr(new ObjectTree[]{OT_ca, OT_va, OT_ua});

        MyFocusManager.viewArrFocusL(this, new View[]{txt_ca, txt_va, txt_ua},getTTS_import());
        MyFocusManager.btnListFocusL(grid_ca_allView, getTTS_import());
        MyFocusManager.btnListFocusL(grid_va_allView, getTTS_import());
        MyFocusManager.btnListFocusL(grid_ua_allView, getTTS_import());

        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.abbreviation);
        super.onCreate(savedInstanceState);

        txt_ca = findViewById(R.id.txt_conAbb);
        txt_va = findViewById(R.id.txt_voAbb);
        txt_ua = findViewById(R.id.txt_uAbb);

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

        m_gridAdt_ca.setKeyType(4);
        m_gridAdt_va.setKeyType(4);
        m_gridAdt_ua.setKeyType(4);

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

}
