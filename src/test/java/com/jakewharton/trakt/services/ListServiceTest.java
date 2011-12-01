package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.ListResponse;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.enumerations.ListPrivacy;

public class ListServiceTest extends BaseTestCase {
	public void test_list() {
		final ListService service = getManager().listService();
		
		ListResponse addResponse = service.add("Test List", ListPrivacy.Private).fire();
		assertNotNull("Response was null.", addResponse);
		assertEquals("List create was not successful.", "success", addResponse.status);
		assertEquals("Created list name does not match.", "Test List", addResponse.name);
		assertEquals("Created list privacy does not match.", ListPrivacy.Private, addResponse.privacy);
		assertNotNull("Created list does not have a slug.", addResponse.slug);
		
		Response delResponse = service.delete(addResponse.slug).fire();
		assertNotNull("Response was null. List will need to be deleted manually.", delResponse);
		assertEquals("List delete was not successful. List will need to be deleted manually.", "success", delResponse.status);
	}
}
