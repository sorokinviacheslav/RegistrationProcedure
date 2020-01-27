package com.company.registrationprocedure.web.screens.userext;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.UserExt;

import javax.inject.Inject;

@UiController("registrationprocedure_UserExtAdmin.browse")
@UiDescriptor("user-ext-browse-admin.xml")
@LookupComponent("userExtsTable")
@LoadDataBeforeShow
public class UserExtBrowseAdmin extends StandardLookup<UserExt> {
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private GroupTable<UserExt> userExtsTable;

    @Subscribe("userExtsTable.edit")
    protected void onUserExtsTableEditActionPerformed(Action.ActionPerformedEvent event) {
        screenBuilders.editor(userExtsTable)
                //.editEntity(userExtsTable.getSingleSelected())
                .withScreenClass(UserExtEditAdmin.class)     // specific editor screen
                .withLaunchMode(OpenMode.DIALOG)        // open as modal dialog
                .build()
                .show();
    }

}