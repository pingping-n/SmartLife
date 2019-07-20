package com.projectThesis.SmartLife;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add_device;
    private ListView listViewDevice;
    private ListViewAdapter_AddDevice listViewDeviceAdapter;
    private List<Device_View> device_viewList;

    private String id_device;
    private Intent intent;

    DatabaseHelper_Device mDatabaseHelper_device;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_device);
        setTitle("SELECT DEVICE");

        intent = new Intent(this, AddDeviceDetailActivity.class);
        mDatabaseHelper_device = new DatabaseHelper_Device(this);

        add_device = (Button) findViewById(R.id.button_add_device_selected);
        listViewDevice = (ListView) findViewById(R.id.my_ListView_Device);

        getDeviceList();

        listViewDeviceAdapter = new ListViewAdapter_AddDevice(getApplicationContext(), R.layout.list_item_device, device_viewList);
        listViewDevice.setAdapter(listViewDeviceAdapter);

        listViewDevice.setOnItemClickListener(onClickItemListener);
        add_device.setOnClickListener(this);
        add_device.setEnabled(false);
    }


    private List<Device_View> getDeviceList() {
        device_viewList = new ArrayList<>();
        device_viewList.add(new Device_View("1", "111111111 SMART LIFE"));
        device_viewList.add(new Device_View("2", "222222222 SMART LIFE"));

        return device_viewList;
    }

    AdapterView.OnItemClickListener onClickItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            id_device = device_viewList.get(position).getDeviceId();
            add_device.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Select : " + id_device, Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        intent.putExtra("id_device", id_device);

        startActivity(intent);
    }
}
