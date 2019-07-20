package com.projectThesis.SmartLife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDeviceDetailActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText editText_title;
    private Button add_device_end;

    private Intent intent;

    private String id_device;
    private String imageId;
    private String title;

    DatabaseHelper_Device mDatabaseHelper_device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_device_detail);
        setTitle("DETAIL DEVICE");

        mDatabaseHelper_device = new DatabaseHelper_Device(this);

        editText_title = (EditText) findViewById(R.id.input_title_addDevice);
        add_device_end = (Button) findViewById(R.id.button_add_device_selected_end);

        intent = getIntent();
        id_device = intent.getStringExtra("id_device");

        imageId = "testtext";

        add_device_end.setOnClickListener(this);
        editText_title.addTextChangedListener(this);

    }

    @Override
    public void onClick(View v) {
        title = editText_title.getText().toString();
        if (editText_title.length() != 0) {
            AddData(id_device, imageId, title);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else {
            toastMessage("You must put something in the text field!");
        }
    }

    public void AddData(String id_device, String imageId, String title) {
        boolean insertData = mDatabaseHelper_device.addData(id_device, imageId, title);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        boolean isReady = editText_title.getText().toString().length() > 2;
        add_device_end.setEnabled(isReady);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}


