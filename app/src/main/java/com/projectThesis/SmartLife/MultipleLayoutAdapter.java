package com.projectThesis.SmartLife;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.infideap.stylishwidget.view.*;


import pl.pawelkleczkowski.customgauge.CustomGauge;

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
                convertView = inflateNull(convertView, parent, item);
                break;
            case CustomList_Device_Data.TYPE_PROGRESSBAR:
                convertView = inflateProgressBar(convertView, parent, item);
                break;
            case CustomList_Device_Data.TYPE_METER_1:
                convertView = inflateMeter(convertView, parent, item);
                break;
            case CustomList_Device_Data.TYPE_METER_2:
                convertView = inflateMeter2(convertView, parent, item);
                break;
            case CustomList_Device_Data.TYPE_METER_3:
                convertView = inflateMeter3(convertView, parent, item);
                break;
        }
        return convertView;

    }

    /** ------------------------------------------------------------------------------------------------ **/
    /** Null **/
    static class NullViewHolder {
        private TextView textView;

        public NullViewHolder(View view) {
            this.textView = (TextView) view.findViewById(R.id.nulltext);
        }
    }
    private View inflateNull(View convertView, ViewGroup parent, CustomList_Device_Data item) {
        NullViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_data_null, parent, false);
            viewHolder = new NullViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (NullViewHolder) convertView.getTag();
        }

        return convertView;
    }
    /** ------------------------------------------------------------------------------------------------ **/
    /** Meter 1 **/
    static class MeterViewHolder {
        private AMeter meter;

        public MeterViewHolder(View view) {
            this.meter = (AMeter) view.findViewById(R.id.meter);
        }
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
        if (Float.parseFloat(item.getData()) > 100) {
            viewHolder.meter.setMaxValue(Float.parseFloat(item.getData()) + 50);
        }
        viewHolder.meter.setValue(Float.parseFloat(item.getData()));

        return convertView;
    }
    /** ------------------------------------------------------------------------------------------------ **/
    /** ProgressBar **/
    static class ProgressBarViewHolder {
        private AProgressBar progressBar;

        public ProgressBarViewHolder(View view) {
            this.progressBar = (AProgressBar) view.findViewById(R.id.progressbar);
        }
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
        if (Float.parseFloat(item.getData()) > 100) {
            viewHolder.progressBar.setMaxValue(Float.parseFloat(item.getData()) + 50);
        }
        viewHolder.progressBar.setProgressValue(Float.parseFloat(item.getData()));
        viewHolder.progressBar.setProgressText(item.getData());


        return convertView;
    }
    /** ------------------------------------------------------------------------------------------------ **/
    /** Meter 2 **/
    static class Meter2ViewHolder {
        private CustomGauge meter2;
        private TextView textView;

        public Meter2ViewHolder(View view) {
            this.meter2 = (CustomGauge) view.findViewById(R.id.meter2);
            this.textView = (TextView) view.findViewById(R.id.meter2TextView);
        }
    }
    private View inflateMeter2(View convertView, ViewGroup parent, CustomList_Device_Data item) {
        Meter2ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_data_meter2, parent, false);
            viewHolder = new Meter2ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (Meter2ViewHolder) convertView.getTag();
        }
        int temp = Math.round(Float.parseFloat(item.getData()));
        if (temp > 100) {
            viewHolder.meter2.setEndValue(temp + 50);
        }
        viewHolder.meter2.setValue(temp);
        viewHolder.textView.setText(String.valueOf(temp));

        return convertView;
    }
    /** ------------------------------------------------------------------------------------------------ **/
    /** Meter 3 **/
    static class Meter3ViewHolder {
        private AMeter meter;

        public Meter3ViewHolder(View view) {
            this.meter = (AMeter) view.findViewById(R.id.meter3);
        }
    }
    private View inflateMeter3(View convertView, ViewGroup parent, CustomList_Device_Data item) {
        Meter3ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_data_meter3, parent, false);
            viewHolder = new Meter3ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (Meter3ViewHolder) convertView.getTag();
        }
        viewHolder.meter.animate();
        viewHolder.meter.setMaxValue(100);
        if (Float.parseFloat(item.getData()) > 100) {
            viewHolder.meter.setMaxValue(Float.parseFloat(item.getData()) + 50);
        }
        viewHolder.meter.setValue(Float.parseFloat(item.getData()));

        return convertView;
    }
    /** ------------------------------------------------------------------------------------------------ **/

}
