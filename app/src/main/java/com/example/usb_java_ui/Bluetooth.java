package com.example.usb_java_ui;

import static com.example.usb_java_ui.BluetoothConnection.REQUEST_ENABLE_BT;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.Objects;

public class Bluetooth extends MyAppActivity {

    TextView txt_pt;
    TextView textStatus;
    private Button btnParied, btnSearch;
    ListView listView;


    private BluetoothConnection mBC;
    private ConnectedThread connectedThread;

    private ObjectTree OT_root;


    protected void VoiceModeOn(){
        super.VoiceModeOn();
        OT_root = new ObjectTree().rootObject();
        ObjectTree OT_blue = new ObjectTree().initObject(txt_pt);
        OT_blue.addChildViewArr(new View[]{btnParied});
        MyFocusManager.viewArrFocusL(this, new View[]{txt_pt, btnParied}, getTTS_import());
        OT_root.addChild(OT_blue);
        getTouchpad().setCurObj(OT_root.getChildObjectOfIndex(0));

    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
        String deviceN = sp_bluetooth.getString("DN", "isNot");

        if(!Objects.equals(deviceN, "isNot")) {
            connectedThread = BluetoothConnection.connectedThread;
            textStatus.setText(deviceN);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.bluetooth);
        super.onCreate(savedInstanceState);


        txt_pt = findViewById(R.id.pageTitle);
//  Bluetooth
        mBC = new BluetoothConnection(getApplicationContext());

        ActivityCompat.requestPermissions(Bluetooth.this, mBC.getPermission_list(), 1);

        // Enable bluetooth

        if (!mBC.getBtAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // variables
        textStatus = (TextView) findViewById(R.id.txt_status);
        btnParied = findViewById(R.id.btn_pairedD);
        btnSearch = (Button) findViewById(R.id.btn_searchD);
//        btnBraille = (Button) findViewById(R.id.btn_braille);
//        btnQuiz = (Button) findViewById(R.id.btn_quiz);
//        btnBack = (Button) findViewById(R.id.btn_back);
//        btnNext = (Button) findViewById(R.id.btn_next);
//        txtText = findViewById(R.id.txtText);
        listView = (ListView) findViewById(R.id.lstv_bluetoothD);

        btnParied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBC.clearArray();
                if (mBC.getPairedDevices().size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : mBC.getPairedDevices()) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        mBC.addBtArrAdt(deviceName);
                        mBC.addDAddArr(deviceHardwareAddress);
//                        if (getSp_setting().getBoolean("voiceChecked", false)){
//
//                            @SuppressLint("ResourceType") TextView usb =  mBC.getBtArrayAdapter().getView(0, new View(getApplicationContext()), listView).findViewById(android.R.layout.simple_list_item_1);
//                            OT_root.addChild(new ObjectTree().initObject(usb));
//                        }
                    }
                }
            }
        });


        // Show paired devices

        listView.setAdapter(mBC.getBtArrayAdapter());
        listView.setOnItemClickListener(new myOnItemClickListener());

//  Bluetooth

    }

    public class myOnItemClickListener implements AdapterView.OnItemClickListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), mBC.getBtArrayAdapter().getItem(position), Toast.LENGTH_SHORT).show();


            final String name = mBC.getBtArrayAdapter().getItem(position); // get name
            SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
            String deviceN = sp_bluetooth.getString("DN", "isNot");
            if(Objects.equals(deviceN, name)){
                view.setClickable(false);
                return;
            }

            textStatus.setText("try...");

            final String address = mBC.getDeviceAddressArray().get(position); // get address
            boolean flag = true;

            BluetoothDevice device = mBC.getBtAdapter().getRemoteDevice(address);

            // create & connect socket
            try {
                mBC.setBtSocket(device);
                mBC.getBtSocket().connect();
            } catch (IOException e) {
                flag = false;
                textStatus.setText("연결에 실패했습니다.");
                e.printStackTrace();
            }

            // start bluetooth communication
            if (flag) {
                textStatus.setText(name);
                mBC.setConnectedThread();
                mBC.getConnectedThread().start();
//                SharedPreferences sp_bluetooth = getSharedPreferences("bluetoothDN", MODE_PRIVATE);
                SharedPreferences.Editor spe_bluetooth = sp_bluetooth.edit();
                spe_bluetooth.putString("DN", name);
                spe_bluetooth.apply();
            }

        }
    }


}
