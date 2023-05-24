package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.IRoleDao;
import com.dogoo.SystemWeighingSas.entity.Role;
import com.dogoo.SystemWeighingSas.model.RoleMapperModel;
import com.dogoo.SystemWeighingSas.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private IRoleDao iRoleDao;

    @Override
    public void addRole(RoleMapperModel model) {
        Role role = Constants.SERIALIZER.convertValue(model, Role.class);
        iRoleDao.save(role);
    }

    @Override
    public void updateRole(RoleMapperModel model) {
        Role role = iRoleDao.findById(model.getId());
        role.setRoleCreate(model.isRoleCreate());
        role.setRoleView(model.isRoleView());
        role.setModule(model.getModule());

        iRoleDao.save(role);
    }

    @Override
    public List<RoleMapperModel> getListRole(long accountId) {
        return iRoleDao.findByAccountId(accountId).stream()
                .map(role -> Constants.SERIALIZER.convertValue(role, RoleMapperModel.class))
                .collect(Collectors.toList());
    }
}
