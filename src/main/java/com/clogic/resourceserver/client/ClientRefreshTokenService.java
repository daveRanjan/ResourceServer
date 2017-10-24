package com.clogic.resourceserver.client;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientRefreshTokenService {

    private ClientRefreshTokenRepository refreshTokenRepository;

    public List<ClientRefreshToken> getRefreshTokens(){
        return refreshTokenRepository.findAll();
    }

    public ClientRefreshToken getClientRefreshTokenFromApiKey(String apiKey){
        return refreshTokenRepository.findByApiKey(apiKey);
    }

    public ClientRefreshToken getClientRefreshTokenFromAgentId(int agentId){
        return refreshTokenRepository.findByAgentId(agentId);
    }

    public ClientRefreshToken getClientRefreshTokenFromRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

}
