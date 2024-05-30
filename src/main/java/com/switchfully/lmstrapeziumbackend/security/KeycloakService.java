package com.switchfully.lmstrapeziumbackend.security;

import com.switchfully.lmstrapeziumbackend.user.dto.CreateStudentDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class KeycloakService {
    public static final String ROLE_FOR_ANONYMOUS_USER_REGISTRATION = "STUDENT";

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.client-id}")
    private String keycloakAdminClientId;

    @Value("${keycloak.lms.client-uuid}")
    private String keycloakLMSClientId;

    @Value("${keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("${keycloak.admin-username}")
    private String keycloakAdminUsername;

    @Value("${keycloak.admin-password}")
    private String keycloakAdminPassword;

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(keycloakRealm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(keycloakAdminClientId)
                .clientSecret(keycloakClientSecret)
                .username(keycloakAdminUsername)
                .password(keycloakAdminPassword)
                .build();
    }

    public UUID createUser(CreateStudentDTO createStudentDTO) {
        Keycloak keycloak = getKeycloakInstance();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(createStudentDTO.password());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(createStudentDTO.email());
        user.setEnabled(true);
        user.setCredentials(List.of(credential));

        Response response = keycloak.realm(keycloakRealm).users().create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION");
        }

        UUID userId = getUserId(response);

        try {
            assignRoleToUser(keycloak, userId);
        }
        catch(Exception ex) {
            this.deleteUserFromKeycloak(userId);
            throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION");
        }
        return userId;
    }

    private UUID getUserId(Response response) {
        String location = response.getHeaderString("Location");
        if (location == null) {
            throw new RuntimeException("IMPLEMENT CUSTOM EXCEPTION");
        }
        return UUID.fromString(location.replaceAll(".*/([^/]+)$", "$1"));
    }

    private void assignRoleToUser(Keycloak keycloak, UUID userId) {
        UserResource userResource = keycloak.realm(keycloakRealm).users().get(userId.toString());
        ClientResource clientResource = keycloak.realm(keycloakRealm).clients().get(keycloakLMSClientId);
        RoleRepresentation clientRole = clientResource.roles().get(ROLE_FOR_ANONYMOUS_USER_REGISTRATION).toRepresentation();
        userResource.roles().clientLevel(keycloakLMSClientId).add(Collections.singletonList(clientRole));
    }

    public void deleteUserFromKeycloak(UUID userId) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(keycloakRealm);
        UserResource userResource = realmResource.users().get(userId.toString());
        userResource.remove();
    }
}
