package com.sherybedrock.numberencoding.encoders;


import com.sherybedrock.numberencoding.entities.number.EncodedNumber;
import com.sherybedrock.numberencoding.mapping.NumberToWordMapping;

import java.util.*;

/**
 * Implementation of the phone number encoder. Encoding numbers using number to word mapping
 * in {@link #numberToWordMapping}.
 */
public final class PhoneNumberEncoder implements NumberEncoder {
    /**
     * Number to word mapping to be used for number encoding.
     */
    private final NumberToWordMapping numberToWordMapping;

    /**
     * Constructs {@link PhoneNumberEncoder} with the passed in number to word mapping.
     * @param numberToWordMapping number to word mapping to be used for number encoding.
     */
    public PhoneNumberEncoder(NumberToWordMapping numberToWordMapping) {
        this.numberToWordMapping = numberToWordMapping;
    }
    /**
     * <p>Encode the specified phone number as follows:
     <p>Only exactly each encoding that is possible from this dictionary and
     that matches the phone number exactly shall be printed. Thus, possibly
     nothing is printed at all. The words in the dictionary contain letters
     (capital or small, but the difference is ignored in the sorting), dashes
     - and double quotes " . For the encoding only the letters are used, but
     the words must be printed in exactly the form given in the dictionary.
     Leading non-letters do not occur in the dictionary.

     <p>Encodings of phone numbers can consist of a single word or of multiple
     words separated by spaces. The encodings are built word by word from
     left to right. If and only if at a particular point no word at all from
     the dictionary can be inserted, a single digit from the phone number can
     be copied to the encoding instead. Two subsequent digits are never
     allowed, though. To put it differently: In a partial encoding that
     currently covers k digits, digit k+1 is encoded by itself if and only if,
     first, digit k was not encoded by a digit and, second, there is no word
     in the dictionary that can be used in the encoding starting at digit k+1.

     <p>Your program must work on a series of phone numbers; for each encoding
     that it finds, it must print the phone number followed by a colon, a
     single(!) space, and the encoding on one line; trailing spaces are not
     allowed. All remaining ambiguities in this specification will be
     resolved by the following example. (Still remaining ambiguities are
     intended degrees of freedom.)

     <p>Sample phone number lists:

	 6146795
	 059-4-5-3336
	 33548-7572
	 08-3-7-1823

     <p>Corresponding correct program output (on screen):
     <pre>
     059-4-5-3336:any w ed d"ug
	 059-4-5-3336:any w ed duo
	 08-3-7-1823:aid 7 fib 3
	 08-3-7-1823:aid 7 f"it 3
	 08-3-7-1823:aid 7 mild
	 08-3-7-1823:aid 7 m ilk
     </pre>

     * @param number {@link String} with the number to be encoded.
     * @return {@link Collection} of encoded numbers as {@link EncodedNumber}.
     */
    @Override
    public Collection<EncodedNumber> encode(String number) {
        final String numberDigits =  getASCIIDigits(number);
        Collection<EncodedNumber> encodedNumbersList = new ArrayList<>();
        //encode
        final List<String> encodedNumberStrings = Collections.unmodifiableList((
                List<String>) encodeNumber(numberDigits, 0, false));
        for (Iterator<String> i = encodedNumberStrings.iterator(); i.hasNext();){
            final String encodedNumberString = i.next();
            final EncodedNumber encodedNumber = new EncodedNumber(number, encodedNumberString);
            encodedNumbersList.add(encodedNumber);
            System.out.println(encodedNumber.toString());
        }
        return encodedNumbersList;
    }

    /**
     * Recursively traverses each passed in number and encodes with the available words for
     * {@link #numberToWordMapping} as per algorithm described in {@link #encode(String)}.
     * @param number number to be encoded.
     * @param startIndex index of the number digit to start with.
     * @param isLeftDigit indicates if the number's digit to the left of the start index
     *                    was encoded as a numeric character [0-9].
     * @return {@link Collection} of encoded numbers as {@link String}.
     */
    private final Collection<String> encodeNumber(final String number, final int startIndex, final boolean isLeftDigit) {
        Collection<String> encodedNumbers = new ArrayList<>();
        for (int currentIndex = startIndex; currentIndex < number.length(); currentIndex++) {
            final String currentNumber = number.substring(startIndex, currentIndex + 1);
            final List<String> words = Collections.unmodifiableList((List<String>) numberToWordMapping.getWords(currentNumber));
            final boolean isCurrentDigit = (currentNumber.length() == 1) ? true : false;
            final boolean isCurrentLastDigit = (currentIndex == number.length() - 1) ? true : false;
            final Collection<String> partlyEncodedNumbers;
            if (!isCurrentLastDigit) {
                if (!words.isEmpty()) {
                    partlyEncodedNumbers = encodeNumber(number.substring(currentIndex + 1), 0, false);
                    for (String word : words) {
                        if (partlyEncodedNumbers.isEmpty() && isCurrentDigit) {
                            encodedNumbers.add(word);
                        } else if (!partlyEncodedNumbers.isEmpty()) {
                            encodedNumbers.addAll(buildEncodedNumberStrings(word, partlyEncodedNumbers));
                        }
                    }
                    continue;
                } else if (!isLeftDigit && isCurrentDigit && !findWord(number)) {
                    partlyEncodedNumbers = encodeNumber(number.substring(currentIndex + 1), 0, true);
                    if (!partlyEncodedNumbers.isEmpty()){
                        encodedNumbers.addAll(buildEncodedNumberStrings(currentNumber, partlyEncodedNumbers));
                    }
                    continue;
                }
            } else if (words.isEmpty() && !isLeftDigit && isCurrentDigit) {
                encodedNumbers.add(currentNumber);
            } else if (!words.isEmpty()) {
                for (String word : words) {
                    encodedNumbers.add(word);
                }
            }
        }
        validateAndRemove(encodedNumbers, number);
        return encodedNumbers;
    }

    /**
     * Creates a new collection of strings for each combination of root and partly encoded numbers.
     * @param root string that will be appended with the partly encoded numbers form the passed in collection.
     * @param partlyEncodedNumbers a {@link Collection} of encoded numbers as strings.
     * @return a {@link Collection} root and partly encoded numbers.
     */
    final static Collection<String> buildEncodedNumberStrings(final String root, final Collection<String> partlyEncodedNumbers){
        final Collection<String> encodedNumberStrings = new ArrayList<>();
        for (String partlyEncodedNumber : partlyEncodedNumbers) {
            StringBuilder encodedNumberBuilder = new StringBuilder();
            encodedNumberBuilder.append(root).append(" ").append(partlyEncodedNumber);
            encodedNumberStrings.add(encodedNumberBuilder.toString());
        }
        return encodedNumberStrings;
    }

    /**
     * Incrementally substring the passed in number and search for words in the {@link #numberToWordMapping}.
     * @param number input number.
     * @return true if word exists, false otherwise.
     */
    final boolean findWord(final String number){
        final int numberLength = number.length();
        for (int currentIndex = 0; currentIndex < numberLength; currentIndex++){
            final int endIndex = numberLength - currentIndex;
            if (this.numberToWordMapping.getWords(number.substring(0, endIndex)).size() > 0) {
                if (endIndex == numberLength || !encodeNumber(number.substring(endIndex), 0, false).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes all the characters from the the number except for ASCII numbers [0-9].
     * @return {@link String} as ASCII digits [0-9].
     */
    final String getASCIIDigits(String number) {
        return number.replaceAll("[^0-9]+", "");
    }

    /**
     * Removes all the characters from the the number except for ASCII numbers [0-9] and letters [a-zA-Z].
     * @return {@link String} as ASCII digits [0-9] and letters [a-zA-Z]..
     */
    final String getASCIILettersAndDigits(String word) {
        return word.replaceAll("[^a-zA-Z0-9]+", "");
    }

    /**
     * Validate all the encoded numbers in the passed in list and remove all that do not match the length of the number.
     * @param encodedNumbers {@link Collection} with encoded numbers as {@link String} words to be validated.
     * @param number {@link String} number that all the encoded numbers in the {@link Collection}
     *                             will be validated against.
     */
    final void validateAndRemove(Collection<String> encodedNumbers, String number){
        if (encodedNumbers != null){
            //Only add the encoding if it's length matches the length of the current number.
            for (Iterator<String> it = encodedNumbers.iterator(); it.hasNext(); ){
                final String encodedNumber = it.next();
                if (getASCIILettersAndDigits(encodedNumber).length() != number.length()){
                    //remove this encodedNumber, because validation did not pass.
                    it.remove();
                }
            }
        }
    }

}
