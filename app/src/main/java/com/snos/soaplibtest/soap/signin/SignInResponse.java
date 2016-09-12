package com.snos.soaplibtest.soap.signin;

import com.snos.soaplibtest.FireflyCustomer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 08.09.16.
 */
@Root(name = SignInResponse.ROOT_NAME, strict = false)
public class SignInResponse {
    public static final String ROOT_NAME = "n0:fireflyCustomerSignInResponse";

    @Element(name = "customerData",required = false)
    @Path("Body/fireflyCustomerSignInResponse")
    private FireflyCustomer result;

    public FireflyCustomer getResult() {
        return result;
    }

    public void setResult(FireflyCustomer result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    @Element(name = "Value", required = false)
    @Path("Body/Fault/Code")
    private String errorCode;

    @Element(name = "Text", required = false)
    @Path("Body/Fault/Reason")
    private String errorText;


}
