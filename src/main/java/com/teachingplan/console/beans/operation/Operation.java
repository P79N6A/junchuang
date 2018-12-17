package com.teachingplan.console.beans.operation;

public class Operation {

    private int id;

    private String deviceName;

    private String elevationAngle;

    private String azimuth;

    private int shootCount;

    private String shootType;

    private String messageType;

    private String createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getElevationAngle() {
        return elevationAngle;
    }

    public void setElevationAngle(String elevationAngle) {
        this.elevationAngle = elevationAngle;
    }

    public String getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(String azimuth) {
        this.azimuth = azimuth;
    }

    public int getShootCount() {
        return shootCount;
    }

    public void setShootCount(int shootCount) {
        this.shootCount = shootCount;
    }

    public String getShootType() {
        return shootType;
    }

    public void setShootType(String shootType) {
        this.shootType = shootType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
