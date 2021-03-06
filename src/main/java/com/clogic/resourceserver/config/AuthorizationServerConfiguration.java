package com.clogic.resourceserver.config;


import com.clogic.resourceserver.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);

    @Value("${resource.id:spring-boot-application}")
    private String resourceId;
    private static final int ACCESS_TOKEN_EXPIRY_TIME = 5000;
    private static final int REFRESH_TOKEN_EXPIRY_TIME = 6000;

    @Value("${jwt.secret}")
    String jwtSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter tokenConverter;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSecret);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore);
        services.setSupportRefreshToken(true);
        services.setAccessTokenValiditySeconds(ACCESS_TOKEN_EXPIRY_TIME);
        services.setRefreshTokenValiditySeconds(REFRESH_TOKEN_EXPIRY_TIME);
        services.setTokenEnhancer(accessTokenConverter());
        return services;
    }

    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authentication -> {

                    Authentication newAuth = null;

                    String password = authentication.getCredentials().toString();
                    Object details = authentication.getDetails();

                    Gson gson = new Gson();
                    JsonElement element = gson.toJsonTree(details);
                    JsonObject object = element.getAsJsonObject();

                    String username = object.get("username").getAsString();

                    LOGGER.debug("DATA -- " + password + "\nDetails : " + username);

                    User user = userService.getUserByEmailId(username);

                    LOGGER.debug("User : " + user.toString());


                    if (user != null && SecurityUtils.validatePassword(password, user.getPassword(), user.getSalt(), user.getIterations())) {
                        newAuth = new UsernamePasswordAuthenticationToken(username, user.getPassword(), null);
                    }

                    return newAuth;

                }).tokenServices(tokenServices());
    }*/

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }


}