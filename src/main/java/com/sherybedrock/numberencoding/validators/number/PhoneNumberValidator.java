package com.bipinet.numberencoding.validators.number;

/**
 * Implementation of the {@link NumberValidator} for phone number validation.
 * Valid phone number is an arbitrary(!) string of dashes - , slashes / and digits,
 * and cannot exceed the specified max length.
 */
public final class PhoneNumberValidator extends AbstractNumberValidator {
    public PhoneNumberValidator(final int maxPhoneNumberLength) {
        super(String.format("^[0-9-/]{1,%s}", maxPhoneNumberLength));
    }
}
