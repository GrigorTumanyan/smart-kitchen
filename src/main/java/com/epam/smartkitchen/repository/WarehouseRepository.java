package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.models.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,String> {

}
