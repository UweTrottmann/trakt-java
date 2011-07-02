package com.jakewharton.trakt.services;

import java.util.List;
import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.UserProfile;

public class FriendsServiceTest extends BaseTestCase {
	public void test_requests() {
		List<UserProfile> requests = getManager().friendsService().requests().fire();
		assertNotNull("Result was null.", requests);
		
		if (!requests.isEmpty()) {
			assertNotNull("Request list item was null.", requests.get(0));
		}
	}
	
	public void test_all() {
		List<UserProfile> all = getManager().friendsService().all().fire();
		assertNotNull("Result was null.", all);
		
		if (!all.isEmpty()) {
			assertNotNull("All list item was null.", all.get(0));
		}
	}
}