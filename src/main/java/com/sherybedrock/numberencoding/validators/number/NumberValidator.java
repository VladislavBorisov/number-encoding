package com.bipinet.numberencoding.validators.number;

public interface NumberValidator {
    /**
     * Validates if the passed in number is valid.
     * @param number {@link String} number to be validated.
     * @return tru if valid, false otherwise.
     */
    boolean isValid(String number);
}
