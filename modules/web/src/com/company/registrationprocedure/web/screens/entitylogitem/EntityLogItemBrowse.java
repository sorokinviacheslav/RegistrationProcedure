package com.company.registrationprocedure.web.screens.entitylogitem;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.EntityLogItem;

@UiController("sec$EntityLog.browse")
@UiDescriptor("entity-log-item-browse.xml")
@LookupComponent("entityLogItemsTable")
@LoadDataBeforeShow
public class EntityLogItemBrowse extends StandardLookup<EntityLogItem> {
}