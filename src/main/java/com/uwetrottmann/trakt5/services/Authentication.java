package com.uwetrottmann.trakt5.services;

import com.uwetrottmann.trakt5.TraktV2;
import com.uwetrottmann.trakt5.entities.AccessToken;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Authentication {

    @FormUrlEncoded
    @POST(TraktV2.OAUTH2_TOKEN_URL)
    Call<AccessToken> exchangeCodeForAccessToken(
            @Field("grant_type") String grantType,
            @Field("code") String code,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri
    );

    @FormUrlEncoded
    @POST(TraktV2.OAUTH2_TOKEN_URL)
    Call<AccessToken> refreshAccessToken(
            @Field("grant_type") String grantType,
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri
    );

}
