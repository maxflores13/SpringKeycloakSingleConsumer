package com.threepillar.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO used to get JSON keycloak Session to begin transactions to Mission Control API.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakSession {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expire_in")
    private Long expiresIn;
    @JsonProperty("refresh_expires_in")
    private Integer refreshExpiresIn;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("not_before_policy")
    private Long notBeforePolicy;
    private String scope;
}
