package com.example.usb_java_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Korean extends MyAppActivity {

    TextView txt_pt;
    Button kor_consonant;
    Button kor_vowel;
    Button kor_abbreviation;

    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();

        // make objTree
        ObjectTree OT_pt = new ObjectTree().initObject(txt_pt);
        OT_pt.addChildViewArr(new View[]{kor_consonant, kor_vowel, kor_abbreviation});
        OT_root.addChild(OT_pt);
        // make objTree
        MyFocusManager.viewArrFocusL(this, new View[]{txt_pt,kor_consonant, kor_vowel, kor_abbreviation},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.korean);
        super.onCreate(savedInstanceState);

        txt_pt = findViewById(R.id.pageTitle);
        kor_consonant = (Button) findViewById(R.id.kor_consonant);
        kor_vowel = (Button) findViewById(R.id.kor_vowel);
        kor_abbreviation = (Button) findViewById(R.id.kor_abbreviation);
        kor_consonant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Consonant.class);
                startActivity(intent);
            }
        });

        kor_vowel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Vowel.class);
                startActivity(intent);
            }
        });

        kor_abbreviation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Abbreviation.class);
                startActivity(intent);
            }
        });
    }
}