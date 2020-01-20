package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.web.screens.userext.UserExtEdit;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.UserExt;

import javax.inject.Inject;

@UiController("registrationprocedure_UserExt.browse")
@UiDescriptor("user-ext-browse.xml")
@LookupComponent("userExtsTable")
@LoadDataBeforeShow
public class UserExtBrowse extends StandardLookup<UserExt> {
}