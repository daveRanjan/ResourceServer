package com.clogic.resourceserver.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmailId(String emailId);
    public List<User> findByUserId(int userId);
    public List<User> findByRole(String role);
    public List<User> findByStatus(String status);

}
