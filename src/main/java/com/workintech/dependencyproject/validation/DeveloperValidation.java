package com.workintech.dependencyproject.validation;

import com.workintech.dependencyproject.mapping.DeveloperResponse;

public class DeveloperValidation {
    public static boolean isIdValid (int id) {
        return id > 0;
    }
}
