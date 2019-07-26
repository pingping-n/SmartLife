package com.projectThesis.SmartLife;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.app.infideap.stylishwidget.view.*;


import java.util.ArrayList;
import java.util.List;

public class MultipleLayoutAdapter extends BaseAdapter {

    private Context context;
    private List<CustomList_Device_Data> items;

    public MultipleLayoutAdapter(Context context, List<CustomList_Device_Data> items) {
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CustomList_Device_Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomList_Device_Data item = getItem(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case CustomList_Device_Data.TYPE_NONE:
                break;

            case CustomList_Device_Data.TYPE_PROGRESSBAR:
                convertView = inflateProgressBar(convertView, parent, item);
                break;

            case CustomList_Device_Data.TYPE_METER:
                convertView = inflateMeter(convertView, parent, item);
                break;
        }
        return convertView;

    }

    private View inflateMeter(View convertView, ViewGroup parent, CustomList_Device_Data item) {
        MeterViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_data_meter, parent, false);
            viewHolder = new MeterViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MeterViewHolder) convertView.getTag();
        }
        viewHolder.meter.animate();
        viewHolder.meter.setMaxValue(100);
        viewHolder.meter.setValue(Float.parseFloat(item.getData()));

        return convertView;
    }

    private View inflateProgressBar(View convertView, ViewGroup parent, CustomList_Device_Data item) {
        ProgressBarViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_data_progressbar, parent, false);
            viewHolder = new ProgressBarViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ProgressBarViewHolder) convertView.getTag();
        }
        viewHolder.progressBar.setProgressBackground(Color.GRAY);
        viewHolder.progressBar.setProgressColor(Color.rgb(252,216,66));
        viewHolder.progressBar.withAnimation(1);
        viewHolder.progressBar.setMaxValue(100);
        viewHolder.progressBar.setProgressValue(Float.parseFloat(item.getData()));
        viewHolder.progressBar.setProgressText(item.getData());


        return convertView;
    }

    static class MeterViewHolder {
        private AMeter meter;

        public MeterViewHolder(View view) {
            this.meter = (AMeter) view.findViewById(R.id.meter);
        }

    }

    static class ProgressBarViewHolder {
        private AProgressBar progressBar;

        public ProgressBarViewHolder(View view) {
            this.progressBar = (AProgressBar) view.findViewById(R.id.progressbar);
        }
    }
}
