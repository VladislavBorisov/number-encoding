package com.bipinet.numberencoding.mapping;


import java.util.Collection;
import java.util.Map;

/**
 * Interface for different implementations of the NumberToWordMapping.
 */
public interface NumberToWordMapping {
    /**
     * Map the passed in collection of words to numbers.
     * @param words {@link Collection} of {@link String} words.
     * @return a {@link Collection} of numbers with their mapped words.
     */
    Map<String, Collection<String>> mapWords(Collection<String> words);

    /**
     * Get all mapped words for the passed in number.
     * @param number {@link String} number to search the mapping for.
     * @return a {@link Collection} of words mapped to the specified number.
     */
    Collection<String> getWords(String number);

    /**
     * Removes all the characters from the the {@link String} words except for ASCII letters [a-zA-Z].
     * @param number {@link String} number to search the mapping for.
     * @return a {@link Collection} of words as ASCII letters [a-zA-Z]. mapped to the specified number.
     */
    Collection<String> getWordsAsASCIILetters(String number);
}
