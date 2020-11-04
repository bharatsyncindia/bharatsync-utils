package com.bharatsync.emailvalidator4j.validator;

import com.bharatsync.emailvalidator4j.ValidationStrategy;
import com.bharatsync.emailvalidator4j.parser.Email;

public class WarningsNotAllowed implements ValidationStrategy {
    @Override
    public Boolean isValid(String email, Email parser) {
        return parser.getWarnings().size() == 0;
    }
}
