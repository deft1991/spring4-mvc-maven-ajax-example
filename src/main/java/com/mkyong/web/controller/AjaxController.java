package com.mkyong.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.mkyong.validators.MainValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.web.jsonview.Views;
import com.mkyong.web.model.AjaxResponseBody;
import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
//    @RequestMapping(value = "/search/api/getSearchResult",
    @RequestMapping(value = "/welcome",
            method = RequestMethod.POST)
    public @ResponseBody AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {

        AjaxResponseBody result = new AjaxResponseBody();
        MainValidator mainValidator = new MainValidator();
        if (isValidSearchCriteria(search)) {
            List<User> users = mainValidator.validateData(search);

            if (users.size() > 0) {
                result.setCode("204");
                result.setMsg("qqqqqqqqqqqqqqqqqq");
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
}
