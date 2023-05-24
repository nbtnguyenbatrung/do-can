package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.model.RoleMapperModel;

import java.util.List;

public interface RoleService {
    void addRole(RoleMapperModel model) ;
    void updateRole(RoleMapperModel model);
    List<RoleMapperModel> getListRole(long accountId);
}
