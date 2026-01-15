package com.pg.MS1.access.service;

import com.pg.MS1.access.dto.VerifyPinRequest;
import com.pg.MS1.access.entity.AccessLog;
import com.pg.MS1.access.repository.AccessLogRepository;
import com.pg.MS1.common.enums.AccessStatus;
import com.pg.MS1.pin.entity.ResidentPin;
import com.pg.MS1.pin.repository.ResidentPinRepository;
import com.pg.MS1.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AccessService {

    private final ResidentPinRepository pinRepository;
    private final AccessLogRepository accessLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Autowired
    public AccessService(ResidentPinRepository pinRepository, AccessLogRepository accessLogRepository,
            PasswordEncoder passwordEncoder,
            EntityManager entityManager) {
        this.pinRepository = pinRepository;
        this.accessLogRepository = accessLogRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Transactional
    public AccessLog verifyAndEnter(Long residentId, VerifyPinRequest request) {

        // Ensure no ACTIVE access exists
        //TODO: Later set a max number of allowed guests rn setting to 1
        if (accessLogRepository.existsByResidentIdAndStatus(
                residentId, AccessStatus.ACTIVE)) {
            throw new RuntimeException("An active access already exists");
        }

        //Fetch active PINs for resident
        ResidentPin matchedPin = pinRepository
                .findByResidentIdAndIsActiveTrue(residentId)
                .stream()
                .filter(pin -> passwordEncoder.matches(
                        request.getPin(), pin.getPinHash()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid PIN"));

        //Validate PIN rules
        // TODO: Later set it to expire after sometime, rn taking it manually or is null
        if (matchedPin.getExpiresAt() != null &&
                matchedPin.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("PIN expired");
        }

        // How many times a pin can be used at max
        if (matchedPin.getCurrentUses() >= matchedPin.getMaxUses()) {
            throw new RuntimeException("PIN usage exceeded");
        }

        //Increment PIN usage
        matchedPin.setCurrentUses(matchedPin.getCurrentUses() + 1);

        //Create access log
        User residentRef = entityManager.getReference(User.class, residentId);

        AccessLog log = new AccessLog();
        log.setResident(residentRef);
        log.setPin(matchedPin);
        log.setAccessType(matchedPin.getPinType());
        log.setVisitorName(request.getVisitorName());
        log.setVisitorPhone(request.getVisitorPhone());
        log.setPurpose(request.getPurpose());
        log.setVehicleNumber(request.getVehicleNumber());
        log.setStatus(AccessStatus.ACTIVE);
        log.setEntryTime(Instant.now());

        return accessLogRepository.save(log);
    }

    @Transactional
    public void exitAccess(Long residentId, Long accessId) {

        AccessLog log = accessLogRepository
                .findByIdAndResidentId(accessId, residentId)
                .orElseThrow(() -> new RuntimeException("Access not found"));

        if (log.getStatus() != AccessStatus.ACTIVE) {
            throw new RuntimeException("Access is not active");
        }

        log.setStatus(AccessStatus.EXITED);
        log.setExitTime(Instant.now());
    }

}
