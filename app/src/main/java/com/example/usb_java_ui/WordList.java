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

import java.util.List;

public class WordList extends AppCompatActivity {

    private ListView w_list;
    private ListAdapterWord w_listAdt;

    private RecyclerView w_rcy;
    private RecycleAdapterWord w_rcyAdt;

    private final String[] exList = { "더미", "바이",
            "고재원", "이지훈", "유나경", "차영원",
            "사과", "호모사피엔스", "노트북", "아두이노",
            "기타", "블루투스", "아파트", "미동석",
            "안녕하세요", "아니", "단어", "더", "뭐하지",
            "무구정광대다라니경", "일산화질소", "이편지는영국에서부터시작되어",
            "삼도수군통제사", "또모르지내마음이", "아마꿈만같겠지만",
            "그냥좋다는게아냐", "왓츠에프터라잌", "저태양보다뜨거울테니",
            "두번세번피곤하게다시질문하지마", "내장점이뭔지알아바로솔직한거야",
            "유후앤아아이", "에이비씨도레미", "활짝꽃피웠던시간도", "꽇향기만남기고갔단다",
            "유앤미미칠듯이", "처참하게짖밟혀진", "잡지않는것은너니까", "구름한점없이이쁜날",
            "이젠안녕굳바이", "꽇향기만남아헤이헤에", "따분한나의눈빛이","무표정하던얼굴이","내입술을간지럽힌낯선그이름",
            "잘봐원투쓰리포파입식세븐유메잌미필라잌일레븐", "투명한너와나의사이" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


//        w_list = (ListView) findViewById(R.id.lstv_wordLearning);
//        w_listAdt = new ListAdapterWord(this);
//
//        for (int i =1; i<exList.length;i++){
//            w_listAdt.setWItem(i, exList[i]);
//        }
//        w_list.setAdapter(w_listAdt);

        w_rcy = (RecyclerView) findViewById(R.id.rcyv_wordLearning);
        w_rcy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        w_rcyAdt = new RecycleAdapterWord();
        for (int i =1; i<exList.length;i++){
            w_rcyAdt.setRecycleItemWord(i,exList[i]);
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
