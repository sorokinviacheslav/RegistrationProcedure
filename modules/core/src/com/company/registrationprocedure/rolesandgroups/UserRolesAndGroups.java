package com.company.registrationprocedure.rolesandgroups;

import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class UserRolesAndGroups {
        private List<Role> roles = new ArrayList<>();
        private List<Group> groups = new ArrayList<>();;


        public List<Role> getRoles() {
            return roles;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }

        public List<Group> getGroups() {
            return groups;
        }

        public void setGroups(List<Group> groups) {
            this.groups = groups;
        }

        public void addRoles(Role role) {
            this.roles.add(role);
        }

        public void addGroup(Group group) {
            this.groups.add(group);
        }
}
