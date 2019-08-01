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

        Intent intent = getIntent();
        int nav_back = intent.getIntExtra("nav_back", 0);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //First navigation selected
        navigation.getMenu().getItem(nav_back).setChecked(true);
        onNavigationItemSelected(navigation.getMenu().getItem(nav_back));
        switch (nav_back) {
            case 1:
                loadFragment(new MyDevice_Fragment());
                break;
            case 2:
                loadFragment(new Setting_Fragment());
                break;
            case 3:
                loadFragment(new Help_Fragment());
                break;
            case 0:
                loadFragment(new Home_Fragment());
                break;
        }
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
