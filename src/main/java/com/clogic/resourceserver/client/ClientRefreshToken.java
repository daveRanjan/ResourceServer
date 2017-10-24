package com.clogic.resourceserver.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "oauth_refresh_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "agent_id")
    private Integer agentId;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "refresh_token")
    private String refreshToken;

}
