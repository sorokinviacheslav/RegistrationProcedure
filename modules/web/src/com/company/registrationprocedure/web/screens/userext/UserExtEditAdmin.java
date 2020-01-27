package com.company.registrationprocedure.web.screens.userext;

import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.UserExt;

@UiController("registrationprocedure_UserExtAdmin.edit")
@UiDescriptor("user-ext-admin-edit.xml")
@EditedEntityContainer("userExtDc")
@LoadDataBeforeShow
public class UserExtEditAdmin extends StandardEditor<UserExt> {
}