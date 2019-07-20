package com.projectThesis.SmartLife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter_AddDevice extends ArrayAdapter<Device_View> {


    public ListViewAdapter_AddDevice(Context context, int resource, List<Device_View> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item_device, null);
        }

        Device_View device_view = getItem(position);
        TextView textId = (TextView) v.findViewById(R.id.textId_AddDevice);
        TextView textTitle = (TextView) v.findViewById(R.id.textTitle_AddDevice);

        textId.setText(String.valueOf(device_view.getDeviceId()));
        textTitle.setText(device_view.getTitle());


        return v;
    }
}
