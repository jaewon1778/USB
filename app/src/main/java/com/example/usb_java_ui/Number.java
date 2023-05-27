package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

public class Number extends MyAppActivity {
    private TextView txt_num;
    private GridView m_grid_n;
    private GridAdapter m_gridAdt_n;
    private final String[] n_list = {
            "0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9",
            "수표"};

    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();
        ObjectTree OT_num = new ObjectTree().initObject(txt_num);
        Button[] grid_n_allView =new Button[m_grid_n.getCount()];
        for (int i = 0; i<m_grid_n.getCount(); i++){
            grid_n_allView[i] = m_gridAdt_n.getView(i,new View(this), m_grid_n).findViewById(R.id.btn_item_con);
        }
        OT_num.addChildViewArr(grid_n_allView);

        MyFocusManager.txtFocusL(this, txt_num, getTTS_import());
        MyFocusManager.viewArrFocusL(this, grid_n_allView,getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.number);
        super.onCreate(savedInstanceState);

        m_grid_n = (GridView) findViewById(R.id.grdv_number);
        m_gridAdt_n = new GridAdapter(this);

        for (String voStr : n_list) {
            m_gridAdt_n.setItem(voStr);
        }
        m_gridAdt_n.setKeyType(5);

        m_grid_n.setAdapter(m_gridAdt_n);

        int btn_height = 550;
        ViewGroup.LayoutParams param_v = m_grid_n.getLayoutParams();
        if(param_v == null) {
            param_v = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        param_v.height = btn_height;
        m_grid_n.setLayoutParams(param_v);
    }

}
