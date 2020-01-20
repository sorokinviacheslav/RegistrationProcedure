package com.company.registrationprocedure.web;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.RequiredValueMissingException;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component(ValidationUiExceptionHandler.NAME)
public class ValidationUiExceptionHandler extends AbstractUiExceptionHandler {
    public static final String NAME = "registrationprocedure.web_ValidationUiExceptionHandler";

    public ValidationUiExceptionHandler() {
        super(ValidationException.class.getName(), RequiredValueMissingException.class.getName());
    }

    @Override
    protected void doHandle(String className, String message, @Nullable Throwable throwable, UiContext context) {
        context.getNotifications().create(Notifications.NotificationType.ERROR)
                .withCaption("Error")
                .withDescription(message)
                .show();
    }
}