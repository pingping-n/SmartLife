package com.projectThesis.SmartLife;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {

    private Button mLogin;
    private EditText mUsername;
    private EditText mPassword;
    private Context mContext;
    private ViewDialog viewDialog;
    private DatabaseHelper_User databaseHelper_user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (Button) findViewById(R.id.btn_login);
        mUsername = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mContext = this;
        viewDialog = new ViewDialog(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseHelper_user = new DatabaseHelper_User(this);
        String usernameDB = databaseHelper_user.getUser();
        String passwordDB = databaseHelper_user.getPassword();

        if (!usernameDB.equals("") && !passwordDB.equals("")) {
            viewDialog.showDialog();
            checkLogin(usernameDB, passwordDB);
            viewDialog.hideDialog();
        }

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim().toLowerCase();
                String password = mPassword.getText().toString().trim();
                viewDialog.showDialog();
                checkLogin(username, password);
            }
        });


    }

    public void passLogin() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkLogin(final String username, final String password) {
        viewDialog.showDialog();
        boolean isSuccess = checkLoginValidate(username, password);
        if (isSuccess) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewDialog.hideDialog();
                    databaseHelper_user.addUser(username, password);
                    passLogin();
                }
            }, 2000);

        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewDialog.hideDialog();
                    String message = getString(R.string.login_error_message);
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            }, 2000);
        }
    }

    public boolean checkLoginValidate(String username, String password) {
        String urlParameters  = "email=" + username + "&password=" + password;

        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        String request = "http://146.148.42.206:4000/api/login";
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
            if (response.toString().equals("successful")) {
                return true;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}