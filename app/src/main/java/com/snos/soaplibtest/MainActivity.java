package com.snos.soaplibtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snos.soaplibtest.soap.FireflyCustomer;
import com.snos.soaplibtest.soap.SOAPRequestManager;
import com.snos.soaplibtest.soap.emailsignup.SignUpWithEmailResponse;
import com.snos.soaplibtest.soap.login.LoginResponse;
import com.snos.soaplibtest.soap.signin.SignInResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String MAGENTO_NAMESPACE = "urn:Magento";
    private static final String MAGENTO_URL = "http://dev.thefirefly.com/index.php/api/v2_soap/index/";
    private static final String MAGENTO_LOGIN_METHOD_NAME = "login";
    private static final String MAGENTO_SIGNUP_METHOD_NAME = "fireflyCustomerSignIn";
    private static final String SESSION_EXPIRED = "5";

    private Button mSendLoginRequestButton;
    private Button mSendRequestButton;
    private Button mSendSignInRequestButton;
    private Button mSendSignUpWithEmailRequestButton;
    private TextView mLoginResponseText;
    private TextView mResponseText;
    private TextView mSignInResponseText;
    private TextView mSignUpWithEmailResponseText;

    private String mSessionId;

    private Callback<LoginResponse> loginResponseCallback;
    private Callback<SignInResponse> signInResponseCallback;
    private Callback<SignUpWithEmailResponse> signUpWithEmailResponseCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSendLoginRequestButton = (Button) findViewById(R.id.send_login_request);
        mLoginResponseText = (TextView) findViewById(R.id.login_response_text);
        mSendLoginRequestButton.setOnClickListener(this);

        mSendRequestButton = (Button) findViewById(R.id.send_request);
        mResponseText = (TextView) findViewById(R.id.response_text);
        mSendRequestButton.setOnClickListener(this);

        mSendSignInRequestButton = (Button) findViewById(R.id.send_signin_request);
        mSignInResponseText = (TextView) findViewById(R.id.signin_response_text);
        mSendSignInRequestButton.setOnClickListener(this);

        mSendSignUpWithEmailRequestButton = (Button) findViewById(R.id.send_signup_with_email_request);
        mSignUpWithEmailResponseText = (TextView) findViewById(R.id.signup_with_email_response_text);
        mSendSignUpWithEmailRequestButton.setOnClickListener(this);

        loginResponseCallback = new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse respEnv, Response response) {
                if (respEnv.getErrorCode() != null && !respEnv.getErrorCode().isEmpty()) {
                    Log.e(TAG, "api error status code: " + respEnv.getErrorCode() + " " + respEnv.getErrorText());

                } else {
                    String resp = respEnv.getLoginReturn();
                    Log.i(TAG, "success! " + resp);
                    mResponseText.setText(resp);
                    mSessionId = resp;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "failure! " + error.toString());
            }
        };

        signInResponseCallback = new Callback<SignInResponse>() {
            @Override
            public void success(SignInResponse respEnv, Response response) {
                if (respEnv.getErrorCode() != null && !respEnv.getErrorCode().isEmpty()) {
                    Log.e(TAG, "api error status code: " + respEnv.getErrorCode() + " " + respEnv.getErrorText());
                    if (respEnv.getErrorCode().equals(SESSION_EXPIRED)){
                        SOAPRequestManager.retrofitLoginRequest(loginResponseCallback);
                    }
                } else {
                    FireflyCustomer customer = respEnv.getResult();
                    Log.i(TAG, "success! " + customer);
                    mSignInResponseText.setText(customer.getEmail());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error occurred. Status code: " + error.getResponse().getStatus());
                Log.e(TAG, error.getCause().getMessage());
            }
        };

        signUpWithEmailResponseCallback = new Callback<SignUpWithEmailResponse>() {
            @Override
            public void success(SignUpWithEmailResponse respEnv, Response response) {
                if (respEnv.getErrorCode() != null && !respEnv.getErrorCode().isEmpty()) {
                    Log.e(TAG, "api error status code: " + respEnv.getErrorCode() + " " + respEnv.getErrorText());
                } else {
                    Log.i(TAG, "success! " + respEnv.getResult());
                    mSignInResponseText.setText(respEnv.getResult());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error occurred. Status code: " + error.getResponse().getStatus());
                Log.e(TAG, error.getCause().getMessage());
            }
        };
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.send_login_request:
                // empty
                break;
            case R.id.send_request:

                break;
            case R.id.send_signin_request:
                // $result = $session->fireflyCustomerSignIn($sessionId, 'aa@example.com', '123123', 'aa123123');
                SOAPRequestManager.retrofitSignInRequest(mSessionId, signInResponseCallback);
                break;
            case R.id.send_signup_with_email_request:
//                fireflyCustomerSignUpWithEmail(string sessionId, string email, string mobileSecret)
                SOAPRequestManager.retrofitSignUpWithEmailRequest(mSessionId, signUpWithEmailResponseCallback);
                break;
        }
    }

}


