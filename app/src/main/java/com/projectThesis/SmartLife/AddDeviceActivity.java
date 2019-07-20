package com.projectThesis.SmartLife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddDeviceActivity extends AppCompatActivity {

    private Button add_device;
    private ListView listViewDevice;
    private ListViewAdapter_AddDevice listViewDeviceAdapter;
    private List<Device_View> device_viewList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_device);

        add_device = (Button) findViewById(R.id.button_add_device);
        listViewDevice = (ListView) findViewById(R.id.my_ListView_Device);

        getDeviceList();

        listViewDeviceAdapter = new ListViewAdapter_AddDevice(getApplicationContext(), R.layout.list_item_device, device_viewList);
        listViewDevice.setAdapter(listViewDeviceAdapter);

        //listViewDevice.setOnItemClickListener(onItemClick);
        listViewDevice.setOnItemSelectedListener(onItemSelectedListener);
        add_device.setOnClickListener(onClickButton);
    }


    private List<Device_View> getDeviceList() {
        device_viewList = new ArrayList<>();
        device_viewList.add(new Device_View(0, "111111111 SMART LIFE"));
        device_viewList.add(new Device_View(1, "222222222 SMART LIFE"));

        return device_viewList;
    }

//    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            Toast.makeText(getApplicationContext(), device_viewList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//
//        }
//    };

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                view.setSelected(true);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnClickListener onClickButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


}
