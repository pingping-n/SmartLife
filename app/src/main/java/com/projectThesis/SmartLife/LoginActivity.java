package com.projectThesis.SmartLife;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button mLogin;
    private EditText mUsername;
    private EditText mPassword;
    private Context mContext;
    private UserManager mManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (Button) findViewById(R.id.btn_login);
        mUsername = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mContext = this;
        mManager  = new UserManager(this);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

    }

    private void checkLogin() {
        String username = mUsername.getText().toString().trim().toLowerCase();
        String password = mPassword.getText().toString().trim();

//        boolean isSuccess = mManager.checkLoginValidate(username, password);
        boolean isSuccess = true;

        if (isSuccess) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
//            finish();
        } else {
            String message = getString(R.string.login_error_message);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }
}