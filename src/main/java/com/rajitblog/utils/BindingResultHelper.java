package com.rajitblog.utils;

import org.springframework.validation.BindingResult;

public class BindingResultHelper {
    public String getErrorMessage(String uri, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            return String.format("No errors found in binding result for uri: '{}'", uri);
        }

        return String.format("Errors in request binding for uri: '{}', errors: '{}'", uri,
                bindingResult.getAllErrors());
    }
}
