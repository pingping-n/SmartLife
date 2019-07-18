package com.projectThesis.SmartLife;

public class Device_View {

    private String title;
    private int deviceId;

    public Device_View(int deviceId, String title) {
        this.deviceId = deviceId;
        this.title = title;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
