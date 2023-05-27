package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordList extends MyAppActivity {

    private TextView txt_pt;
    private RecyclerView w_rcy;
    private RecycleAdapterWord w_rcyAdt;

    private DBManager dbManager = new DBManager();

    @Override
    protected void VoiceModeOn() {
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();
        ObjectTree OT_pt = new ObjectTree().initObject(txt_pt);
//        Log.d("item", String.valueOf(w_rcyAdt.getItemCount()));
//        Log.d("item i", String.valueOf(Objects.requireNonNull(w_rcy.getLayoutManager()).getItemCount()));
//        Log.d("item c", String.valueOf(Objects.requireNonNull(w_rcy.getLayoutManager()).getChildCount()));
//        View[] itemViewArr = new View[w_rcyAdt.getItemCount()];
//        for (int i = 1; i<w_rcyAdt.getItemCount(); i++){
//            itemViewArr[i] = Objects.requireNonNull(w_rcy.getLayoutManager()).findViewByPosition(i);
//        }
//        OT_pt.addChildViewArr(itemViewArr);
        OT_root.addChild(OT_pt);
//        MyFocusManager.wordItemFocusL(this, itemViewArr, getTTS_import());
        MyFocusManager.viewArrFocusL(this, new View[]{txt_pt},getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.word_list);
        super.onCreate(savedInstanceState);

        txt_pt = findViewById(R.id.pageTitle);
        w_rcy = (RecyclerView) findViewById(R.id.rcyv_wordLearning);
        w_rcy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        w_rcyAdt = new RecycleAdapterWord(this);

        ArrayList<String> wordList = dbManager.getDataList(DBManager.TABLE_WORD);
        for (int i=0; i<wordList.size();i++){
            w_rcyAdt.setRecycleItemWord(i+1, wordList.get(i));
        }

        w_rcy.setAdapter(w_rcyAdt);


    }
}
