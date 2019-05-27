package com.projectThesis.SmartLife;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //First navigation selected
        navigation.getMenu().getItem(0).setChecked(true);

        loadFragment(new Home_Fragment());
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

}
