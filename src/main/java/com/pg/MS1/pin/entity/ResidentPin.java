package com.pg.MS1.pin.entity;

import com.pg.MS1.common.entity.BaseEntity;
import com.pg.MS1.common.enums.PinType;
import com.pg.MS1.user.entity.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "resident_pins")
public class ResidentPin extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private User resident;

    @Column(name = "pin_hash", nullable = false)
    private String pinHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "pin_type", nullable = false)
    private PinType pinType;

    private Instant expiresAt;

    private int maxUses;
    private int currentUses = 0;
    private boolean isActive = true;

    public int getCurrentUses() {
        return currentUses;
    }

    public void setCurrentUses(int currentUses) {
        this.currentUses = currentUses;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setResident(User resident) {
        this.resident = resident;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public void setPinType(PinType pinType) {
        this.pinType = pinType;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }
    public int getMaxUses() {
        return maxUses;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public PinType getPinType() {
        return pinType;
    }

    public String getPinHash() {
        return pinHash;
    }

    public User getResident() {
        return resident;
    }
}