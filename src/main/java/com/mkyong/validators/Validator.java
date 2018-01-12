package com.mkyong.validators;

import com.mkyong.web.model.Trade;

import java.util.List;

/**
 * Created by s.golitsyn on 12.01.2018
 */

public interface Validator {
     boolean isValidSearchCriteria();
     List<Trade> validateData();
}
