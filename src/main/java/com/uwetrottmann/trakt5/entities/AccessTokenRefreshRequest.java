package com.uwetrottmann.trakt5.entities;

public class AccessTokenRefreshRequest {

    public String grant_type;
    public String refresh_token;
    public String client_id;
    public String client_secret;
    public String redirect_uri;

    public AccessTokenRefreshRequest(String refresh_token, String client_id, String client_secret,
            String redirect_uri) {
        this.grant_type = "refresh_token";
        this.refresh_token = refresh_token;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
    }
}
