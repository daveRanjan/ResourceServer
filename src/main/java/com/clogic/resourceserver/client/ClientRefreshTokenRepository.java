package com.clogic.resourceserver.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRefreshTokenRepository extends JpaRepository<ClientRefreshToken, Integer> {

    public ClientRefreshToken findByApiKey(String apiKey);
    public ClientRefreshToken findByAgentId(int agentId);
    public ClientRefreshToken findByRefreshToken(String refreshToken);

}
