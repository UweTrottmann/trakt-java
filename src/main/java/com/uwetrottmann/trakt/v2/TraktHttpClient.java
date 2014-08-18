package com.uwetrottmann.trakt.v2;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import org.apache.oltu.oauth2.client.HttpClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthClientResponseFactory;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class TraktHttpClient implements HttpClient {

    @Override
    public <T extends OAuthClientResponse> T execute(OAuthClientRequest request, Map<String, String> headers,
            String requestMethod, Class<T> responseClass) throws OAuthSystemException, OAuthProblemException {
        OkHttpClient client = Utils.createOkHttpClient();

        try {
            HttpURLConnection connection = new OkUrlFactory(client).open(new URL(request.getLocationUri()));

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    connection.addRequestProperty(header.getKey(), header.getValue());
                }
            }

            if (request.getHeaders() != null) {
                for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                    connection.addRequestProperty(header.getKey(), header.getValue());
                }
            }

            if (!OAuthUtils.isEmpty(requestMethod)) {
                connection.setRequestMethod(requestMethod);
                if (requestMethod.equals("POST")) {
                    connection.setDoOutput(true);
                    OutputStream ost = connection.getOutputStream();
                    PrintWriter pw = new PrintWriter(ost);
                    pw.print(request.getBody());
                    pw.flush();
                    pw.close();
                }
            } else {
                connection.setRequestMethod("GET");
            }

            connection.connect();

            InputStream inputStream;
            int responseCode = connection.getResponseCode();
            if (responseCode >= 400) {
                inputStream = connection.getErrorStream();
            } else {
                inputStream = connection.getInputStream();
            }

            String body = null;
            if (inputStream != null) {
                body = OAuthUtils.saveStreamAsString(inputStream);
            }

            if (body != null) {
                return OAuthClientResponseFactory
                        .createCustomResponse(body, connection.getContentType(), responseCode, responseClass);
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
