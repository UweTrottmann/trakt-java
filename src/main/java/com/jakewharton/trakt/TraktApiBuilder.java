package com.jakewharton.trakt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.apibuilder.ApiBuilder;

/**
 * Trakt-specific API builder extension which provides helper methods for
 * adding fields, parameters, and post-parameters commonly used in the API.
 * 
 * @param <T> Native class type of the HTTP method call result.
 * @author Jake Wharton <jakewharton@gmail.com>
 */
public abstract class TraktApiBuilder<T> extends ApiBuilder {
	/** API key field name. */
	protected static final String FIELD_API_KEY = "apikey";

	protected static final String FIELD_USERNAME = "username";
	protected static final String FIELD_DATE = "date";
	protected static final String FIELD_DAYS = "days";
	
	/** Format for encoding a {@link java.util.Date} in a URL. */
	private static final SimpleDateFormat URL_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
	/** Trakt API URL base. */
	private static final String BASE_URL = "http://api.trakt.tv";
	
	/** Number of milliseconds in a single second. */
	/*package*/ static final long MILLISECONDS_IN_SECOND = 1000;
	
	/** Valued-list seperator. */
	private static final char SEPERATOR = ',';
	
	
	/** Valid HTTP request methods. */
	protected static enum HttpMethod {
		Get, Post
	}
	
	
	/** Service instance. */
	private final TraktApiService service;
	
	/** Type token of return type. */
	private final TypeToken<T> token;
	
	/** HTTP request method to use. */
	private final HttpMethod method;
	
	
	/**
	 * Initialize a new builder for an HTTP GET call.
	 * 
	 * @param service Service to bind to.
	 * @param token Return type token.
	 * @param methodUri URI method format string.
	 * @param fieldsUri URI field format string.
	 */
	public TraktApiBuilder(TraktApiService service, TypeToken<T> token, String methodUri) {
		this(service, token, methodUri, HttpMethod.Get);
	}
	
	/**
	 * Initialize a new builder for the specified HTTP method call.
	 * 
	 * @param service Service to bind to.
	 * @param token Return type token.
	 * @param urlFormat URL format string.
	 * @param method HTTP method.
	 */
	public TraktApiBuilder(TraktApiService service, TypeToken<T> token, String urlFormat, HttpMethod method) {
		super(BASE_URL + urlFormat);
		
		this.service = service;
		
		this.token = token;
		this.method = method;
		
		this.field(FIELD_API_KEY, this.service.getApiKey());
	}

	
	/**
	 * Execute remote API method and unmarshall the result to its native type.
	 * 
	 * @return Instance of result type.
	 */
	public final T fire() {
		return this.service.unmarshall(this.token, this.execute());
	}
	
	public String asUrl() {
		String url = this.buildUrl();
		while (url.endsWith("/")) {
			url = url.substring(0, url.length() - 2);
		}
		return url;
	}
	
	/**
	 * <p>Execute the remote API method and return the JSON object result.<p>
	 * 
	 * <p>This method can be overridden to select a specific subset of the JSON
	 * object. The overriding implementation should still call 'super.execute()'
	 * and then perform the filtering from there.</p> 
	 * 
	 * @return JSON object instance.
	 */
	protected JsonElement execute() {
		String url = this.buildUrl();
		while (url.endsWith("/")) {
			url = url.substring(0, url.length() - 2);
		}
		switch (this.method) {
			case Get:
				return this.service.get(url);
			case Post:
				return this.service.post(url, new JsonObject()); //TODO: THIS w/JSON!
			default:
				throw new IllegalArgumentException("Unknown HttpMethod type " + this.method.toString());
		}
	}
	
	/**
	 * Set the API key.
	 * 
	 * @param apiKey API key string.
	 * @return Current instance for builder pattern.
	 */
	/*package*/ ApiBuilder api(String apiKey) {
		return this.field(FIELD_API_KEY, apiKey);
	}
	
	/**
	 * Add a URL parameter value.
	 * 
	 * @param name Name.
	 * @param value Value.
	 * @return Current instance for builder pattern.
	 */
	protected ApiBuilder parameter(String name, Date value) {
		return this.parameter(name, Long.toString(value.getTime() / MILLISECONDS_IN_SECOND));
	}
	
	/**
	 * Add a URL parameter value.
	 * 
	 * @param name Name.
	 * @param value Value.
	 * @return Current instance for builder pattern.
	 */
	protected <K extends TraktEnumeration> ApiBuilder parameter(String name, K value) {
		if ((value == null) || (value.toString() == null) || (value.toString().length() == 0)) {
			return this.parameter(name, "");
		} else {
			return this.parameter(name, value.toString());
		}
	}
	
	/**
	 * Add a URL parameter value.
	 * 
	 * @param name Name.
	 * @param valueList List of values.
	 * @return Current instance for builder pattern.
	 */
	protected <K extends Object> ApiBuilder parameter(String name, List<K> valueList) {
    	StringBuilder builder = new StringBuilder();
    	Iterator<K> iterator = valueList.iterator();
    	while (iterator.hasNext()) {
    		builder.append(encodeUrl(iterator.next().toString()));
    		if (iterator.hasNext()) {
    			builder.append(SEPERATOR);
    		}
		}
    	return this.parameter(name, builder.toString());
    }
	
	/**
	 * Add a URL field value.
	 * 
	 * @param name Name.
	 * @param date Value.
	 * @return Current instance for builder pattern.
	 */
	protected ApiBuilder field(String name, Date date) {
		return this.field(name, URL_DATE_FORMAT.format(date));
	}
    
    /**
     * Add a URL field value.
     * 
     * @param name Name.
     * @param value Value.
     * @return Current instance for builder pattern.
     */
	protected <K extends TraktEnumeration> ApiBuilder field(String name, K value) {
		if ((value == null) || (value.toString() == null) || (value.toString().length() == 0)) {
			return this.field(name);
		} else {
			return this.field(name, value.toString());
		}
	}
}
