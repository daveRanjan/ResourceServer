package com.clogic.resourceserver.security;


import com.clogic.resourceserver.user.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import static java.util.Collections.emptyList;

@Component
public class TokenAuthenticationService {

    public static final String SECRET = "UQIbGGWIeQ";
    public static final String HEADER_STRING = "Authorization";


    private static UserService instance;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        TokenAuthenticationService.instance = userService;
    }


    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            Jws jws = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token);

            Claims claims = (Claims) jws.getBody();

            String user = claims.getSubject();

            //System.out.println("user Data from token: "+jws.toString());

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }

    public static UserHeaderTokenData getUserData(String token) {

        if (token != null) {

            // parse the token.
            Jws jws = Jwts.parser()
                    .setSigningKey("UQIbGGWIeQ")
                    .parseClaimsJws(token);

            Claims claims = (Claims) jws.getBody();

            String user = claims.getSubject();

            int userId = Integer.parseInt("" + claims.get("userid"));
            String role = "" + claims.get("role");

            System.out.println("user Data -- from token: " + jws.toString());
            System.out.println("user Id : " + userId);
            System.out.println("Role : " + role);
            System.out.println("username : " + user);

            UserHeaderTokenData userHeaderTokenData = new UserHeaderTokenData();
            userHeaderTokenData.setId(userId);
            userHeaderTokenData.setRole(role);

            return userHeaderTokenData;
        }
        return null;
    }
}
