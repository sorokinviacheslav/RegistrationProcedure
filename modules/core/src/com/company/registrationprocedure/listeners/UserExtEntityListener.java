package com.company.registrationprocedure.listeners;

import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.entity.UserStatus;
import com.company.registrationprocedure.entity.UserSystemRole;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.UserRole;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("registrationprocedure_UserExtEntityListener")
public class UserExtEntityListener implements BeforeInsertEntityListener<UserExt>,  BeforeUpdateEntityListener<UserExt> {

    @Inject
    private Metadata metadata;

    @Override
    public void onBeforeInsert(UserExt entity, EntityManager entityManager) {
        assignRoles(entity,entityManager);
    }

    @Override
    public void onBeforeUpdate(UserExt entity, EntityManager entityManager) {
        assignRoles(entity,entityManager);
    }

    private void assignRoles(UserExt user, EntityManager em) {
        if(user.getStatus().equals(UserStatus.NEW)||user.getStatus().equals(UserStatus.DEACTIVATED)) {
            user.setActive(false);
        }
        else if(!user.getStatus().equals(UserStatus.ACTIVATED)) {
            //TO DO
            //Assign restricted role with permission to see user screen only
            Role role = em.find(Role.class, UUID.fromString("4a07a346-19b1-89b0-c43f-f15221196bfd"));
            user.getUserRoles().clear();
            UserRole userRole = metadata.create(UserRole.class);
            userRole.setUser(user);
            userRole.setRole(role);
            user.getUserRoles().add(userRole);
            user.setActive(true);
        }
        else {
            //TO DO
            //Assign roles according to UserRole value
            Role role = em.find(Role.class, UUID.fromString("3ec31528-dc0e-c341-7727-7b46771ae9ff"));
            user.getUserRoles().clear();
            UserRole userRole = metadata.create(UserRole.class);
            userRole.setUser(user);
            userRole.setRole(role);
            user.getUserRoles().add(userRole);
            user.setActive(true);
        }
    }
}