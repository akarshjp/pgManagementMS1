package com.pg.MS1.pin.repository;

import com.pg.MS1.pin.entity.ResidentPin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentPinRepository extends JpaRepository<ResidentPin, Long> {
    List<ResidentPin> findByResidentIdAndIsActiveTrue(Long residentId);
}
