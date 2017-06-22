package com.bipinet.numberencoding.validators.dictionary;


import com.bipinet.numberencoding.dictionaries.AbstractDictionary;

public class GermanDictionaryValidator extends AbstractDictionaryValidator {

    /**
     * Validate the length of the word represented as {@link String}.
     * @param dictionary {@link AbstractDictionary} dictionaries for which the word will be validated.
     * @param word {@link String} word to be validated.
     * @return true if the size is GREATER than 0 and LESS THAN or EQUALS the max allowed length in the dictionaries,
     * and starts with an alphabetical character, and contains only ASCII alphabetical characters [a-zA-Z]
     * or alphabetical characters [AaOoUu] followed by double-quotes ["] for umlaut character encoding, false otherwise.
     */
    @Override
    public boolean isWordValid(AbstractDictionary dictionary, String word) {
        //First validate word length and if valid and perform further validation.
        if (!super.isWordValid(dictionary, word)) return false;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            //Validate that the word starts with a letter [a-zA-Z]
            if (i == 0) {
                if (!super.isASCIILetter(currentChar)) {
                    return false;
                }
            }
            if (i > 0) {
                if (currentChar == '"') {
                    //Validate that the double-quotes encode umlaut characters [AaOoUu]
                    char previousChar = word.charAt(i - 1);
                    //If previous character is not a letter [AaOoUu] return false.
                    if (((!super.isASCIILetter(previousChar)) &&
                            ((Character.toUpperCase(previousChar) != 'A') ||
                                    (Character.toUpperCase(previousChar) != 'O') ||
                                    (Character.toUpperCase(previousChar) != 'E'))
                    )) return false;
                    //Next character must be a letter [a-zA-Z]
                    //Do not validate if this is the last char in the word
                    if (i != (word.length() - 1)) {
                        char nextChar = word.charAt(i + 1);
                        if (!super.isASCIILetter(nextChar)) return false;
                    }

                    //if current char is not a double quote then validate if it is is a letter [a-zA-Z]
                } else if (!super.isASCIILetter(currentChar)) {
                    return false;
                }
            }
        }
        return true;
    }

}
