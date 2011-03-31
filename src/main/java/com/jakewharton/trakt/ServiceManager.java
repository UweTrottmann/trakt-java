package com.jakewharton.trakt;

/**
 * Class to manage service creation with default settings.
 * 
 * @author Jake Wharton <jakewharton@gmail.com>
 */
public class ServiceManager {
	/** API key. */
	private String appKeyValue;
	/** User email. */
	private String email;
	/** User password. */
	private String password;
	/** Connection timeout (in milliseconds). */
	private Integer connectionTimeout;
	/** Read timeout (in milliseconds). */
	private Integer readTimeout;
	
	
	/** Create a new manager instance. */
	public ServiceManager() {}
	
	
	/**
	 * Set default authentication credentials.
	 * 
	 * @param email User email.
	 * @param password User password.
	 * @return Current instance for builder pattern.
	 */
	public ServiceManager setAuthentication(String email, String password) {
		this.email = email;
		this.password = password;
		return this;
	}
	
	/**
	 * Set default API key.
	 * 
	 * @param value API key value.
	 * @return Current instance for builder pattern.
	 */
	public ServiceManager setAppKey(String value) {
		this.appKeyValue = value;
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
		if (this.appKeyValue != null) {
			service.setAppKey(this.appKeyValue);
		}
		if ((this.email != null) && (this.password != null)) {
			service.setAuthentication(this.email, this.password);
		}
		if (this.connectionTimeout != null) {
			service.setConnectTimeout(this.connectionTimeout);
		}
		if (this.readTimeout != null) {
			service.setReadTimeout(this.readTimeout);
		}
	}
	
	
}
