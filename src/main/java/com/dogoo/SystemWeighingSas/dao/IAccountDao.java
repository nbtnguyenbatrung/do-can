package com.dogoo.SystemWeighingSas.dao;

import com.dogoo.SystemWeighingSas.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAccountDao extends JpaRepository<Account,Long> {
    Account findAccountByScreenName(String screenName);
    Account findAccountByEmail(String email);
    Account findAccountByKey(String key);

    @Query("select ac from DG_account ac Where ac.accountId=:accountId ")
    Account fetchAccountByAccountId( @Param("accountId") long accountId);

    Page<Account> findAll(Pageable pageable);

    @Query("select ac from DG_account ac Where ac.key=:key ")
    Page<Account> findAllCustomer(@Param("key") String key , Pageable pageable);

    @Query("select ac from DG_account ac Where ac.key=:key AND role='adminUser' ")
    Account getCustomer(@Param("key") String key);

    long countByKey(String key);
    boolean existsByScreenName(String screenName);

    Account findByAccountId(long accountId);


}
