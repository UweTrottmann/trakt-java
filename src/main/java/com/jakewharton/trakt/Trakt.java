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

public class Trakt {

    /**
     * API key path parameter name.
     */
    protected static final String PARAM_API_KEY = "apikey";

    /**
     * trakt API URL.
     */
    private static final String API_URL = "http://api.trakt.tv";

    /**
     * Whether to return more detailed log output.
     */
    private boolean mIsDebug;

    /**
     * API key.
     */
    private String mApiKey;

    /**
     * User email.
     */
    private String mUsername;

    /**
     * User password.
     */
    private String mPasswordSha1;


    /**
     * Create a new manager instance.
     */
    public Trakt() {
    }

    /**
     * POST API methods on trakt require basic setAuthentication. You must set your trakt username
     * and sha1 of the password. They will be sent in the HTTP header.
     *
     * @param username     Username.
     * @param passwordSha1 SHA1 of user password.
     */
    public Trakt setAuthentication(String username, String passwordSha1) {
        mUsername = username;
        mPasswordSha1 = passwordSha1;
        return this;
    }

    /**
     * Set your trakt API key. All API methods require a valid API key.
     *
     * @param key trakt API key value.
     */
    public Trakt setApiKey(String key) {
        mApiKey = key;
        return this;
    }

    public Trakt setIsDebug(boolean isDebug) {
        mIsDebug = isDebug;
        return this;
    }

    /**
     * Set up a new service with the defaults.
     *
     * @param service Service to set up.
     */
    private void setupService(TraktApiService service) {
        if (this.mApiKey != null) {
            service.setApiKey(this.mApiKey);
        }
        if ((this.mUsername != null) && (this.mPasswordSha1 != null)) {
            service.setAuthentication(this.mUsername, this.mPasswordSha1);
        }
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setServer(API_URL)
                .setConverter(new GsonConverter(TraktApiService.getGsonBuilder().create()));

        // if available, send mUsername and password in header
        if ((mUsername != null) && (mPasswordSha1 != null)) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade requestFacade) {
                    requestFacade.addPathParam(PARAM_API_KEY, mApiKey);
                    String source = mUsername + ":" + mPasswordSha1;
                    String authorization = "Basic " + Base64.encodeBytes(source.getBytes());
                    requestFacade.addHeader("Authorization", authorization);
                }
            });
        }

        if (mIsDebug) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        return builder.build();
    }

    public AccountService accountService() {
        return buildRestAdapter().create(AccountService.class);
    }

    public ActivityService activityService() {
        return buildRestAdapter().create(ActivityService.class);
    }

    public CalendarService calendarService() {
        return buildRestAdapter().create(CalendarService.class);
    }

    public NetworkService networkService() {
        return buildRestAdapter().create(NetworkService.class);
    }

    public GenreService genreService() {
        return buildRestAdapter().create(GenreService.class);
    }

    public ListService listService() {
        return buildRestAdapter().create(ListService.class);
    }

    public MovieService movieService() {
        return buildRestAdapter().create(MovieService.class);
    }

    public RateService rateService() {
        return buildRestAdapter().create(RateService.class);
    }

    public RecommendationsService recommendationsService() {
        return buildRestAdapter().create(RecommendationsService.class);
    }

    public SearchService searchService() {
        return buildRestAdapter().create(SearchService.class);
    }

    public CommentService commentService() {
        return buildRestAdapter().create(CommentService.class);
    }

    public ShowService showService() {
        return buildRestAdapter().create(ShowService.class);
    }

    public UserService userService() {
        return buildRestAdapter().create(UserService.class);
    }
}
