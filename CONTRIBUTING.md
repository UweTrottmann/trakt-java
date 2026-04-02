# Contributing

## Would you like to contribute code?

**Note:** [This work](README.md) is licensed under the [Apache License 2.0](LICENSE.txt).
If you contribute any [non-trivial](http://www.gnu.org/prep/maintain/maintain.html#Legally-Significant) patches make
sure to read and agree with the license.

**Note:** Keep your pull requests small, otherwise it is less likely they will get merged.

**Note:** This project is built with [Maven](https://maven.apache.org/), see the `pom.xml` in the root folder.

1. [Fork trakt-java](https://github.com/UweTrottmann/trakt-java/fork).
2. Create a new branch and make great commits + messages:

   * Each line should be 72 characters or less.
   * Add a subject line and body text separated by an empty line.
   * Describe why this change, how does it address an issue or what side effects it has.
   * Link related issues or documentation as needed.

3. If you add or modify methods, make sure to also create and run basic tests (see `src/test`).
4. [Start a pull request](https://github.com/UweTrottmann/tmdb-java/compare) against `main`.

## Setting up Test Credentials

The tests require at least two credentials to run:

- `TEST_CLIENT_ID`: A Trakt API client ID
- `TEST_ACCESS_TOKEN`: A Trakt API access token

They can be set either 

- in a `secrets.properties` file (a template is available in [secrets.properties.template](secrets.properties.template))
- or as environment variables.

If both are set, environment variables take precedence over the properties file.

### Getting Credentials

To obtain Trakt API credentials:

1. Create a Trakt account at https://trakt.tv (⚠️ Tests do modify data of the Trakt account, don't use your personal account!)
2. Register an application at https://trakt.tv/oauth/applications
3. Copy the Client ID and set it as `TEST_CLIENT_ID`
4. Copy the Client Secret and set it as `TEST_CLIENT_SECRET`
5. Add `http://localhost` to the Redirect URIs
6. Click the "Authorize" button next to the localhost redirect URI and authorize access (alternatively, run `test_getAuthorizationRequest()` in [AuthTest][] and visit the URL it prints)
7. Extract the value of `code` from the redirect URI and set it as `TEST_AUTH_CODE`
8. Run `test_getAccessToken()` in [AuthTest][]
9. Copy the printed access and refresh token and set them as `TEST_ACCESS_TOKEN` and `TEST_REFRESH_TOKEN`

### Refreshing the Access Token

If `TEST_ACCESS_TOKEN` is expired, but `TEST_REFRESH_TOKEN` isn't:

1. Run `test_refreshAccessToken()` in [AuthTest][]
2. Copy the printed access and refresh token and set them as `TEST_ACCESS_TOKEN` and `TEST_REFRESH_TOKEN`

Otherwise, repeat steps 6 and following from above.

## No code!

You can [discuss or submit bug reports](https://github.com/UweTrottmann/trakt-java/issues)!

[AuthTest]: src/test/java/com/uwetrottmann/trakt5/AuthTest.java
