package com.jakewharton.trakt.services;

import com.jakewharton.apibuilder.ApiException;
import com.jakewharton.trakt.BaseTestCase;

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
	
	public void test_test() {
		try {
			getManager().accountService().test("JakeWharton", "this is not my real password SHA!").fire();
			fail("Account test did not throw exception.");
		} catch (ApiException e) {
			assertEquals("Exception text does not match.", "{\"status\":\"failure\",\"error\":\"failed authentication\"}", e.getMessage().trim());
			
			//TODO: change this to a custom error that includes a response we
			//can actually test against to differentiate between a problem with
			//the API or simply a failed authentication.
		}
	}
}
