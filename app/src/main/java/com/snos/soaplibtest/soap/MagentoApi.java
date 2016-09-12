package com.snos.soaplibtest.soap;

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
    void requestLoginOp(@Body RequestEnvelope body, Callback<LoginResponse> cb);

    @POST(API_V2_SOAP)
    void requestSignIn(@Body SignInRequestEnvelope body, Callback<SignInResponse> cb);

}
