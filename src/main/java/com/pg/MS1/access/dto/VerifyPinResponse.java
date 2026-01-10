package com.pg.MS1.access.dto;

public class VerifyPinResponse {

    private Long accessId;
    private String status;

    public VerifyPinResponse(Long accessId, String status) {
        this.accessId = accessId;
        this.status = status;
    }

    public Long getAccessId() {
        return accessId;
    }

    public String getStatus() {
        return status;
    }
}
