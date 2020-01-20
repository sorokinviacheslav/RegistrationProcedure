package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.service.RegistrationService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.validation.MethodParametersValidationException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("registrationprocedure_RegistrationScreen")
@UiDescriptor("registration-screen.xml")
public class RegistrationScreen extends Screen {

    @Inject
    private PasswordField passwordField;
    @Inject
    private TextField<String> login;
    @Inject
    private TextField<String> emailField;
    @Inject
    private RegistrationService registrationService;
    @Inject
    private Notifications notifications;
    @Inject
    private Messages messages;

    @Subscribe("register")
    public void onRegisterClick(Button.ClickEvent event) throws ValidationException {
        /*if(!this.getWindow().validateAll()) {
            this.getWindow().showNotification("Invalid field input", Frame.NotificationType.TRAY);
            return;
        }*/
        try {
            passwordField.validate();
        } catch (ValidationException e) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Error password1")
                    .show();
        }
        try {
            emailField.validate();
        } catch (ValidationException e) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Error email")
                    .show();
        }
    /*try {
            registrationService.registerUser(getLogin(), getPassword());

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Created user " + getLogin())
                    .show();

            close(WINDOW_COMMIT_AND_CLOSE_ACTION);
        } catch (MethodParametersValidationException e) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(
                            messages.getMessage(
                                    "com.company.sample.validation",
                                    "UserExistsValidator.message"))
                    .show();
        }*/
    }

    public String getPassword() {
        return passwordField.getValue();
    }

    public String getLogin() {
        return login.getValue();
    }
}