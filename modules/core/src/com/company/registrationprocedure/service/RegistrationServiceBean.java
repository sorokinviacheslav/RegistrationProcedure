package com.company.registrationprocedure.service;

import com.company.registrationprocedure.entity.*;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.app.EntityLog;
import com.haulmont.cuba.security.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Service(RegistrationService.NAME)
public class RegistrationServiceBean implements RegistrationService {

    /**
     * ID of the Group for self-registered users.
     */
    private static final String COMPANY_GROUP_ID = "0fa2b1a5-1d68-4d69-9fbd-dff348347f93";

    /**
     * ID of the Role to be assigned to self-registered users.
     */
    private static final String DEFAULT_ROLE_ID = "3ec31528-dc0e-c341-7727-7b46771ae9ff";

    @Inject
    private Metadata metadata;
    @Inject
    private PasswordEncryption passwordEncryption;
    @Inject
    private EmailService emailService;
    @Inject
    private Persistence persistence;


    @Override
    @Transactional
    public RegistrationResult registerUser(UserExt user,Organization org, Collection<RoleExt> roles) {
        EntityManager em = persistence.getEntityManager();
        if(isUserExists(user,em)) return new RegistrationResult(null);
        if(!isOrganizationExists(org.getId(),em)) {
            em.persist(org);
            user.setOrganization(org);
        }
        // Load group and role to be assigned to the new user
        Group group = em.find(Group.class,UUID.fromString(COMPANY_GROUP_ID));
        //Organization org = em.find(Organization.class,user.getOrganization().getId());

       // Note that the platform does not support the default group out of the box, so here we define the default group id and set it for the newly registered users.
        user.setGroup(group);
        user.setStatus(UserStatus.NEW);
        em.persist(user);
        for(RoleExt role:roles) {
            UserRole uRole = metadata.create(UserRole.class);
            uRole.setRole(role);
            uRole.setUser(user);
            em.persist(uRole);
        }
        /*EmailInfo emailInfo = new EmailInfo(
                user.getEmail(), // recipients
                "Activate your account",
                "http://localhost:8080/app/#activate?value=" + user.getId()
        );
        emailService.sendEmailAsync(emailInfo);*/
        return new RegistrationResult(user);
    }

    @Override
    @Transactional
    public void restoreOldValues(UUID id, String... atrNames) {
        EntityManager em = persistence.getEntityManager();
        if(atrNames==null||atrNames.length==0) return;
        UserExt user = em.find(UserExt.class,id);
        EntityLogItem le = (EntityLogItem)em.createQuery(
                "select e from sec$EntityLog e where e.entityInstanceName = :name")
                .setParameter("name",user.getInstanceName()).getFirstResult();
        //if(le!=null) user.setStatus(UserStatus.DEACTIVATED);
    }

    @Override
    @Transactional
    public boolean userExists(UserExt user) {
        EntityManager em = persistence.getEntityManager();
        return isUserExists(user,em);
    }

    private boolean isUserExists(UserExt user,EntityManager em) {
        int existing = em.createQuery(
                "select u from sec$User u where u.loginLowerCase = :login or u.email = :email")
                        .setParameter("login", user.getLogin()).setParameter("email",user.getEmail()).getResultList().size();
        return !(existing < 1);
    }

    private boolean isOrganizationExists(UUID orgUUID,EntityManager em) {
        int existing = em.createQuery(
                "select o from registrationprocedure_Organization o where o.id = :id")
                .setParameter("id", orgUUID).getResultList().size();
        return !(existing < 1);
    }
}