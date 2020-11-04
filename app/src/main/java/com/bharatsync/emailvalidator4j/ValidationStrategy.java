package com.bharatsync.emailvalidator4j;

import com.bharatsync.emailvalidator4j.parser.Email;

public interface ValidationStrategy {
    Boolean isValid(String email, Email parser);
}
