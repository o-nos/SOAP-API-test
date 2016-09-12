package com.snos.soaplibtest.soap;

import com.snos.soaplibtest.soap.emailsignup.SignInWithEmailRequestEnvelope;
import com.snos.soaplibtest.soap.emailsignup.SignUpWithEmailResponse;
import com.snos.soaplibtest.soap.login.LoginRequestEnvelope;
import com.snos.soaplibtest.soap.login.LoginResponse;
import com.snos.soaplibtest.soap.signin.SignInRequestEnvelope;
import com.snos.soaplibtest.soap.signin.SignInResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * All operations use the same endpoint and only RequestBody and Callback response type are changed
 */

public interface MagentoApi {

    String API_V2_SOAP = "/api/v2_soap";

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })

    @POST(API_V2_SOAP)
    void requestLogin(@Body LoginRequestEnvelope body, Callback<LoginResponse> cb);

    @POST(API_V2_SOAP)
    void requestSignIn(@Body SignInRequestEnvelope body, Callback<SignInResponse> cb);

    @POST(API_V2_SOAP)
    void requestSignUpWithEmail(@Body SignInWithEmailRequestEnvelope body, Callback<SignUpWithEmailResponse> cb);

}
