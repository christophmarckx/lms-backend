package com.switchfully.lmstrapeziumbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll() //Swagger-ui
                        .requestMatchers(HttpMethod.POST, "/students/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/students/**").hasAnyAuthority("COACH", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("COACH", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/coaches").permitAll() //TODO Add Security
                        .requestMatchers("/modules/**").permitAll() //TODO Add Security
                        .requestMatchers(HttpMethod.GET,"/codelabs/**").hasAnyAuthority("COACH", "STUDENT")
                        .requestMatchers(HttpMethod.POST,"/codelabs/**").hasAuthority("COACH")
                        .requestMatchers(HttpMethod.PUT,"/codelabs/*").hasAuthority("COACH")
                        .requestMatchers(HttpMethod.PUT,"/codelabs/*/progress").hasAuthority("STUDENT")
                        .requestMatchers("/courses/**").permitAll() //TODO Add Security
                        .requestMatchers("/classgroups/**").permitAll() //TODO Add Security
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                        ))
                .httpBasic(withDefaults());
        return http.build();
    }

    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakGrantedAuthoritiesConverter());
        return converter;
    }
}
