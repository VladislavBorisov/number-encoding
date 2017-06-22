package com.sherybedrock.numberencoding.lists;


import com.sherybedrock.numberencoding.dictionaries.AbstractDictionary;
import com.sherybedrock.numberencoding.encoders.NumberEncoder;
import com.sherybedrock.numberencoding.entities.number.EncodedNumber;
import com.sherybedrock.numberencoding.helpers.TestHelpers;
import com.sherybedrock.numberencoding.lists.EncodedPhoneNumberList;
import com.sherybedrock.numberencoding.mapping.PhoneNumberToWordMapping;
import com.sherybedrock.numberencoding.parameters.Parameters;
import com.sherybedrock.numberencoding.validators.dictionary.AbstractDictionaryValidator;
import com.sherybedrock.numberencoding.validators.number.PhoneNumberValidator;

import org.junit.Test;

import java.io.File;
import java.util.*;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class EncodedPhoneNumberListTest {

    /**
     * File reference to the resources directory.
     */
    private static final File RESOURCES_DIRECTORY = new File(
            "src" + File.separator + "test" + File.separator + "resources");

    @Test
    public void testPhoneNumberListIsLoadedCorrectly(){
        String fullPathPhoneNumberFile = RESOURCES_DIRECTORY.getAbsolutePath() + File.separator +
                "phone_number_list.txt";
        int expectedPhoneNumberListSize = 1000;
        final Collection<EncodedNumber> loadedPhoneNumberList = new ArrayList<>();
        //Prove the file exists! This is important!
        TestHelpers.testFileExists(fullPathPhoneNumberFile);
        //Create a dummy dictionary validator.
        AbstractDictionaryValidator dummyDictionaryValidator = new AbstractDictionaryValidator(){};
        //Create a dummy dictionary.
        AbstractDictionary dummyDictionary = new AbstractDictionary(
                Parameters.MAX_GERMAN_DICTIONARY_SIZE,
                Parameters.MAX_GERMAN_DICTIONARY_WORD_LENGTH,
                dummyDictionaryValidator)
        {};
        //Create a dummy honeNumberValidator.
        PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator(
                Parameters.MAX_PHONE_NUMBER_LENGTH);
        //Create a dummy encoder that adds numbers to the lists,
        // we just want to test that all numbers in a valid file are loaded correctly.
        NumberEncoder dummyNumberEncoder = new NumberEncoder() {
            @Override
            public Collection<EncodedNumber> encode(String number) {
                loadedPhoneNumberList.add(new EncodedNumber(number, "ENCODED"));
                return null;
            }
        };
        PhoneNumberToWordMapping phoneNumberToWordMapping= new PhoneNumberToWordMapping(
                Parameters.DIGIT_TO_LETTER_MAPPING);
        //Create an instance of the EncodedPhoneNumberList.
        EncodedPhoneNumberList encodedPhoneNumberList =  new EncodedPhoneNumberList(
                phoneNumberValidator, dummyDictionary, dummyNumberEncoder, phoneNumberToWordMapping);
        //Load the phone number and test.
        assertTrue("Valid phone number file must be loaded successfully.",
                encodedPhoneNumberList.load(fullPathPhoneNumberFile));
        assertThat(String.format("There must be %s phone numbers in %s.",
                expectedPhoneNumberListSize, fullPathPhoneNumberFile),
                loadedPhoneNumberList.size(), is(expectedPhoneNumberListSize));
    }

    @Test
    public void testParseInput(){
        String[] validArgs = new String[] {"-d", "C:\\temp\\dictionary.txt", "-i", "C:\\temp\\input.txt"};
        String[] invalidArgs1 = new String[] {"-d", "C:\\temp", " ", " spaces \\dictionary.txt", "-i", "C:\\temp\\input.txt"};
        String[] invalidArgs2 = new String[] {"asetbeb", "wtrbr", "srbgt", "-i", "C:\\temp\\input.txt"};
        String[] invalidArgs3 = new String[] {"-d", "C:\\temp \\dictionary.txt", "srtb", "-i", "C:\\temp\\input.txt"};

        //Test valid args and options.
        Map<String, List<String>> actualParams = EncodedPhoneNumberList.parseInput(validArgs);
        assertNotNull("Parameter d must not be null.", actualParams.get("d"));
        assertNotNull("Parameter i must not be null.", actualParams.get("i"));
        assertThat("Parameter d must have 1 option.", actualParams.get("d").size(), is(1));
        assertThat("Parameter i must have 1 option.", actualParams.get("i").size(), is(1));
        assertThat(String.format("Parameter d option must be %s", validArgs[1]),
                actualParams.get("d").get(0), is(validArgs[1]));
        assertThat(String.format("Parameter d option must be %s", validArgs[1]),
                actualParams.get("i").get(0), is(validArgs[3]));

        //Test invalid args and options.
        assertNull(String.format("Args %s must be invalid!", Arrays.asList(invalidArgs1)),
                EncodedPhoneNumberList.parseInput(invalidArgs1));
        assertNull(String.format("Args %s must be invalid!", Arrays.asList(invalidArgs2)),
                EncodedPhoneNumberList.parseInput(invalidArgs2));
        assertNull(String.format("Args %s must be invalid!", Arrays.asList(invalidArgs3)),
                EncodedPhoneNumberList.parseInput(invalidArgs3));
    }
}
