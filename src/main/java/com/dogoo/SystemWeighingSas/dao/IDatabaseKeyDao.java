package com.dogoo.SystemWeighingSas.dao;

import com.dogoo.SystemWeighingSas.entity.DatabaseKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDatabaseKeyDao extends JpaRepository<DatabaseKey, Long> {

    @Query("select dk from DG_DatabaseKey dk Where dk.key=:key ")
    Page<DatabaseKey> findDatabaseKeyByKey(@Param("key") String key, Pageable pageable);

    @Query("select dk from DG_DatabaseKey dk Where dk.key=:key AND dk.databaseKey=:databaseKey ")
    DatabaseKey fetchDatabaseKeyByKey(@Param("key") String key,
                                      @Param("databaseKey") String databaseKey);
}
