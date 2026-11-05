/*
 * Copyright 2024 Uwe Trottmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uwetrottmann.trakt5.entities;

public class AccessTokenRequest {

    public String grant_type;
    public String code;
    public String client_id;
    public String client_secret;
    public String code_verifier;
    public String redirect_uri;

    private AccessTokenRequest(String code, String clientId, String clientSecret, String codeVerifier,
            String redirectUri) {
        this.grant_type = "authorization_code";
        this.code = code;
        this.client_id = clientId;
        this.client_secret = clientSecret;
        this.code_verifier = codeVerifier;
        this.redirect_uri = redirectUri;
    }

    /**
     * Request to exchange an authorization code for an access token, authenticating with the client secret.
     *
     * @see #withCodeVerifier(String, String, String, String)
     */
    public AccessTokenRequest(String code, String client_id, String client_secret, String redirect_uri) {
        this(code, client_id, client_secret, null, redirect_uri);
    }

    /**
     * Request to exchange an authorization code for an access token using PKCE. Does not send a client secret.
     *
     * @param codeVerifier The code verifier the code challenge of the authorization request was derived from.
     */
    public static AccessTokenRequest withCodeVerifier(String code, String clientId, String codeVerifier,
            String redirectUri) {
        return new AccessTokenRequest(code, clientId, null, codeVerifier, redirectUri);
    }
}
