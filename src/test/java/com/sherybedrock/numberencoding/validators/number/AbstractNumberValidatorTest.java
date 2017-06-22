package com.sherybedrock.numberencoding.validators.number;

import org.junit.Test;

import com.sherybedrock.numberencoding.validators.number.AbstractNumberValidator;
import com.sherybedrock.numberencoding.validators.number.NumberValidator;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractNumberValidatorTest {

    @Test
    public void testDefaultConstructor(){
        NumberValidator testDefaultAbstractNumberValidator = new AbstractNumberValidator() {};
        //Test valid numbers
        String[] validNumbers = new String[]{
                "12341251435",
                "0262565",
                "01234567891011121314151617181920212223242526272829"
        };
        testValidNumbers(validNumbers, testDefaultAbstractNumberValidator);
        //Test invalid numbers
        String[] invalidNumbers = new String[]{
                "",
                "123-41251435",
                "02/62565",
                "02agaetb62565",
                "02#%^#62565",
                "0123456789101112131415161718192021222324252627282930"
        };
        testInvalidNumbers(invalidNumbers, testDefaultAbstractNumberValidator);
    }

    @Test
    public void testConstructorWithCustomLength(){
        int maxNumberLength = 10;
        NumberValidator testCustomLengthAbstractNumberValidator = new AbstractNumberValidator(maxNumberLength){};
        //Test valid numbers
        String[] validNumbers = new String[]{
                "1234125143",
                "0262565",
                "000000000"
        };
        testValidNumbers(validNumbers, testCustomLengthAbstractNumberValidator);
        //Test invalid numbers
        String[] invalidNumbers = new String[]{
                "",
                "123-41251435",
                "02/62565",
                "02agaetb62565",
                "02#%^#62565",
                "0123456789101112131415161718192021222324252627282930"
        };
        testInvalidNumbers(invalidNumbers, testCustomLengthAbstractNumberValidator);
    }

    @Test
    public void testConstructorWithCustomRegExp(){
        String customRegExp = "^(\\d+-?/?)*\\d+$";
        NumberValidator testCustomRegExpAbstractNumberValidator = new AbstractNumberValidator(customRegExp){};
        //Test valid numbers
        String[] validNumbers = new String[]{
                "01234777",
                "0-345",
                "098/9",
                "0-/345"
        };
        testValidNumbers(validNumbers, testCustomRegExpAbstractNumberValidator);
        //Test invalid numbers
        String[] invalidNumbers = new String[]{
                "",
                "/897",
                "76/",
                "-897",
                "76-",
                "-976-",
                "/976/",
                "aebe",
                "#%^&#546",
        };
        testInvalidNumbers(invalidNumbers, testCustomRegExpAbstractNumberValidator);
    }

    /**
     * Tests if all numbers in the passed in String array are valid.
     * @param validNumbers {@link String} array with valid numbers to be tested.
     * @param numberValidator reference to an implementation of the {@link NumberValidator} to be used for the
     *                        number validation.
     */
    static void testValidNumbers(String[] validNumbers, NumberValidator numberValidator){
        for (String validNumber : validNumbers){
            assertTrue(String.format("Number %s must be valid!", validNumber),
                    numberValidator.isValid(validNumber));
        }
    }

    /**
     * Tests if all numbers in the passed in String array are invalid.
     * @param invalidNumbers {@link String} array with invalid numbers to be tested.
     * @param numberValidator reference to an implementation of the {@link NumberValidator} to be used for the
     *                        number validation.
     */
    static void testInvalidNumbers(String[] invalidNumbers, NumberValidator numberValidator){
        for (String invalidNumber : invalidNumbers){
            assertFalse(String.format("Number %s must be invalid!", invalidNumber),
                    numberValidator.isValid(invalidNumber));
        }
    }
}
