package com.redissi.trakt

import com.redissi.trakt.entities.AccessToken
import com.redissi.trakt.services.Authentication
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StagingAuthentication : Authentication {
    @FormUrlEncoded
    @POST(Trakt.OAUTH2_TOKEN_URL_STAGING)
    override suspend fun exchangeCodeForAccessToken(
        @Field("grant_type") grantType: String?,
        @Field("code") code: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("redirect_uri") redirectUri: String?
    ): Response<AccessToken>

    @FormUrlEncoded
    @POST(Trakt.OAUTH2_TOKEN_URL_STAGING)
    override suspend fun refreshAccessToken(
        @Field("grant_type") grantType: String?,
        @Field("refresh_token") refreshToken: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("redirect_uri") redirectUri: String?
    ): Response<AccessToken>
}