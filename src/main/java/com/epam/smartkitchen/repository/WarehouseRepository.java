package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.models.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,String> {
    Page<Warehouse> findAllByDeletedFalse(Pageable pageable);
    Page<Warehouse> findAllByDeletedTrue(Pageable pageable);
    Page<Warehouse> findAll(Pageable pageable);
    Page<Warehouse> findByProductNameAndDeletedFalse(String name,Pageable pageable);
    Page<Warehouse> findByProductId(String id, Pageable pageable);
}
