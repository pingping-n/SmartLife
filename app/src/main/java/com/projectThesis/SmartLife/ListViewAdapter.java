package com.projectThesis.SmartLife;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<MyDevice_View> {

    public ListViewAdapter(Context context, int resource, List<MyDevice_View> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        MyDevice_View myDevice_view = getItem(position);
        TextView textTitle = (TextView) v.findViewById(R.id.textTitle);

        textTitle.setText(myDevice_view.getTitle());

        return v;
    }
}
