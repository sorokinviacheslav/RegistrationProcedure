package com.company.registrationprocedure.entity;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Extends(User.class)
@Entity(name = "registrationprocedure_UserExt")
public class UserExt extends User {
    private static final long serialVersionUID = -6864145103277166530L;

    @NotNull
    @Column(name = "ACTIVATED", nullable = false)
    protected Boolean activated = false;

    @NotNull
    @Column(name = "CONFIRMED", nullable = false)
    protected Boolean confirmed = false;

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
}