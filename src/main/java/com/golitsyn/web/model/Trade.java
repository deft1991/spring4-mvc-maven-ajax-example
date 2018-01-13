package com.golitsyn.web.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.golitsyn.web.jsonview.Views;

import java.util.ArrayList;
import java.util.List;

/*Trade object for search function.
 Fields which annotated with @JsonView will be displayed.*/
public class Trade {

    @JsonView(Views.Public.class)
    String customer;
    @JsonView(Views.Public.class)
    String ccyPair;
    @JsonView(Views.Public.class)
    List<String> error = new ArrayList<>();

    public Trade() {
    }

    public Trade(String customer, String email) {
        super();
        this.customer = customer;
        this.ccyPair = email;
    }

    public Trade(String customer, String email, String error) {
        super();
        this.customer = customer;
        this.ccyPair = email;
        this.error.add(error);
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCcyPair() {
        return ccyPair;
    }

    public void setCcyPair(String ccyPair) {
        this.ccyPair = ccyPair;
    }

    public List<String > getError() {
        return error;
    }

    public void setError(String error) {
        this.error.add(error);
    }

    public static Trade createTrade(String customer, String ccyPair, String error) {
        return new Trade(customer, ccyPair, error);
    }

    @Override
    public String toString() {
        return "{" +
                "customer='" + customer + '\'' +
                ", ccyPair='" + ccyPair + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
