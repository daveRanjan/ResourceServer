package com.clogic.resourceserver.user;

import com.clogic.resourceserver.security.SecurityUtils;
import com.clogic.resourceserver.security.TokenAuthenticationService;
import com.clogic.resourceserver.security.UserHeaderTokenData;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void createUser(@RequestHeader HttpHeaders headers, @RequestBody JsonUser user){

        String token;

        if(headers.containsKey("Authorization")) {
            token = headers.getFirst("Authorization");
        }else{
            throw new UnauthorizedUserException("Invalid Authentication!");
        }

        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnauthorizedUserException("No JWT token found in request headers");
        }

        /*

        UserHeaderTokenData tokenData = TokenAuthenticationService.getUserData(token.substring(7));

        if(tokenData.getRole().contains("ROLE_TRUSTED_ADMIN")) {
            userService.saveUser(user);
        }else{
            throw new UnauthorizedUserException("write access is required");
        }*/

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.saveUser(User.getUserFromJsonUser(user));

    }

}
