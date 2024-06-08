package com.switchfully.lmstrapeziumbackend.utility;

import com.switchfully.lmstrapeziumbackend.user.UserRole;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakTestingUtility {
    @Value("${AUTH_SERVER_URL}")
    private String keycloakServerUrl;

    @Value("${KEYCLOAK_REALM}")
    private String keycloakRealm;

    @Value("${KEYCLOAK_RESOURCE}")
    private String keycloakClientId;

    @Value("${KEYCLOAK_CREDENTIALS_SECRET}")
    private String keycloakClientSecret;

    private Keycloak getKeycloakInstance(String username, String password) {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(keycloakRealm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .username(username)
                .password(password)
                .build();
    }

    public String getTokenFromTestingUser(UserRole role) {
        return getKeycloakInstance(
                "testing@" + role.toString().toLowerCase() + ".com",
                "pass" + role.toString().toLowerCase() + "123")
                .tokenManager().getAccessToken().getToken();
    }
}
