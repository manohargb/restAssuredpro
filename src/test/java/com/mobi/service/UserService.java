package com.mobi.service;

import com.mobi.utils.common.RequestBuilder;
import com.mobi.utils.common.RequestBuilderFactory;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import static com.mobi.utils.common.PropertiesLoader.readProperty;

public class UserService {

    private static final String HOST_NAME = readProperty("hostname");

    public static Response getUsers() throws MalformedURLException {
        return makeGetRequest("/users");
    }

    public static Response getPosts() throws MalformedURLException {
        return makeGetRequest("/posts");
    }

    public static Response getComments() throws MalformedURLException {
        return makeGetRequest("/comments");
    }

    private static Response makeGetRequest(String servicePath) throws MalformedURLException {
        String serviceUrl = HOST_NAME + servicePath;
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, "application/json");
        return new RequestBuilderFactory().builder()
                .withHeaders(headers)
                .execute(RequestBuilder.HttpMethod.GET, serviceUrl);
    }

}
