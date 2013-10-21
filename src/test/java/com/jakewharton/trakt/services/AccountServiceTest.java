package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.ServiceManager;
import com.jakewharton.trakt.TraktException;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.enumerations.Status;
import retrofit.RetrofitError;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown;

public class AccountServiceTest extends BaseTestCase {
    /*
    //XXX: This requires a developer API key.
	public void test_create() {
		try {
			getManager().accountService().create("JakeWharton", "zomg secret password", "jakewharton@gmail.com").fire();
			fail("Account creation of duplicate user succeeded.");
		} catch (ApiException e) {
			assertEquals("Exception text does not match.", "{\"status\":\"failure\",\"error\":\"JakeWharton is already a registered username\"}", e.message.trim());

			//TODO: change this to a custom error that includes a response we
			//can actually test against to differentiate between a problem with
			//the API or simply a failed creation.
		}
	}
	*/

    public void test_settings() {
        AccountService.Settings settings = getManager().accountService().settings();
        assertThat(settings).isNotNull();
        assertThat(settings.status).isEqualTo(Status.SUCCESS);
        assertThat(settings.message).isEqualTo("All settings for " + BaseTestCase.USERNAME);
    }

    public void test_testSuccess() {
        Response response = getManager().accountService().test();
        assertThat(response).isNotNull();
        assertThat(response.status).isEqualTo(Status.SUCCESS);
        assertThat(response.message).isEqualTo("all good!");
    }

    public void test_testFailure() {
        // We have to create our own uninitialized service for this
        ServiceManager manager = new ServiceManager();
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
}
