package com.bipinet.numberencoding.mapping;

import com.bipinet.numberencoding.dictionaries.Dictionary;

import java.util.*;

/**
 * Provides an implementation of the {@link NumberToWordMapping} that maps all the words in the {@link Dictionary}
 * to numbers using the specified mapping.
 */
public abstract class AbstractNumberToWordMapping implements NumberToWordMapping{
    /**
     * Map storing number to word mappings. Must be assigned a value at construction time and
     * should not be changed at runtime.
     */
    protected final Map<String, Collection<String>> numberToWordMappingStore =
            new HashMap<String, Collection<String>>();
    private final Map<Integer, Set<String>> digitToLetterMapping;


    /**
     * Constructs {@link AbstractNumberToWordMapping} with the {@link #numberToWordMappingStore}.
     * @param digitToLetterMapping digit to letter mapping to be used for word to number mapping algorithm.
     */
    public AbstractNumberToWordMapping(Map<Integer, Set<String>> digitToLetterMapping) {
        this.digitToLetterMapping = digitToLetterMapping;
    }

    public Map<String, Collection<String>> getNumberToWordMappingStore() {
        return numberToWordMappingStore;
    }

    public Map<Integer, Set<String>> getDigitToLetterMapping() {
        return digitToLetterMapping;
    }


    @Override
    public final Collection<String> getWords(final String number) {
        Collection<String> words = this.numberToWordMappingStore.get(number);
        return (words != null) ? words : new ArrayList<>();
    }

    @Override
    public final Collection<String> getWordsAsASCIILetters(final String number) {
        Collection<String> wordsAsASCIILetters = new ArrayList<>();
        final Collection<String> words = getWords(number);
        if (words != null) {
            for (String word : words) {
                wordsAsASCIILetters.add(getASCIILetters(word));
            }
        }
        return wordsAsASCIILetters;
    }

    /**
     * Removes all the characters from the the {@link String} word except for ASCII letters [a-zA-Z].
     * @return {@link String} as ASCII letters [a-zA-Z].
     */
    final String getASCIILetters(final String word) {
        return word.replaceAll("[^a-zA-Z]+", "");
    }

}
