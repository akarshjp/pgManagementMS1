package com.pg.MS1.access.controller;

import com.pg.MS1.access.dto.VerifyPinRequest;
import com.pg.MS1.access.dto.VerifyPinResponse;
import com.pg.MS1.access.entity.AccessLog;
import com.pg.MS1.access.service.AccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/access")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PreAuthorize("hasRole('RESIDENT')")
    @PostMapping("/verify")
    public ResponseEntity<VerifyPinResponse> verifyPin(
            @RequestBody VerifyPinRequest request
    ) {
        Long residentId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        AccessLog log = accessService.verifyAndEnter(residentId, request);

        return ResponseEntity.ok(
                new VerifyPinResponse(log.getResident().getId(), log.getStatus().name())
        );
    }

    @PreAuthorize("hasRole('RESIDENT')")
    @PatchMapping("/{accessId}/exit")
    public ResponseEntity<Void> exitAccess(@PathVariable Long accessId) {

        Long residentId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        accessService.exitAccess(residentId, accessId);

        return ResponseEntity.ok().build();
    }

}
