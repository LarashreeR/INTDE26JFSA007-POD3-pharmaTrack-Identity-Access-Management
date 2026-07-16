package com.cts.pharmaTrack.module.identityAccessManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 21 CFR Part 11 electronic signature applied to an audit-log entry.
 * The signatureHash binds userId + auditId + signedAt so the signature is
 * verifiable and non-repudiable.
 */
@Entity
@Table(name = "electronic_signature")
@Data
public class ElectronicSignature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer signatureId;

    @Column(nullable = false)
    private Integer auditId;

    @Column(nullable = false)
    private Integer userId;

    @Column(updatable = false)
    private LocalDateTime signedAt = LocalDateTime.now();

    @Column(length = 255, nullable = false)
    private String signatureHash;

    /** What the signature represents — Approved, Reviewed, Submitted, ... */
    @Column(length = 200, nullable = false)
    private String meaning;
}
