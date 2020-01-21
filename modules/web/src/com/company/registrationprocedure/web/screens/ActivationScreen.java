package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.entity.UserStatus;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.navigation.UrlParamsChangedEvent;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.UUID;

@Route("activate")
@UiController("registrationprocedure_ActivationScreen")
@UiDescriptor("activation-screen.xml")
@LoadDataBeforeShow
public class ActivationScreen extends Screen {
    @Inject
    private InstanceLoader<UserExt> userExtDl;
    @Inject
    private InstanceContainer<UserExt> userExtDc;
    @Inject
    private DataContext dataContext;
    @Inject
    private Label<String> label1;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onUrlParamsChanged(UrlParamsChangedEvent event) {
        //TO DO
        //check if already logged in
        //probably move data manipulation to bean
        UUID userId = UUID.fromString(event.getParams().get("value"));
        userExtDl.setEntityId(userId);
        userExtDl.load();

        if(userExtDc.getItemOrNull()==null) {
            label1.setValue("Invalid link! Please contact support.");
        }
        else if(!userExtDc.getItem().getStatus().equals(UserStatus.NEW)) {
            label1.setValue("Your account has been already activated!");
        }
        else {
            label1.setValue("Welcome "+userExtDc.getItem().getLogin()+"!");
            userExtDc.getItem().setStatus(UserStatus.EMAIL_CONFIRMED);
            dataContext.commit();
        }
    }

    @Subscribe("backToLogin")
    public void onBackToLoginClick(Button.ClickEvent event) {
            redirectToLogin();
    }

    private void redirectToLogin() {
        screenBuilders.screen(this)
                .withScreenClass(ExtLoginScreen.class)
                .withOpenMode(OpenMode.ROOT)
                .build()
                .show();
    }

    private void redirectToLogin(ScreenOptions options) {
        screenBuilders.screen(this)
                .withScreenClass(ExtLoginScreen.class)
                .withOpenMode(OpenMode.ROOT)
                .withOptions(options)
                .build()
                .show();
    }
}