package com.projectThesis.SmartLife;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    private String username;
    private Button add_device;
    private ListView listViewDevice;
    private ListViewAdapter_AddDevice listViewDeviceAdapter;
    private List<Device_View> device_viewList;
    private ViewDialog viewDialog;

    private String id_device;
    private Intent intent;

    DatabaseHelper_Device mDatabaseHelper_device;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_device);
        setTitle("SELECT DEVICE");
        viewDialog = new ViewDialog(this);

        intent = getIntent();
        username = intent.getStringExtra("username");

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

        viewDialog.showDialog();
        getDeviceID(username);
        viewDialog.hideDialog();

        return device_viewList;
    }

    public boolean getDeviceID(String username) {
        String urlParameters  = "email=" + username;

        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        String request = "http://146.148.42.206:4000/api/UserDvID";
        URL url = null;
        try {
            url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);

            try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write( postData );
                wr.flush();
            }

            int responseCode = conn.getResponseCode();
//            System.out.println("\nSending 'POST' request to URL : " + url);
//            System.out.println("Post parameters : " + urlParameters);
//            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
//            System.out.println(response.toString());

            JSONObject jsonObject = new JSONObject(String.valueOf(response));
            JSONArray jsonArray = jsonObject.getJSONArray("devices");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
//                System.out.println(jo.getString("deviceId"));
//                System.out.println(jo.getString("deviceName"));
                device_viewList.add(new Device_View(jo.getString("deviceId"), jo.getString("deviceName")));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }


    AdapterView.OnItemClickListener onClickItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            id_device = device_viewList.get(position).getDeviceId();
            add_device.setEnabled(true);
            //Toast.makeText(getApplicationContext(), "Select : " + id_device, Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        intent = new Intent(this, AddDeviceDetailActivity.class);
        intent.putExtra("id_device", id_device);

        startActivity(intent);
    }
}
