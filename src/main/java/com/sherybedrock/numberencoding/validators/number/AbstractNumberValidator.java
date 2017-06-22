package com.sherybedrock.numberencoding.validators.number;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractNumberValidator implements NumberValidator{
    private Pattern pattern;
    private Matcher matcher;

    /**
     * Validates that the number contains only digits [0-9] and does not exceed the max length.
     */
    private String regExp = "^[0-9]{1,%s}$";
    /**
     * Default max length is 50.
     */
    private int maxNumberLength = 50;

    /**
     * Default constructor initialising the {@link AbstractNumberValidator} with the default {@link #regExp} and
     * {@link #maxNumberLength};
     */
    protected  AbstractNumberValidator() {
        //Default
        this.pattern = Pattern.compile(String.format(this.regExp, this.maxNumberLength));
    }

    /**
     * Constructor initialising the {@link AbstractNumberValidator} with the default {@link #regExp} and
     * passed in {@link #maxNumberLength};
     */
    protected AbstractNumberValidator(int maxNumberLength) {
        this.maxNumberLength = maxNumberLength;
        this.pattern = Pattern.compile(String.format(this.regExp, this.maxNumberLength));
    }

    /**
     * Custom constructor initialising the {@link AbstractNumberValidator} with the passed in {@link #regExp}.
     *
     * @param regExp {@link String} with regular expression for number validation.
     */
    protected AbstractNumberValidator(String regExp) {
        this.regExp = regExp;
        this.pattern = Pattern.compile(this.regExp);
    }

    /**
     * Validate passed in {@link String} number against the {@link #pattern}.
     * @param number {@link String} with number to validate.
     * @return true if number is valid, false otherwise;
     */
    @Override
    public boolean isValid(String number) {
        this.matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
