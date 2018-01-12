package com.mkyong.web.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.web.jsonview.Views;

/*Trade object for search function.
 Fields which annotated with @JsonView will be displayed.*/
public class Trade {

    @JsonView(Views.Public.class)
    String customer;
    @JsonView(Views.Public.class)
    String ccyPair;
    @JsonView(Views.Public.class)
    String error;

    public Trade() {
    }

    public Trade(String customer, String email, String error) {
        super();
        this.customer = customer;
        this.ccyPair = email;
        this.error = error;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
