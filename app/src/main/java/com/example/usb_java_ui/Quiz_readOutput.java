package com.example.usb_java_ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Quiz_readOutput extends MyAppOutputActivity {

    private SpeechRecognizer mRecognizer;
    private Intent intent;
    private String str = "default";
    private final int PERMISSION = 1;

    private String keyStr;
    private ArrayList<int[]> resList;
    private int[] randomIndex;
    private String[] AnswerArr;
    private int curIndex;


    private TextView txt_str;
    private ImageButton btn_nextQz;
    private ImageButton btn_prevQz;
    private Button btn_output;
    private ImageButton btn_nextO;
    private ImageButton btn_prevO;
    private int send_idx;
    private int max_idx;
    private int rem;
    private Button btn_reSpeak;
    private ImageButton btn_STT;
    private ImageButton btn_submit;
    private EditText edt_myAnswer;

    private GridView qzr_grid_output;
    private GridOutputAdapter qzr_gridOAdt;
    private DBManager dbManager;



    protected void VoiceModeOn(){
        super.VoiceModeOn();
        ObjectTree OT_root = new ObjectTree().rootObject();

        // make objTree

        btn_prevQz.setContentDescription("이전 퀴즈");
        btn_nextQz.setContentDescription("다음 퀴즈");
        btn_prevO.setContentDescription("이전 출력");
        btn_nextO.setContentDescription("다음 출력");
        btn_STT.setContentDescription("음성 인식");
        btn_submit.setContentDescription("제출");
        OT_root.addChildViewArr(new View[]{txt_str,btn_output,btn_nextO,btn_prevO,btn_STT,btn_submit});
        OT_root.getChildObjectOfIndex(0).addChildViewArr(new View[]{btn_nextQz,btn_prevQz});
//        myFocusManager.txtFocusL(this, new TextView[]{txt_str},getTTS_import());
//        myFocusManager.btnFocusL(new Button[]{btn_output}, getTTS_import());
//        myFocusManager.imgBtnFocusL(new ImageButton[]{btn_nextO,btn_prevO,btn_nextQz,btn_prevQz,btn_STT,btn_submit}, tts_import);
        // make objTree
        MyFocusManager.viewArrFocusL(this, new View[]{txt_str,btn_output,btn_nextO,btn_prevO,btn_STT,btn_submit,btn_nextQz,btn_prevQz}, getTTS_import());
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));
        OT_root.getChildObjectOfIndex(0).getCurrentView().requestFocus();

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.quiz_read_output);
        super.onCreate(savedInstanceState);


        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO},PERMISSION);
        txt_str = findViewById(R.id.txt_qzrStr);
        btn_nextQz = findViewById(R.id.btn_nextQz);
        btn_prevQz = findViewById(R.id.btn_prevQz);
        btn_prevO = findViewById(R.id.btn_prevOutput);
        btn_output = findViewById(R.id.btn_output);
        btn_nextO = findViewById(R.id.btn_nextOutput);
        btn_reSpeak = findViewById(R.id.btn_listen);
        btn_reSpeak.setEnabled(false);
        btn_STT = findViewById(R.id.btn_speakAnswer);
        btn_submit = findViewById(R.id.btn_submitAnswer);
        edt_myAnswer = findViewById(R.id.edt_myAnswer);

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE) ;
        edt_myAnswer.setBackground(getDrawable(R.drawable.border_fill_gray));

        dbManager = new DBManager();
        dbManager.checkDB(this);

        qzr_grid_output = (GridView) findViewById(R.id.grdv_qzrBrailles);
        String table_name = getIntent().getStringExtra("keyTableName");

        int[] indexes = dbManager.getIndexesOfWord(table_name,"더미");
        int maxIndex = indexes[1];
        randomIndex = generateShuffledArray(1, maxIndex);
        AnswerArr = new String[maxIndex];
        Arrays.fill(AnswerArr, "");
        curIndex = 0;
        String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
        keyStr = info[0];
        resList = StringToBraille(info[1]);
        setQzBraille(resList);

        btn_prevQz.setEnabled(false);
        btn_prevQz.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                curIndex--;
                if (curIndex == 0) btn_prevQz.setEnabled(false);
                if (curIndex == maxIndex-2) btn_nextQz.setEnabled(true);
                if (!Objects.equals(AnswerArr[curIndex], "")) {
                    edt_myAnswer.setText(AnswerArr[curIndex]);
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_green));
                    edt_myAnswer.setFocusable(false);
                    edt_myAnswer.setFocusableInTouchMode(false);
                    btn_STT.setEnabled(false);
                    btn_submit.setEnabled(false);
                    btn_reSpeak.setEnabled(true);
                } else {
                    edt_myAnswer.setText("");
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_fill_gray));
                    edt_myAnswer.setFocusable(true);
                    edt_myAnswer.setFocusableInTouchMode(true);
                    btn_STT.setEnabled(true);
                    btn_submit.setEnabled(true);
                    btn_reSpeak.setEnabled(false);
                }
                String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
                keyStr = info[0];
                resList = StringToBraille(info[1]);
                setQzBraille(resList);
            }
        });
        btn_nextQz.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                curIndex++;
                if (curIndex == 1) btn_prevQz.setEnabled(true);
                if (curIndex == maxIndex-1) btn_nextQz.setEnabled(false);
                if (!Objects.equals(AnswerArr[curIndex], "")) {
                    edt_myAnswer.setText(AnswerArr[curIndex]);
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_green));
                    edt_myAnswer.setFocusable(false);
                    edt_myAnswer.setFocusableInTouchMode(false);
                    btn_STT.setEnabled(false);
                    btn_submit.setEnabled(false);
                    btn_reSpeak.setEnabled(true);
                } else {
                    edt_myAnswer.setText("");
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_fill_gray));
                    edt_myAnswer.setFocusable(true);
                    edt_myAnswer.setFocusableInTouchMode(true);
                    btn_STT.setEnabled(true);
                    btn_submit.setEnabled(true);
                    btn_reSpeak.setEnabled(false);
                }
                String[] info = dbManager.getInfoOfIndex(table_name,randomIndex[curIndex]);
                keyStr = info[0];
                resList = StringToBraille(info[1]);
                setQzBraille(resList);
            }
        });

        btn_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = 3;
                send_idx = 0;
                if(max_idx == 0) {
                    cnt = rem;
                }
                sendBraille(resList,send_idx,cnt);
            }
        });

        btn_nextO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(send_idx == max_idx) {
                    Toast.makeText(getApplicationContext(), "마지막 출력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                send_idx++;
                int cnt = 3;
                if(send_idx == max_idx){
                    cnt = rem;
                }
                sendBraille(resList,send_idx,cnt);
            }
        });

        btn_prevO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(send_idx == 0) {
                    Toast.makeText(getApplicationContext(), "첫 번째 출력입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                send_idx--;
                int cnt = 3;
                if(send_idx+max_idx == 0){
                    cnt = rem;
                }
                sendBraille(resList,send_idx,cnt);
            }
        });

        btn_reSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTTS_import().speakOut(keyStr);
            }
        });

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정

        btn_STT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_myAnswer.setBackground(getDrawable(R.drawable.border_fill_gray));
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext()); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                mRecognizer.startListening(intent); // 듣기 시작
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                String myAnswer = edt_myAnswer.getText().toString();
                if (myAnswer.equals(keyStr) || myAnswer.equals(changeKey(keyStr))){
                    AnswerArr[curIndex] = myAnswer;
                    edt_myAnswer.setText(keyStr);
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_green));
                    if ( edt_myAnswer.isFocused())
                        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    edt_myAnswer.setFocusable(false);
                    edt_myAnswer.setFocusableInTouchMode(false);
                    btn_STT.setEnabled(false);
                    btn_submit.setEnabled(false);
                    btn_reSpeak.setEnabled(true);
                    getTTS_import().speakOut("정답입니다");
                }
                else {
                    edt_myAnswer.setBackground(getDrawable(R.drawable.border_red));
                    getTTS_import().speakOut("오답입니다");
                }
            }
        });

    }

    public String changeKey(String inputStr){
        String outputKey = inputStr;
        switch (inputStr) {
            case "ㅏ":
                outputKey = "아";
                break;
            case "ㅑ":
                outputKey = "야";
                break;
            case "ㅓ":
                outputKey = "어";
                break;
            case "ㅕ":
                outputKey = "여";
                break;
            case "ㅗ":
                outputKey = "오";
                break;
            case "ㅛ":
                outputKey = "요";
                break;
            case "ㅜ":
                outputKey = "우";
                break;
            case "ㅠ":
                outputKey = "유";
                break;
            case "ㅡ":
                outputKey = "으";
                break;
            case "ㅣ":
                outputKey = "이";
                break;
            case "ㅐ":
            case "ㅔ":
                outputKey = "에";
                break;
            case "ㅒ":
            case "ㅖ":
                outputKey = "예";
                break;
            case "ㅘ":
                outputKey = "와";
                break;
            case "ㅚ":
            case "ㅞ":
            case "ㅙ":
                outputKey = "왜";
                break;
            case "ㅝ":
                outputKey = "워";
                break;
            case "ㅟ":
                outputKey = "위";
                break;
            case "ㅢ":
                outputKey = "의";
                break;
        }
        return outputKey;
    }

    public void setQzBraille(ArrayList<int[]> braille){
        qzr_gridOAdt = new GridOutputAdapter(this);

        for (int[] BItem : braille) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("@drawable/b");
            for (int dot : BItem){
                stringBuilder.append(dot);
            }
            String resName = stringBuilder.toString();

            String packName = this.getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packName);

            qzr_gridOAdt.setBItem(resId);
        }
        int numcol = braille.size();
        int line = 1;

        if (numcol > 6) {

            numcol = 6;
            line = 1 + (braille.size()-1)/6;

        }

        qzr_grid_output.setNumColumns(numcol);
        qzr_grid_output.setAdapter(qzr_gridOAdt);

        ViewGroup.LayoutParams o_param = qzr_grid_output.getLayoutParams();
        if(o_param == null) {
            o_param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        o_param.height = 160 * line;
        qzr_grid_output.setLayoutParams(o_param);

        send_idx = 0;
        max_idx = (braille.size()-1) / 3;
        rem = braille.size() % 3 == 0 ? 3 : braille.size() % 3;
    }

    public static int[] generateShuffledArray(int start, int end) {
        int[] array = new int[end - start + 1];
        for (int i = start; i <= end; i++) {
            array[i - start] = i;
        }

        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        return array;
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
//                edt_myAnswer.setText(matches.get(i));
//            }
            str = matches.get(0);
            str = str.replaceAll("\n","");
            str = str.replaceAll(" ","");
            str = str2con(str);
            edt_myAnswer.setText(str);
            getTTS_import().speakOut(str);

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

    public String str2con(String inputStr){
        String outputStr = "";
        switch (inputStr) {
            case "기역":
                outputStr = "ㄱ";
                break;
            case "니은":
                outputStr = "ㄴ";
                break;
            case "디귿":
                outputStr = "ㄷ";
                break;
            case "리을":
                outputStr = "ㄹ";
                break;
            case "미음":
                outputStr = "ㅁ";
                break;
            case "비읍":
                outputStr = "ㅂ";
                break;
            case "시옷":
                outputStr = "ㅅ";
                break;
            case "이응":
                outputStr = "ㅇ";
                break;
            case "지읒":
                outputStr = "ㅈ";
                break;
            case "치읓":
                outputStr = "ㅊ";
                break;
            case "키읔":
                outputStr = "ㅋ";
                break;
            case "티읕":
                outputStr = "ㅌ";
                break;
            case "피읖":
                outputStr = "ㅍ";
                break;
            case "히읗":
                outputStr = "ㅎ";
                break;
            case "쌍기역":
                outputStr = "ㄲ";
                break;
            case "쌍디귿":
                outputStr = "ㄸ";
                break;
            case "쌍시옷":
                outputStr = "ㅆ";
                break;
            case "쌍지읒":
                outputStr = "ㅉ";
                break;
            case "기역시옷":
                outputStr = "ㄳ";
                break;
            case "니은지읏":
                outputStr = "ㄵ";
                break;
            case "니은히읗":
                outputStr = "ㄶ";
                break;
            case "리을기역":
                outputStr = "ㄺ";
                break;
            case "리을미음":
                outputStr = "ㄻ";
                break;
            case "리을비읍":
                outputStr = "ㄼ";
                break;
            case "리을시옷":
                outputStr = "ㄽ";
                break;
            case "리을티읕":
                outputStr = "ㄾ";
                break;
            case "리을피읖":
                outputStr = "ㄿ";
                break;
            case "리을히읗":
                outputStr = "ㅀ";
                break;
            case "비읍시옷":
                outputStr = "ㅄ";
                break;
            default:
                outputStr = inputStr;
                break;
        }
        return outputStr;
    }

}
