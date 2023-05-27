package com.example.usb_java_ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class Help extends MyAppActivity {

    private TextView txt_titleHelp;
    private TextView txt_desHelp;
    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();
        OT_root.addChildViewArr(new View[]{txt_titleHelp,txt_desHelp});
        MyFocusManager.viewArrFocusL(this, new View[]{txt_titleHelp,txt_desHelp},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.help);
        super.onCreate(savedInstanceState);

        txt_titleHelp = findViewById(R.id.txt_titleHelp);
        txt_desHelp = findViewById(R.id.txt_desHelp);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.help_menu, menu);

        return true;
    }

}
