package com.example.edu.bluetoothadapter_1122;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener,View.OnClickListener {
    private BluetoothAdapter bluetoothAdapter = null;
    private Set<BluetoothDevice> pairedDeviceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.buttonStart)).setOnClickListener(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    @Override
    public void onClick(View v) {
        ListView listViewPairedDeviceList = findViewById(R.id.listView);
        pairedDeviceList = bluetoothAdapter.getBondedDevices();
        ArrayList pairedList = new ArrayList();
        for (BluetoothDevice bt : pairedDeviceList) {
            pairedList.add(bt.getName()+"\n"+bt.getAddress());
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,pairedList);
        listViewPairedDeviceList.setAdapter(adapter);
        listViewPairedDeviceList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String info = ((TextView)view).getText().toString();
        String address = info.substring(info.length()-17);
//        Toast.makeText(this,address,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(view.getContext(),BlutoothClientActivity.class);
        intent.putExtra("device_address",address);
        startActivity(intent);
    }
}
