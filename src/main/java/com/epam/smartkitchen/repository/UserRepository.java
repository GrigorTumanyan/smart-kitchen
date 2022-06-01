package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAll(Pageable pageable);

    Page<User> findAllByDeletedFalse(Pageable pageable);

    Page<User> findAllByDeletedTrue(Pageable pageable);

    Page<User> findByUserType(UserType userType, Pageable pageable);

    Page<User> findByUserTypeAndDeletedTrue(UserType userType, Pageable pageable);

    Page<User> findByUserTypeAndDeletedFalse(UserType userType, Pageable pageable);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);


}
