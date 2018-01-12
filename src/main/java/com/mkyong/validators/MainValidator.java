package com.mkyong.validators;

import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.Trade;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by s.golitsyn on 12.01.2018
 */

// todo add list for errors
public class MainValidator implements Validator {
    private static final String PLUTO1 = "PLUTO1", PLUTO2 = "PLUTO2";

    private static SearchCriteria search;
    private static List<Trade> errTrade = new ArrayList<>();

    public MainValidator() {
    }

    public MainValidator(SearchCriteria search) {
        MainValidator.search = search;
    }

    public List<Trade> getErrTrade() {
        return errTrade;
    }

    public boolean isValidSearchCriteria() {
        boolean usFlag = false;
        try {
            if (search == null) {
                return false;
            }
            Class searchClass = search.getClass();
            for (Object field : searchClass.getDeclaredFields()) {
                Field f = (Field) field;
                usFlag = checkUs(f);
                if (usFlag) {
                    continue;
                }
                validField(f);
            }
            // if I correctly understood that US fields may or may not be
            if (usFlag) {
                validUsFields();
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            errTrade.add(new Trade(null, null, e.getMessage()));
            e.printStackTrace();
        }
        return errTrade.isEmpty();
    }

    private void validField(Field f) throws IllegalAccessException {
        f.setAccessible(true);
        Object value = f.get(search);
        if (value == null) {
            errTrade.add(new Trade(search.getCustomer(), search.getCcyPair(), f.getName() + " cannot be null"));
        }
    }

    private boolean checkUs(Field f) {
        return f.getName().matches("excerciseStartDate|expiryDate|premium");
    }

    private void validUsFields() throws NoSuchFieldException, IllegalAccessException {
        Class searchClass = search.getClass();
        Field excerciseStartDate = searchClass.getField("excerciseStartDate");
        excerciseStartDate.setAccessible(true);
        Field expiryDate = searchClass.getField("expiryDate");
        expiryDate.setAccessible(true);
        Field premium = searchClass.getField("premium");
        premium.setAccessible(true);
        if (excerciseStartDate.get(search) == null
                || expiryDate.get(search) == null
                || premium.get(search) == null) {
            errTrade.add(new Trade(null, null, "All US fields must be filled!"));
        }
    }


    @Override
    public List<Trade> validateData() {
        //todo migrate all in new class validator
        validateDate();
        validateWeekend();
        validateHolidays();
        if (search.getExcerciseStartDate() != null) {
            validateUsDates();
        }
        validateCustomers();
        return errTrade;
    }

    private void validateDate() {
        Date valueDate = search.getValueDate();
        Date tradeDate = search.getTradeDate();
        if (valueDate.before(tradeDate)) {
            errTrade.add(new Trade(search.getCustomer(), search.getCcyPair(), "Value date cannot be before trade date"));
        }
    }

    private void validateWeekend() {
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.set(2017, Calendar.JULY, 18);
        Calendar valueDate = GregorianCalendar.getInstance();
        valueDate.setTime(search.getValueDate());
        if (isSaturdayOrSunday(startDate) || validateHolidays()) {
            errTrade.add(new Trade(search.getCustomer(), search.getCcyPair(), "Value date cannot fall on weekend or non-working day for currency "));
//            }
        }
    }


    // maybe I did something wrong understood in the condition. but I take delivery date = ValueDate
    private void validateUsDates() {
        Date trade = search.getTradeDate();
        Date excercise = search.getExcerciseStartDate();
        Date value = search.getValueDate();
        Date premium = search.getPremium();
        if (excercise.before(trade) || excercise.after(value)) {
            errTrade.add(new Trade(search.getCustomer(), search.getCcyPair(), "Excercise start date cannot be up to trade"));
        }
        if (value.after(trade) || premium.after(trade)) {
            errTrade.add(new Trade(search.getCustomer(), search.getCcyPair(), "Excercise start date cannot be up to trade"));
        }

    }

    private void validateCustomers() {
        String customer = search.getCustomer();
        if (!PLUTO1.equals(customer) && !PLUTO2.equals(customer))
            new Trade(search.getCustomer()
                    , search.getCcyPair()
                    , "counterparty is one of the supported ones: " + PLUTO1 + ", " + PLUTO2);

    }

    private boolean isSaturdayOrSunday(Calendar gc) {
        return (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || gc.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }

    private boolean validateHolidays() {
        for (GregorianCalendar holiday : Holidays.HOLIDAYS) {
            if (holiday.equals(search.getValueDate())) {
                return true;
            }
        }
        return false;
    }
}
