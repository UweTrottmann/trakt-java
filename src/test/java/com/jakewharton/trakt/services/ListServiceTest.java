package com.jakewharton.trakt.services;

import com.jakewharton.trakt.BaseTestCase;
import com.jakewharton.trakt.entities.ListResponse;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.enumerations.ListPrivacy;
import com.jakewharton.trakt.enumerations.Status;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ListServiceTest extends BaseTestCase {

    @Test
    public void test_list() {
        final ListService service = getManager().listService();

        ListResponse listResponse = service.add(
                new ListService.NewList("Test List", ListPrivacy.Private));
        assertThat(listResponse).isNotNull();
        assertThat(listResponse.status).isEqualTo(Status.SUCCESS);
        assertThat(listResponse.name).isEqualTo("Test List");
        assertThat(listResponse.privacy).isEqualTo(ListPrivacy.Private);

        Response response = service.delete(new ListService.DeleteList(listResponse.slug));
        assertThat(response).isNotNull();
        // If the following fails, the list needs to be removed manually!
        assertThat(response.status).isEqualTo(Status.SUCCESS);
    }
}
