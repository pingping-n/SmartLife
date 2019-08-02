package com.projectThesis.SmartLife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class AddDeviceDataDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Button add_device_end;
    private ListView listView;

    DatabaseHelper_Device_Data mDatabaseHelper_device_data;
    private String data1;
    private String data2;
    private String data3;
    private String data4;
    private String data5;

    private Intent intent;

    private List<CustomList_Device_Data> items;
    private String id_device;
    private String v_device;
    private String v1 = "0";
    private String v2 = "0";
    private String v3 = "0";
    private String v4 = "0";
    private String v5 = "0";

    private ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_device);
        setTitle("select layout");
        viewDialog = new ViewDialog(this);

        add_device_end = (Button) findViewById(R.id.button_add_device_selected);
        listView = (ListView) findViewById(R.id.my_ListView_Device);

        intent = getIntent();
        id_device = intent.getStringExtra("id_device");
        v_device = intent.getStringExtra("v_device");

        mDatabaseHelper_device_data = new DatabaseHelper_Device_Data(this);
        data1 = mDatabaseHelper_device_data.getTypeV1(id_device);
        data2 = mDatabaseHelper_device_data.getTypeV2(id_device);
        data3 = mDatabaseHelper_device_data.getTypeV3(id_device);
        data4 = mDatabaseHelper_device_data.getTypeV4(id_device);
        data5 = mDatabaseHelper_device_data.getTypeV5(id_device);

        items = new ArrayList<>();
        items.add(new CustomList_Device_Data("72.6", 1, "L1"));
        items.add(new CustomList_Device_Data("68.2", 2, "L2"));
        items.add(new CustomList_Device_Data("49.7", 3, "L3"));
        items.add(new CustomList_Device_Data("88.7", 4, "L4"));
        items.add(new CustomList_Device_Data("28.7", 5, "L5"));


        MultipleLayoutAdapter adapter = new MultipleLayoutAdapter(this, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onClickItemListener);

        add_device_end.setText("Done");
        add_device_end.setOnClickListener(this);
        add_device_end.setEnabled(false);

        // hide keyboard when touch
        LinearLayout showListDevice;
        showListDevice = (LinearLayout)findViewById(R.id.show_list_device);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(showListDevice.getWindowToken(), 0);


    }

    AdapterView.OnItemClickListener onClickItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            setLayoutData(v_device, position + 1);
            add_device_end.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Select Type : " + (position + 1), Toast.LENGTH_SHORT).show();
        }
    };

    public void setLayoutData(String temp, int position) {
        if (temp.equals("v1"))
        {
            v1 = String.valueOf(position);
            v2 = data2;
            v3 = data3;
            v4 = data4;
            v5 = data5;
        } else if(temp.equals("v2")) {
            v1 = data1;
            v2 = String.valueOf(position);
            v3 = data3;
            v4 = data4;
            v5 = data5;
        } else if (temp.equals("v3")) {
            v1 = data1;
            v2 = data2;
            v3 = String.valueOf(position);
            v4 = data4;
            v5 = data5;
        } else if (temp.equals("v4")) {
            v1 = data1;
            v2 = data2;
            v3 = data3;
            v4 = String.valueOf(position);
            v5 = data5;
        } else if(temp.equals("v5")) {
            v1 = data1;
            v2 = data2;
            v3 = data3;
            v4 = data4;
            v5 = String.valueOf(position);
        }
    }

    @Override
    public void onClick(View v) {
        viewDialog.showDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewDialog.hideDialog();
                AddData(id_device, v1, v2, v3, v4, v5);
                Intent intent = new Intent(getApplicationContext(), ShowDataDevice.class);
                intent.putExtra("id_device", id_device);
                startActivity(intent);
            }
        }, 1000);
    }

    public void AddData(String id_device, String v1, String v2, String v3, String v4, String v5) {

        if (mDatabaseHelper_device_data.CheckIsDataAlreadyInDBorNot(id_device)) {
            boolean updateData = mDatabaseHelper_device_data.updateName(id_device, v1, v2, v3, v4, v5);
            if (updateData) {
                toastMessage("Data Successfully Inserted!");
            } else {
                toastMessage("Something went wrong");
            }
        } else {
            boolean insertData = mDatabaseHelper_device_data.addData(id_device, v1, v2, v3, v4, v5);

            if (insertData) {
                toastMessage("Data Successfully Updated!");
            } else {
                toastMessage("Something went wrong");
            }
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
