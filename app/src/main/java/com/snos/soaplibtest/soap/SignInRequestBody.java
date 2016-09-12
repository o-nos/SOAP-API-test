package com.snos.soaplibtest.soap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 08.09.16.
 */
@Root(name = "n0:body", strict = false)
@NamespaceList({
        @Namespace(reference = "urn:Magento", prefix = "n0")
})
public class SignInRequestBody {

    @Element(name = "n0:fireflyCustomerSignIn", required = false)
    private Object object;

    public SignInRequestBody() {}

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
