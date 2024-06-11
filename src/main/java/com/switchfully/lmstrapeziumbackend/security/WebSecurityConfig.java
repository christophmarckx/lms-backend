package com.switchfully.lmstrapeziumbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private final String COACH = "COACH";
    private final String STUDENT = "STUDENT";

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
                        .requestMatchers(HttpMethod.POST, "/students").not().hasAnyAuthority(STUDENT, COACH)
                        .requestMatchers(HttpMethod.GET, "/students/**").hasAuthority(STUDENT)
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.GET, "/users/*/classgroups").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.GET, "/users/*").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.GET, "/coaches").hasAuthority(COACH)
                        .requestMatchers(HttpMethod.POST, "/modules").hasAuthority(COACH)
                        .requestMatchers(HttpMethod.GET, "/modules/**").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.GET, "/codelabs/*/comments").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.GET, "/codelabs/**").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.POST, "/codelabs/*/comments").hasAuthority(STUDENT)
                        .requestMatchers(HttpMethod.POST, "/codelabs/**").hasAuthority(COACH)
                        .requestMatchers(HttpMethod.PUT, "/codelabs/*/progress").hasAuthority(STUDENT)
                        .requestMatchers(HttpMethod.PUT, "/codelabs/**").hasAuthority(COACH)
                        .requestMatchers(HttpMethod.GET, "/courses/**").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.POST, "/courses").hasAuthority(COACH)
                        .requestMatchers(HttpMethod.PUT, "/courses/**").hasAuthority(COACH)
                        .requestMatchers(HttpMethod.GET, "/classgroups/**").hasAnyAuthority(COACH, STUDENT)
                        .requestMatchers(HttpMethod.PUT, "/classgroups/**").hasAnyAuthority(STUDENT)
                        .requestMatchers(HttpMethod.POST, "/classgroups").hasAuthority(COACH)
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
