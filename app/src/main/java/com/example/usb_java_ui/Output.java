package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.Objects;

public class Output extends AppCompatActivity {
    private int[][] exmlist3 = {{1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1}};
    private int[][] exmlist6 = {{1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1}, {1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1}};
    private int[][] exmlist2 = {{1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}};
    private int[][] exmlist9 = {{1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1}, {1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1}, {1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1}};
    private int[][] exmlist1 = {{1, 1, 1, 0, 0, 0}};
    private GridView o_grid_output;
    private GridOutputAdapter o_gridOAdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView txt_str = (TextView)findViewById(R.id.txt_str);

        Intent recv_intent = getIntent();
        String temp = recv_intent.getStringExtra("keyStr");
        txt_str.setText(temp);

        o_grid_output = (GridView) findViewById(R.id.grdv_brailles);
        o_gridOAdt = new GridOutputAdapter(this);



        int[][] exmlist = exmlist3;

        if (Objects.equals(temp, "ㄱ")) exmlist = exmlist1;
        if (Objects.equals(temp, "ㄴ")) exmlist = exmlist2;
        if (Objects.equals(temp, "ㄷ")) exmlist = exmlist3;
        if (Objects.equals(temp, "ㄹ")) exmlist = exmlist6;
        if (Objects.equals(temp, "ㅁ")) exmlist = exmlist9;

        for (int[] BItem : exmlist) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@drawable/b");
            for (int dot : BItem){
                stringBuilder.append(dot);
            }
            String resName = stringBuilder.toString();

            String packName = this.getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packName);


            o_gridOAdt.setBItem(resId);
        }
        int numcol = exmlist.length;
        int line = 1;

        if (numcol > 6) {

            numcol = 6;
            line = 1 + (exmlist.length-1)/6;

        }



        o_grid_output.setNumColumns(numcol);
        o_grid_output.setAdapter(o_gridOAdt);

        ViewGroup.LayoutParams o_param = o_grid_output.getLayoutParams();
        if(o_param == null) {
            o_param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        o_param.height = 160 * line;
        o_grid_output.setLayoutParams(o_param);


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
