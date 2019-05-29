package com.projectThesis.SmartLife;

import android.os.AsyncTask;
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

public class fetchData extends AsyncTask<Void, Void, Void> {

    private String url;
    private String data;
    String dataParsed = "";
    String singleParsed ="";

    private JSONObject JO;

    public fetchData(String url) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(final Void... voids) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = obj.getJSONArray("");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                System.out.println(jsonObject.getString("v1"));

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
        //       RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        //     requestQueue.add(stringRequest);


//        try {
//            URL url = new URL(this.url);
//
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line = "";
//            while (line != null) {
//                line = bufferedReader.readLine();
//                data = data + line;
//
//                System.out.println(data);
//            }
//
//            JSONArray JA = new JSONArray(data);
//            for (int i = JA.length()-1; i < JA.length(); i++) {
//                JO = (JSONObject) JA.get(i);
//                singleParsed =  "v1:" + JO.get("v1") + "\n"+
//                        "v2:" + JO.get("v2") + "\n"+
//                        "v3:" + JO.get("v3") + "\n"+
//                        "v4:" + JO.get("v4") + "\n"+
//                        "v5:" + JO.get("v5") + "\n";
//
//                dataParsed = dataParsed + singleParsed +"\n" ;
//
//            }
//
//
//        } catch(MalformedURLException e) {
//            e.printStackTrace();
//        } catch(IOException e) {
//            e.printStackTrace();
//        } catch(JSONException e) {
//            e.printStackTrace();
//        }
//
        return null;
    }

    @Override
    protected void onPostExecute (Void aVoid){
        super.onPostExecute(aVoid);

    }
}

