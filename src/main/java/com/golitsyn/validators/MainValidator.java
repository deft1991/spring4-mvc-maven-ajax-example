package com.golitsyn.validators;

import com.golitsyn.web.model.SearchCriteria;
import com.golitsyn.web.model.Trade;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by s.golitsyn on 12.01.2018
 */

// todo add list for errors
public class MainValidator implements Validator {
    private static final String PLUTO1 = "PLUTO1", PLUTO2 = "PLUTO2";

    private SearchCriteria search;
    private static List<Trade> errTrade = new ArrayList<>();
    private Trade curTrade = new Trade();
    private boolean usFlag = false;
    private boolean isStyle = true;
    private static String ENTITY = "CS Zurich";
    private static String STYLE_AMERICAN = "AMERICAN";
    private static String STYLE_EUROPEAN = "EUROPEAN";

    public MainValidator() {
    }

    public MainValidator(SearchCriteria search) {
        this.search = search;
        this.curTrade = new Trade(search.getCustomer(), search.getCcyPair());
    }

    public List<Trade> getErrTrade() {
        return errTrade;
    }

    public boolean isValidSearchCriteria() {
        try {
            if (search == null) {
                return false;
            }
            Class searchClass = search.getClass();
            usFlag = checkStyleAndUs();
            for (Object field : searchClass.getDeclaredFields()) {
                Field f = (Field) field;
                if ((!usFlag && skipUs(f.getName()))
                        || (isStyle && skipStyleFields(f))
                        || (!isStyle && skipNotStyleFields(f))) {
                    continue;
                }
                validField(f);
            }
//          if I correctly understood that US fields may or may not be
            if (usFlag) {
                validUsFields();
            }
            if (isStyle) {
                validStyleFields();
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            setTradeErr(e.getMessage());
            e.printStackTrace();
        }
        if (!curTrade.getError().isEmpty()) {
            errTrade.add(curTrade);
        }
        return errTrade.isEmpty();
    }

    private boolean skipUs(String f) {
        return f.matches("valueDate|excerciseStartDate|expiryDate|deliveryDate|premium|payCcy|premiumCcy|premiumType|premiumDate");
    }

    private boolean skipNotStyleFields(Field f) {
        return f.getName().matches("style|strategy");
    }

    private boolean skipStyleFields(Field f) {
        return f.getName().matches("valueDate");
    }

    private void validField(Field f) throws IllegalAccessException {
        f.setAccessible(true);
        Object value = f.get(search);
        if (value == null) {
            setTradeErr(f.getName() + " cannot be null");
        }
    }

    private boolean checkStyleAndUs() throws IllegalAccessException {
        Class searchClass = search.getClass();
        Field style;
        try {
            style = searchClass.getDeclaredField("style");
            style.setAccessible(true);
        } catch (NoSuchFieldException e) {
            isStyle = false;
            return false;
        }
        if (style.get(search) == null) {
            isStyle = false;
        }
        else if (STYLE_AMERICAN.equals(style.get(search))) {
            return true;
        } else if (STYLE_EUROPEAN.equals(style.get(search))) {
            return false;
        }
        return false;
    }

    // todo put into a separate class us fields
    private void validUsFields() throws NoSuchFieldException, IllegalAccessException {
        Class searchClass = search.getClass();
        Field excerciseStartDate = searchClass.getDeclaredField("excerciseStartDate");
        excerciseStartDate.setAccessible(true);
        Field expiryDate = searchClass.getDeclaredField("expiryDate");
        expiryDate.setAccessible(true);
        Field premium = searchClass.getDeclaredField("premium");
        premium.setAccessible(true);
        Field deliveryDate = searchClass.getDeclaredField("deliveryDate");
        deliveryDate.setAccessible(true);
        Field payCcy = searchClass.getDeclaredField("payCcy");
        payCcy.setAccessible(true);
        Field premiumCcy = searchClass.getDeclaredField("premiumCcy");
        premiumCcy.setAccessible(true);
        Field premiumType = searchClass.getDeclaredField("premiumType");
        premiumType.setAccessible(true);
        Field premiumDate = searchClass.getDeclaredField("premiumDate");
        premiumDate.setAccessible(true);
        if (excerciseStartDate.get(search) == null
                || expiryDate.get(search) == null
                || premium.get(search) == null
                || deliveryDate.get(search) == null
                || payCcy.get(search) == null
                || premiumCcy.get(search) == null
                || premiumType.get(search) == null
                || premiumDate.get(search) == null) {
            setTradeErr("All US fields must be filled!");
        }
    }

    private void validStyleFields() throws NoSuchFieldException, IllegalAccessException {
        Class searchClass = search.getClass();
        Field strategy = searchClass.getDeclaredField("strategy");
        strategy.setAccessible(true);
        Field style = searchClass.getDeclaredField("style");
        style.setAccessible(true);
        if (strategy.get(search) == null || style.get(search) == null) {
            setTradeErr("strategy and style must be filled!");
        }
    }


    @Override
    public List<Trade> validateData() {
        if (!isStyle) {
            validateDate();
            validateWeekend();
            validateHolidays();
        }
        if (usFlag) {
            validateUsDates();
        }
        validateCustomers();
        validateEntity();
        validateType();
        if (!curTrade.getError().isEmpty()) {
            errTrade.add(curTrade);
        }
        return errTrade;
    }

    private void validateDate() {
        Date valueDate = search.getValueDate();
        Date tradeDate = search.getTradeDate();
        if (valueDate.before(tradeDate)) {
            setTradeErr("Value date cannot be before trade date");
        }
    }

    private void validateWeekend() {
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.set(2017, Calendar.JULY, 18);
        Calendar valueDate = GregorianCalendar.getInstance();
        valueDate.setTime(search.getValueDate());
        if (isSaturdayOrSunday(startDate) || validateHolidays()) {
            setTradeErr("Value date cannot fall on weekend or non-working day for currency ");
//            }
        }
    }

    // in test example i cant see ValueDate if using style
    private void validateUsDates() {
        Date premiumDate = search.getPremiumDate();
        Date excercise = search.getExcerciseStartDate();
//        Date value = search.getValueDate();
        Date delivery = search.getDeliveryDate();
//        if (excercise.before(trade) || excercise.after(value)) {
//            setTradeErr("Excercise start date cannot be up to trade");
//        }
        if (delivery.after(premiumDate) || delivery.after(excercise)) {
            setTradeErr("Expiry date and premium date shall be before delivery date");
        }
    }

    private void validateCustomers() {
        String customer = search.getCustomer();
        if (!PLUTO1.equals(customer) && !PLUTO2.equals(customer)) {
            setTradeErr("counterparty is one of the supported ones: " + PLUTO1 + ", " + PLUTO2);
        }
    }

    private void validateEntity() {
        if (!ENTITY.equals(search.getLegalEntity())) {
            setTradeErr("Legal entity must be only: " + ENTITY);
        }
    }

    // I did not understand a little about validateType
    private void validateType() {
        if (usFlag) {
            if (!Currency.getInstance("USD")
                    .getSymbol(Locale.US)
                    .equals(search.getPremiumType().substring(0, 1))) {
                setTradeErr("Legal type not correct: " + search.getPremiumType().substring(0, 1));
            }
        }
    }

    private boolean isSaturdayOrSunday(Calendar gc) {
        return (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || gc.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }

    private boolean validateHolidays() {
        for (GregorianCalendar holiday : Holidays.HOLIDAYS) {
            if (holiday.getTime().equals(search.getValueDate())) {
                return true;
            }
        }
        return false;
    }

    private void setTradeErr(String error) {
        curTrade.setError(error);
    }
}
