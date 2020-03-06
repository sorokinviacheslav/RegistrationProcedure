package com.company.registrationprocedure.entity;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.security.entity.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Extends(Role.class)
@Entity(name = "registrationprocedure_RoleExt")
public class RoleExt extends Role {
    private static final long serialVersionUID = 5390812204027402608L;

    @NotNull
    @Column(name = "ORGANIZATION_ROLE", nullable = false)
    protected Integer organizationRole;

    @NotNull
    @Column(name = "USER_ROLE", nullable = false)
    protected Integer userRole;

    public OrganizationRole getOrganizationRole() {
        return organizationRole == null ? null : OrganizationRole.fromId(organizationRole);
    }

    public void setOrganizationRole(OrganizationRole organizationRole) {
        this.organizationRole = organizationRole == null ? null : organizationRole.getId();
    }
}