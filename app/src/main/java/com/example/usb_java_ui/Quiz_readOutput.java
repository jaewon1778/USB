package com.example.usb_java_ui;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Quiz_readOutput extends AppCompatActivity {
    private int[][] exmlist3 = {{1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1},
                                {1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1},
                                {1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1},
                                {1, 1, 1, 0, 0, 0}, {0, 0, 0, 1, 0, 0}, {1, 1, 1, 0, 0, 1}};
    private GridView qzr_grid_output;
    private GridOutputAdapter qzr_gridOAdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_read_output);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        qzr_grid_output = (GridView) findViewById(R.id.grdv_qzrBrailles);
        qzr_gridOAdt = new GridOutputAdapter(this);

        int[][] exmlist = exmlist3;
        for (int[] BItem : exmlist) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@drawable/b");
            for (int dot : BItem){
                stringBuilder.append(dot);
            }
            String resName = stringBuilder.toString();

            String packName = this.getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packName);


            qzr_gridOAdt.setBItem(resId);
        }
        int numcol = exmlist.length;
        int line = 1;

        if (numcol > 6) {

            numcol = 6;
            line = 1 + (exmlist.length-1)/6;

        }



        qzr_grid_output.setNumColumns(numcol);
        qzr_grid_output.setAdapter(qzr_gridOAdt);

        ViewGroup.LayoutParams o_param = qzr_grid_output.getLayoutParams();
        if(o_param == null) {
            o_param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        o_param.height = 160 * line;
        qzr_grid_output.setLayoutParams(o_param);
    }
}
