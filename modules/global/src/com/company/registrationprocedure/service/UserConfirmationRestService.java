package com.company.registrationprocedure.service;

import java.util.List;

public interface UserConfirmationRestService {
    String NAME = "registrationprocedure_UserConfirmationRestService";

    boolean confirmRegistration(String username);
}