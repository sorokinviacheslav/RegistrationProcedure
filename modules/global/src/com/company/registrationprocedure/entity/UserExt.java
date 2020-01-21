package com.company.registrationprocedure.entity;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@PublishEntityChangedEvents
@Extends(User.class)
@Entity(name = "registrationprocedure_UserExt")
public class UserExt extends User {
    private static final long serialVersionUID = -6864145103277166530L;

    @Column(name = "DOMAIN_LOGIN")
    protected String domainLogin;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    protected Integer status;

    @Column(name = "PHONE_NUMBER")
    protected String phoneNumber;

    @NotNull
    @Column(name = "RECIEVE_EMAIL_NOTIFICATIONS", nullable = false)
    protected Boolean recieveEmailNotifications = false;

    @NotNull
    @Column(name = "HIDE_EMAIL", nullable = false)
    protected Boolean hideEmail = false;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    protected Organization organization;

    @Column(name = "COMMENTS", length = 1000)
    protected String comments;

    public void setStatus(UserStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public UserStatus getStatus() {
        return status == null ? null : UserStatus.fromId(status);
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Boolean getHideEmail() {
        return hideEmail;
    }

    public void setHideEmail(Boolean hideEmail) {
        this.hideEmail = hideEmail;
    }

    public Boolean getRecieveEmailNotifications() {
        return recieveEmailNotifications;
    }

    public void setRecieveEmailNotifications(Boolean recieveEmailNotifications) {
        this.recieveEmailNotifications = recieveEmailNotifications;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDomainLogin() {
        return domainLogin;
    }

    public void setDomainLogin(String domainLogin) {
        this.domainLogin = domainLogin;
    }

    @PreUpdate
    @PrePersist
    public void assignRole() {
        if(getStatus().equals(UserStatus.NEW)||getStatus().equals(UserStatus.DEACTIVATED)) {
            setActive(false);
        }
        else if(!getStatus().equals(UserStatus.ACTIVATED)) {
            //TO DO
            //Assign restricted role with permission to see user screen only
            setActive(true);
        }
        else {
            //TO DO
            //Assign roles according to UserRole value
            setActive(true);
        }
    }
}