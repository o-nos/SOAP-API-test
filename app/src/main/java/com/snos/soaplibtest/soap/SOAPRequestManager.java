package com.snos.soaplibtest.soap;

import com.snos.soaplibtest.soap.emailsignup.SignInWithEmailRequestEnvelope;
import com.snos.soaplibtest.soap.emailsignup.SignUpWithEmailRequest;
import com.snos.soaplibtest.soap.emailsignup.SignUpWithEmailRequestBody;
import com.snos.soaplibtest.soap.emailsignup.SignUpWithEmailResponse;
import com.snos.soaplibtest.soap.login.LoginRequest;
import com.snos.soaplibtest.soap.login.LoginRequestBody;
import com.snos.soaplibtest.soap.login.LoginRequestEnvelope;
import com.snos.soaplibtest.soap.login.LoginResponse;
import com.snos.soaplibtest.soap.signin.SignInRequest;
import com.snos.soaplibtest.soap.signin.SignInRequestBody;
import com.snos.soaplibtest.soap.signin.SignInRequestEnvelope;
import com.snos.soaplibtest.soap.signin.SignInResponse;
import com.squareup.okhttp.OkHttpClient;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by snos on 12.09.16.
 */
public class SOAPRequestManager {

    private static String mEndpoint = "http://dev.thefirefly.com/index.php/api/v2_soap/?wsdl";

    public static void retrofitLoginRequest(String username, String apiKey, Callback<LoginResponse> loginResponseCallback) {

        LoginRequest login = new LoginRequest();
        login.setUsername(username);
        login.setApiKey(apiKey);

        LoginRequestBody body = new LoginRequestBody();
        body.setObject(login);
        LoginRequestEnvelope request = new LoginRequestEnvelope();
        request.setBody(body);

        RestAdapter restAdapter = getRestAdapter();
        MagentoApi api = restAdapter.create(MagentoApi.class);

        api.requestLogin(request, loginResponseCallback);
    }

    public static void retrofitSignInRequest(String sessionId, String mobileSecret, Callback<SignInResponse> signInResponseCallback) {

        SignInRequest signInRequest = new SignInRequest(sessionId, "aa@example.com", "123123", mobileSecret);

        SignInRequestBody body = new SignInRequestBody();
        body.setObject(signInRequest);
        SignInRequestEnvelope request = new SignInRequestEnvelope();
        request.setBody(body);

        RestAdapter restAdapter = getRestAdapter();
        MagentoApi api = restAdapter.create(MagentoApi.class);

        api.requestSignIn(request, signInResponseCallback);
    }

    public static void retrofitSignUpWithEmailRequest(String sessionId, String mobileSecret, Callback<SignUpWithEmailResponse> signUpCallback) {

        SignUpWithEmailRequest signUpWithEmailRequest = new SignUpWithEmailRequest(sessionId, "aa@example.com", mobileSecret);

        SignUpWithEmailRequestBody requestBody = new SignUpWithEmailRequestBody();
        requestBody.setObject(signUpWithEmailRequest);
        SignInWithEmailRequestEnvelope request = new SignInWithEmailRequestEnvelope();
        request.setBody(requestBody);

        RestAdapter restAdapter = getRestAdapter();
        MagentoApi api = restAdapter.create(MagentoApi.class);

        api.requestSignUpWithEmail(request, signUpCallback);

    }

    private static RestAdapter getRestAdapter() {
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);

        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(mEndpoint)
                .setClient(new OkClient(okHttpClient))
                .setConverter(new SimpleXMLConverter(serializer))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter;
    }



}
