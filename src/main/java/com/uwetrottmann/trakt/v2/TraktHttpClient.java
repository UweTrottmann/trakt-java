package com.uwetrottmann.trakt.v2;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class TraktHttpClient implements HttpClient {

    @Override
    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request, Map<String, String> headers,
            String requestMethod, Class<T> responseClass) throws OAuthSystemException, OAuthProblemException {
        OkHttpClient client = Utils.createOkHttpClient();

        try {
            Request.Builder requestBuilder = new Request.Builder().url(request.getLocationUri());

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    requestBuilder.header(header.getKey(), header.getValue());
                }
            }

            if (request.getHeaders() != null) {
                for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                    requestBuilder.header(header.getKey(), header.getValue());
                }
            }

            // we only use POST with an empty body
            requestBuilder.post(RequestBody.create(null, new byte[0]));

            Response response = client.newCall(requestBuilder.build()).execute();

            String body = null;
            if (response.body() != null) {
                InputStream inputStream = response.body().byteStream();
                if (inputStream != null) {
                    body = OAuthUtils.saveStreamAsString(inputStream);
                }
            }

            if (body != null) {
                return OAuthClientResponseFactory.createCustomResponse(body, response.body().contentType().toString(),
                        response.code(), responseClass);
            }
        } catch (IOException e) {
            throw new OAuthSystemException(e);
        }

        return null;
    }

    @Override
    public void shutdown() {
        // not re-using HTTP client for now
    }
}
