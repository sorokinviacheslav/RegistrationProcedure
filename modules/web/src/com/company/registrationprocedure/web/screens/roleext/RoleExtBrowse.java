package com.company.registrationprocedure.web.screens.roleext;

import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.RoleExt;

@UiController("registrationprocedure_RoleExt.browse")
@UiDescriptor("role-ext-browse.xml")
@LookupComponent("roleExtsTable")
@LoadDataBeforeShow
public class RoleExtBrowse extends StandardLookup<RoleExt> {
}