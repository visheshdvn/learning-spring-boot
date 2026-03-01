package com.eazybytes.jobportal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class JobPortalSecurityConfig
{

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
    {
        return http.csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(
                        (requests) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) requests.anyRequest()).permitAll())
                .formLogin(flc -> flc.disable())
                .httpBasic(withDefaults())
                .build();
        //        http.formLogin(withDefaults());
        //        http.httpBasic(withDefaults());
        //        return (SecurityFilterChain) http.build();
    }
}
