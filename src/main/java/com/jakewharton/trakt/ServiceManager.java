package com.jakewharton.trakt;

import com.jakewharton.trakt.services.CalendarService;
import com.jakewharton.trakt.services.UserService;

/**
 * Class to manage service creation with default settings.
 * 
 * @author Jake Wharton <jakewharton@gmail.com>
 */
public class ServiceManager {
	/** API key. */
	private String apiKeyValue;
	/** User email. */
	private String username;
	/** User password. */
	private String password_sha;
	/** Connection timeout (in milliseconds). */
	private Integer connectionTimeout;
	/** Read timeout (in milliseconds). */
	private Integer readTimeout;
	
	
	/** Create a new manager instance. */
	public ServiceManager() {}
	
	
	/**
	 * Set default authentication credentials.
	 * 
	 * @param username Username.
	 * @param password_sha SHA1 of user password.
	 * @return Current instance for builder pattern.
	 */
	public ServiceManager setAuthentication(String username, String password_sha) {
		this.username = username;
		this.password_sha = password_sha;
		return this;
	}
	
	/**
	 * Set default API key.
	 * 
	 * @param value API key value.
	 * @return Current instance for builder pattern.
	 */
	public ServiceManager setApiKey(String value) {
		this.apiKeyValue = value;
		return this;
	}
	
	/**
	 * Set default connection timeout.
	 * 
	 * @param connectionTimeout Timeout (in milliseconds).
	 * @return Current instance for builder pattern.
	 */
	public ServiceManager setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}
	
	/**
	 * Set default read timeout.
	 * 
	 * @param readTimeout Timeout (in milliseconds).
	 * @return Current instance for builder pattern.
	 */
	public ServiceManager setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}
	
	/**
	 * Set up a new service with the defaults.
	 * 
	 * @param service Service to set up.
	 */
	private void setupService(TraktApiService service) {
		if (this.apiKeyValue != null) {
			service.setApiKey(this.apiKeyValue);
		}
		if ((this.username != null) && (this.password_sha != null)) {
			service.setAuthentication(this.username, this.password_sha);
		}
		if (this.connectionTimeout != null) {
			service.setConnectTimeout(this.connectionTimeout);
		}
		if (this.readTimeout != null) {
			service.setReadTimeout(this.readTimeout);
		}
	}
	
	public CalendarService calendarService() {
		CalendarService service = ServiceManager.createCalendarService();
		this.setupService(service);
		return service;
	}
	
	public UserService userService() {
		UserService service = ServiceManager.createUserService();
		this.setupService(service);
		return service;
	}
	
	public static final CalendarService createCalendarService() {
		return new CalendarService();
	}
	
	public static final UserService createUserService() {
		return new UserService();
	}
}
