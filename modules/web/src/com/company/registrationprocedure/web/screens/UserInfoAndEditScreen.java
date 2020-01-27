package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.web.screens.organization.OrganizationEdit;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.app.PersistenceManagerService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("registrationprocedure_UserInfoAndEditScreen")
@UiDescriptor("user-info-and-edit-screen.xml")
public class UserInfoAndEditScreen extends AbstractUserViewScreen {

    @Inject
    private MaskedField<String> phoneField;
    @Inject
    private InstanceContainer<UserExt> userExtDc;
    @Inject
    private TextField<String> firstNameField;
    @Inject
    private TextField<String> middleNameField;
    @Inject
    private TextField<String> login;
    @Inject
    private TextField<String> lastNameField;
    @Inject
    private CheckBox hideEmail;
    @Inject
    private InstanceLoader<UserExt> userExtDl;
    @Inject
    private CheckBox emailNotificationsCheckBox;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private TextField<String> emailField;
    @Inject
    private Button editAccountButton;
    @Inject
    private PickerField<Organization> orgPickerField;

    private UUID userId =null;
    private boolean editMode = false;

    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Screens screens;

    public void setUserId(UUID id) {
        this.userId = id;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public boolean setEditMode(boolean editMode) {
        this.editMode = editMode;
        return isEditMode();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if(userId==null) {
            userExtDl.setEntityId(userSessionSource.getUserSession().getUser().getId());
        }
        else {
            userExtDl.setEntityId(userId);
        }
        userExtDl.load();
        orgPickerField.getAction("open").setEnabled(false);
        orgPickerField.setFieldEditable(false);
    }

    @Subscribe("backButton")
    public void onBackButtonClick(Button.ClickEvent event) {
        close(Screen.WINDOW_DISCARD_AND_CLOSE_ACTION);
    }

    @Subscribe("editAccountButton")
    public void onEditAccountButtonClick(Button.ClickEvent event) {
        if(this.setEditMode(!this.isEditMode())) {
            phoneField.setEditable(true);
            firstNameField.setEditable(true);
            middleNameField.setEditable(true);
            lastNameField.setEditable(true);
            orgPickerField.setEnabled(true);
            orgPickerField.getAction("open").setEnabled(true);
            hideEmail.setEditable(true);
            emailNotificationsCheckBox.setEditable(true);
            editAccountButton.setCaption("Save Changes");
        } else {
            if(!validateAllWithMessages()) return;
            UserExt user = userExtDc.getItem();
            user.setPhoneNumber(phoneField.getValue());
            user.setFirstName(firstNameField.getValue());
            user.setLastName(lastNameField.getValue());
            user.setMiddleName(middleNameField.getValue());
            user.setHideEmail(hideEmail.getValue());
            user.setRecieveEmailNotifications(emailNotificationsCheckBox.isChecked());
            dataManager.commit(user);
            phoneField.setEditable(false);
            firstNameField.setEditable(false);
            middleNameField.setEditable(false);
            lastNameField.setEditable(false);
            orgPickerField.setEnabled(false);
            orgPickerField.getAction("open").setEnabled(false);
            hideEmail.setEditable(false);
            emailNotificationsCheckBox.setEditable(false);
            editAccountButton.setCaption("Edit Profile");
        }
    }
}