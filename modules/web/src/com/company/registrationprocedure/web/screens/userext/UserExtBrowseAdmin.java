package com.company.registrationprocedure.web.screens.userext;

import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.UserExt;

import javax.inject.Named;

@UiController("registrationprocedure_UserExtAdmin.browse")
@UiDescriptor("user-ext-browse-admin.xml")
@LookupComponent("userExtsTable")
@LoadDataBeforeShow
public class UserExtBrowseAdmin extends StandardLookup<UserExt> {
    @Named("userExtsTable.edit")
    private EditAction userExtsTableEdit;

    @Subscribe
    public void onInit(InitEvent event) {
        userExtsTableEdit.setScreenId("registrationprocedure_UserExtAdmin.edit");
    }


}