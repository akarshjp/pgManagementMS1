package com.pg.MS1.pin.service;

import com.pg.MS1.pin.entity.CreatePinRequest;
import com.pg.MS1.pin.entity.ResidentPin;
import com.pg.MS1.pin.repository.ResidentPinRepository;
import com.pg.MS1.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResidentPinService {

    private final ResidentPinRepository pinRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;


    @Autowired
    public ResidentPinService(ResidentPinRepository pinRepository, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.pinRepository = pinRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Transactional
    public void createPin(Long residentId, CreatePinRequest request) {

        // Create a lightweight reference (no DB hit)
        User residentRef = entityManager.getReference(User.class, residentId);

        ResidentPin pin = new ResidentPin();
        pin.setResident(residentRef);
        pin.setPinType(request.getPinType());
        pin.setExpiresAt(request.getExpiresAt());
        pin.setMaxUses(request.getMaxUses());
        pin.setPinHash(passwordEncoder.encode(request.getPin()));

        pinRepository.save(pin);
    }
}
