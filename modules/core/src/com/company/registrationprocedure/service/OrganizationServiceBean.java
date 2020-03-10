package com.company.registrationprocedure.service;

import com.company.registrationprocedure.entity.Organization;
import com.haulmont.cuba.core.Persistence;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(OrganizationService.NAME)
public class OrganizationServiceBean implements OrganizationService {

    @Inject
    private Persistence persistence;

    @Override
    @Transactional
    public OrganizationData getOrganizationsByInn(String inn,String kpp) {
        Organization org = (Organization)persistence.getEntityManager()
                .createQuery("select o from registrationprocedure_Organization o where o.inn=:inn and o.kpp=:kpp")
                .setParameter("inn",inn)
                .setParameter("kpp",kpp)
                .getFirstResult();
        if(org!=null) {
            return new OrganizationData(org,"iskooDatabase");
        }
        return null;
    }
}