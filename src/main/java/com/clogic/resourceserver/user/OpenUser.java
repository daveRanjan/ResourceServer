package com.clogic.resourceserver.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenUser {

    private Integer userId;
    private String fullName;
    private String userName;
    private String emailId;
    private String password;
    private String salt;
    private Integer iterations;

    private enum Role {
        ROLE_ADMINISTRATOR,
        ROLE_OWNER,
        ROLE_AGENT,
        ROLE_SUPERVISOR,
        ROLE_SERVICE_NODE
    }

    private Role role;

    private enum Status {
        INACTIVE,
        ACTIVE,
        DELETED
    }

    private Status status;
    private Integer contactCenter;
    private Integer customer;

}
