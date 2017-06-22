package com.sherybedrock.numberencoding.validators.dictionary;

import com.sherybedrock.numberencoding.dictionaries.AbstractDictionary;
import com.sherybedrock.numberencoding.validators.dictionary.AbstractDictionaryValidator;
import com.sherybedrock.numberencoding.validators.dictionary.DictionaryValidator;

import org.junit.Test;
import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractDictionaryValidatorTest {

    private static final DictionaryValidator DICTIONARY_VALIDATOR = new AbstractDictionaryValidator(){};
    private static final int MAX_DICTIONARY_SIZE_VALID = 3;
    private static final int MAX_DICTIONARY_SIZE_INVALID = 120;
    private static final int MAX_VALID_WORD_LENGTH = 4;

    /**
     * Create a test implementation of the {@link AbstractDictionary} that creates dictionaryStore with dummy data
     * depending on the argument passed into the load method. This is basically used to test the method implementation
     * in {@link AbstractDictionary}.
     */

    private class TestAbstractDictionary extends AbstractDictionary {
        /**
         * Constructs an instance of the dictionaries with the passed in parameters.
         *
         * @param maxDictionarySize   maximum allowed dictionaries size.
         * @param maxWordLength       maximum allowed word length in the dictionaries.
         * @param dictionaryValidator reference to an instance of the {@link DictionaryValidator}.
         */
        public TestAbstractDictionary(int maxDictionarySize, int maxWordLength, DictionaryValidator dictionaryValidator) {
            super(maxDictionarySize, maxWordLength, dictionaryValidator);
        }

        @Override
        public boolean load(String fileName) {
            if (dictionaryStore == null) return false;
            dictionaryStore.clear();
            switch(fileName){
                case "valid_size":
                    dictionaryStore.addAll(Arrays.asList(new String[maxDictionarySize - 1]));
                    break;
                case "invalid_size":
                    dictionaryStore.addAll(Arrays.asList(new String[maxDictionarySize + MAX_DICTIONARY_SIZE_INVALID]));
                    break;
                default:
                    //valid size
                    dictionaryStore.addAll(Arrays.asList(new String[maxDictionarySize]));
            }
            return true;
        }
    };

    @Test
    public void testValidDictionarySize(){
        TestAbstractDictionary testAbstractDictionary = new TestAbstractDictionary(
                MAX_DICTIONARY_SIZE_VALID, MAX_VALID_WORD_LENGTH, DICTIONARY_VALIDATOR);
        //Load the valid data.
        testAbstractDictionary.load("valid_size");
        assertTrue(String.format("Dictionary size %s must be valid!", testAbstractDictionary.getMaxDictionarySize()),
                DICTIONARY_VALIDATOR.isDictionarySizeLessThanMaxAllowed(testAbstractDictionary));
    }

    @Test
    public void testInvalidDictionarySize(){
        TestAbstractDictionary testAbstractDictionary = new TestAbstractDictionary(
                MAX_DICTIONARY_SIZE_VALID, MAX_VALID_WORD_LENGTH, DICTIONARY_VALIDATOR);
        //Load the invalid data, this will overrun the allowed size.
        testAbstractDictionary.load("invalid_size");
        assertFalse(String.format("Dictionary size %s must be invalid!", testAbstractDictionary.getDictionaryStore().size()),
                DICTIONARY_VALIDATOR.isDictionarySizeLessThanMaxAllowed(testAbstractDictionary));
    }

    @Test
    public void testValidateWordLength(){
        TestAbstractDictionary testAbstractDictionary = new TestAbstractDictionary(
                MAX_DICTIONARY_SIZE_VALID, MAX_VALID_WORD_LENGTH, DICTIONARY_VALIDATOR);
        //Test valid words
        String wordWithValidLength = "ADBU";
        assertTrue(String.format("Word length %s must be valid!", wordWithValidLength.length()),
                DICTIONARY_VALIDATOR.isWordValid(testAbstractDictionary, wordWithValidLength));
        //Test invalid words
        String [] wordsWithInvalidLength = new String[]{"ADBUD", "", "ajvaeojnvejvnojenvoeirvneovnuaenvae;"};
        for (String wordWithInvalidLength : wordsWithInvalidLength){
            assertFalse(String.format("Word length %s must be invalid!", wordWithInvalidLength.length()),
                    DICTIONARY_VALIDATOR.isWordValid(testAbstractDictionary, wordWithInvalidLength));
        }
    }
}
