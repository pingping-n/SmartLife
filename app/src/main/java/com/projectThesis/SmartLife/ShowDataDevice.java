package com.projectThesis.SmartLife;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.app.infideap.stylishwidget.view.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShowDataDevice extends AppCompatActivity {


    private static final String url = "http://146.148.42.206:4000/api/value/1";

    private ListView listView;


    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_device);
        setTitle("title device");

        listView = (ListView) findViewById(R.id.listView_Device_Data);

        List items = new ArrayList<>();
        items.add(new CustomList_Device_Data("50.0", 1));
        items.add(new CustomList_Device_Data("50.0", 2));


        MultipleLayoutAdapter adapter = new MultipleLayoutAdapter(this, items);
        listView.setAdapter(adapter);

        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                setShowDataAPI();
//               //Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_SHORT).show();
//                handler.postDelayed(this, 1000);
//            }
//        }; handler.postDelayed(runnable, 1000);
//        show = (TextView) findViewById(R.id.textView12);
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
                            //System.out.println(response);
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



                            jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                            //datashow = jsonArray.getJSONObject(jsonArray.length() - 1).toString();
//                            vt1 = jsonObject.getString("v1");
//                            vt2 = jsonObject.getString("v2");
//                            vt3 = jsonObject.getString("v3");
//                            vt4 = jsonObject.getString("v4");
//                            vt5 = jsonObject.getString("v5");
//                            show.setText(datashow);
//                            v1.setText(vt1);
//                            v2.setText(vt2);
//                            v3.setText(vt3);
//                            v4.setText(vt4);
//                            v5.setText(vt5);


                            Iterator<String> iter = jsonObject.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                System.out.println(key);
                                try {
                                    Object value = jsonObject.get(key);
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }
                            }

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
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
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
