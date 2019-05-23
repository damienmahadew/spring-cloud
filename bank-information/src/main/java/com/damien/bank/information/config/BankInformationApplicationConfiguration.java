package com.damien.bank.information.config;

import com.damien.bank.information.decoders.BankInformationErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@Import({SwaggerConfig.class})
public class BankInformationApplicationConfiguration implements WebMvcConfigurer {

    @Value("${client.id}")
    private String clientId;

    @Value("${client.password}")
    private String clientPassword;

    @Value("${auth-server.url}")
    private String authEndpoint;

    @Bean
    public BankInformationErrorDecoder bankInformationErrorDecoder() {
        return new BankInformationErrorDecoder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setGrantType("client_credentials");
        resourceDetails.setAccessTokenUri(authEndpoint + "/oauth/token");

        //-- set the clients info
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientPassword);

        // set scopes
        List<String> scopes = new ArrayList<>();
        scopes.add("read");
        scopes.add("write");
        resourceDetails.setScope(scopes);

        return new OAuth2RestTemplate(resourceDetails);
    }

}
