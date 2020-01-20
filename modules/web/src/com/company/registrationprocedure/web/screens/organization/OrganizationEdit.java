package com.company.registrationprocedure.web.screens.organization;

import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.Organization;

@UiController("registrationprocedure_Organization.edit")
@UiDescriptor("organization-edit.xml")
@EditedEntityContainer("organizationDc")
@LoadDataBeforeShow
public class OrganizationEdit extends StandardEditor<Organization> {
}