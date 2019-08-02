package com.projectThesis.SmartLife;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Help_Fragment extends Fragment {
    private DatabaseHelper_User databaseHelper_user;
    private String username;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databaseHelper_user = new DatabaseHelper_User(getContext());
        username = databaseHelper_user.getUser();
        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(username);

        return inflater.inflate(R.layout.fragment_help, null);
    }
}
