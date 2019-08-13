package com.projectThesis.SmartLife;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.infideap.stylishwidget.view.Stylish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ShowDataDevice extends AppCompatActivity {
    private String username;
    private final static String url = "http://146.148.42.206:4000/api/value/";

    private ListView listView;
    private List<CustomList_Device_Data> items;
    private String id_device;
    private String title_device;

    private Intent intent;
    private Handler handler;
    private Runnable runnable;

    DatabaseHelper_Device_Data databaseHelper_device_data;
    private MultipleLayoutAdapter adapter;
    AlertDialog.Builder builder;
    private String[] data;
    private String[] vt;
    private String[] vTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fontFolder = "/";
        Stylish.getInstance().set(
                fontFolder.concat("multicolore.otf"),
                fontFolder.concat("multicolore.otf"),
                fontFolder.concat("multicolore.otf"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_device);

        intent = getIntent();
        id_device = intent.getStringExtra("id_device");
        title_device = intent.getStringExtra("title_device");
        username = intent.getStringExtra("username");
        this.setTitle(title_device);

        intent = new Intent(this, AddDeviceDataActivity.class);
        builder = new AlertDialog.Builder(this);
        listView = (ListView) findViewById(R.id.listView_Device_Data);

        databaseHelper_device_data = new DatabaseHelper_Device_Data(this);

        data = new String[6];
        data[1] = databaseHelper_device_data.getTypeV1(id_device);
        data[2] = databaseHelper_device_data.getTypeV2(id_device);
        data[3] = databaseHelper_device_data.getTypeV3(id_device);
        data[4] = databaseHelper_device_data.getTypeV4(id_device);
        data[5] = databaseHelper_device_data.getTypeV5(id_device);

        items = new ArrayList<>();
        vt = new String[6];
        vTitle = new String[6];
        for (int i = 1; i <= 5; i++) {
            vt[i] = "0";
            vTitle[i] = "v" + i;
        }

        for (int i = 1; i <= 5; i++) {
            vt[i] = "0";
            items.add(new CustomList_Device_Data(vt[i], Integer.parseInt(data[i]), vTitle[i]));
        }

        adapter = new MultipleLayoutAdapter(getApplicationContext(), items);
        listView.setAdapter(adapter);

        handler = new Handler();

        listView.setOnItemLongClickListener(onItemLongClick);

    }


    @Override
    protected void onStart() {
        super.onStart();
        runnable = new Runnable() {
            @Override
            public void run() {
                items = new ArrayList<>();
                new setShowDataAPI().execute();
                handler.postDelayed(this, 6000);
            }
        };
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
//            System.out.println(url + id_device + "/last");
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
//                    System.out.println(jsonArray);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                    vt[1] = jsonObject.getString("v1");
//                    vt[2] = jsonObject.getString("v2");
//                    vt[3] = jsonObject.getString("v3");
//                    vt[4] = jsonObject.getString("v4");
//                    vt[5] = jsonObject.getString("v5");
                    for (String key : iterate(jsonObject.keys()))
                    {
                        if (!key.equals("_id") && !key.equals("id") && !key.equals("Date") && !key.equals("__v")) {
                            if (key.equals("v1")) {
                                vt[1] = jsonObject.getString(key);
//                                System.out.println("key: " + key + ": " + vt[1]);
                            }
                            if (key.equals("v2")) {
                                vt[2] = jsonObject.getString(key);
//                                System.out.println("key: " + key + ": " + vt[2]);
                            }
                            if (key.equals("v3")) {
                                vt[3] = jsonObject.getString(key);
//                                System.out.println("key: " + key + ": " + vt[3]);
                            }
                            if (key.equals("v4")) {
                                vt[4] = jsonObject.getString(key);
//                                System.out.println("key: " + key + ": " + vt[4]);
                            }
                            if (key.equals("v5")) {
                                vt[5] = jsonObject.getString(key);
//                                System.out.println("key: " + key + ": " + vt[5]);
                            }
                        }
                    }

                    for (int i = 1; i <= 5; i++) {
                        items.add(new CustomList_Device_Data(vt[i], Integer.parseInt(data[i]), vTitle[i]));
                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
                                    "Device not have Unit!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
                                "Can not connect server! \nPlease check your internet",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new MultipleLayoutAdapter(getApplicationContext(), items);
            listView.setAdapter(adapter);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_device_data, menu);// Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.add_layout_device_data:

                //Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                intent.putExtra("id_device", id_device);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // On long click item delete
    AdapterView.OnItemLongClickListener onItemLongClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final int p = position + 1;
            final String id_devices = id_device;

            builder.setMessage("Do you want to delete v" + p + " ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            data[p] = "0";

                            DatabaseHelper_Device_Data tempdel = new DatabaseHelper_Device_Data(ShowDataDevice.this);
                            tempdel.updateName(id_devices, data[1], data[2], data[3], data[4], data[5]);

                            Toast.makeText(ShowDataDevice.this, "Item Deleted", Toast.LENGTH_LONG).show();

                            finish();
                            startActivity(getIntent());
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Confirm to delete?");
            alertDialog.show();

            return true;
        }
    };

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("nav_back", 1);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        handler.postDelayed(runnable, 1000);
        super.onResume();
    }

}


