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

The tests require two credentials to run:

- `TEST_CLIENT_ID`: Your Trakt API client ID
- `TEST_ACCESS_TOKEN`: Your Trakt API access token

### Option 1: Environment Variables (Recommended for CI/CD)

Set environment variables in your shell:

**Windows (PowerShell):**

```powershell
$env:TEST_CLIENT_ID="your_client_id_here"
$env:TEST_ACCESS_TOKEN="your_access_token_here"
```

**Linux/Mac:**

```bash
export TEST_CLIENT_ID="your_client_id_here"
export TEST_ACCESS_TOKEN="your_access_token_here"
```

### Option 2: secrets.properties File (Recommended for Local Development)

1. Copy the sample file:

   ```
   cp secrets.properties.sample secrets.properties
   ```

2. Edit `secrets.properties` and add your credentials:

   ```properties
   TEST_CLIENT_ID=your_client_id_here
   TEST_ACCESS_TOKEN=your_access_token_here
   ```

3. The `secrets.properties` file is automatically ignored by git and will not be committed.

### Priority

If both are set, environment variables take precedence over the properties file.

### Getting Credentials

To obtain your Trakt API credentials:

1. Create a Trakt account at https://trakt.tv
2. Register your application at https://trakt.tv/oauth/applications
3. Use the provided Client ID and generate an access token.
   For example, add the `http://localhost` URI and use the "Authorize" button on the Trakt API apps website.
   Then use the `code` in the redirect URL to obtain an access token using `AuthTest.test_getAccessToken` (set client 
   secret and code first).

## No code!

You can [discuss or submit bug reports](https://github.com/UweTrottmann/trakt-java/issues)!
