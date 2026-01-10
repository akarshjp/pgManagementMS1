package com.pg.MS1.pin.controller;

import com.pg.MS1.pin.entity.CreatePinRequest;
import com.pg.MS1.pin.service.ResidentPinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/residentPin")
public class ResidentPinController {
    private final ResidentPinService residentPinService;

    @Autowired
    public ResidentPinController(ResidentPinService residentPinService) {
        this.residentPinService = residentPinService;
    }

    @PreAuthorize("hasRole('RESIDENT')")
    @PostMapping
    public ResponseEntity<Void> createPin(
            @RequestBody CreatePinRequest request ) {
        Long residentId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        residentPinService.createPin(residentId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
