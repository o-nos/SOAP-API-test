package com.snos.soaplibtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snos.soaplibtest.soap.Login;
import com.snos.soaplibtest.soap.LoginResponse;
import com.snos.soaplibtest.soap.MagentoApi;
import com.snos.soaplibtest.soap.RequestEnvelope;
import com.snos.soaplibtest.soap.RequestLogInBody;
import com.snos.soaplibtest.soap.SignInRequest;
import com.snos.soaplibtest.soap.SignInRequestBody;
import com.snos.soaplibtest.soap.SignInRequestEnvelope;
import com.snos.soaplibtest.soap.SignInResponse;
import com.squareup.okhttp.OkHttpClient;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.SimpleXMLConverter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String MAGENTO_NAMESPACE = "urn:Magento";
    private static final String MAGENTO_URL = "http://dev.thefirefly.com/index.php/api/v2_soap/index/";
    private static final String MAGENTO_LOGIN_METHOD_NAME = "login";
    private static final String MAGENTO_SIGNUP_METHOD_NAME = "fireflyCustomerSignIn";

    private Button mSendLoginRequestButton;
    private Button mSendRequestButton;
    private Button mSendSignInRequestButton;
    private TextView mLoginResponseText;
    private TextView mResponseText;
    private TextView mSignInResponseText;

    private String mSessionId;
    private String mUsername = "farapp";
    private String mApiKey = "qruo5rZkLyu22";
    private String mMobileSecret = "aa123123";
    private String mEndpoint = "http://dev.thefirefly.com/index.php/api/v2_soap/?wsdl";

    private Callback<LoginResponse> loginResponseCallback;
    private Callback<SignInResponse> signInResponseCallback;

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
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.send_login_request:
                new LogInTask().execute();
                break;
            case R.id.send_request:
                retrofitLoginRequest();
                break;
            case R.id.send_signin_request:
                // $result = $session->fireflyCustomerSignIn($sessionId, 'aa@example.com', '123123', 'aa123123');
                retrofitSignInRequest();
                break;
        }
    }

    private void retrofitSignInRequest() {
        //input message
        SignInRequest signInRequest = new SignInRequest(mSessionId, "aa@example.com", "123123", mMobileSecret);

        SignInRequestBody body = new SignInRequestBody();
        body.setObject(signInRequest);
        SignInRequestEnvelope request = new SignInRequestEnvelope();
        request.setBody(body);

        RestAdapter restAdapter = getRestAdapter();
        MagentoApi api = restAdapter.create(MagentoApi.class);

        api.requestSignIn(request, signInResponseCallback);
    }

    private void retrofitLoginRequest() {
        //input message
        Login login = new Login();
        login.setUsername(mUsername);
        login.setApiKey(mApiKey);

        RequestLogInBody body = new RequestLogInBody();
        body.setObject(login);
        RequestEnvelope request = new RequestEnvelope();
        request.setBody(body);

        RestAdapter restAdapter = getRestAdapter();
        MagentoApi api = restAdapter.create(MagentoApi.class);

        api.requestLoginOp(request, loginResponseCallback);
    }

    public RestAdapter getRestAdapter() {
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


