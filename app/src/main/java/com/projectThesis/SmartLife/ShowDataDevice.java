package com.projectThesis.SmartLife;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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


    private static final String url = "http://146.148.42.206:4000/api/value/";

    private TextView show;
    private String datashow;

    private TextView v1;
    private TextView v2;
    private TextView v3;
    private TextView v4;
    private TextView v5;

    private String vt1;
    private String vt2;
    private String vt3;
    private String vt4;
    private String vt5;

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_device);

        v1 = (TextView) findViewById(R.id.textView7);
        v2 = (TextView) findViewById(R.id.textView8);
        v3 = (TextView) findViewById(R.id.textView9);
        v4 = (TextView) findViewById(R.id.textView10);
        v5 = (TextView) findViewById(R.id.textView11);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                setShowDataAPI();
               //Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 1000);
            }
        }; handler.postDelayed(runnable, 1000);
        show = (TextView) findViewById(R.id.textView12);
//        getShowDataAPI(url);

    }

//    private void getShowDataAPI(String url) {
//        fetchData process = new fetchData(url);
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
                            System.out.println(response);
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
                            datashow = jsonArray.getJSONObject(jsonArray.length() - 1).toString();
                            vt1 = jsonObject.getString("v1");
                            vt2 = jsonObject.getString("v2");
                            vt3 = jsonObject.getString("v3");
                            vt4 = jsonObject.getString("v4");
                            vt5 = jsonObject.getString("v5");
                            show.setText(datashow);
                            v1.setText(vt1);
                            v2.setText(vt2);
                            v3.setText(vt3);
                            v4.setText(vt4);
                            v5.setText(vt5);
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
