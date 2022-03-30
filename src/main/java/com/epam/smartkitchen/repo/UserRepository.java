package com.epam.smartkitchen.repo;

import com.epam.smartkitchen.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

}
