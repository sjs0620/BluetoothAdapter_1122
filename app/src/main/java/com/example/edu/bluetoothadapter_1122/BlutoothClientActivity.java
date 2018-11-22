package com.example.edu.bluetoothadapter_1122;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.UUID;

public class BlutoothClientActivity extends AppCompatActivity implements View.OnClickListener {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String address;
    private BluetoothSocket bluetoothSocket;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blutooth_client);

        progressBar = findViewById(R.id.progressBar);
        Intent newint = getIntent();
        address = newint.getStringExtra("device_address");
        new ConnectBluetoothTask().execute();

        ((Button)findViewById(R.id.buttonUp)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonDown)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonLeft)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonright)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonCenter)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonAscTurn)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonBend)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonMe1)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonMe2)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonMoonWalker)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonObsMode)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonUpDown)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String message = "";
        switch (v.getId()){
            case R.id.buttonUp:
                message = "U"; //up
                break;
            case R.id.buttonDown:
                message = "D"; //Down
                break;
            case R.id.buttonCenter:
                message = "C"; //center
                break;
            case R.id.buttonLeft:
                message = "L"; //left
                break;
            case R.id.buttonright:
                message = "R"; //right
                break;
            case R.id.buttonMoonWalker:
                message = "a"; //moonWalker
                break;
            case R.id.buttonBend:
                message = "b"; //bend
                break;
            case R.id.buttonAscTurn:
                message = "c"; //ascending Turn
                break;
            case R.id.buttonObsMode:
                message = "d"; //obstacle Detector
                break;
            case R.id.buttonMe1:
            case R.id.buttonMe2:
                message = "1"; //up
                break;
        }

        try {
            bluetoothSocket.getOutputStream().write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectBluetoothTask extends AsyncTask<Void,Void,Void>{
        BluetoothAdapter bluetoothAdapter;
        private  boolean ConnectSucess = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... devices) {
            if(bluetoothSocket == null){
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
                try {
                    bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
             return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            progressBar.setVisibility(View.GONE);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }
}
