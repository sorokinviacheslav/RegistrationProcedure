package com.company.registrationprocedure.web.screens;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("registrationprocedure_AbstractUserViewScreen")
@UiDescriptor("abstract-user-view-screen.xml")
public class AbstractUserViewScreen extends Screen {

    @Inject
    private Notifications notifications;
    @Inject
    private ScreenValidation screenValidation;

    protected boolean validateAllWithMessages() {
        ValidationErrors errors = screenValidation.validateUiComponents(this.getWindow().getComponents());
        if(errors.isEmpty()) return true;
        for (ValidationErrors.Item er : errors.getAll()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(er.description)
                    .show();
        }
        return false;
    }

    protected void setAllElementsEditable(boolean param) {
        for(Component c: this.getWindow().getComponents()) {
            if(c instanceof Component.Editable) {
                ((Component.Editable) c).setEditable(param);
            }
            else {
                c.setEnabled(param);
            }
        }
    }
}