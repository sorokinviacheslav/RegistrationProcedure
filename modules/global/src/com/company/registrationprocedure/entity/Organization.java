package com.company.registrationprocedure.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "REGISTRATIONPROCEDURE_ORGANIZATION")
@Entity(name = "registrationprocedure_Organization")
public class Organization extends StandardEntity {
    private static final long serialVersionUID = -3001805392891197361L;

    @NotNull
    @Column(name = "ROLE", nullable = false)
    protected Integer role;

    @Email(message = "{msg://registrationprocedure_Organization.email.validation.Email}")
    @NotNull
    @Column(name = "EMAIL", nullable = false)
    protected String email;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "SHORT_NAME")
    protected String shortName;

    @NotNull
    @Column(name = "INN", nullable = false, length = 20)
    protected String inn;

    @Column(name = "KPP")
    protected String kpp;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(OrganizationRole role) {
        this.role = role == null ? null : role.getId();
    }

    public OrganizationRole getRole() {
        return role == null ? null : OrganizationRole.fromId(role);
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}