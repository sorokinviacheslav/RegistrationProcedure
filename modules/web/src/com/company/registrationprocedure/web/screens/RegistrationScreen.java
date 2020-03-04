package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.OrganizationRole;
import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.entity.UserSystemRole;
import com.company.registrationprocedure.service.RegistrationService;
import com.company.registrationprocedure.web.screens.organization.OrganizationFragment;
import com.company.registrationprocedure.web.screens.userext.UserExtFragment;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.company.registrationprocedure.web.screens.AbstractUserViewScreen;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

@UiController("registrationprocedure_RegistrationScreen")
@UiDescriptor("registration-screen.xml")
@EditedEntityContainer("userExtDc")
@LoadDataBeforeShow
public class RegistrationScreen extends StandardEditor<UserExt> {

    @Inject
    private RegistrationService registrationService;
    @Inject
    private Notifications notifications;
    @Inject
    private Messages messages;
    @Inject
    private LookupPickerField<Organization> organizationLookupPickerField;
    @Inject
    private UserExtFragment fragment;
    @Inject
    private OrganizationFragment fragment_1;
    @Inject
    private CollectionContainer<Organization> organizationsDc;
    @Inject
    private TabSheet tabSheet;
    @Inject
    private InstancePropertyContainer<Organization> organizationDc;
    @Inject
    private Metadata metadata;

    @Subscribe("organizationLookupPickerField")
    public void onOrganizationLookupPickerFieldValueChange(HasValue.ValueChangeEvent<Organization> event) {
        fragment_1.setAllElementsEditable(event.getValue()==null);
    }



    @Subscribe("back")
    public void onBack(Action.ActionPerformedEvent event) {
        int currentTab = Integer.parseInt(tabSheet.getSelectedTab().getName());
        if(currentTab>0) {
            tabSheet.setSelectedTab(String.valueOf(currentTab-1));
        }
    }

    @Subscribe("next")
    public void onNext(Action.ActionPerformedEvent event) {
        int maxTabs=tabSheet.getTabs().size();
        int currentTab = Integer.parseInt(tabSheet.getSelectedTab().getName());
        if(currentTab<(maxTabs-1)) {
            tabSheet.setSelectedTab(String.valueOf(currentTab+1));
        }
    }

    @Subscribe
    public void onInit(InitEvent event) {
        BiFunction<String, String, Boolean> predicate = String::contains;
        /*organizationLookupPickerField.setFilterPredicate((itemCaption, searchString) ->
                predicate.apply(itemCaption.toLowerCase(), searchString));*/
        organizationLookupPickerField.setFilterPredicate((itemCaption, searchString) ->{
            for(Organization org: organizationsDc.getItems()) {
                if(itemCaption.equals(org.getName())) {
                    String orgData = "" + org.getName() + org.getInn();
                    return predicate.apply(orgData.toLowerCase(), searchString);
                }
            }
            return false;
        } );
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {

    }

    @Subscribe("register")
    public void onRegisterClick(Button.ClickEvent event) {
        if(!fragment.validateAllWithMessages()) return;
        if(!fragment_1.validateAllWithMessages()) return;
        Organization org = fragment_1.getOrganization();
        org.setRole(OrganizationRole.ADMINISTRATIVE);
        RegistrationService.RegistrationResult result =registrationService.registerUser(getEditedEntity(),org);
        if(!result.isSuccess()) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(
                            "User with these email and login already exists!")
                    .show();
            return;
        }
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Created user " + getLogin())
                .show();
        close(WINDOW_DISCARD_AND_CLOSE_ACTION);
    }

    @Subscribe("cancel")
    public void onCancelClick(Button.ClickEvent event) {
        close(WINDOW_DISCARD_AND_CLOSE_ACTION);
    }
    
    public String getPassword() {
        return getEditedEntity().getPassword();
    }

    public String getLogin() {
        return getEditedEntity().getLogin();
    }
}