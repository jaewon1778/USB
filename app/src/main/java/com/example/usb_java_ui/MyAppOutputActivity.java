package com.example.usb_java_ui;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MyAppOutputActivity extends MyAppActivity{

    private ConnectedThread connectedThread;

    protected ConnectedThread getConnectedThread(){
        return connectedThread;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
        String deviceN = sp_bluetooth.getString("DN", "isNot");

        if(!Objects.equals(deviceN, "isNot")) {
            connectedThread = BluetoothConnection.connectedThread;
        }


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendBraille(new ArrayList<int[]>(),0, 0);
    }
    public void sendBraille(ArrayList<int[]> resList, int send_idx, int cnt) {
        ArrayList<int[]> brailleList = new ArrayList<>();
        if(cnt == 0) {
            brailleList.add(new int[]{0,0,0,0,0,0});
        }
        else {
            for(int j = 0; j < cnt; j++) {
                brailleList.add(resList.get(send_idx*3 + j));
            }
        }

        String row = String.valueOf(brailleList.size());
        String braille = Arrays.deepToString(brailleList.toArray());
        braille = row + braille + ";";  //끝에 ; 추가
        Log.d("braille", braille);
        if(connectedThread!=null){ Log.d("braille", braille);connectedThread.write(braille); }
    }

    public ArrayList<int[]> StringToBraille(String str){
        Log.d("str", str);
        String[] strArr = str.split("");
        Log.d("strArr", Arrays.toString(strArr));
        String[] strArrFor = Arrays.copyOfRange(strArr, 2, strArr.length-1);
        Log.d("strArrFor", Arrays.toString(strArrFor));
        ArrayList<int[]> braille = new ArrayList<>();
        int[] newB = {0,0,0,0,0,0};
        int indexB = 0;
        for (String one : strArrFor){

            if(Objects.equals(one, "1")){
                newB[indexB] = 1;
                indexB++;
            }
            else if (Objects.equals(one, "0")){
                indexB++;
            }
            else if (Objects.equals(one, "]")) {
                braille.add(newB);
                newB = new int[]{0, 0, 0, 0, 0, 0};
                indexB = 0;
            }
        }
//        Log.d("strArrBra", String.valueOf(braille));
        return braille;
    }

}
