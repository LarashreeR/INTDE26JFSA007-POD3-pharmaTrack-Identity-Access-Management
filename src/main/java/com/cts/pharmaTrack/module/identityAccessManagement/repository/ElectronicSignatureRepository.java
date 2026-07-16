package com.cts.pharmaTrack.module.identityAccessManagement.repository;

import com.cts.pharmaTrack.module.identityAccessManagement.entity.ElectronicSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ElectronicSignatureRepository extends JpaRepository<ElectronicSignature, Integer> {
    List<ElectronicSignature> findByAuditId(Integer auditId);
    long countByAuditId(Integer auditId);
}
