package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.RoleExt;
import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.entity.UserStatus;
import com.company.registrationprocedure.service.RegistrationService;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.auth.Credentials;
import com.haulmont.cuba.security.auth.LoginPasswordCredentials;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.LoginException;
import com.haulmont.cuba.web.app.login.LoginScreen;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

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

    /*@Subscribe("testButton")
    public void onTestButtonClick(Button.ClickEvent event) {

    }*/

    @Subscribe("registerButton")
    public void onRegisterButtonClick(Button.ClickEvent event) {
        RegistrationScreen registerScreen = screens.create(RegistrationScreen.class, OpenMode.DIALOG);
        registerScreen.setEntityToEdit(metadata.create(UserExt.class));
        // Add a listener to be notified when the "Register" screen is closed with COMMIT_ACTION_ID
        registerScreen.addAfterCloseListener(afterCloseEvent -> {
            CloseAction closeAction = afterCloseEvent.getCloseAction();
            if (closeAction == WINDOW_COMMIT_AND_CLOSE_ACTION) {
                // Set the new registered user credentials to login and password fields
                loginField.setValue(registerScreen.getLogin());
                passwordField.setValue(registerScreen.getPassword());
                // Set focus on "Submit" button
                loginButton.focus();
            }
        });

        // Show "Register" screen
        registerScreen.show();
    }
    
}