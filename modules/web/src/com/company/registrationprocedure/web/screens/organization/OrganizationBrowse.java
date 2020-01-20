package com.company.registrationprocedure.web.screens.organization;

import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.entity.Organization;

@UiController("registrationprocedure_Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
@LoadDataBeforeShow
public class OrganizationBrowse extends StandardLookup<Organization> {
}