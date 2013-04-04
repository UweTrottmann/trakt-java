
package com.jakewharton.trakt.services;

import com.google.gson.reflect.TypeToken;
import com.jakewharton.trakt.TraktApiBuilder;
import com.jakewharton.trakt.TraktApiService;
import com.jakewharton.trakt.entities.Response;
import com.jakewharton.trakt.entities.UserProfile;

import java.util.List;

public class NetworkService extends TraktApiService {
    /**
     * Follow a user. If the user has a protected profile, the follow request
     * will be in a pending state. If they have a public profile, they will be
     * followed immediately.
     * 
     * @param user Username of the person to follow.
     * @return Builder instance.
     */
    public FollowBuilder follow(String user) {
        return (new FollowBuilder(this)).user(user);
    }

    /**
     * Unfollow someone you already follow.
     * 
     * @param friend Username of the person to unfollow.
     * @return Builder instance.
     */
    public UnfollowBuilder unfollow(String user) {
        return (new UnfollowBuilder(this)).user(user);
    }

    /**
     * Approve a follower request.
     * 
     * @param user Username of the follower to approve.
     * @return Builder instance.
     */
    public ApproveBuilder approve(String user) {
        return (new ApproveBuilder(this)).user(user);
    }

    /**
     * Deny a follower request.
     * 
     * @param user Username of the follower to deny.
     * @return Builder instance.
     */
    public DenyBuilder deny(String user) {
        return (new DenyBuilder(this)).user(user);
    }

    /**
     * Get a list of all follower requests including the timestamp when the
     * request was made. Use the approve and deny methods to manage each
     * request.
     * 
     * @return Builder instance.
     */
    public RequestsBuilder requests() {
        return new RequestsBuilder(this);
    }

    private static final String POST_USER = "user";

    public static final class FollowBuilder extends TraktApiBuilder<Response> {
        private static final String URI = "/network/follow/" + FIELD_API_KEY;

        private FollowBuilder(NetworkService service) {
            super(service, new TypeToken<Response>() {
            }, URI, HttpMethod.Post);
        }

        /**
         * Username of the person to follow.
         * 
         * @param user Value.
         * @return Builder instance.
         */
        public FollowBuilder user(String user) {
            postParameter(POST_USER, user);
            return this;
        }
    }

    public static final class UnfollowBuilder extends TraktApiBuilder<Response> {
        private static final String URI = "/network/unfollow/" + FIELD_API_KEY;

        private UnfollowBuilder(NetworkService service) {
            super(service, new TypeToken<Response>() {
            }, URI, HttpMethod.Post);
        }

        /**
         * Username of the person to unfollow.
         * 
         * @param user Value.
         * @return Builder instance.
         */
        public UnfollowBuilder user(String user) {
            postParameter(POST_USER, user);
            return this;
        }
    }

    public static final class ApproveBuilder extends TraktApiBuilder<Response> {
        private static final String POST_FOLLOW_BACK = "follow_back";

        private static final String URI = "/network/approve/" + FIELD_API_KEY;

        private ApproveBuilder(NetworkService service) {
            super(service, new TypeToken<Response>() {
            }, URI, HttpMethod.Post);
        }

        /**
         * Username of the follower to approve.
         * 
         * @param user Value.
         * @return Builder instance.
         */
        public ApproveBuilder user(String user) {
            postParameter(POST_USER, user);
            return this;
        }

        /**
         * (Optional) If true, the follower request will be approved, plus the
         * user will be followed back. If false, only the approval will take
         * place.
         * 
         * @param followBack Value.
         * @return Builder instance.
         */
        public ApproveBuilder followBack(boolean followBack) {
            postParameter(POST_FOLLOW_BACK, followBack);
            return this;
        }
    }

    public static final class DenyBuilder extends TraktApiBuilder<Response> {
        private static final String URI = "/network/deny/" + FIELD_API_KEY;

        private DenyBuilder(NetworkService service) {
            super(service, new TypeToken<Response>() {
            }, URI, HttpMethod.Post);
        }

        /**
         * Username of the follower to deny.
         * 
         * @param friend Value.
         * @return Builder instance.
         */
        public DenyBuilder user(String friend) {
            postParameter(POST_USER, friend);
            return this;
        }
    }

    public static final class RequestsBuilder extends TraktApiBuilder<List<UserProfile>> {
        private static final String URI = "/network/requests/" + FIELD_API_KEY;

        private RequestsBuilder(NetworkService service) {
            super(service, new TypeToken<List<UserProfile>>() {
            }, URI, HttpMethod.Post);
        }
    }
}
