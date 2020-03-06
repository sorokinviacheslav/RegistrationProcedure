package com.company.registrationprocedure.web.screens.userext;

import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.web.screens.MyScreenFragment;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.PasswordField;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("registrationprocedure_UserExtFragment")
@UiDescriptor("user-ext-fragment.xml")
public class UserExtFragment extends MyScreenFragment {

    @Inject
    private Notifications notifications;
    @Inject
    private PasswordField passwordField;
    @Inject
    private ScreenValidation screenValidation;
    @Inject
    private PasswordField passwordConfirmField;

    @Override
    public boolean validateAllWithMessages() {
       if(!super.validateAllWithMessages()) return false;
        if(!passwordField.getValue().equals(passwordConfirmField.getValue())) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Passwords don't match!")
                    .show();
            return false;
        }
        return true;
    }
}