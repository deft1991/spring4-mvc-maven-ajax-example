package com.mkyong.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.web.jsonview.Views;
import com.mkyong.web.model.AjaxResponseBody;
import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;

@Controller
public class AjaxController {

    public static final String PLUTO1 =  "PLUTO1", PLUTO2 = "PLUTO2";
    static List<User> result;

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/search/api/getSearchResult",
            method = RequestMethod.POST)
    public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {

        AjaxResponseBody result = new AjaxResponseBody();

        if (isValidSearchCriteria(search)) {
            List<User> users = validateData(search);

            if (users.size() > 0) {
                result.setCode("204");
//                result.setMsg("");
                result.setResult(users);
            } else {
                result.setCode("200");
                result.setMsg("All fine!");
            }

        } else {
            result.setCode("400");
            result.setMsg("Search criteria is empty!");
        }

        //AjaxResponseBody will be converted into json format and send back to client.
        return result;

    }

    private boolean isValidSearchCriteria(SearchCriteria search) {

        boolean valid = true;

        if (search == null) {
            valid = false;
        }
// todo check all fields
//        if ((StringUtils.isEmpty(search.getCustomer())) && (StringUtils.isEmpty(search.getCcyPair()))) {
//            valid = false;
//        }

        return valid;
    }

    // todo validate all fields
    private List<User> validateData(SearchCriteria search) {

        List<User> result = new ArrayList<User>();
        //todo migrate all in new class validator
        validateDate(search);
        validateWeekend(search);
        validateCustomers(search);
        return result;
    }

    private void validateDate(SearchCriteria search) {
        if (search.getValueDate().before(search.getTradeDate()))
            result.add(new User(search.getCustomer(), search.getCcyPair(), "Value date cannot be before trade date"));
    }

    private void validateWeekend(SearchCriteria search) {
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.set(2017, 06, 18); // 23 MAY 2014
        Calendar valueDate = GregorianCalendar.getInstance();
        valueDate.setTime(search.getValueDate());
        if (isSaturdayOrSunday(startDate)) {
            result.add(new User(search.getCustomer(), search.getCcyPair(), "Value date cannot fall on weekend or non-working day for currency "));
        }
    }

    private void validateCustomers(SearchCriteria search) {
        String customer = search.getCustomer();
        if (!PLUTO1.equals(customer) && !PLUTO2.equals(customer))
            new User(search.getCustomer()
                    , search.getCcyPair()
                    , "counterparty is one of the supported ones: " + PLUTO1 + ", " + PLUTO2);

    }

    public static boolean isSaturdayOrSunday(Calendar gc) {
        return (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || gc.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }
}
