package com.clogic.resourceserver.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "3c_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userID")
    private Integer userId;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String emailId;

    @Column(name = "pass")
    @JsonIgnore
    private String password;

    private String salt;
    private Integer iterations;


    public enum Role {
        ROLE_ADMINISTRATOR,
        ROLE_OWNER,
        ROLE_AGENT,
        ROLE_SUPERVISOR,
        ROLE_SERVICE_NODE
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Status {
        INACTIVE,
        ACTIVE,
        DELETED
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer contactCenter;
    private Integer customer;

    public static User getUserFromJsonUser(JsonUser jsonUser){
        User user = new User();
        user.setFullName(jsonUser.getFullName());
        user.setUserName(jsonUser.getUserName());
        user.setEmailId(jsonUser.getEmailId());
        user.setPassword(jsonUser.getPassword());
        user.setSalt(jsonUser.getSalt());
        user.setIterations(jsonUser.getIterations());
        user.setRole(jsonUser.getRole());
        user.setStatus(jsonUser.getStatus());
        user.setContactCenter(jsonUser.getContactCenter());
        user.setCustomer(jsonUser.getCustomer());

        return user;
    }
}
