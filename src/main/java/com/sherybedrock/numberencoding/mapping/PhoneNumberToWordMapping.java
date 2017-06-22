package com.sherybedrock.numberencoding.mapping;


import java.util.*;

import com.sherybedrock.numberencoding.parameters.Parameters;

public class PhoneNumberToWordMapping extends AbstractNumberToWordMapping {
    /**
     * Constructs {@link PhoneNumberToWordMapping} with the {@link #numberToWordMappingStore}.
     *
     * @param digitToLetterMapping digit to letter mapping to be used for word to number mapping algorithm.
     */
    public PhoneNumberToWordMapping(Map<Integer, Set<String>> digitToLetterMapping) {
        super(digitToLetterMapping);
    }

    /**
     * Constructs {@link AbstractNumberToWordMapping} with the Parameters.DIGIT_TO_LETTER_MAPPING.
     *
     */
    public PhoneNumberToWordMapping() {
        super(Parameters.DIGIT_TO_LETTER_MAPPING);
    }

    @Override
    public Map<String, Collection<String>> mapWords(final Collection<String> words) {
        //Loop through all the words.
        //System.out.println("Mapping words to numbers.");
        for (final String word : words){
            //Get word with ASCII letters only.
            final String wordAsASCIILetters = getASCIILetters(word);
            final StringBuilder numberBuilder = new StringBuilder();
            //Loop through all the letters in the wordAsASCIILetters and find a number for each letter
            // from the digitToLetterMapping
            for (int i = 0; i < wordAsASCIILetters.length(); i++){
                //Loop through all the digitToLetterMapping and find a number for the current letter
                //Key represents Number, Value represents Letter
                for (final Map.Entry<Integer, Set<String>> digitToLetter : super.getDigitToLetterMapping().entrySet()){
                    if (digitToLetter.getValue().contains(String.valueOf(wordAsASCIILetters.charAt(i)).toUpperCase())){
                        numberBuilder.append(digitToLetter.getKey());
                        break;
                    }
                }
            }
            final String number = numberBuilder.toString();
            if (super.numberToWordMappingStore.get(number) == null){
                super.numberToWordMappingStore.put(number, new ArrayList<String>(Arrays.asList(word)));
            } else {
                super.numberToWordMappingStore.get(number).add(word);
            }
        }
        return super.numberToWordMappingStore;
    }
}
