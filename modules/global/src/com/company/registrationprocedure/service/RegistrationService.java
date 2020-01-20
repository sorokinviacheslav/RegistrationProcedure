package com.company.registrationprocedure.service;

import com.haulmont.cuba.security.entity.User;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.UUID;
import com.company.registrationprocedure.validation.CheckUserExists;

public interface RegistrationService {
    String NAME = "registrationprocedure_RegistrationService";

    @Validated
    RegistrationResult registerUser(@CheckUserExists String login, String password);

    class RegistrationResult implements Serializable {

        private UUID userId;

        public RegistrationResult(User user) {
            if (user != null) {
                this.userId = user.getId();
            }
        }

        public UUID getUserId() {
            return userId;
        }
    }
}