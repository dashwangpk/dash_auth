package com.sprint.dash.auth.properties;

import lombok.Data;

@Data
public class DashClientsProperties {

    private String client;
    private String secret;
    private String grantType = "password,authorization_code,refresh_token";
    private String scope = "all";
    private String redirectUri = "";

    /**
     * 访问令牌有效时间，单位秒
     */
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    /**
     * 刷新令牌有效视角，单位秒
     */
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

}
