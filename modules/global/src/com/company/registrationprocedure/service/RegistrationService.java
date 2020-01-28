package com.company.registrationprocedure.service;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.UserSystemRole;
import com.haulmont.cuba.security.entity.User;

import java.io.Serializable;
import java.util.UUID;

public interface RegistrationService {
    String NAME = "registrationprocedure_RegistrationService";

    RegistrationResult registerUser(RegistrationData regData);

    void restoreOldValues(UUID id,String...atrNames);

    boolean userExists(String login,String email);

    class RegistrationResult implements Serializable {

        private UUID userId;
        private boolean success;

        public RegistrationResult(User user) {
            if (user != null) {
                this.userId = user.getId();
                this.success = true;
            }
            else {
                this.userId =null;
                this.success = false;
            }
        }

        public UUID getUserId() {
            return userId;
        }

        public boolean isSuccess() {
            return success;
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
        private UUID organizationUUID;
        private UserSystemRole role;

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

        public String getLogin() {
            return login;
        }

        public UUID getOrganizationUUID() {
            return organizationUUID;
        }

        public void setOrganizationUUID(UUID organizationUUID) {
            this.organizationUUID = organizationUUID;
        }

        public UserSystemRole getRole() {
            return role;
        }

        public void setRole(UserSystemRole role) {
            this.role = role;
        }
    }
}