package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.service.RegistrationService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.validation.MethodParametersValidationException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;

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
    @Inject
    private ScreenValidation screenValidation;

    @Subscribe("register")
    public void onRegisterClick(Button.ClickEvent event) throws ValidationException {
        ValidationErrors errors = screenValidation.validateUiComponents(this.getWindow().getComponents());
        if(!errors.isEmpty()) {
            for (ValidationErrors.Item er : errors.getAll()) {
                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption(er.description)
                        .show();
            }
            return;
        }
        try {
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
        }
    }

    public String getPassword() {
        return passwordField.getValue();
    }

    public String getLogin() {
        return login.getValue();
    }
}