package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository <MenuItem,String> {
    Optional<MenuItem> findById(String id);

}
