package com.example.usb_java_ui;

import android.bluetooth.BluetoothSocket;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    private String one_braille  = "";

    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes = 0; // bytes returned from read()
        String received = "";

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.available();
                if (bytes != 0) {
                    buffer = new byte[1024];
                    SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                    bytes = mmInStream.available(); // how many bytes are ready to be read?
                    //Log.d("bytes", String.valueOf(bytes));
                    bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read

                    // 읽은 데이터를 로그로 출력
                    String receivedData = new String(buffer, 0, bytes);
                    //Log.d("Received Data", receivedData);
                    //Log.d("Data length", String.valueOf(receivedData.length()));
                    //receivedData = receivedData.substring(0, receivedData.length() - 2);
//                    Log.d("Received Data same?", receivedData);
//                    Log.d("Data length 8?", String.valueOf(receivedData.length()));
//                    Log.d("compare", String.valueOf(receivedData == "000000"));

//                    if(receivedData.contains("@") && receivedData.contains(";")) {
//                        received_braille = "";
//                        received_braille += receivedData.substring(1, receivedData.length() - 1);
//                        Log.d("received_braille", received_braille);
//                        received_braille = "";
//                        //break;
//                    }
//                    else if(receivedData.contains("@")) {
//                        received_braille = "";
//                        received_braille += receivedData.substring(1);
//                    }
//                    else if(receivedData.contains(";")) {
//                        received_braille += receivedData.substring(0, receivedData.length() - 1);
//                        Log.d("received_braille", received_braille);
//                        received_braille = "";
//                        //break;
//                    }

                    if(receivedData.charAt(receivedData.length()-1) == ';') {
                        received += receivedData.substring(0, receivedData.length() - 1);
                        Log.d("received_braille", received);
                        Log.d("received_braille size", String.valueOf(received.length()));
                        //Log.d("compare", String.valueOf(received.equals("000000")));
                        Log.d("=========", "===============================");

                        one_braille = received;
                        received = "";
                        break;
                    }
                    else {
                        received += receivedData;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();

                break;
            }
        }
    }

    //quiz test
//    @Override
//    public void run() {
//        byte[] buffer = new byte[1024];  // buffer store for the stream
//        int bytes = 0; // bytes returned from read()
//        // Keep listening to the InputStream until an exception occurs
//        while (true) {
//            try {
//                // Read from the InputStream
//                bytes = mmInStream.available();
//                if (bytes != 0) {
//                    buffer = new byte[1024];
//                    SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
//                    bytes = mmInStream.available(); // how many bytes are ready to be read?
//                    bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
//
//                    // 읽은 데이터를 로그로 출력
//                    String receivedData = new String(buffer, 0, bytes);
//                    Log.d("Received Data", receivedData);
//                    received = receivedData
//                    break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//
//                break;
//            }
//        }
//    }

    //quiz execute
    public ArrayList<int[]> quizresult() {
        ArrayList<int[]> quizarray = new ArrayList<>();
        while(true) {
            one_braille = "";
            run();
            if(one_braille.length() == 1) {
                if(one_braille.charAt(0) == 'A') {
                    break;
                }
                else if(one_braille.charAt(0) == '@') {
                    if(quizarray.size() != 0) {
                        quizarray.remove(quizarray.size() - 1);
                    }
                }
                else {
                    continue;
                }
            }
            else if(one_braille.length() == 6){
                int[] one = {0, 0, 0, 0, 0, 0};
                //Log.d("one array", String.valueOf(one));
                for(int i = 0; i < 6; i++) {
                    if(one_braille.charAt(i) == '1') {
                        one[i] = 1;
                    }
                }
                //Log.d("one array", String.valueOf(one));
                quizarray.add(one);
                Log.d("quiz array", Arrays.deepToString(quizarray.toArray()));
            }
            else {
                continue;
            }
        }

        return quizarray;
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(String input) {
        byte[] bytes = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}
