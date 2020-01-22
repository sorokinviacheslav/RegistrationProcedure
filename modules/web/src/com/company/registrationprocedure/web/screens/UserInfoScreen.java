package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.UserExt;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.company.registrationprocedure.web.screens.AbstractUserViewScreen;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.UUID;

@UiController("registrationprocedure_UserInfoScreen")
@UiDescriptor("user-info-screen.xml")
public class UserInfoScreen extends AbstractUserViewScreen {

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

}