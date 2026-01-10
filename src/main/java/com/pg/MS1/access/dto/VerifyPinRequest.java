package com.pg.MS1.access.dto;

public class VerifyPinRequest {

    private String pin;              // raw PIN entered
    private String visitorName;
    private String visitorPhone;
    private String purpose;
    private String vehicleNumber;

    public String getPin() {
        return pin;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getVisitorPhone() {
        return visitorPhone;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}
