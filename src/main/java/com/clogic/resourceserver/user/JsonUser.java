package com.clogic.resourceserver.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonUser {

    private Integer userId;

    private String fullName;

    private String userName;

    private String emailId;

    private String password;

    private String salt;
    private Integer iterations;

    private User.Role role;

    private User.Status status;

    private Integer contactCenter;
    private Integer customer;
}
