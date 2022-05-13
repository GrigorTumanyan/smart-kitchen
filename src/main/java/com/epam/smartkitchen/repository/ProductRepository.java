package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {


    Page<Product> findAllByDeleted(Pageable pageable, boolean deleted);

    Optional<Product> findByIdAndDeleted(String id,boolean deleted);
}
