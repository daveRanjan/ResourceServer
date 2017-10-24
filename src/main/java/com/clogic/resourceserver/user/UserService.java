package com.clogic.resourceserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserByEmailId(String emailId){
        return userRepository.findByEmailId(emailId);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
}
