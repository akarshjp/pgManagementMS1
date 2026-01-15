package com.pg.MS1.amenity.controller;

import com.pg.MS1.amenity.dto.CreateAmenityRequest;
import com.pg.MS1.amenity.entity.Amenity;
import com.pg.MS1.amenity.service.AmenityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Amenity> createAmenity(
            @RequestBody CreateAmenityRequest request
    ) {
        return ResponseEntity.ok(
                amenityService.createAmenity(request)
        );
    }
}
