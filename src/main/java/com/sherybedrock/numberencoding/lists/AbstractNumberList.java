package com.bipinet.numberencoding.lists;

import com.bipinet.numberencoding.validators.number.NumberValidator;

/**
 * Abstract implementation of the {@link NumberList} providing the core functionality that
 * can be re-used by the sub-classes.
 */
public abstract class AbstractNumberList implements NumberList {
    /**
     * Validator used to validate numbers from the file. Must be assigned a value at construction time and
     * should not be changed at runtime.
     */
    protected final NumberValidator numberValidator;

    public AbstractNumberList(final NumberValidator numberValidator) {
        this.numberValidator = numberValidator;
    }

}
