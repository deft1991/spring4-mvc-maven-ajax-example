package com.golitsyn.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.golitsyn.validators.MainValidator;
import com.golitsyn.web.jsonview.Views;
import com.golitsyn.web.model.AjaxResponseBody;
import com.golitsyn.web.model.SearchCriteria;
import com.golitsyn.web.model.Trade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AjaxController {

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, limited the json data display to client.
    @JsonView(Views.Public.class)
    @RequestMapping(value = "/search/api/getSearchResult",
//    @RequestMapping(value = "/welcome",
            method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria[] search) {

        AjaxResponseBody result = new AjaxResponseBody();
        List<Trade> errTrades = new ArrayList<>();
        for (SearchCriteria searchCriteria : search) {
            MainValidator mainValidator = new MainValidator(searchCriteria);

            if (mainValidator.isValidSearchCriteria()) {
                errTrades.addAll(mainValidator.validateData());
            } else {
                result.setCode("400");
                result.setMsg("Trade isn't valid!");
                result.setResult(mainValidator.getErrTrade());
            }
        }
        if (errTrades.size() > 0) {
            result.setCode("204");
            result.setMsg("Some errors found during validation!");
            result.setResult(errTrades);
        } else {
            result.setCode("200");
            result.setMsg("All fine!");
        }

        //AjaxResponseBody will be converted into json format and send back to client.
        return result;

    }

}
