package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

    Optional<Category> findByIdAndDeleted(String id, boolean deleted);


    Page<Category> findAllByDeleted(Pageable pageable, boolean deleted);

}
