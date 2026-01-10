package com.pg.MS1.access.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.pg.MS1.common.entity.BaseEntity;
import com.pg.MS1.common.enums.AccessStatus;
import com.pg.MS1.common.enums.PinType;
import com.pg.MS1.pin.entity.ResidentPin;
import com.pg.MS1.user.entity.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "access_logs")
public class AccessLog extends BaseEntity {

    public User getResident() {
        return resident;
    }

    public void setResident(User resident) {
        this.resident = resident;
    }

    public ResidentPin getPin() {
        return pin;
    }

    public void setPin(ResidentPin pin) {
        this.pin = pin;
    }

    public PinType getAccessType() {
        return accessType;
    }

    public void setAccessType(PinType accessType) {
        this.accessType = accessType;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorPhone() {
        return visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone) {
        this.visitorPhone = visitorPhone;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public AccessStatus getStatus() {
        return status;
    }

    public void setStatus(AccessStatus status) {
        this.status = status;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Instant entryTime) {
        this.entryTime = entryTime;
    }

    public Instant getExitTime() {
        return exitTime;
    }

    public void setExitTime(Instant exitTime) {
        this.exitTime = exitTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private User resident;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pin_id", nullable = false)
    private ResidentPin pin;

    @Enumerated(EnumType.STRING)
    private PinType accessType;

    private String visitorName;
    private String visitorPhone;
    private String purpose;
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private AccessStatus status;

    private Instant entryTime;
    private Instant exitTime;
}
