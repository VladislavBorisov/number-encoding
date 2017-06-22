package com.bipinet.numberencoding.validators.dictionary;


import com.bipinet.numberencoding.dictionaries.AbstractDictionary;


/**
 * Implements validation methods for the {@link AbstractDictionary} and its sub-classes.
 */
public abstract class AbstractDictionaryValidator implements DictionaryValidator {

    /**
     * Validate the size of the {@link AbstractDictionary}.
     * @param dictionary reference to the {@link AbstractDictionary} to be validated.
     * @return true if current size is LESS than the max allowed size of the dictionary, false otherwise.
     */
    @Override
    public boolean isDictionarySizeLessThanMaxAllowed(AbstractDictionary dictionary) {
        return (dictionary.wordCount() < dictionary.getMaxDictionarySize());
    }

    /**
     * Validate the length of the word represented as {@link String}.
     * @param dictionary {@link AbstractDictionary} dictionaries for which the word will be validated.
     * @param word {@link String} word to be validated.
     * @return true if the size is GREATER than 0 and LESS THAN or EQUALS the max allowed length in the dictionaries,
     * false otherwise.
     */
    @Override
    public boolean isWordValid(AbstractDictionary dictionary, String word) {
        return ((word.length() > 0) && (word.length() <= dictionary.getMaxWordLength()));
    }

    /**
     * Validates if the passed in char is an ASCII alphabetical character [a-zA-Z];
     * @param c character to be validated;
     * @return true if the specified char is a ASCII alphabetical character, false otherwise;
     */
    public final boolean isASCIILetter(char c){
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }
}
