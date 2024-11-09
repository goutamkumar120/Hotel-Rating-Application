package com.gk.user.repositories;

import com.gk.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    //if u want to implement any custom method or query
}
