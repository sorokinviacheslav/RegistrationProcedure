package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.web.screens.organization.OrganizationFragment;
import com.company.registrationprocedure.web.screens.userext.UserExtFragment;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.UserExt;

import javax.inject.Inject;
import java.util.UUID;

@UiController("registrationprocedure_UserExtInfo")
@UiDescriptor("user-ext-info.xml")
@EditedEntityContainer("userExtDc")
public class UserExtInfo extends Screen {
    @Inject
    private UserExtFragment fragment;
    @Inject
    private OrganizationFragment fragment_1;
    @Inject
    private InstanceLoader<UserExt> userExtDl;

    @Subscribe
    public void onInit(InitEvent event) {
        fragment.setAllElementsEditable(false);
        fragment_1.setAllElementsEditable(false);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        userExtDl.load();
    }

    public void setUserId(UUID id) {
        userExtDl.setEntityId(id);
    }

}