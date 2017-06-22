package com.bipinet.numberencoding.validators.number;

import org.junit.Test;

public class PhoneNumberValidatorTest {

    @Test
    public void testPhoneNumberValidation(){
        int maxPhoneNumberLength = 20;
        NumberValidator phoneNumberValidator = new PhoneNumberValidator(maxPhoneNumberLength);
        String[] validNumbers = new String[]{
                "1234125143",
                "026////5",
                "///0000",
                "06//--//65",
                "///000000",
                "//--06//--//65/",
                "--06//--//65//-"
        };
        AbstractNumberValidatorTest.testValidNumbers(validNumbers, phoneNumberValidator);
        //Test invalid numbers
        String[] invalidNumbers = new String[]{
                "",
                "123@$%^41251435",
                "02\\62565",
                "02agaetb62565",
                "02#%^#62565",
                "0123456789101112131415161718192021222324252627282930"
        };
        AbstractNumberValidatorTest.testInvalidNumbers(invalidNumbers, phoneNumberValidator);
    }
}
