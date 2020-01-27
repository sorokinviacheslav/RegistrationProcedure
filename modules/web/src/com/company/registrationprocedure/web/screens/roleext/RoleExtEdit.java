package com.company.registrationprocedure.web.screens.roleext;

import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.RoleExt;

@UiController("registrationprocedure_RoleExt.edit")
@UiDescriptor("role-ext-edit.xml")
@EditedEntityContainer("roleExtDc")
@LoadDataBeforeShow
public class RoleExtEdit extends StandardEditor<RoleExt> {
}