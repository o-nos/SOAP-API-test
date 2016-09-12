package com.snos.soaplibtest.soap.emailsignup;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 12.09.16.
 */
@Root(name = "n0:body", strict = false)
@NamespaceList({
        @Namespace(reference = "urn:Magento", prefix = "n0")
})
public class SignUpWithEmailRequestBody {

    @Element(name = "n0:fireflyCustomerSignUpWithEmail", required = false)
    private Object object;

    public SignUpWithEmailRequestBody() {}

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
