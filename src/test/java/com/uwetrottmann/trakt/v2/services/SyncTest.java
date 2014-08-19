package com.uwetrottmann.trakt.v2.services;

import com.uwetrottmann.trakt.v2.BaseTestCase;
import org.junit.Test;

public class SyncTest extends BaseTestCase {

    @Test
    public void test_getCollectionMovies() {
        getTrakt().sync().getCollectionMovies();
    }

    @Test
    public void test_getCollectionShows() {
        getTrakt().sync().getCollectionShows();
    }

}
