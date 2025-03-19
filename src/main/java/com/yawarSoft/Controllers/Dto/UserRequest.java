package com.yawarSoft.Controllers.Dto;

import com.yawarSoft.Entities.UserEntity;

import java.util.Set;

public class UserRequest {
    private UserEntity user;
    private Set<Integer> roleIds;

    // Getters y Setters
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}