package com.threepillar.consumer.service;

import com.threepillar.consumer.model.KeycloakSession;
import com.threepillar.consumer.utils.Common;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Class used to interact with Mission Control Api, send some request like get Session with Keycloak and consume
 * some endpoints to retrieve HOS based on Hierarchy ID.
 */
@Data
@Slf4j
@Service
public class KeycloakService {

    private final RestTemplate restTemplate;
    private final Common utils;

    @Autowired
    public KeycloakService(RestTemplate restTemplate, Common utils) {
        this.restTemplate = restTemplate;
        this.utils = utils;
    }

    /**
     * Method used to get a Session token for connect DxCommunicator to Mission Control API using a keycloak token.
     *
     * @return McSession object with a session token.
     */
    public KeycloakSession getSessionToken() {

        ResponseEntity<KeycloakSession> response;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", this.utils.getKeycloakGrantType());
        map.add("client_id", this.utils.getKeycloakClientId());
        map.add("realm", this.utils.getKeycloakRealm());
        map.add("username", this.utils.getKeycloakUsername());
        map.add("password", this.utils.getKeycloakPassword());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        try {
            response = restTemplate
                    .exchange(utils.getKeycloakUrlToken(), HttpMethod.POST, entity, KeycloakSession.class);

            return response.getBody();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
