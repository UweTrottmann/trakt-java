package com.jakewharton.trakt.services;

import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.UserProfile;

public class NetworkServiceTest extends BaseTestCase {
	public void test_requests() {
		List<UserProfile> requests = getManager().networkService().requests().fire();
		assertNotNull("Result was null.", requests);
		
		if (!requests.isEmpty()) {
			assertNotNull("Request list item was null.", requests.get(0));
		}
	}
}