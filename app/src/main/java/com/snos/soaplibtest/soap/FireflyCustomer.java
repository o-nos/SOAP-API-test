package com.snos.soaplibtest.soap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by snos on 12.09.16.
 */
@Root(name = "fireflyCustomerEntity", strict = false)
public class FireflyCustomer {

    @Element(name = "first_name", required = false)
    private String firstName;

    @Element(name = "last_name", required = false)
    private String lastName;

    @Element(name = "year_of_birthday", required = false)
    private int bdayYear;

    @Element(required = false)
    private String email;

    @Element(required = false)
    private String city;

    @Element(required = false)
    private String state;

    @Element(required = false)
    private String zipcode;

    @Element(required = false)
    private String country;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBdayYear() {
        return bdayYear;
    }

    public void setBdayYear(int bdayYear) {
        this.bdayYear = bdayYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



}
