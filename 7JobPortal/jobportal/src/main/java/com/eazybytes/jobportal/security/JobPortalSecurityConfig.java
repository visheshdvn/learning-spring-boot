package com.eazybytes.jobportal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JobPortalSecurityConfig
{

    @Qualifier("publicPaths")
    private final List<String> publicPaths;

    @Qualifier("securedPaths")
    private final List<String> securedPaths;

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http)
    {
        return http.csrf(csrfConfig -> csrfConfig.disable())
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        requests -> {
                            publicPaths.forEach(publicPath -> requests.requestMatchers(publicPath).permitAll());
                            securedPaths.forEach(securedPath -> requests.requestMatchers(securedPath).authenticated());
                            requests.anyRequest().denyAll();
                        }
                )
                .formLogin(flc -> flc.disable())
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        //        var pass1 = passwordEncoder().encode("P@ssw0rd");
        //        var pass2 = passwordEncoder().encode("P@ssw0rd@123");

        var user1 = User.builder().username("visheshdvn").password(
                "$2a$10$UIE5Yp4bsegQJKfNSHsQf.hqtM6NzidaGsPFGoOjEHP0csi07No6W").roles("USER").build();
        var user2 = User.builder().username("admin").password(
                "$2a$10$7hV/ghYpXns8b.h.AICj9exf9n77uw6iVnADJbmPItDXpzVzJALxy").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}