package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.Trakt;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.enumerations.Status;

import org.junit.Test;

import retrofit.RetrofitError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class AccountServiceTest extends BaseTestCase {

    @Test
    public void test_settings() {
        AccountService.Settings settings = getManager().accountService().settings();
        assertThat(settings).isNotNull();
        assertThat(settings.status).isEqualTo(Status.SUCCESS);
        assertThat(settings.message).isEqualTo("All settings for " + BaseTestCase.USERNAME);
    }

    @Test
    public void test_testFailure() {
        // We have to create our own uninitialized service for this
        Trakt manager = new Trakt();
        manager.setIsDebug(true);
        manager.setApiKey(BaseTestCase.API_KEY);
        manager.setAuthentication(BaseTestCase.USERNAME, "this is not my password hash!");

        try {
            Response response = manager.accountService().test();
            failBecauseExceptionWasNotThrown(RetrofitError.class);
        } catch (RetrofitError e) {
            assertThat(e.getResponse()).isNotNull();
            assertThat(e.getResponse().getStatus()).isEqualTo(401); // unauthorized
        }
    }

    @Test
    public void test_testSuccess() {
        Response response = getManager().accountService().test();
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
        assertThat(response.message).isEqualTo("all good!");
    }
}
