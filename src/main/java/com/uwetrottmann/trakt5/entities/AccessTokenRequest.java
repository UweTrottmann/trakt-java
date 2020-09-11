package com.uwetrottmann.trakt5.entities;

public class AccessTokenRequest {

    public String grant_type;
    public String code;
    public String client_id;
    public String client_secret;
    public String redirect_uri;

    public AccessTokenRequest(String code, String client_id, String client_secret, String redirect_uri) {
        this.grant_type = "authorization_code";
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
    }
}
