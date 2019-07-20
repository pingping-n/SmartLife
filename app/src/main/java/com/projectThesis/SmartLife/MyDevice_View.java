package com.projectThesis.SmartLife;

public class MyDevice_View {

    private String id_device;
    private int imageId;
    private String title;

    public MyDevice_View(String id_device, int imageId, String title) {
        this.id_device = id_device;
        this.imageId = imageId;
        this.title = title;
    }

    public String getId_device() {
        return id_device;
    }

    public void setId_device(String id_device) {
        this.id_device = id_device;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
