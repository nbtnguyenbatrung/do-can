package com.dogoo.SystemWeighingSas.model;

public class UserTokenModel {

    private Long accountId;
    private String email;
    private String key ;
    private String roles ;
    private Boolean roleCreate;
    private Boolean roleView ;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Boolean getRoleCreate() {
        return roleCreate;
    }

    public void setRoleCreate(Boolean roleCreate) {
        this.roleCreate = roleCreate;
    }

    public Boolean getRoleView() {
        return roleView;
    }

    public void setRoleView(Boolean roleView) {
        this.roleView = roleView;
    }
}
