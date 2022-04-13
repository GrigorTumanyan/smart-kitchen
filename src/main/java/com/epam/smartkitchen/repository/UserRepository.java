package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {


    Page<User> findByUserType(UserType userType, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    boolean existsByEmail(String email);

    User findByEmail(String email);


}