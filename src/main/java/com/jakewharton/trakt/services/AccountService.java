package com.jakewharton.trakt.services;

import com.google.gson.annotations.SerializedName;
import com.jakewharton.trakt.entities.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Endpoints for Account.
 */
public interface AccountService {

    static class Settings extends Response {
        static class Profile {
            String username;
            String full_name;
            String gender;
            Integer age;
            String location;
            String about;
            int joined;
            int last_login;
            String avatar;
            String url;
            boolean vip;
        }

        static class Account {
            /**
             * <a href="http://go.trakt.tv/HX7SfQ">Info about timezone descriptors.</a>
             */
            String timezone;
            boolean use_24hr;
            @SerializedName("protected")
            boolean _protected;
        }

        static class Viewing {
            static class Ratings {
                /**
                 * simple, advanced
                 */
                String mode;
            }

            static class Shouts {
                boolean show_badges;
                boolean show_spoilers;
            }

            Ratings ratings;
            Shouts shouts;
        }

        static class Connections {
            static class Facebook {
                boolean connected;
            }

            static class Twitter {
                boolean connected;
            }

            static class Tumblr {
                boolean connected;
            }

            static class Path {
                boolean connected;
            }

            static class Prowl {
                boolean connected;
            }

            Facebook facebook;
            Twitter twitter;
            Tumblr tumblr;
            Path path;
            Prowl prowl;
        }

        static class SharingText {
            String watching;
            String watched;
        }

        Profile profile;
        Account account;
        Viewing viewing;
        Connections connections;
        SharingText sharing_text;
    }

    static class NewAccount {
        String username;
        String password;
        String email;
    }

    /**
     * Create a new trakt account. Username and e-mail must be unique and not
     * already exist in trakt.
     */
    @POST("/account/create/{apikey}")
    Response create(
            @Body NewAccount account
    );

    /**
     * Returns all settings for the authenticated user. Use these settings to
     * customize your app based on the user's settings. For example, if they use
     * advanced ratings show a 10 heart scale. If they prefer simple ratings,
     * show the binary scale. The social connections are also useful to
     * customize the checkin prompt.
     */
    @POST("/account/settings/{apikey}")
    Settings settings();

    /**
     * Test trakt credentials. This is useful for your configuration screen and
     * is a simple way to test someone's trakt account.
     */
    @POST("/account/test/{apikey}")
    Response test();

}
