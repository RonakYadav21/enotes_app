package com.enotes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.model.User;

public interface userRepository extends JpaRepository<User,Integer>{
public User findByEmail(String email);

}
