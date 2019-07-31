package com.projectThesis.SmartLife;

public class CustomList_Device_Data {

    public static final int TYPE_NONE  = 0;
    public static final int TYPE_PROGRESSBAR  = 1;
    public static final int TYPE_METER_1 = 2;
    public static final int TYPE_METER_2 = 3;
    public static final int TYPE_METER_3 = 4;

    private String data;
    private int    type;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CustomList_Device_Data(String data, int type) {
        this.data = data;
        this.type = type;
    }


}
