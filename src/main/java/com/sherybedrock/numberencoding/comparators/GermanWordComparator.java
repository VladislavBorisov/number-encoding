package com.sherybedrock.numberencoding.comparators;


import java.util.Comparator;

/**
 * {@link Comparator} implementation to compare two {@link String} words.
 */
public class GermanWordComparator implements Comparator<String> {

    /**
     * Removes all double-quotes " from the passed in {@link String} words and compares them.
     * @param w1 the first {@link String} word to be compared.
     * @param w2 the second {@link String} word to be compared.
     * @return  the value {@code 0} if both strings are equal; a value less than {@code 0} if the first string
     *          is lexicographically less than the second string argument; and a
     *          value greater than {@code 0} if the second string is
     *          lexicographically greater than the first string argument.
     */
    @Override
    public int compare(String w1, String w2) {
        return w1.replaceAll("\"", "").compareTo(w2.replaceAll("\"", ""));
    }
}
