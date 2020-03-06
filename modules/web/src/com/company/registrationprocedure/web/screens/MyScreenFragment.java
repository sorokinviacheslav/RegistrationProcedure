package com.company.registrationprocedure.web.screens;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.ScreenValidation;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("registrationprocedure_MyScreenFragment")
public class MyScreenFragment extends ScreenFragment {

    @Inject
    private ScreenValidation screenValidation;
    @Inject
    private Notifications notifications;

    public boolean validateAllWithMessages() {
        ValidationErrors errors = screenValidation.validateUiComponents(this.getFragment().getComponents());
        if(errors.isEmpty()) return true;
        for (ValidationErrors.Item er : errors.getAll()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(er.description)
                    .show();
        }
        return false;
    }

    public void setAllElementsEditable(boolean param) {
        for(Component c: this.getFragment().getComponents()) {
            if(c instanceof Component.Editable) {
                ((Component.Editable) c).setEditable(param);
            }
            else {
                c.setEnabled(param);
            }
        }
    }
}