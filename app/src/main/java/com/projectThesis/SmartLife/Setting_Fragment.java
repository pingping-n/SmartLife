package com.projectThesis.SmartLife;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Setting_Fragment extends Fragment {
    private DatabaseHelper_User databaseHelper_user;
    private DatabaseHelper_Device databaseHelper_device;
    private DatabaseHelper_Device_Data databaseHelper_device_data;
    private String username;
    private Button button_logout;
    private ViewDialog viewDialog;
    AlertDialog.Builder builder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databaseHelper_user = new DatabaseHelper_User(getContext());
        databaseHelper_device = new DatabaseHelper_Device(getContext());
        databaseHelper_device_data = new DatabaseHelper_Device_Data(getContext());
        username = databaseHelper_user.getUser();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(username);

        return inflater.inflate(R.layout.fragment_setting, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
        button_logout = (Button) view.findViewById(R.id.button_logout);
        viewDialog = new ViewDialog(getActivity());

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Do you want to logout? \nInformation will be lost.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewDialog.showDialog();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        databaseHelper_user.deleteUser();
                                        databaseHelper_device.deleteDevice();
                                        databaseHelper_device_data.deleteDeviceData();

                                        viewDialog.hideDialog();
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }, 2000);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Confirm to logout?");
                alertDialog.show();
            }
        });
    }
}
