package com.redissi.trakt.services

import com.redissi.trakt.Trakt
import com.redissi.trakt.entities.AccessToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Authentication {
    @FormUrlEncoded
    @POST(Trakt.OAUTH2_TOKEN_URL)
    suspend fun exchangeCodeForAccessToken(
        @Field("grant_type") grantType: String?,
        @Field("code") code: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("redirect_uri") redirectUri: String?
    ): Response<AccessToken>

    @FormUrlEncoded
    @POST(Trakt.OAUTH2_TOKEN_URL)
    suspend fun refreshAccessToken(
        @Field("grant_type") grantType: String?,
        @Field("refresh_token") refreshToken: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("redirect_uri") redirectUri: String?
    ): Response<AccessToken>
}