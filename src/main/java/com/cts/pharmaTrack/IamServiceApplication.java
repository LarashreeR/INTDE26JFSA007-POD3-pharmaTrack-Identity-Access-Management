package com.cts.pharmaTrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Service entry point. The application sits in the com.cts.pharmaTrack root
 * package, so component scanning, JPA entity scanning and repository scanning
 * all default to this package and its subpackages (module code + shared lib).
 */
@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {
    "com.cts.pharmaTrack.module.identityAccessManagement.repository",
    "com.cts.pharmaTrack.common.audit"
})
public class IamServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(IamServiceApplication.class);

    public static void main(String[] args) {
        logger.info("Starting IamServiceApplication");
        SpringApplication.run(IamServiceApplication.class, args);
    }
}