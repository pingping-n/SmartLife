package com.projectThesis.SmartLife;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseHelper_User databaseHelper_user;
    private DatabaseHelper_Device databaseHelper_device;
    private DatabaseHelper_Device_Data databaseHelper_device_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper_user = new DatabaseHelper_User(this);
        databaseHelper_device = new DatabaseHelper_Device(this);
        databaseHelper_device_data = new DatabaseHelper_Device_Data(this);

        Intent intent = getIntent();
        int nav_back = intent.getIntExtra("nav_back", 0);

        setSubtitleBar();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //First navigation selected
        navigation.getMenu().getItem(nav_back).setChecked(true);
        onNavigationItemSelected(navigation.getMenu().getItem(nav_back));
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()) {

            case R.id.navigation_home:
                fragment = new Home_Fragment();
                break;

            case R.id.navigation_mydevice:
                fragment = new MyDevice_Fragment();
                break;

            case R.id.navigation_setting:
                fragment = new Setting_Fragment();
                break;

            case R.id.navigation_help:
                fragment = new Help_Fragment();
                break;

        }

        return loadFragment(fragment);
    }

    public void setSubtitleBar() {
        databaseHelper_device_data = new DatabaseHelper_Device_Data(this);
        Cursor test = databaseHelper_device_data.getData();
        int i = 0;
//        System.out.println("C: "+test.getCount());
        if (test.moveToFirst() ){
            do {
                //System.out.println("id_device: " + test.getString(i));
                i++;
            } while (test.moveToNext());
        }

        getSupportActionBar().setSubtitle("You have " + i + " devices");
        getSupportActionBar().setLogo(R.drawable.ic_info);
    }


    @Override
    protected void onStart() {
        super.onStart();
        setSubtitleBar();
    }
}
