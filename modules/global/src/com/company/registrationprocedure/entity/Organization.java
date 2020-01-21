package com.company.registrationprocedure.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "REGISTRATIONPROCEDURE_ORGANIZATION")
@Entity(name = "registrationprocedure_Organization")
public class Organization extends StandardEntity {
    private static final long serialVersionUID = -3001805392891197361L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "INN", nullable = false, unique = true, length = 20)
    protected String inn;

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