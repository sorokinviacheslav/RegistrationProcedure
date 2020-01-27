package com.company.registrationprocedure.web.screens.roleext;

import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.web.screens.userext.UserExtEditAdmin;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.RoleExt;

import javax.inject.Inject;

@UiController("registrationprocedure_RoleExt.browse")
@UiDescriptor("role-ext-browse.xml")
@LookupComponent("roleExtsTable")
@LoadDataBeforeShow
public class RoleExtBrowse extends StandardLookup<RoleExt> {

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private GroupTable<RoleExt> roleExtsTable;

    @Subscribe("roleExtsTable.edit")
    protected void onRoleExtsTableEditActionPerformed(Action.ActionPerformedEvent event) {
        screenBuilders.editor(roleExtsTable)
                //.editEntity(userExtsTable.getSingleSelected())
                .withScreenClass(RoleExtEdit.class)     // specific editor screen
                .withLaunchMode(OpenMode.DIALOG)        // open as modal dialog
                .build()
                .show();
    }
}