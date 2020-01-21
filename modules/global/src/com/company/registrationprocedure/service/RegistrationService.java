package com.company.registrationprocedure.service;

import com.company.registrationprocedure.entity.Organization;
import com.haulmont.cuba.security.entity.User;

import java.io.Serializable;
import java.util.UUID;

public interface RegistrationService {
    String NAME = "registrationprocedure_RegistrationService";

    RegistrationResult registerUser(RegistrationData regData);

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

    class RegistrationData implements Serializable {
        private String login;
        private String firstName;
        private String lastName;
        private String middleName;
        private String email;
        private String domainLogin;
        private boolean isInternal;
        private String phoneNumber;
        private boolean emailNotifications;
        private boolean hideEmail;
        private String password;
        private Organization organization;

        public RegistrationData(String login,String password, String email,Boolean isInternal) {
            this.password=password;
            this.email=email;
            this.isInternal=isInternal;
            if(this.isInternal()) {
                this.login=login;
                this.domainLogin=login;
            }
            else {
                this.login=login;
                this.domainLogin="";
            }

        }


        public boolean isInternal() {
            return isInternal;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getEmail() {
            return email;
        }

        public String getDomainLogin() {
            if(this.isInternal()) {
                return domainLogin;
            }
            return "";
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public boolean isEmailNotifications() {
            return emailNotifications;
        }

        public void setEmailNotifications(boolean emailNotifications) {
            this.emailNotifications = emailNotifications;
        }

        public boolean isHideEmail() {
            return hideEmail;
        }

        public void setHideEmail(boolean hideEmail) {
            this.hideEmail = hideEmail;
        }

        public String getPassword() {
            return password;
        }

        public Organization getOrganization() {
            return organization;
        }

        public void setOrganization(Organization organization) {
            this.organization = organization;
        }

        public String getLogin() {
            return login;
        }
    }
}