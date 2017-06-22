package com.bipinet.numberencoding.entities.number;

import java.lang.*;

/**
 * Represents an encoded number and provides specific method implementations.
 */
public class EncodedNumber extends Number {
    /**
     * Encoded representation of the {@link #number}.
     */
    private final String encodedNumber;

    public EncodedNumber(String number, String encodedNumber) {
        super(number);
        this.encodedNumber = encodedNumber;
    }

    public String getEncodedNumber() {
        return encodedNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EncodedNumber that = (EncodedNumber) o;

        return encodedNumber.equals(that.encodedNumber);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + encodedNumber.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getNumber() + ": " + this.encodedNumber;
    }
}
