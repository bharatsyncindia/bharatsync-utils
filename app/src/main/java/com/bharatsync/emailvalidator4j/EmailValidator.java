package com.bharatsync.emailvalidator4j;

import android.text.TextUtils;

import com.bharatsync.emailvalidator4j.lexer.EmailLexer;
import com.bharatsync.emailvalidator4j.parser.Email;
import com.bharatsync.emailvalidator4j.parser.exception.InvalidEmail;

import java.util.Collections;
import java.util.List;


public final class EmailValidator {
    private final Email parser = new Email(new EmailLexer());
    private List<ValidationStrategy> validators = Collections.emptyList();

    public EmailValidator(List<ValidationStrategy> validators) {
        this.validators = validators;
    }

    public EmailValidator() {
    }

    public boolean isValid(String email) {
        boolean parserResult = true;
        try {
            this.parser.parse(email);
        } catch (InvalidEmail invalidEmail) {
            parserResult = false;
        }

        return parserResult && this.applyValidators(email);
    }

    private boolean applyValidators(String email) {
        boolean validatorsResult = true;
        for (ValidationStrategy validator : this.validators) {
            validatorsResult = validatorsResult && validator.isValid(email, this.parser);
        }
        //ravi changes here
        boolean b = validatorsResult;
        if (!TextUtils.isEmpty(email) && email.contains("@")) {
            String[] arr = email.split("@");
            if (arr != null && arr.length == 2) {
                if (!arr[1].contains(".")) {
                    b = false;
                }
            }
        }
        //end
        return b;
    }

    public boolean hasWarnings() {
        return !this.parser.getWarnings().isEmpty();
    }

    public List getWarnings() {
        return this.parser.getWarnings();
    }
}
