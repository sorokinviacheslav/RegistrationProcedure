package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.Organization;
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
    private MaskedField<String> phoneField;
    @Inject
    private PasswordField passwordConfirmField;
    @Inject
    private TextField<String> login;
    @Inject
    private TextField<String> emailField;
    @Inject
    private PickerField<Organization> organizationPickerField;
    @Inject
    private TextField<String> middleNameField;
    @Inject
    private TextField<String> lastNameField;
    @Inject
    private CheckBox hideEmail;
    @Inject
    private TextField<String> firstNameField;
    @Inject
    private CheckBox emailNotificationsCheckBox;
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
        if(!validateAllWithMessages()) return;
        if(!passwordField.getValue().equals(passwordConfirmField.getValue())) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Passwords don't match!")
                    .show();
            return;
        }
        try {
            RegistrationService.RegistrationData regData = new RegistrationService.RegistrationData(login.getValue(),passwordField.getValue(),emailField.getValue(),false);
            regData.setFirstName(firstNameField.getValue());
            regData.setLastName(lastNameField.getValue());
            regData.setMiddleName(middleNameField.getValue());
            regData.setPhoneNumber(phoneField.getValue());
            regData.setEmailNotifications(emailNotificationsCheckBox.getValue());
            regData.setHideEmail(hideEmail.getValue());
            regData.setOrganization(organizationPickerField.getValue());
            registrationService.registerUser(regData);

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

    private boolean validateAllWithMessages() {
        ValidationErrors errors = screenValidation.validateUiComponents(this.getWindow().getComponents());
        if(errors.isEmpty()) return true;
        for (ValidationErrors.Item er : errors.getAll()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(er.description)
                    .show();
        }
        return false;
    }
}