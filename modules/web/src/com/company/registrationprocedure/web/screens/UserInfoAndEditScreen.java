package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.UserExt;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.Actions;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.UUID;

@UiController("registrationprocedure_UserInfoAndEditScreen")
@UiDescriptor("user-info-and-edit-screen.xml")
public class UserInfoAndEditScreen extends AbstractUserViewScreen {

    @Inject
    private InstanceContainer<UserExt> userExtDc;
    @Inject
    private InstanceLoader<UserExt> userExtDl;
    private UUID userId =null;
    @Inject
    private UserSessionSource userSessionSource;

    public void setUserId(UUID id) {
        this.userId = id;
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
    }

    @Subscribe("backButton")
    public void onBackButtonClick(Button.ClickEvent event) {
        close(Screen.WINDOW_DISCARD_AND_CLOSE_ACTION);
    }

    @Subscribe("editAccountButton")
    public void onEditAccountButtonClick(Button.ClickEvent event) {
        setAllElementsEditable(true);
    }


}