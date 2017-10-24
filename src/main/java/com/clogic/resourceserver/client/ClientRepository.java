package com.clogic.resourceserver.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    public Client findById(int clientId);
    public Client findByClientId(String clientId);

}
