package com.mkyong.validators;

import com.mkyong.web.model.SearchCriteria;
import com.mkyong.web.model.User;

import java.util.List;

/**
 * Created by s.golitsyn on 12.01.2018
 */

public interface Validator {
     List<User> validateData(SearchCriteria search);
}
