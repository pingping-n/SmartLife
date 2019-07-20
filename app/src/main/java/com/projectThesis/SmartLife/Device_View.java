package com.projectThesis.SmartLife;

public class Device_View {

    private String title;
    private String deviceId;

    public Device_View(String deviceId, String title) {
        this.deviceId = deviceId;
        this.title = title;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
