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

    private static String one_braille  = "";

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

    public ConnectedThread() {
        mmSocket = null;
        mmInStream = null;
        mmOutStream = null;
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
                    bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read

                    // 읽은 데이터를 로그로 출력
                    String receivedData = new String(buffer, 0, bytes);
                    if(receivedData.length() == 0){
                        continue;
                    }

                    if(receivedData.charAt(receivedData.length()-1) == ';') {
                        received += receivedData.substring(0, receivedData.length() - 1);

                        one_braille = received;
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

//    @Override
//    public synchronized void start() {
//        byte[] buffer = new byte[1024];  // buffer store for the stream
//        int bytes = 0; // bytes returned from read()
//        String received = "";
//
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
//
//                    if(receivedData.charAt(receivedData.length()-1) == ';') {
//                        received += receivedData.substring(0, receivedData.length() - 1);
//
//                        one_braille = received;
//                        break;
//                    }
//                    else {
//                        received += receivedData;
//                    }
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//
//                break;
//            }
//        }
//    }

    public String KeypadInput() {
//        start();
        run();
        String str = one_braille;
        one_braille = "";

        return str;
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