package com.snos.soaplibtest.soap.emailsignup;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 12.09.16.
 */
@Root(name = SignUpWithEmailResponse.ROOT_NAME, strict = false)
public class SignUpWithEmailResponse {

    public static final String ROOT_NAME = "n0:fireflyCustomerSignUpWithEmailResponse";

    @Element(name = "result", required = false)
    @Path("Body/fireflyCustomerSignUpWithEmailResponse")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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
