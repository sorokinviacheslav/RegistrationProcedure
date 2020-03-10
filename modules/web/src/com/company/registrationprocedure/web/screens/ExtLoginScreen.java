package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.UserExt;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.auth.Credentials;
import com.haulmont.cuba.security.auth.LoginPasswordCredentials;
import com.haulmont.cuba.security.global.LoginException;
import com.haulmont.cuba.web.app.login.LoginScreen;

import javax.inject.Inject;
import java.util.List;

@UiController("login")
@UiDescriptor("ext-login-screen.xml")
public class ExtLoginScreen extends LoginScreen {

    @Inject
    private Button loginButton;

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    @Override
    protected void doLogin(Credentials cr) throws LoginException {
        LoginPasswordCredentials extCred = (LoginPasswordCredentials)cr;
        extCred.setLogin(findLoginByEmail(extCred.getLogin()));
        super.doLogin(extCred);
    }

    private String findLoginByEmail(String email) throws LoginException {
        if (!email.contains("@")) {
            return email;
        }
        // find user login by email using dataService
        List<UserExt> users = dataManager.loadList(LoadContext.create(UserExt.class)
                .setQuery(new LoadContext.Query("select u from registrationprocedure_UserExt u where u.email = :email")
                        .setParameter("email", email)));

        if (users.isEmpty()) {
            throw new LoginException("Unable to find user");
        }

        return users.get(0).getLogin();
    }

    @Subscribe("registerButton")
    public void onRegisterButtonClick(Button.ClickEvent event) {
        RegistrationScreen registerScreen = screens.create(RegistrationScreen.class, OpenMode.ROOT);
        registerScreen.setEntityToEdit(metadata.create(UserExt.class));
        // Add a listener to be notified when the "Register" screen is closed with COMMIT_ACTION_ID
        // Show "Register" screen
        registerScreen.show();
    }
    
}