package com.dogoo.SystemWeighingSas.dao;

import com.dogoo.SystemWeighingSas.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomerDao extends JpaRepository<Customer,Long> {
    Page<Customer> findAll(Pageable pageable);

    Customer findCustomerByCustomerCode(String customerCode);
    Customer findCustomerByKey(String key);
    Customer findCustomerByEmail(String email);

    boolean existsByCustomerCode(String customerCode);
    List<Customer> findByCustomerNameLike(String customerName);
}
