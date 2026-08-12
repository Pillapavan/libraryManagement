package com.java.libraryManagement.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.libraryManagement.Entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserName(String name);

}
