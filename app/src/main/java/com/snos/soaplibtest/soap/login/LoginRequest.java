package com.snos.soaplibtest.soap.login;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 08.09.16.
 */
@Root(name = LoginRequest.ROOT_NAME, strict = false)
public class LoginRequest {
    public static final String ROOT_NAME = "n0:login";

    @Element(required = false)
    private String username;
    @Element(required = false)
    private String apiKey;

    public LoginRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}