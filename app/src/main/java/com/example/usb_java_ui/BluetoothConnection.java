package com.example.usb_java_ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection {

    private UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    private BluetoothAdapter btAdapter;

    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> btArrayAdapter;
    private ArrayList<String> deviceAddressArray;
    public final static int REQUEST_ENABLE_BT = 1;

    public static BluetoothSocket btSocket = null;

    public static ConnectedThread connectedThread;

    private String[] permission_list = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
                btArrayAdapter.notifyDataSetChanged();
            }
        }
    };
    public BluetoothConnection(Context context){
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = btAdapter.getBondedDevices();
        btArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
    }

//    public void closeBluetooth() throws IOException {
//        btSocket.close();
//    }

    public void clearArray(){
        btArrayAdapter.clear();
        if (deviceAddressArray != null && !deviceAddressArray.isEmpty()) {
            deviceAddressArray.clear();
        }
    }

    public String[] getPermission_list() {
        return permission_list;
    }

    public BluetoothAdapter getBtAdapter() {
        return btAdapter;
    }

    public Set<BluetoothDevice> getPairedDevices() {
        return pairedDevices;
    }

    public void addBtArrAdt(String deviceName){
        btArrayAdapter.add(deviceName);
    }

    public void addDAddArr(String deviceHardwareAddress){
        deviceAddressArray.add(deviceHardwareAddress);
    }

    public ArrayAdapter<String> getBtArrayAdapter() {
        return btArrayAdapter;
    }

    public ArrayList<String> getDeviceAddressArray() {
        return deviceAddressArray;
    }


    public void setBtSocket(BluetoothDevice device) throws IOException {
        btSocket = createBluetoothSocket(device);
    }

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    public void setConnectedThread() {
        connectedThread = new ConnectedThread(btSocket);
    }

    public ConnectedThread getConnectedThread() {
        return connectedThread;
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
//            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }


    //    private ArrayList<Object> Result = new ArrayList<>();
//
//    private int send_idx = 0;
//    private int max_idx = 0;

//            ActivityCompat.requestPermissions(Bluetooth.this, permission_list, 1);
// Enable bluetooth

}
