package com.snos.soaplibtest.soap.emailsignup;

import com.snos.soaplibtest.soap.signin.SignInRequestBody;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 12.09.16.
 */
@Root(name = "soap12:Envelope")
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
        @Namespace(prefix = "soap12", reference = "http://www.w3.org/2003/05/soap-envelope")
})
public class SignInWithEmailRequestEnvelope {

    @Element(name = "soap12:Body")
    private SignUpWithEmailRequestBody body;

    public SignUpWithEmailRequestBody getBody() {
        return body;
    }

    public void setBody(SignUpWithEmailRequestBody body) {
        this.body = body;
    }

}
