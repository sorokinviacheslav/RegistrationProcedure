package com.company.registrationprocedure.web.screens.organization;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.web.screens.MyScreenFragment;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("registrationprocedure_OrganizationFragment")
@UiDescriptor("organization-fragment.xml")
public class OrganizationFragment extends MyScreenFragment {

    @Inject
    private TextField<String> innField;
    @Inject
    private TextField<String> emailField;
    @Inject
    private TextField<String> nameField;
    @Inject
    private TextField<String> kppField;
    @Inject
    private InstanceContainer<Organization> organizationDc;
    @Inject
    private Metadata metadata;

    @Subscribe
    public void onInit(InitEvent event) {

    }

    public Organization getOrganization() {
        if(organizationDc.getItemOrNull()!=null) {
            return organizationDc.getItem();
        }
        Organization org = metadata.create(Organization.class);
        org.setInn(innField.getValue());
        org.setName(nameField.getValue());
        org.setEmail(emailField.getValue());
        org.setKpp(kppField.getValue());
        return org;
    }
}