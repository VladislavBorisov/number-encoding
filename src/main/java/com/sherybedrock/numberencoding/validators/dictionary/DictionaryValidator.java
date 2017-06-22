package com.bipinet.numberencoding.validators.dictionary;

import com.bipinet.numberencoding.dictionaries.AbstractDictionary;

/**
 * Defines validation methods to be implemented for the {@link AbstractDictionary} validation.
 */
public interface DictionaryValidator {
    /**
     * Validate the size of the {@link AbstractDictionary}.
     * @param dictionary reference to the {@link AbstractDictionary} to be validated.
     * @return true if the size is less than the max allowed size in the dictionaries, false otherwise.
     */
    boolean isDictionarySizeLessThanMaxAllowed(AbstractDictionary dictionary);

    /**
     * Validate the length of the word represented as {@link String}.
     * @param dictionary {@link AbstractDictionary} dictionaries for which the word will be validated.
     * @param word {@link String} word to be validated.
     * @return true if the word is valid, false otherwise.
     */
    boolean isWordValid(AbstractDictionary dictionary, String word);
}
