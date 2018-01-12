package com.mkyong.validators;

import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by s.golitsyn on 12.01.2018
 */

public class MainValidator implements Validator {

    private static final String PLUTO1 = "PLUTO1", PLUTO2 = "PLUTO2";

    private static List<User> result = new ArrayList<>();

    @Override
    public List<User> validateData(SearchCriteria search) {
        //todo migrate all in new class validator
        validateDate(search);
        validateWeekend(search);
        validateCustomers(search);
        return result;
    }

    private void validateDate(SearchCriteria search) {
        Date valueDate = search.getValueDate();
        Date tradeDate = search.getTradeDate();
        if (valueDate == null || tradeDate == null) {
            result.add(new User("search.getCustomer()", "search.getCcyPair()", "Value date or trade date cannot be null"));
        } else if (valueDate.before(tradeDate))
            result.add(new User(search.getCustomer(), search.getCcyPair(), "Value date cannot be before trade date"));
    }

    private void validateWeekend(SearchCriteria search) {
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.set(2017, Calendar.JULY, 18);
        Calendar valueDate = GregorianCalendar.getInstance();
        if (search.getValueDate() == null) {
            result.add(new User(search.getCustomer(), search.getCcyPair(), "Value date cannot be empty "));
        }else {
            valueDate.setTime(search.getValueDate());
            if (isSaturdayOrSunday(startDate)) {
                result.add(new User(search.getCustomer(), search.getCcyPair(), "Value date cannot fall on weekend or non-working day for currency "));
            }
        }
    }

    private void validateCustomers(SearchCriteria search) {
        String customer = search.getCustomer();
        if (!PLUTO1.equals(customer) && !PLUTO2.equals(customer))
            new User(search.getCustomer()
                    , search.getCcyPair()
                    , "counterparty is one of the supported ones: " + PLUTO1 + ", " + PLUTO2);

    }

    private static boolean isSaturdayOrSunday(Calendar gc) {
        return (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || gc.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }
}
