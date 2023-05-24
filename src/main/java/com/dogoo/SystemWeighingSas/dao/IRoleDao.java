package com.dogoo.SystemWeighingSas.dao;

import com.dogoo.SystemWeighingSas.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoleDao extends JpaRepository<Role,Long> {
    Role findById(long id);
    List<Role> findByAccountId(long accountId);
}
