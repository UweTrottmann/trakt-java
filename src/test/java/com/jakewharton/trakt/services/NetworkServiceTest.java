package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.FollowResponse;
import com.jakewharton.trakt.enumerations.Status;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NetworkServiceTest extends BaseTestCase {

    @Test
    public void test_follow() {
        FollowResponse response = getManager().networkService()
                .follow(new NetworkService.User("aeonmckay"));

        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }
}