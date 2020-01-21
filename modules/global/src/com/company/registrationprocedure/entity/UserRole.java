package com.company.registrationprocedure.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum UserRole implements EnumClass<Integer> {

    ADMINISTRATOR(10),
    ACCESS_ADMINISTRATOR(20),
    MINZHKH_OPERATOR(30),
    MINZHKH_ET_INSPECTOR(40),
    MINZHKH_DATA_COLLECTION_INSPECTOR(50),
    OVERWATCH_INSPECTOR(60),
    EXPORT_CONTROL_DISPATCHER(70),
    OMSU_EMPLOYEE(80);

    private Integer id;

    UserRole(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static UserRole fromId(Integer id) {
        for (UserRole at : UserRole.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}