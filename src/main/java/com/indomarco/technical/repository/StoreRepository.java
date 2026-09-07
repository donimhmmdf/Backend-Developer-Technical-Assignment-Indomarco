package com.indomarco.technical.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.indomarco.technical.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

        @Query("""
                        SELECT s
                        FROM Store s
                        JOIN s.branch b
                        JOIN b.province p
                        WHERE (LOWER(p.name) LIKE LOWER(CONCAT(:provinceName, '%')) OR s.isWhitelisted = true)
                        AND s.isDeleted = false
                        AND s.isActive = true
                        """)
        Page<Store> findByProvinceName(
                        @Param("provinceName") String provinceName,
                        Pageable pageable);

        List<Store> findByBranchId(Long id);
}
