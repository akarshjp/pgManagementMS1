package com.pg.MS1.amenity.repository;

import com.pg.MS1.amenity.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
