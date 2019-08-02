package com.projectThesis.SmartLife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home_Fragment extends Fragment {
    private String username;
    private Button button_add_device;

    private DatabaseHelper_User databaseHelper_user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databaseHelper_user = new DatabaseHelper_User(getContext());
        username = databaseHelper_user.getUser();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(username);

        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button_add_device = (Button) view.findViewById(R.id.button_add_device);

        button_add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddDeviceActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }

}
