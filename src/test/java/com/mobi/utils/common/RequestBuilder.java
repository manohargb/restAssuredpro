package com.mobi.utils.common;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.MalformedURLException;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class RequestBuilder {

    private RequestSpecification requestSpecification;
    private String body;
    private Map<String, String> headers;
    private String path;

    public enum HttpMethod {GET, POST}

    RequestBuilder() {
        requestSpecification = given();
    }

    public RequestBuilder withHeaders(Map<String, String> headers) {
        this.headers = headers;
        requestSpecification.headers(headers);
        return this;
    }

    public RequestBuilder withBody(String body) {
        this.body = body;
        requestSpecification.body(body);
        return this;
    }

    public  RequestBuilder withPath(String path){
        this.path = path;
        requestSpecification.basePath(path);
        return this;
    }

    public RequestBuilder withBasicAuth(String username, String password) {
        requestSpecification.auth().preemptive().basic(username, password);
        return this;
    }

    public Response execute(HttpMethod httpMethod, String url) throws MalformedURLException {
        switch (httpMethod) {
            case GET:
                return requestSpecification.get(url).andReturn();
            case POST:
                return requestSpecification.post(url).andReturn();
            default:
                throw new RuntimeException("Unsupported HTTP method");
        }
    }

}
