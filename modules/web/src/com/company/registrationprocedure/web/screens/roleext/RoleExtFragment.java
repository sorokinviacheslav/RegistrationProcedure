package com.company.registrationprocedure.web.screens.roleext;

import com.company.registrationprocedure.entity.OrganizationRole;
import com.company.registrationprocedure.entity.RoleExt;
import com.company.registrationprocedure.web.screens.MyScreenFragment;
import com.haulmont.cuba.gui.components.TwinColumn;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@UiController("registrationprocedure_RoleExtFragment")
@UiDescriptor("role-ext-fragment.xml")
public class RoleExtFragment extends MyScreenFragment {

    @Inject
    private TwinColumn<RoleExt> twinColumn;

    public Collection<RoleExt> getRoles() {
        return twinColumn.getValue();
    }
}