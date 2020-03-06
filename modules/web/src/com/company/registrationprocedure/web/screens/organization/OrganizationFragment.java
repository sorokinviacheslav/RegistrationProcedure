package com.company.registrationprocedure.web.screens.organization;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.web.screens.MyScreenFragment;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationErrors;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.ScreenValidation;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

@UiController("registrationprocedure_OrganizationFragment")
@UiDescriptor("organization-fragment.xml")
public class OrganizationFragment extends MyScreenFragment {

    @Inject
    private InstanceContainer<Organization> organizationDc;
    @Inject
    private TextField<String> nameField;
    @Inject
    private Metadata metadata;
    @Inject
    private TextField<String> innField;
    @Inject
    private TextField<String> emailField;
    @Inject
    private TextField<String> kppField;

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