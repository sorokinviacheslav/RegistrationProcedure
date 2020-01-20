package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.service.RegistrationService;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UrlRouting;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.CssLayout;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.app.login.LoginScreen;
import com.haulmont.cuba.web.gui.components.WebPasswordField;

import javax.inject.Inject;


@UiController("login")
@UiDescriptor("ext-login-screen.xml")
public class ExtLoginScreen extends LoginScreen {

    @Inject
    private Button loginButton;
    @Inject
    private CssLayout loginCredentials;
    @Inject
    private RegistrationService registrationService;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Metadata metadata;

    @Subscribe("testButton")
    public void onTestButtonClick(Button.ClickEvent event) {
        screenBuilders.editor(UserExt.class, this)
                .withOpenMode(OpenMode.DIALOG)
                .editEntity(metadata.create(UserExt.class))
                .build();
    }

    @Subscribe("registerButton")
    public void onRegisterButtonClick(Button.ClickEvent event) {
        RegistrationScreen registerScreen = screens.create(RegistrationScreen.class, OpenMode.DIALOG);

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