package com.company.registrationprocedure.web.screens;

import com.company.registrationprocedure.entity.Organization;
import com.company.registrationprocedure.entity.OrganizationRole;
import com.company.registrationprocedure.entity.RoleExt;
import com.company.registrationprocedure.entity.UserExt;
import com.company.registrationprocedure.service.OrganizationService;
import com.company.registrationprocedure.service.RegistrationService;
import com.company.registrationprocedure.web.screens.organization.OrganizationFragment;
import com.company.registrationprocedure.web.screens.roleext.RoleExtFragment;
import com.company.registrationprocedure.web.screens.userext.UserExtFragment;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.Collection;
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
    private TextField<String> kppField;
    @Inject
    private TextField<String> innField;
    @Inject
    private UserExtFragment userFragment;
    @Inject
    private OrganizationFragment organizationFragment;
    @Inject
    private RoleExtFragment roleFragment;
    @Inject
    private TabSheet tabSheet;
    @Inject
    private CollectionLoader<RoleExt> roleExtsDl;
    @Inject
    private Screens screens;
    @Inject
    private OrganizationService organizationService;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataComponents dataComponents;
    @Inject
    private InstancePropertyContainer<Organization> organizationDc;
    @Inject
    private Dialogs dialogs;

    @Subscribe("search")
    public void onSearch(Action.ActionPerformedEvent event) {
        if(innField.getValue()!=null&&kppField.getValue()!=null) {
            OrganizationService.OrganizationData orgData = organizationService.getOrganizationsByInn(innField.getValue(),kppField.getValue());
            if(orgData==null) {
                dialogs.createOptionDialog()
                        .withCaption("Поиск организации")
                        .withMessage("Организация не найдена в базе данных, хотите добавить новую организацию?")
                        .withActions(
                                new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY).withHandler(e -> {
                                    organizationDc.setItem(null);
                                    organizationFragment.getFragment().setVisible(true);
                                    organizationFragment.setAllElementsEditable(true);
                                }),
                                new DialogAction(DialogAction.Type.NO)
                        )
                        .show();
                return;
            }
            organizationDc.setItem(orgData.getOrganization());
            organizationFragment.getFragment().setVisible(true);
            organizationFragment.setAllElementsEditable(false);
        }
    }

    @Subscribe("back")
    public void onBack(Action.ActionPerformedEvent event) {
        int currentTab = Integer.parseInt(tabSheet.getSelectedTab().getName());
        if(currentTab>0) {
            tabSheet.setSelectedTab(String.valueOf(currentTab-1));
            tabSheet.getTab(String.valueOf(currentTab)).setEnabled(false);
            tabSheet.getTab(String.valueOf(currentTab-1)).setEnabled(true);
        }
    }

    @Subscribe("next")
    public void onNext(Action.ActionPerformedEvent event) {
        if(!validateAllWithMessages()) return;
        int maxTabs=tabSheet.getTabs().size();
        int currentTab = Integer.parseInt(tabSheet.getSelectedTab().getName());
        if(currentTab<(maxTabs-1)) {
            tabSheet.setSelectedTab(String.valueOf(currentTab+1));
            tabSheet.getTab(String.valueOf(currentTab)).setEnabled(false);
            tabSheet.getTab(String.valueOf(currentTab+1)).setEnabled(true);
        }
        if ("2".equals(tabSheet.getTab(String.valueOf(currentTab+1)).getName())) {
            roleExtsDl.setQuery("select e from registrationprocedure_RoleExt e where e.organizationRole=:role");
            roleExtsDl.setParameter("role",organizationFragment.getOrganization().getRole());
            roleExtsDl.load();
        }
    }

    @Subscribe
    public void onInit(InitEvent event) {
        BiFunction<String, String, Boolean> predicate = String::contains;
        /*organizationLookupPickerField.setFilterPredicate((itemCaption, searchString) ->
                predicate.apply(itemCaption.toLowerCase(), searchString));*/
        /*organizationLookupPickerField.setFilterPredicate((itemCaption, searchString) ->{
            for(Organization org: organizationsDc.getItems()) {
                if(itemCaption.equals(org.getName())) {
                    String orgData = "" + org.getName() + org.getInn();
                    return predicate.apply(orgData.toLowerCase(), searchString);
                }
            }
            return false;
        } );*/
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {

    }

    @Subscribe("register")
    public void onRegister(Action.ActionPerformedEvent event) {
        if(!userFragment.validateAllWithMessages()) return;
        if(!organizationFragment.validateAllWithMessages()) return;
        if(!roleFragment.validateAllWithMessages()) return;
        Organization org = organizationFragment.getOrganization();
        Collection<RoleExt> roles = roleFragment.getRoles();
        org.setRole(OrganizationRole.ADMINISTRATIVE);
        RegistrationService.RegistrationResult result =registrationService.registerUser(getEditedEntity(),org,roles);
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
        closeScreen();
    }

    @Subscribe("cancel")
    public void onCancel(Action.ActionPerformedEvent event) {
        closeScreen();
    }

    private void closeScreen() {
        close(WINDOW_DISCARD_AND_CLOSE_ACTION);
        screens.create(ExtLoginScreen.class, OpenMode.ROOT).show();
    }
    
    public String getPassword() {
        return getEditedEntity().getPassword();
    }

    public String getLogin() {
        return getEditedEntity().getLogin();
    }

    private boolean validateAllWithMessages() {
        switch(tabSheet.getSelectedTab().getName()) {
            case "0":
                return userFragment.validateAllWithMessages();
            case "1":
                return organizationFragment.validateAllWithMessages();
            case "2":
                return roleFragment.validateAllWithMessages();
            default:
                return true;
        }
    }
}