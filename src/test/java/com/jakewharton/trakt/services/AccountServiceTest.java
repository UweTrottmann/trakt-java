package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.ServiceManager;
import com.jakewharton.trakt.TraktException;
import com.jakewharton.trakt.entities.Response;

public class AccountServiceTest extends BaseTestCase {
	/*
	//XXX: This requires a developer API key.
	public void test_create() {
		try {
			getManager().accountService().create("JakeWharton", "zomg secret password", "jakewharton@gmail.com").fire();
			fail("Account creation of duplicate user succeeded.");
		} catch (ApiException e) {
			assertEquals("Exception text does not match.", "{\"status\":\"failure\",\"error\":\"JakeWharton is already a registered username\"}", e.getMessage().trim());

			//TODO: change this to a custom error that includes a response we
			//can actually test against to differentiate between a problem with
			//the API or simply a failed creation.
		}
	}
	*/

	public void test_testSuccess() {
		Response response = getManager().accountService().test().fire();
		assertNotNull("Result was null.", response);
		assertEquals("Authentication failed.", "success", response.getStatus());
		assertEquals("Authentication failed.", "all good!", response.getMessage());
	}

	public void test_testFailure() {
		//We have to create our own uninitialized service for this
		AccountService service = ServiceManager.createAccountService();
		service.setApiKey(BaseTestCase.API_KEY);
		service.setAuthentication("JakeWharton", "this is not my password hash!");

		try {
			service.test().fire();
			fail("Authentication test succeeded with invalid credentials.");
		} catch (TraktException e) {
			assertNotNull("Response was null.", e.getResponse());
			assertEquals("Response error does not match.", "failure", e.getResponse().getStatus());
			assertEquals("Response error does not match.", "failed authentication", e.getResponse().getError());
		}
	}
}
