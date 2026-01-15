package com.pg.MS1.pin.entity;

import com.pg.MS1.common.enums.PinType;

import java.time.Instant;

public class CreatePinRequest {

    private String pin;
    private PinType pinType;
    private Instant expiresAt;
    private int maxUses = 1;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public PinType getPinType() {
        return pinType;
    }

    public void setPinType(PinType pinType) {
        this.pinType = pinType;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }
}
