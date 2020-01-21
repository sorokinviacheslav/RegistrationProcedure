package com.company.registrationprocedure.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum OrganizationRole implements EnumClass<Integer> {

    ADMINISTRATIVE(10),
    WASTE_FORMATION(20),
    WASTE_TRANSPORTATION(30),
    REGIONAL_OPERATOR(40),
    WASTE_HANDLING(50);

    private Integer id;

    OrganizationRole(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static OrganizationRole fromId(Integer id) {
        for (OrganizationRole at : OrganizationRole.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}