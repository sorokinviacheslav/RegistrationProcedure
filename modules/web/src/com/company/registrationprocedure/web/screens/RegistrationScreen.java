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
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.Collection;

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
    private InstancePropertyContainer<Organization> organizationDc;
    @Inject
    private Dialogs dialogs;
    @Inject
    private Metadata metadata;

    private boolean newOrganization = false;

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
                                    Organization newOrg =metadata.create(Organization.class);
                                    newOrg.setInn(innField.getValue());
                                    newOrg.setKpp(kppField.getValue());
                                    organizationDc.setItem(newOrg);
                                    organizationFragment.getFragment().setVisible(true);
                                    organizationFragment.setAllElementsEditable(true);
                                    newOrganization=true;
                                }),
                                new DialogAction(DialogAction.Type.NO)
                        )
                        .show();
                return;
            }
            organizationDc.setItem(orgData.getOrganization());
            organizationFragment.getFragment().setVisible(true);
            organizationFragment.setAllElementsEditable(false);
            newOrganization=false;
        }
    }

    @Subscribe("back")
    public void onBack(Action.ActionPerformedEvent event) {
        int currentTab = Integer.parseInt(tabSheet.getSelectedTab().getName());
        if(currentTab>0) {
            switchToPrevTab(currentTab);
        }
    }

    @Subscribe("next")
    public void onNext(Action.ActionPerformedEvent event) {
        if(!validateAllWithMessages()) return;
        int maxTabs=tabSheet.getTabs().size();
        int currentTab = Integer.parseInt(tabSheet.getSelectedTab().getName());
        if(currentTab<(maxTabs-1)) {
            switchToNextTab(currentTab);
        }
        if ("2".equals(tabSheet.getTab(String.valueOf(currentTab+1)).getName())) {
            roleExtsDl.setParameter("role",organizationDc.getItem().getRole());
            roleExtsDl.load();
        }
    }

    private void switchToNextTab(int currentTab) {
        tabSheet.setSelectedTab(String.valueOf(currentTab+1));
        tabSheet.getTab(String.valueOf(currentTab)).setEnabled(false);
        tabSheet.getTab(String.valueOf(currentTab+1)).setEnabled(true);
    }

    private void switchToPrevTab(int currentTab) {
        tabSheet.setSelectedTab(String.valueOf(currentTab-1));
        tabSheet.getTab(String.valueOf(currentTab)).setEnabled(false);
        tabSheet.getTab(String.valueOf(currentTab-1)).setEnabled(true);
    }

    @Subscribe("register")
    public void onRegister(Action.ActionPerformedEvent event) {
        if(!userFragment.validateAllWithMessages()) return;
        if(!organizationFragment.validateAllWithMessages()) return;
        if(!roleFragment.validateAllWithMessages()) return;
        Organization org = organizationDc.getItem();
        Collection<RoleExt> roles = roleFragment.getRoles();
        org.setRole(OrganizationRole.ADMINISTRATIVE);
        RegistrationService.RegistrationResult result =registrationService.registerUser(getEditedEntity(),org,roles);
        if(!result.isSuccess()) {
            showMessage("User with these email and login already exists!", Notifications.NotificationType.TRAY);
            return;
        }
        showMessage("User " + getEditedEntity().getLogin()+" has been created successfully",Notifications.NotificationType.TRAY);
        closeScreen();
    }

    @Subscribe("cancel")
    public void onCancel(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withMessage("Вы уверены что хотите прервать регистрацию?\nВсе введенные данные будут утеряны.")
                .withActions(
                        new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY).withHandler(e -> {
                            closeScreen();
                        }),
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }

    private void closeScreen() {
        close(WINDOW_DISCARD_AND_CLOSE_ACTION);
        screens.create(ExtLoginScreen.class, OpenMode.ROOT).show();
    }

    private boolean validateAllWithMessages() {
        switch(tabSheet.getSelectedTab().getName()) {
            case "0":
                return userFragment.validateAllWithMessages();
            case "1":
                if(organizationDc.getItemOrNull()==null) {
                    showMessage("No organization is selected!", Notifications.NotificationType.TRAY);
                    return false;
                }
                else if(newOrganization&&organizationService.getOrganizationsByInn(organizationDc.getItem().getInn(),organizationDc.getItem().getKpp())!=null) {
                    showMessage("Organization with these inn and kpp already exists!", Notifications.NotificationType.TRAY);
                    return false;
                }
                else {
                    return organizationFragment.validateAllWithMessages();
                }
            case "2":
                return roleFragment.validateAllWithMessages();
            default:
                return true;
        }
    }

    private void showMessage(String text, Notifications.NotificationType type) {
        notifications.create(type)
                .withCaption(
                        text)
                .show();
    }
}