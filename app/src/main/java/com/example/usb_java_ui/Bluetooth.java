package com.example.usb_java_ui;

import static com.example.usb_java_ui.BluetoothConnection.REQUEST_ENABLE_BT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity {

    TextView textStatus;
    Button btnParied, btnSearch, btnNext, btnBack, btnBraille, btnQuiz;
    ListView listView;
    EditText txtText;


    private ArrayList<Object> Result = new ArrayList<>();

    private int send_idx = 0;
    private int max_idx = 0;

    private BluetoothConnection mBC;
    private ConnectedThread connectedThread;
    @Override
    protected void onDestroy() {
//        unregisterReceiver(receiver);

        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("USB_Project");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        btnParied = (Button) findViewById(R.id.btn_pairedD);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bluetooth_menu, menu);

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
//                try {
//                    mBC.getBtSocket().close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        try {
//            mBC.getBtSocket().close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        super.onBackPressed();
    }
}
