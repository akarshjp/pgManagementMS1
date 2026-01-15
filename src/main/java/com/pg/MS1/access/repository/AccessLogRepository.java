package com.pg.MS1.access.repository;

import com.pg.MS1.access.entity.AccessLog;
import com.pg.MS1.common.enums.AccessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    boolean existsByResidentIdAndStatus(Long residentId, AccessStatus status);
    Optional<AccessLog> findByIdAndResidentId(Long id, Long residentId);
}
