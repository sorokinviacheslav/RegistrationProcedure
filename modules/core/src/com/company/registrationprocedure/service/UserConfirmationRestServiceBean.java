package com.company.registrationprocedure.service;

import com.company.registrationprocedure.entity.UserExt;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(UserConfirmationRestService.NAME)
public class UserConfirmationRestServiceBean implements UserConfirmationRestService {

    @Inject
    private DataManager dataManager;

    @Override
    public boolean confirmRegistration(String username) {
        List<UserExt> users = dataManager.load(UserExt.class)
                .query("select s from registrationprocedure_UserExt s where " +
                        "s.login = :login ")
                .parameter("login", username)
                .list();

        if (users!=null&&users.size()>0) {
            users.get(0).setConfirmed(true);
            dataManager.commit(users.get(0));
            return true;
        }

        return false;
    }
}