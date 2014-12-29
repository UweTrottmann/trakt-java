package com.jakewharton.trakt;

import com.jakewharton.trakt.services.AccountService;
import com.jakewharton.trakt.services.ActivityService;
import com.jakewharton.trakt.services.CalendarService;
import com.jakewharton.trakt.services.CommentService;
import com.jakewharton.trakt.services.GenreService;
import com.jakewharton.trakt.services.ListService;
import com.jakewharton.trakt.services.MovieService;
import com.jakewharton.trakt.services.NetworkService;
import com.jakewharton.trakt.services.RateService;
import com.jakewharton.trakt.services.RecommendationsService;
import com.jakewharton.trakt.services.SearchService;
import com.jakewharton.trakt.services.ShowService;
import com.jakewharton.trakt.services.UserService;
import com.jakewharton.trakt.util.Base64;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Helper class for easy usage of the trakt API using retrofit.
 * <p>
 * Create an instance of this class, {@link #setApiKey(String)} and then call any of the service methods. You may also
 * want to {@link #setAuthentication(String, String)} to get user-related data or to access user-specific methods.
 * <p>
 * The service methods take care of constructing the required {@link retrofit.RestAdapter} and creating the service. You
 * can customize the {@link retrofit.RestAdapter} by overriding {@link #newRestAdapterBuilder()} and setting e.g.
 * your own HTTP client instance or thread executor.
 * <p>
 * Only one {@link retrofit.RestAdapter} instance is created upon the first and re-used for any consequent service
 * method call.
 *
 * @deprecated Use {@link com.uwetrottmann.trakt.v2.TraktV2}. Will be removed with next release.
 */
@Deprecated
public class Trakt {

    /**
     * trakt API URL.
     */
    public static final String API_URL = "https://api.trakt.tv";

    /**
     * API key path parameter name.
     */
    public static final String PARAM_API_KEY = "apikey";

    private String apiKey;
    private boolean isDebug;
    /**
     * User password SHA-1.
     */
    private String userPasswordSha1;
    private String userAccount;
    private RestAdapter restAdapter;

    /**
     * Create a new manager instance.
     *
     * @deprecated Use {@link com.uwetrottmann.trakt.v2.TraktV2}. Will be removed with next release.
     */
    @Deprecated
    public Trakt() {
    }

    /**
     * POST API methods on trakt require basic authentication. You must set your trakt username
     * and sha1 of the password. They will be sent in the HTTP header.
     * <p>
     * The next service method call will trigger a rebuild of the {@link retrofit.RestAdapter}. If
     * you have cached any service instances, get a new one from its service method.
     *
     * @param username     trakt user account name.
     * @param passwordSha1 SHA1 of trakt user account password.
     */
    public Trakt setAuthentication(String username, String passwordSha1) {
        userAccount = username;
        userPasswordSha1 = passwordSha1;
        restAdapter = null;
        return this;
    }

    /**
     * Set your trakt API key. All API methods require a valid API key.
     * <p>
     * The next service method call will trigger a rebuild of the {@link retrofit.RestAdapter}. If
     * you have cached any service instances, get a new one from its service method.
     *
     * @param key trakt API key value.
     */
    public Trakt setApiKey(String key) {
        apiKey = key;
        restAdapter = null;
        return this;
    }

    /**
     * Set the {@link retrofit.RestAdapter} log level.
     *
     * @param isDebug If true, the log level is set to {@link retrofit.RestAdapter.LogLevel#FULL}.
     *                Otherwise {@link retrofit.RestAdapter.LogLevel#NONE}.
     */
    public Trakt setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
        if (restAdapter != null) {
            restAdapter.setLogLevel(isDebug ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
        }
        return this;
    }

    /**
     * Create a new {@link retrofit.RestAdapter.Builder}. Override this to e.g. set your own client or executor.
     *
     * @return A {@link retrofit.RestAdapter.Builder} with no modifications.
     */
    protected RestAdapter.Builder newRestAdapterBuilder() {
        return new RestAdapter.Builder();
    }

    /**
     * Return the current {@link retrofit.RestAdapter} instance. If none exists (first call, API key changed),
     * builds a new one.
     * <p>
     * When building, sets the endpoint, a custom converter ({@link TraktHelper#getGsonBuilder()})
     * and a {@link retrofit.RequestInterceptor} which adds the API key as path param and if available adds
     * authentication to the request header.
     */
    protected RestAdapter getRestAdapter() {
        if (restAdapter == null) {
            RestAdapter.Builder builder = newRestAdapterBuilder();

            builder.setEndpoint(API_URL);
            builder.setConverter(new GsonConverter(TraktHelper.getGsonBuilder().create()));
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade requestFacade) {
                    requestFacade.addPathParam(PARAM_API_KEY, apiKey);
                    // if available, send userAccount and password in header
                    if ((userAccount != null) && (userPasswordSha1 != null)) {
                        String source = userAccount + ":" + userPasswordSha1;
                        String authorization = "Basic " + Base64.encodeBytes(source.getBytes());
                        requestFacade.addHeader("Authorization", authorization);
                    }
                }
            });

            if (isDebug) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            }

            restAdapter = builder.build();
        }

        return restAdapter;
    }

    public AccountService accountService() {
        return getRestAdapter().create(AccountService.class);
    }

    public ActivityService activityService() {
        return getRestAdapter().create(ActivityService.class);
    }

    public CalendarService calendarService() {
        return getRestAdapter().create(CalendarService.class);
    }

    public CommentService commentService() {
        return getRestAdapter().create(CommentService.class);
    }

    public GenreService genreService() {
        return getRestAdapter().create(GenreService.class);
    }

    public ListService listService() {
        return getRestAdapter().create(ListService.class);
    }

    public MovieService movieService() {
        return getRestAdapter().create(MovieService.class);
    }

    public NetworkService networkService() {
        return getRestAdapter().create(NetworkService.class);
    }

    public RateService rateService() {
        return getRestAdapter().create(RateService.class);
    }

    public RecommendationsService recommendationsService() {
        return getRestAdapter().create(RecommendationsService.class);
    }

    public SearchService searchService() {
        return getRestAdapter().create(SearchService.class);
    }

    public ShowService showService() {
        return getRestAdapter().create(ShowService.class);
    }

    public UserService userService() {
        return getRestAdapter().create(UserService.class);
    }
}
