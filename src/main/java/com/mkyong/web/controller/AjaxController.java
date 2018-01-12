package com.mkyong.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.validators.MainValidator;
import com.mkyong.web.jsonview.Views;
import com.mkyong.web.model.AjaxResponseBody;
import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.Trade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.List;

@Controller
public class AjaxController {

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
//    @RequestMapping(value = "/search/api/getSearchResult",
    @RequestMapping(value = "/welcome",
            method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {

        AjaxResponseBody result = new AjaxResponseBody();
        MainValidator mainValidator = new MainValidator(search);
        if (mainValidator.isValidSearchCriteria()) {
            List<Trade> users = mainValidator.validateData();

            if (users.size() > 0) {
                result.setCode("204");
                result.setMsg("Some errors found during validation!");
                result.setResult(users);
            } else {
                result.setCode("200");
                result.setMsg("All fine!");
            }

        } else {
            result.setCode("400");
            result.setMsg("Trade isn't valid!");
            result.setResult(mainValidator.getErrTrade());
        }

        //AjaxResponseBody will be converted into json format and send back to client.
        return result;

    }

}
