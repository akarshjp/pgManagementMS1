package com.pg.MS1.amenity.service;

import com.pg.MS1.amenity.dto.CreateAmenityRequest;
import com.pg.MS1.amenity.entity.Amenity;
import com.pg.MS1.amenity.repository.AmenityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Transactional
    public Amenity createAmenity(CreateAmenityRequest request) {

        Amenity amenity = new Amenity();
        amenity.setName(request.getName());
        amenity.setDescription(request.getDescription());

        amenity.setSlotDurationMinutes(
                request.getSlotDurationMinutes() != null ? request.getSlotDurationMinutes() : 60
        );

        amenity.setMaxAdvanceBookingDays(
                request.getMaxAdvanceBookingDays() != null ? request.getMaxAdvanceBookingDays() : 7
        );

        amenity.setOperatingStartTime(request.getOperatingStartTime());
        amenity.setOperatingEndTime(request.getOperatingEndTime());

        //TODO: Can check this later
        amenity.setRequiresApproval(
                Boolean.TRUE.equals(request.getRequiresApproval())
        );

        return amenityRepository.save(amenity);
    }

}
