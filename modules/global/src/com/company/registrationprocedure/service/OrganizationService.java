package com.company.registrationprocedure.service;

import com.company.registrationprocedure.entity.Organization;

import java.io.Serializable;
import java.util.Map;

public interface OrganizationService {
    String NAME = "registrationprocedure_OrganizationService";

    OrganizationData getOrganizationsByInn(String inn,String kpp);

    class OrganizationData implements Serializable {
        private Organization organization;
        private String database;

        public OrganizationData(Organization organization, String database) {
            this.organization=organization;
            this.database=database;
        }

        public Organization getOrganization() {
            return organization;
        }

        public String getDatabase() {
            return database;
        }
    }
}