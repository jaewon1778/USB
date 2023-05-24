package com.example.usb_java_ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class STT extends AppCompatActivity {

    SpeechRecognizer mRecognizer;
    Intent intent;
    EditText et_STT;
    String str = "default";
    final int PERMISSION = 1;

    private DBManager dbManager;
    private Button btn_saveWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stt);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO},PERMISSION);
        et_STT = findViewById(R.id.et_STTContent);

        Button btn_del = findViewById(R.id.btn_STTDelete);
        btn_del.setOnClickListener(view -> et_STT.setText(null));

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정

        Button btn_startRec = findViewById(R.id.btn_startSTT);
        btn_startRec.setOnClickListener(view -> {
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext()); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
            mRecognizer.setRecognitionListener(listener); // 리스너 설정
            mRecognizer.startListening(intent); // 듣기 시작
        });

        dbManager = new DBManager();
        dbManager.checkDB(this);
        btn_saveWord = findViewById(R.id.btn_STTSave);
        btn_saveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typingWord = et_STT.getText().toString();
                et_STT.setText("");
                if (typingWord.equals("")){
                    Toast.makeText(getApplicationContext(), "단어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dbManager.isWordExists(DBManager.TABLE_WORD,typingWord)){
                    Toast.makeText(getApplicationContext(), "이미 존재하는 단어입니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbManager.insertWord(DBManager.TABLE_WORD, typingWord, braille2String(Hangul2Braille.text(typingWord)));
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

    private final RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // 말하기 시작할 준비가되면 호출
            Toast.makeText(getApplicationContext(), "음성인식 시작", Toast.LENGTH_SHORT).show();
            Log.d("tst5", "시작");
        }

        @Override
        public void onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // 입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            // 말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                case SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED:
                case SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE:
                case SpeechRecognizer.ERROR_SERVER_DISCONNECTED:
                case SpeechRecognizer.ERROR_TOO_MANY_REQUESTS:
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러 발생 : " + message, Toast.LENGTH_SHORT).show();
            Log.d("tst5", "onError: " + message);
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 recognizedTextEt에 단어를 이어줌
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            //test1
            Log.d("test1", arrayListToString(matches));
//            for (int i = 0; i < matches.size(); i++) {
//                et_STT.setText(matches.get(i));
//            }
            str = matches.get(0);
            str = str.replaceAll("\n","");
            str = str.replaceAll(" ","");
            et_STT.setText(str);

            //test2
            Log.d("test2", "str: " + str);
            //speakout test
            //speakOut2();
        }

        public String arrayListToString(ArrayList<String> arrayList) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : arrayList) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    };

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