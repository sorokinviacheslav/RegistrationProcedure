package com.company.registrationprocedure.web.screens.userext;

import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.UserExt;

@UiController("registrationprocedure_UserExt.browse")
@UiDescriptor("user-ext-browse.xml")
@LookupComponent("userExtsTable")
@LoadDataBeforeShow
public class UserExtBrowse extends StandardLookup<UserExt> {
}