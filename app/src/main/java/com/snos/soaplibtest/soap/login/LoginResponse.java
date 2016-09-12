package com.snos.soaplibtest.soap.login;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 08.09.16.
 */
@Root(name = LoginResponse.ROOT_NAME, strict = false)
public class LoginResponse {
    public static final String ROOT_NAME = "n0:loginResponse";

    @Element(required = false)
    @Path("Body/loginResponse")
    private String loginReturn;

    public String getLoginReturn() {
        return loginReturn;
    }

    public void setLoginReturn(String loginReturn) {
        this.loginReturn = loginReturn;
    }
}