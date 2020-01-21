package com.company.registrationprocedure.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum UserStatus implements EnumClass<Integer> {

    NEW(10),
    EMAIL_CONFIRMED(60),
    NEEDS_CORRECTION(20),
    CORRECTED(30),
    ACTIVATED(40),
    DEACTIVATED(50);

    private Integer id;

    UserStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static UserStatus fromId(Integer id) {
        for (UserStatus at : UserStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}