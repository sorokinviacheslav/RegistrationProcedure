package com.company.registrationprocedure.service;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.RoleExt;
import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.entity.UserStatus;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
    public RegistrationResult registerUser(RegistrationData regData) {
        EntityManager em = persistence.getEntityManager();
        if(isUserExists(regData.getLogin(),regData.getEmail(),em)) return new RegistrationResult(null);
        if(!isOrganizationExists(regData.getOrganizationUUID(),em)) return new RegistrationResult(null);
        // Load group and role to be assigned to the new user
        Group group = em.find(Group.class,UUID.fromString(COMPANY_GROUP_ID));
        //Role role =em.find(Role.class,UUID.fromString("4a07a346-19b1-89b0-c43f-f15221196bfd"));
        Organization org = em.find(Organization.class,regData.getOrganizationUUID());

        // Create a user instance
        UserExt user = metadata.create(UserExt.class);
        user.setLogin(regData.getLogin());
        user.setPassword(passwordEncryption.getPasswordHash(user.getId(), regData.getPassword()));
        user.setEmail(regData.getEmail());
        user.setStatus(UserStatus.NEW);
        user.setHideEmail(regData.isHideEmail());
        user.setOrganization(org);
        user.setPhoneNumber(regData.getPhoneNumber());
        user.setRecieveEmailNotifications(regData.isEmailNotifications());
        user.setFirstName(regData.getFirstName());
        user.setLastName(regData.getLastName());
        user.setMiddleName(regData.getMiddleName());
        user.setSystemRole(regData.getRole());

        // Note that the platform does not support the default group out of the box, so here we define the default group id and set it for the newly registered users.
        user.setGroup(group);

        /* Create a link to the role
         * Here we programmatically set the default role.
         * Another way is to set the default role by using the DB scripts. Set IS_DEFAULT_ROLE parameter to true in the insert script for the role.
         * Also, this parameter might be changed in the Role Editor screen.
         */
        /*UserRole userRole = metadata.create(UserRole.class);
        userRole.setUser(user);
        userRole.setRole(role);*/

        // Save new entities
        em.persist(user);
        //em.persist(userRole);
        EmailInfo emailInfo = new EmailInfo(
                user.getEmail(), // recipients
                "Activate your account",
                "http://localhost:8080/app/#activate?value=" + user.getId()
        );
        emailService.sendEmailAsync(emailInfo);
        return new RegistrationResult(user);
    }

    @Override
    @Transactional
    public boolean userExists(String login, String email) {
        EntityManager em = persistence.getEntityManager();
        return isUserExists(login,email,em);
    }

    private boolean isUserExists(String login,String email,EntityManager em) {
        int existing = em.createQuery(
                "select u from sec$User u where u.loginLowerCase = :login or u.email = :email")
                        .setParameter("login", login).setParameter("email",email).getResultList().size();
        return !(existing < 1);
    }

    private boolean isOrganizationExists(UUID orgUUID,EntityManager em) {
        int existing = em.createQuery(
                "select o from registrationprocedure_Organization o where o.id = :id")
                .setParameter("id", orgUUID).getResultList().size();
        return !(existing < 1);
    }
}