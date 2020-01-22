package com.company.registrationprocedure.listeners;

import com.company.registrationprocedure.entity.*;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.security.entity.UserRole;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
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
            //new and deactivated users can't log in
            user.setActive(false);
        }
        else if(!user.getStatus().equals(UserStatus.ACTIVATED)) {
            //Assign restricted role with permission to see user info screen only
            //We don't care about organization type here
            RoleExt role = em.find(RoleExt.class, UUID.fromString("4a07a346-19b1-89b0-c43f-f15221196bfd"));
            user.getUserRoles().clear();
            UserRole userRole = metadata.create(UserRole.class);
            userRole.setUser(user);
            userRole.setRole(role);
            em.persist(userRole);
            user.getUserRoles().add(userRole);
            user.setActive(true);
        }
        else {
            //Main role assignment happens here
            //TO DO
            //Assign roles according to UserRole and OrganizationRole value
            List<RoleExt> roles = em.createQuery("select r from registrationprocedure_RoleExt r where " +
                    "r.organizationRole = :org "+"and r.userRole = :user ").setParameter("org",OrganizationRole.ADMINISTRATIVE).setParameter("user",user.getSystemRole()).getResultList();
            List<UserRole> currentUserRoles = em.createQuery("select r from sec$UserRole r where " +
                    "r.user = :user ").setParameter("user",user).getResultList();
            for(UserRole ur: currentUserRoles) {
                em.remove(ur);
            }
            for(RoleExt role:roles) {
                UserRole userRole = metadata.create(UserRole.class);
                userRole.setUser(user);
                userRole.setRole(role);
                em.persist(userRole);
            }
            user.setActive(true);
        }
    }
}