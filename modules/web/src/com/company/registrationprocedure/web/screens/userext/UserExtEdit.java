package com.company.registrationprocedure.web.screens.userext;

import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.UserExt;

@UiController("registrationprocedure_UserExt.edit")
@UiDescriptor("user-ext-edit.xml")
@EditedEntityContainer("userExtDc")
@LoadDataBeforeShow
public class UserExtEdit extends StandardEditor<UserExt> {
}