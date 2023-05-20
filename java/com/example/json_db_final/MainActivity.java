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


public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editTextWord;
    Button buttonInsert;
    Button buttonDelete;
    Button buttonView;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editTextWord = findViewById(R.id.editTextWord);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonView = findViewById(R.id.buttonView);

        textView.setTextSize(12);  // 글꼴 크기 조정


        // 데이터베이스 테이블 생성 또는 기존 테이블 사용
        db = openOrCreateDatabase("USBDB", Context.MODE_PRIVATE, null);
        createTable();

        // 첫 번째 실행 여부 확인
        boolean isFirstRun = checkFirstRun();

        if (isFirstRun) {
            // JSON 파일에서 데이터 읽어와서 데이터베이스에 저장
            try {
                readDataFromJson();
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

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editTextWord.getText().toString();
                if (isWordExists(word)) {
                    Toast.makeText(MainActivity.this, "단어가 이미 존재합니다.", Toast.LENGTH_LONG).show();
                } else {
                    String braille = getBrailleForWord(word); // 삽입할 단어에 대한 브레일 가져오기
                    insertWord(word, braille);
                    Toast.makeText(MainActivity.this, "단어가 DB에 추가되었습니다.", Toast.LENGTH_LONG).show();
                }
                editTextWord.setText("");
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editTextWord.getText().toString();
                if (isWordExists(word)) {
                    deleteWord(word);
                    Toast.makeText(MainActivity.this, "단어가 DB에서 삭제되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "DB에 존재하지 않는 단어입니다.", Toast.LENGTH_LONG).show();
                }
                editTextWord.setText("");
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatabase();
            }
        });
    }

    private void createTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS records (word VARCHAR, braille VARCHAR)");
    }

    private boolean checkFirstRun() {
        Cursor cursor = db.rawQuery("SELECT * FROM records WHERE word = '더미'", null);
        boolean isFirstRun = (cursor.getCount() == 0);
        cursor.close();
        return isFirstRun;
    }

    private void readDataFromJson() throws IOException, JSONException {
        InputStream inputStream = getAssets().open("data.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer, StandardCharsets.UTF_8);

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String word = item.getString("word");
            String braille = item.getJSONArray("braille").toString();

            insertWord(word, braille);
        }
    }

    private String getBrailleForWord(String word) {
        // 단어 추가 시 각 단어에 대한 점자를 가져오는 코드
        return ""; // 임시로 빈 문자열 반환
    }

    private boolean isWordExists(String word) {
        Cursor cursor = db.rawQuery("SELECT * FROM records WHERE word = ?", new String[]{word});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private void insertWord(String word, String braille) {
        String sql = "INSERT INTO records (word, braille) VALUES (?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, word);
        statement.bindString(2, braille);
        statement.executeInsert();
    }

    private void deleteWord(String word) {
        String sql = "DELETE FROM records WHERE word = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, word);
        statement.executeUpdateDelete();
    }


    private void displayDatabase() {
        Cursor cursor = db.rawQuery("SELECT * FROM records", null);
        StringBuilder stringBuilder = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(0);
                String braille = cursor.getString(1);
                stringBuilder.append("Word: ").append(word).append("\nBraille: ").append(braille).append("\n\n");
            } while (cursor.moveToNext());
        }

        textView.setText(stringBuilder.toString());
        cursor.close();
    }
}



