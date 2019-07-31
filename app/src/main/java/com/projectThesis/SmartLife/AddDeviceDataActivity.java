package com.projectThesis.SmartLife;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddDeviceDataActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String url = "http://146.148.42.206:4000/api/value/";

    private Button add_device;
    private ListView listViewDevice;
    private ListViewAdapter_AddDevice listViewDeviceAdapter;
    private List<Device_View> device_viewList;

    private String id_device;
    private String v_device;
    private Intent intent;

    private String[] vt;

    DatabaseHelper_Device_Data mDatabaseHelper_device_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_device);
        setTitle("SELECT DATA");

        intent = getIntent();
        id_device = intent.getStringExtra("id_device");
        intent = new Intent(this, AddDeviceDataDetailActivity.class);
        mDatabaseHelper_device_data = new DatabaseHelper_Device_Data(this);

        add_device = (Button) findViewById(R.id.button_add_device_selected);
        listViewDevice = (ListView) findViewById(R.id.my_ListView_Device);

        getDeviceList();

        listViewDevice.setOnItemClickListener(onClickItemListener);
        add_device.setOnClickListener(this);
        add_device.setEnabled(false);
    }


    private List<Device_View> getDeviceList() {
        device_viewList = new ArrayList<>();
        vt = new String[6];
        for (int i = 1; i <= 5; i++) {
            vt[i] = "0";
        }
        new setShowDataAPI().execute();

        return device_viewList;
    }

    AdapterView.OnItemClickListener onClickItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setSelected(true);
            v_device = device_viewList.get(position).getDeviceId();
            add_device.setEnabled(true);
            //Toast.makeText(getApplicationContext(), "Select : " + id_device, Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onClick(View v) {
        intent.putExtra("id_device", id_device);
        intent.putExtra("v_device", v_device);

        startActivity(intent);
    }

    private class setShowDataAPI extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(getApplicationContext(),"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + id_device + "/last");

            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    System.out.println(jsonArray);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    for (String key : iterate(jsonObject.keys()))
                    {
                        if (!key.equals("_id") && !key.equals("id") && !key.equals("Date") && !key.equals("__v")) {
                            if (key.equals("v1")) {
                                vt[1] = jsonObject.getString(key);
                                System.out.println("key: " + key + ": " + vt[1]);
                                device_viewList.add(new Device_View(key, vt[1]));
                            }
                            if (key.equals("v2")) {
                                vt[2] = jsonObject.getString(key);
                                System.out.println("key: " + key + ": " + vt[2]);
                                device_viewList.add(new Device_View(key, vt[2]));
                            }
                            if (key.equals("v3")) {
                                vt[3] = jsonObject.getString(key);
                                System.out.println("key: " + key + ": " + vt[3]);
                                device_viewList.add(new Device_View(key, vt[3]));
                            }
                            if (key.equals("v4")) {
                                vt[4] = jsonObject.getString(key);
                                System.out.println("key: " + key + ": " + vt[4]);
                                device_viewList.add(new Device_View(key, vt[4]));
                            }
                            if (key.equals("v5")) {
                                vt[5] = jsonObject.getString(key);
                                System.out.println("key: " + key + ": " + vt[5]);
                                device_viewList.add(new Device_View(key, vt[5]));
                            }
                        }
                    }


                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            listViewDeviceAdapter = new ListViewAdapter_AddDevice(getApplicationContext(), R.layout.list_item_device, device_viewList);
            listViewDevice.setAdapter(listViewDeviceAdapter);
        }
    }
    private <T> Iterable<T> iterate(final Iterator<T> i){
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return i;
            }
        };
    }
}
