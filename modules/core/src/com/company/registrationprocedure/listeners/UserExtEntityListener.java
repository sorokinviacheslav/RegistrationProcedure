package com.company.registrationprocedure.listeners;

import com.company.registrationprocedure.entity.*;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.haulmont.cuba.security.entity.Role;
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
            setRestrictedRole(user,em);
            user.setActive(false);
        }
        else if(!user.getStatus().equals(UserStatus.ACTIVATED)) {
            //Assign restricted role with permission to see user info screen only
            //We don't care about organization type here
            setRestrictedRole(user,em);
            user.setActive(true);
        }
        else {
            //Main role assignment happens here
            setProperRoles(user,em);
            user.setActive(true);
        }
    }

    private void setRestrictedRole(UserExt user,EntityManager em) {
        List<UserRole> currentUserRoles = em.createQuery("select r from sec$UserRole r where " +
                "r.user = :user ").setParameter("user",user).getResultList();
        RoleExt role = em.find(RoleExt.class, UUID.fromString("4a07a346-19b1-89b0-c43f-f15221196bfd"));
        //TO DO try to use Set instead of this check and use seUserRoles for User
        boolean hasRestrictedRole = false;
        for(UserRole ur: currentUserRoles) {
            if(!ur.getRole().equals(role)) {
                em.remove(ur);
            }
            else {
                hasRestrictedRole = true;
            }
        }
        if(!hasRestrictedRole) {
            UserRole userRole = metadata.create(UserRole.class);
            userRole.setUser(user);
            userRole.setRole(role);
            em.persist(userRole);
        }
    }

    private void setProperRoles(UserExt user,EntityManager em) {
        if(user.getLogin().equalsIgnoreCase("admin")) {
            return;
        }
        List<UserRole> currentRoles = em.createQuery("select r from sec$UserRole r where " +
                "r.user = :user ").setParameter("user",user).getResultList();
        List<RoleExt> newRoles = em.createQuery("select r from registrationprocedure_RoleExt r where " +
                "r.organizationRole = :org "+"and r.userRole = :user ").setParameter("org",user.getOrganization().getRole()).setParameter("user",user.getSystemRole()).getResultList();
        for(UserRole currentRole :currentRoles) {
            if(!newRoles.contains(currentRole.getRole())) {
                em.remove(currentRole);
            }
        }
        for(RoleExt newRole: newRoles) {
            if(!currentRoles.contains(newRole)) {
                UserRole userRole = metadata.create(UserRole.class);
                userRole.setUser(user);
                userRole.setRole(newRole);
                em.persist(userRole);
            }
        }

    }
}