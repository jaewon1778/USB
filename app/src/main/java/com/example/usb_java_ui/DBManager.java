package com.example.usb_java_ui;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class DBManager {

    public static SQLiteDatabase db;
    public static final String TABLE_IC = "table_initialC";
    public static final String TABLE_V = "table_V";
    public static final String TABLE_FC = "table_finalC";
    public static final String TABLE_ABB = "table_Abb";
    public static final String TABLE_NUM = "table_Num";
    public static final String TABLE_WORD = "table_Word";
    public static final String[] TABLE_NAMES = {TABLE_IC, TABLE_V, TABLE_FC, TABLE_ABB, TABLE_NUM, TABLE_WORD};


    public void InitDB(Context context){
        db = context.openOrCreateDatabase("USB_DB", Context.MODE_PRIVATE, null);
        for (String table_name : TABLE_NAMES) {
            createTable(table_name);

            if (checkFirstRun(table_name)) {
                // JSON 파일에서 데이터 읽어와서 데이터베이스에 저장
                try {
                    readDataFromJson(context, table_name);
                Toast.makeText(context, "첫번째 실행: json 파일을 읽어 DB 생성", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                Toast.makeText(context, "Error reading data from JSON file", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data", Toast.LENGTH_LONG).show();
                }
            } else {
//            Toast.makeText(context, "이미 생성된 DB 존재", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void checkDB(Context context){
        db = context.openOrCreateDatabase("USB_DB", Context.MODE_PRIVATE, null);
    }

    private void createTable(String table_name) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+table_name+" (word VARCHAR, braille VARCHAR)");
    }

    public boolean checkFirstRun(String table_name) {
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name+" WHERE word = '더미'", null);
        boolean isFirstRun = (cursor.getCount() == 0);
        cursor.close();
        return isFirstRun;
    }

    private String findJsonName(String table_name){
        String fileName = "";
        switch (table_name){
            case TABLE_IC:
                fileName = "data_ic.json";
                break;
            case TABLE_V:
                fileName = "data_v.json";
                break;
            case TABLE_FC:
                fileName = "data_fc.json";
                break;
            case TABLE_ABB:
                fileName = "data_a.json";
                break;
            case TABLE_NUM:
                fileName = "data_n.json";
                break;
            case TABLE_WORD:
                fileName = "data_w.json";
                break;

        }
        return fileName;
    }

    private void readDataFromJson(Context context, String table_name) throws IOException, JSONException {


        InputStream inputStream = context.getAssets().open(findJsonName(table_name));
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

            insertWord(table_name, word, braille);
        }
    }

    private String getBrailleForWord(String table_name, String word) {
        // 단어 추가 시 각 단어에 대한 점자를 가져오는 코드
        ArrayList<int[]> resB = new ArrayList<>();
        switch (table_name){

            case TABLE_IC:
            case TABLE_V:
            case TABLE_FC:
                resB = Hangul2BrailleSpecific.Learnig_hangul(word);
                break;
            case TABLE_ABB:
                resB = Hangul2BrailleSpecific.Learnig_Grammar(word);
                break;
            case TABLE_NUM:
                resB = Hangul2BrailleSpecific.Learning_Number(word);
                break;
            case TABLE_WORD:
                resB = Hangul2Braille.text(word);
                break;
        }

        return resB.toString(); // 임시로 빈 문자열 반환
    }

    public boolean isWordExists(String table_name, String word) {
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name+" WHERE word = ?", new String[]{word});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public int[] getIndexesOfWord(String table_name, String word) {
//        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name+" WHERE word = ?", new String[]{word});
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name, null);
        int index = -1;
        int maxIndex = -1;

        if (cursor.moveToFirst()) {
            do {
                if(Objects.equals(word, cursor.getString(0))){
                    index = cursor.getPosition();
                    maxIndex = cursor.getCount()-1;
                    break;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return new int[]{index, maxIndex};
    }
    public int getCurIndexOfWord(String table_name, String word) {
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name+" WHERE word = ?", new String[]{word});
        int index = cursor.getPosition();
        cursor.close();
        return index;
    }
    public String getBrailleOfWord(String table_name, String word) {
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name+" WHERE word = ?", new String[]{word});
        String braille = "";

        if (cursor.moveToFirst()) {
            braille = cursor.getString(1);
        }

        cursor.close();
        return braille;
    }

    public String[] getInfoOfIndex(String table_name, int index){
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name, null);
        String word = "";
        String braille = "";
        if (cursor.moveToFirst()) {
            do {
                if(index == cursor.getPosition()){
                    word = cursor.getString(0);
                    braille = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return new String[]{word, braille};
    }


    public void insertWord(String table_name, String word, String braille) {
        String sql = "INSERT INTO "+table_name+" (word, braille) VALUES (?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, word);
        statement.bindString(2, braille);
        statement.executeInsert();
    }

    public void deleteWord(String table_name, String word) {
        String sql = "DELETE FROM "+table_name+" WHERE word = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, word);
        statement.executeUpdateDelete();
    }


    private void displayDatabase(String table_name) {
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name, null);
        StringBuilder stringBuilder = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(0);
                String braille = cursor.getString(1);
                stringBuilder.append("Word: ").append(word).append("\nBraille: ").append(braille).append("\n\n");
            } while (cursor.moveToNext());
        }

//        textView.setText(stringBuilder.toString());
        cursor.close();
    }

    public ArrayList<String> getDataList(String table_name){
        ArrayList<String> resArr = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+table_name, null);
        if (cursor.moveToFirst()) {
            cursor.moveToNext();
            do {
                String word = cursor.getString(0);
//                String braille = cursor.getString(1);
                resArr.add(word);
            } while (cursor.moveToNext());
        }
        return resArr;
    }

    public void clearTable(String table_name){
        db.execSQL("delete from "+table_name);
    }

    public void clearAllTables(){
        for (String table_name : TABLE_NAMES){
            clearTable(table_name);
        }
    }

}
