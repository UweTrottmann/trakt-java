package com.jakewharton.trakt.services;

import java.util.List;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.Watched;

public final class UserService extends TraktApiService {
	public WatchedBuilder watched(String username) {
		return new WatchedBuilder(this, username);
	}
	
	public static final class WatchedBuilder extends TraktApiBuilder<List<Watched>> {
		private static final String FIELD_USERNAME = "username";
		
		private static final String URI = "/user/watched.json/{" + FIELD_API_KEY + "}/{" + FIELD_USERNAME + "}";
		
		private WatchedBuilder(UserService service, String username) {
			super(service, new TypeToken<List<Watched>>() {}, URI);
			
			this.field(FIELD_USERNAME, username);
		}
	}
}
