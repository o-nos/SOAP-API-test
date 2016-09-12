package com.snos.soaplibtest.soap.emailsignup;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 12.09.16.
 */
@Root(name = SignUpWithEmailRequest.ROOT_NAME, strict = false)
public class SignUpWithEmailRequest {

    public static final String ROOT_NAME = "n0:fireflyCustomerSignUpWithEmailRequest";

    @Element(required = false)
    private String sessionId;
    @Element(required = false)
    private String email;
    @Element(required = false)
    private String mobileSecret;

    public SignUpWithEmailRequest() {
    }

    public SignUpWithEmailRequest(String sessionId, String email, String mobileSecret) {
        this.sessionId = sessionId;
        this.email = email;
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

    public String getMobileSecret() {
        return mobileSecret;
    }

    public void setMobileSecret(String mobileSecret) {
        this.mobileSecret = mobileSecret;
    }

}
