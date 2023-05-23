package com.example.usb_java_ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Typing extends AppCompatActivity {

    private Button btn_saveWord;
    private EditText edt_typing;

    private RecyclerView t_rcy;
    private RecycleAdapterWord t_rcyAdt;
    private int wordIndex;
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.typing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager();
        dbManager.checkDB(this);

        btn_saveWord = findViewById(R.id.btn_typingSave);
        edt_typing = findViewById(R.id.et_typingContent);
        t_rcy = (RecyclerView) findViewById(R.id.rcyv_typing);
        t_rcy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        t_rcyAdt = new RecycleAdapterWord(this);
        t_rcy.setAdapter(t_rcyAdt);
        wordIndex = 1;

        btn_saveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typingWord = edt_typing.getText().toString();
                typingWord = typingWord.replaceAll(" ", "");
                typingWord = typingWord.replace("\n", "");
                edt_typing.setText("");
                if (typingWord.equals("")){
                    Toast.makeText(getApplicationContext(), "단어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dbManager.isWordExists(DBManager.TABLE_WORD,typingWord)){
                    Toast.makeText(getApplicationContext(), "이미 존재하는 단어입니다", Toast.LENGTH_SHORT).show();
                    return;
                }

//                Log.d("bbb", Hangul2Braille.text(typingWord).toString());
                dbManager.insertWord(DBManager.TABLE_WORD, typingWord, braille2String(Hangul2Braille.text(typingWord)));
                t_rcyAdt.setRecycleItemWord(wordIndex, typingWord);
                t_rcyAdt.notifyItemInserted(wordIndex);
//                t_rcy.setAdapter(t_rcyAdt);
                wordIndex++;
                Toast.makeText(getApplicationContext(), "단어가 저장되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public String braille2String(ArrayList<int[]> newBraille){
        StringBuilder resString = new StringBuilder();
        resString.append("[");
        for (int[] B : newBraille){
            resString.append("[");
            for (int i : B){
                resString.append(i);
            }
            resString.append("]");
        }
        resString.append("]");

        return resString.toString();
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
