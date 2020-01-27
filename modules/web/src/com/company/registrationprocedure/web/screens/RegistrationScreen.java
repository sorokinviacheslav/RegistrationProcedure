package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.UserSystemRole;
import com.company.registrationprocedure.service.RegistrationService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.web.screens.AbstractUserViewScreen;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

@UiController("registrationprocedure_RegistrationScreen")
@UiDescriptor("registration-screen.xml")
public class RegistrationScreen extends AbstractUserViewScreen {

    @Inject
    private RegistrationService registrationService;
    @Inject
    private Notifications notifications;
    @Inject
    private Messages messages;
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
    private LookupPickerField<Organization> organizationLookupPickerField;

    @Inject
    private CollectionContainer<Organization> organizationsDc;
    @Inject
    private CollectionLoader<Organization> organizationsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        organizationsDl.load();
        organizationLookupPickerField.setOptionsList(organizationsDc.getItems());
        organizationLookupPickerField.setPageLength(10);
        BiFunction<String, String, Boolean> predicate = String::contains;
        /*organizationLookupPickerField.setFilterPredicate((itemCaption, searchString) ->
                predicate.apply(itemCaption.toLowerCase(), searchString));*/
        organizationLookupPickerField.setFilterPredicate((itemCaption, searchString) ->{
            for(Organization org: organizationsDc.getItems()) {
                if(itemCaption.equals(org.getName())) {
                    String orgData = "" + org.getName() + org.getInn();
                    return predicate.apply(orgData.toLowerCase(), searchString);
                }
            }
            return false;
        } );
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {

    }

    @Subscribe("register")
    public void onRegisterClick(Button.ClickEvent event) {
        if(!validateAllWithMessages()) return;
        if(!passwordField.getValue().equals(passwordConfirmField.getValue())) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Passwords don't match!")
                    .show();
            return;
        }
        RegistrationService.RegistrationData regData = new RegistrationService.RegistrationData(login.getValue(),passwordField.getValue(),emailField.getValue(),false);
        regData.setFirstName(firstNameField.getValue());
        regData.setLastName(lastNameField.getValue());
        regData.setMiddleName(middleNameField.getValue());
        regData.setPhoneNumber(phoneField.getValue());
        regData.setEmailNotifications(emailNotificationsCheckBox.getValue());
        regData.setHideEmail(hideEmail.getValue());
        regData.setOrganizationUUID(organizationLookupPickerField.getValue().getId());
        regData.setRole(UserSystemRole.ACCESS_ADMINISTRATOR);
        RegistrationService.RegistrationResult result =registrationService.registerUser(regData);
        if(!result.isSuccess()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(
                            "User with these email and login already exists!")
                    .show();
            return;
        }
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Created user " + getLogin())
                .show();
        close(WINDOW_COMMIT_AND_CLOSE_ACTION);
    }

    @Subscribe("cancel")
    public void onCancelClick(Button.ClickEvent event) {
        close(Screen.WINDOW_DISCARD_AND_CLOSE_ACTION);
    }
    
    public String getPassword() {
        return passwordField.getValue();
    }

    public String getLogin() {
        return login.getValue();
    }
}