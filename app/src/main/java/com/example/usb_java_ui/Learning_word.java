package com.example.usb_java_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Learning_word extends MyAppActivity {

    TextView txt_pt;
    Button dotNum;
    Button korean;
    Button number;
    Button wordList;

    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();
        ObjectTree OT_pt = new ObjectTree().initObject(txt_pt);
        OT_pt.addChildViewArr(new View[]{dotNum, korean, number, wordList});
        OT_root.addChild(OT_pt);

        MyFocusManager.viewArrFocusL(this, new View[]{txt_pt,dotNum, korean, number, wordList},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.learning_word);
        super.onCreate(savedInstanceState);

        txt_pt = findViewById(R.id.pageTitle);

        dotNum = (Button) findViewById(R.id.lw_dotNum);
        korean = (Button) findViewById(R.id.lw_korean);
        number = findViewById(R.id.lw_number);
        wordList = (Button) findViewById(R.id.lw_word);
        dotNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dot_num.class);
                startActivity(intent);
            }
        });
        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Korean.class);
                startActivity(intent);
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Number.class);
                startActivity(intent);
            }
        });
        wordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordList.class);
                startActivity(intent);
            }
        });
    }
}
