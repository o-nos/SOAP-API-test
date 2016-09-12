package com.snos.soaplibtest.soap.signin;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 08.09.16.
 */
@Root(name = SignInRequest.ROOT_NAME, strict = false)
public class SignInRequest {
    public static final String ROOT_NAME = "n0:fireflyCustomerSignInRequest";

    @Element(required = false)
    private String sessionId;
    @Element(required = false)
    private String email;
    @Element(required = false)
    private String password;
    @Element(required = false)
    private String mobileSecret;

    public SignInRequest() {
    }

    public SignInRequest(String sessionId, String email, String password, String mobileSecret) {
        this.sessionId = sessionId;
        this.email = email;
        this.password = password;
        this.mobileSecret = mobileSecret;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileSecret() {
        return mobileSecret;
    }

    public void setMobileSecret(String mobileSecret) {
        this.mobileSecret = mobileSecret;
    }
}
