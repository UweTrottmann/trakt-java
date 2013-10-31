package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.FollowResponse;
import com.jakewharton.trakt.enumerations.Status;

import static org.fest.assertions.api.Assertions.assertThat;

public class NetworkServiceTest extends BaseTestCase {

    public void test_follow() {
        FollowResponse response = getManager().networkService()
                .follow(new NetworkService.User("aeonmckay"));

        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }
}