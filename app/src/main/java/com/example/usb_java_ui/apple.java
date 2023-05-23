package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class apple extends AppCompatActivity {

    private ListView w_list;
    private ListAdapterWord w_listAdt;

    private RecyclerView w_rcy;
    private RecycleAdapterWord w_rcyAdt;
    DBManager dbManager = new DBManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apple);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btn_clearDB = findViewById(R.id.btn_dbClear);
        btn_clearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.clearAllTables();
            }
        });

        w_rcy = (RecyclerView) findViewById(R.id.rcyv_wordLearning);
        w_rcy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        w_rcyAdt = new RecycleAdapterWord();

//        for (int i =1; i<exList.length;i++){
//            w_rcyAdt.setRecycleItemWord(i,exList[i]);
//        }

        ArrayList<String> wordList = dbManager.getDataList(DBManager.TABLE_WORD);
        for (int i=0; i<wordList.size();i++){
            w_rcyAdt.setRecycleItemWord(i+1, wordList.get(i));
        }

        w_rcy.setAdapter(w_rcyAdt);

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
