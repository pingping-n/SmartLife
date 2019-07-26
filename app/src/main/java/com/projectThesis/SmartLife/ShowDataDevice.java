package com.projectThesis.SmartLife;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import java.util.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShowDataDevice extends AppCompatActivity {


    private final static String url = "http://146.148.42.206:4000/api/value/1/last";

    private ListView listView;
    private List items;
    private String id_device;

    private Intent intent;
    private Handler handler;
    private Runnable runnable;

    DatabaseHelper_Device_Data databaseHelper_device_data;
    private Cursor data1;
    private Cursor data2;
    private Cursor data3;
    private Cursor data4;
    private Cursor data5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_device);
        setTitle("title device");

        intent = getIntent();
        id_device = intent.getStringExtra("id_device");

        intent = new Intent(this, AddDeviceDataActivity.class);

        listView = (ListView) findViewById(R.id.listView_Device_Data);

        items = new ArrayList<>();
//        items.add(new CustomList_Device_Data("50.0", 1));
//        items.add(new CustomList_Device_Data("50.0", 2));

        databaseHelper_device_data = new DatabaseHelper_Device_Data(this);
        data1 = databaseHelper_device_data.getTypeV1(id_device);
        data2 = databaseHelper_device_data.getTypeV2(id_device);
        data3 = databaseHelper_device_data.getTypeV3(id_device);
        data4 = databaseHelper_device_data.getTypeV4(id_device);
        data5 = databaseHelper_device_data.getTypeV5(id_device);

        MultipleLayoutAdapter adapter = new MultipleLayoutAdapter(getApplicationContext(), items);
        listView.setAdapter(adapter);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                setShowDataAPI();

                //Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 5000);
            }
        }; handler.postDelayed(runnable, 5000);

//        getShowDataAPI(url);

    }

//    private void getShowDataAPI(String url) {
//        FetchData_ItemData process = new FetchData_ItemData(url);
//        process.execute();
//    }

    private void setShowDataAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
//                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = new JSONArray(response);
                            System.out.println(jsonArray.toString());
                            //we have the array named hero inside the object
                            //so here we are getting that json array
//                            JSONArray jsonArray = obj.getJSONArray("heroes");
                            //now looping through all the elements of the json array
                            JSONObject jsonObject;
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                //getting the json object of the particular index inside the array
//                                jsonObject = jsonArray.getJSONObject(i);
//                                datashow = jsonArray.getJSONObject(jsonArray.length()-1).toString();
//                                vt1 = jsonObject.getString("v1");
//                                vt2 = jsonObject.getString("v2");
//                                vt3 = jsonObject.getString("v3");
//                                vt4 = jsonObject.getString("v4");
//                                vt5 = jsonObject.getString("v5");
//                            }


                            jsonObject = jsonArray.getJSONObject(0);
                            System.out.println(jsonObject);
                            String vt1 = jsonObject.getString("v1");
                            String vt2 = jsonObject.getString("v2");
                            String vt3 = jsonObject.getString("v3");
                            String vt4 = jsonObject.getString("v4");
                            String vt5 = jsonObject.getString("v5");
                            items.add(new CustomList_Device_Data(vt1, Integer.parseInt(data1.getString(1))));
                            items.add(new CustomList_Device_Data(vt2, Integer.parseInt(data2.getString(1))));
                            items.add(new CustomList_Device_Data(vt3, Integer.parseInt(data3.getString(1))));
                            items.add(new CustomList_Device_Data(vt4, Integer.parseInt(data4.getString(1))));
                            items.add(new CustomList_Device_Data(vt5, Integer.parseInt(data5.getString(1))));


//                            Get key api
//                            Iterator<String> iter = jsonObject.keys();
//                            while (iter.hasNext()) {
//                                String key = iter.next();
//                                System.out.println(key);
//                                try {
//                                    Object value = jsonObject.get(key);
//                                } catch (JSONException e) {
//                                    // Something went wrong!
//                                }
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

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
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
        Intent intent = new Intent(this, MainActivity.class);
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
