package com.switchfully.lmstrapeziumbackend.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> resourceAccess = source.getClaimAsMap("resource_access");
        if (resourceAccess == null) {
            return List.of();
        }
        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("lms");
        if (clientAccess == null) {
            return List.of();
        }
        List<String> roles = (List<String>) clientAccess.get("roles");

        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
