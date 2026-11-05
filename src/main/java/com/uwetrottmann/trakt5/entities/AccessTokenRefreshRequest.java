/*
 * Copyright 2024 Uwe Trottmann <uwe@uwetrottmann.com>
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

public class AccessTokenRefreshRequest {

    public String grant_type;
    public String refresh_token;
    public String client_id;

    public AccessTokenRefreshRequest(String refreshToken, String clientId) {
        this.grant_type = "refresh_token";
        this.refresh_token = refreshToken;
        this.client_id = clientId;
    }

    /**
     * @deprecated Use the constructor without client secret and redirect URI instead.
     */
    @Deprecated
    public AccessTokenRefreshRequest(String refresh_token, String client_id, String client_secret,
            String redirect_uri) {
        this(refresh_token, client_id);
    }
}
