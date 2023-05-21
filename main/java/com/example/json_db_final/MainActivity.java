package com.example.json_db_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button buttonViewCho;
    Button buttonViewJoong;
    Button buttonViewJong;
    Button buttonViewGrammar;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        buttonViewCho = findViewById(R.id.buttonViewCho);
        buttonViewJoong = findViewById(R.id.buttonViewJoong);
        buttonViewJong = findViewById(R.id.buttonViewJong);
        buttonViewGrammar = findViewById(R.id.buttonViewGrammar);

        textView.setTextSize(12);  // 글꼴 크기 조정

        ///////////// 만들어진 DB없애고 새로 DB만들고 싶으면 아래 주석 제거하고 실행//////////////
        //this.deleteDatabase("USBDB");
        ///////////////////////////////////////////////////////////////////////////

        // 데이터베이스 테이블 생성 또는 기존 테이블 사용
        db = openOrCreateDatabase("USBDB", Context.MODE_PRIVATE, null);
        createTables();

        // 첫 번째 실행 여부 확인
        boolean isFirstRun = checkFirstRun();

        if (isFirstRun) {
            // JSON 파일에서 데이터 읽어와서 데이터베이스에 저장
            try {
                readDataFromJson("cho.json", "cho_table");
                readDataFromJson("joong.json", "joong_table");
                readDataFromJson("jong.json", "jong_table");
                readDataFromJson("grammar.json", "grammar_table");
                Toast.makeText(this, "첫번째 실행: json 파일을 읽어 DB 생성", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading data from JSON file", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error parsing JSON data", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "이미 생성된 DB 존재", Toast.LENGTH_LONG).show();
        }

        buttonViewCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatabase("cho_table");
            }
        });

        buttonViewJoong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatabase("joong_table");
            }
        });

        buttonViewJong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatabase("jong_table");
            }
        });

        buttonViewGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatabase("grammar_table");
            }
        });
    }

    private void createTables() {
        db.execSQL("CREATE TABLE IF NOT EXISTS cho_table (id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, braille VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS joong_table (id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, braille VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS jong_table (id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, braille VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS grammar_table (id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR, braille VARCHAR);");
    }

    private boolean checkFirstRun() {
        Cursor cursor = db.rawQuery("SELECT * FROM cho_table WHERE word = '더미'", null);
        ////////////////////// 모든 json에 더미를 다 넣어놓긴했는데 어차피 모든 json 다열어서 한꺼번에 db만들거면
        ////////////////////// 그냥 json 하나만 열어서 더미 확인하면될듯 여기선 cho 대상임
        boolean isFirstRun = (cursor.getCount() == 0);
        cursor.close();
        return isFirstRun;
    }

    private void readDataFromJson(String fileName, String tableName) throws IOException, JSONException {
        InputStream is = getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        String json = new String(buffer, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String word = item.getString("word");
            String braille = item.getString("braille");
            insertWord(tableName, word, braille);
        }
    }

    private void insertWord(String tableName, String word, String braille) {
        String query = "INSERT INTO " + tableName + " (word, braille) VALUES (?, ?);";
        SQLiteStatement statement = db.compileStatement(query);
        statement.bindString(1, word);
        statement.bindString(2, braille);
        statement.executeInsert();
    }

    private void displayDatabase(String tableName) {
        StringBuilder sb = new StringBuilder();

        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        sb.append("=== ").append(tableName).append(" ===\n");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String word = cursor.getString(1);
            String braille = cursor.getString(2);
            sb.append("ID: ").append(id).append(", Word: ").append(word).append(", Braille: ").append(braille).append("\n");
        }
        cursor.close();

        textView.setText(sb.toString());
    }
}




