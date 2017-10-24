package com.clogic.resourceserver.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<Client> getUsers() {
        return clientService.getClients();
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public void createUser(@RequestHeader HttpHeaders headers, @RequestBody Client client) {

        String token;

        if (headers.containsKey("Authorization")) {
            token = headers.getFirst("Authorization");
        } else {
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


        clientService.saveClient(client);

    }

}
