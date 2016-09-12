package com.snos.soaplibtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snos.soaplibtest.soap.SOAPRequestManager;
import com.snos.soaplibtest.soap.emailsignup.SignUpWithEmailResponse;
import com.snos.soaplibtest.soap.login.LoginResponse;
import com.snos.soaplibtest.soap.signin.SignInResponse;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String MAGENTO_NAMESPACE = "urn:Magento";
    private static final String MAGENTO_URL = "http://dev.thefirefly.com/index.php/api/v2_soap/index/";
    private static final String MAGENTO_LOGIN_METHOD_NAME = "login";
    private static final String MAGENTO_SIGNUP_METHOD_NAME = "fireflyCustomerSignIn";

    private Button mSendLoginRequestButton;
    private Button mSendRequestButton;
    private Button mSendSignInRequestButton;
    private Button mSendSignUpWithEmailRequestButton;
    private TextView mLoginResponseText;
    private TextView mResponseText;
    private TextView mSignInResponseText;
    private TextView mSignUpWithEmailResponseText;

    private String mSessionId;
    private String mUsername = "farapp";
    private String mApiKey = "qruo5rZkLyu22";
    private String mMobileSecret = "aa123123";


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
                String resp = respEnv.getLoginReturn();
                Log.i(TAG, "success! " + resp);
                mResponseText.setText(resp);
                mSessionId = resp;
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
                new LogInTask().execute();
                break;
            case R.id.send_request:
                SOAPRequestManager.retrofitLoginRequest(mUsername, mApiKey, loginResponseCallback);
                break;
            case R.id.send_signin_request:
                // $result = $session->fireflyCustomerSignIn($sessionId, 'aa@example.com', '123123', 'aa123123');
                SOAPRequestManager.retrofitSignInRequest(mSessionId, mMobileSecret, signInResponseCallback);
                break;
            case R.id.send_signup_with_email_request:
//                fireflyCustomerSignUpWithEmail(string sessionId, string email, string mobileSecret)
                SOAPRequestManager.retrofitSignUpWithEmailRequest(mSessionId, mMobileSecret, signUpWithEmailResponseCallback);
                break;
        }
    }


//  Нище код, котрий без ретрофіту працює. Тригериться по першому баттону.


    public static String getLogin(String username, String apiKey) {

        String loginResponse = "no.";
        //Create request
        SoapObject request = new SoapObject(MAGENTO_NAMESPACE, MAGENTO_LOGIN_METHOD_NAME);
        //Property which holds input parameters
        request.addProperty("username", username);
        request.addProperty("apiKey", apiKey);

        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = false;
        envelope.xsd = SoapSerializationEnvelope.XSD;
        envelope.enc = SoapSerializationEnvelope.ENC;
        //Set output SOAP object
        envelope.setOutputSoapObject(request);
        //Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(MAGENTO_URL);
        androidHttpTransport.debug = true;
        try {
            //Involve web service (synchronous)
            androidHttpTransport.call("", envelope);
            //Get the response
            loginResponse = envelope.getResponse().toString();
        } catch (Exception e) {
            //it's nasty to catch Exception. Catch some specific like IOException
            e.printStackTrace();
        }

        return loginResponse;
    }

    class LogInTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            return getLogin(mUsername, mApiKey);
        }

        @Override
        protected void onPostExecute(String s) {
            mLoginResponseText.setText(s);
            mSessionId = s;
        }
    }

}


