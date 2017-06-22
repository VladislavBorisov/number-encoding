package com.bipinet.numberencoding.entities.number;

/**
 * Wraps a {@link String} number.
 */
public class Number {
    private final String number;

    public Number(final String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Number number1 = (Number) o;

        return number.equals(number1.number);

    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return "Number{" +
                "number='" + number + '\'' +
                '}';
    }
}
