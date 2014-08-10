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
     * trakt API URL.
     */
    public static final String API_URL = "https://api.trakt.tv";

    /**
     * API key path parameter name.
     */
    public static final String PARAM_API_KEY = "apikey";

    /**
     * API key.
     */
    private String mApiKey;

    /**
     * Whether to return more detailed log output.
     */
    private boolean mIsDebug;

    /**
     * User password.
     */
    private String mPasswordSha1;

    /**
     * User email.
     */
    private String mUsername;

    /**
     * Currently valid instance of RestAdapter.
     */
    private RestAdapter mRestAdapter;


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
        mRestAdapter = null;
        return this;
    }

    /**
     * Set your trakt API key. All API methods require a valid API key.
     *
     * @param key trakt API key value.
     */
    public Trakt setApiKey(String key) {
        mApiKey = key;
        mRestAdapter = null;
        return this;
    }

    public Trakt setIsDebug(boolean isDebug) {
        mIsDebug = isDebug;
        mRestAdapter = null;
        return this;
    }

    /**
     * If no instance exists yet, builds a new {@link RestAdapter} using the currently set
     * authentication information, API key and debug flag.
     */
    protected RestAdapter buildRestAdapter() {
        if (mRestAdapter == null) {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setConverter(new GsonConverter(TraktHelper.getGsonBuilder().create()));

            // if available, send mUsername and password in header
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade requestFacade) {
                    requestFacade.addPathParam(PARAM_API_KEY, mApiKey);
                    if ((mUsername != null) && (mPasswordSha1 != null)) {
                        String source = mUsername + ":" + mPasswordSha1;
                        String authorization = "Basic " + Base64.encodeBytes(source.getBytes());
                        requestFacade.addHeader("Authorization", authorization);
                    }
                }
            });

            if (mIsDebug) {
                builder.setLogLevel(RestAdapter.LogLevel.FULL);
            }

            mRestAdapter = builder.build();
        }

        return mRestAdapter;
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

    public CommentService commentService() {
        return buildRestAdapter().create(CommentService.class);
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

    public NetworkService networkService() {
        return buildRestAdapter().create(NetworkService.class);
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

    public ShowService showService() {
        return buildRestAdapter().create(ShowService.class);
    }

    public UserService userService() {
        return buildRestAdapter().create(UserService.class);
    }
}
