package com.sherybedrock.numberencoding.entities.number;


import org.junit.Test;

import com.sherybedrock.numberencoding.entities.number.EncodedNumber;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EncodedNumberTest {

    @Test
    public void testToString(){
        String number, encodedNumber, expectedPrintedEncodedPhoneNumber;
        number = "14/46-456";
        encodedNumber = "AADSF awsjawjrn";
        expectedPrintedEncodedPhoneNumber = String.format("%s: %s", number, encodedNumber);
        EncodedNumber encodedPhoneNumber = new EncodedNumber(number, encodedNumber);
        assertThat("Expected and actual strings of the encoded phone number must match!",
                encodedPhoneNumber.toString(), is(expectedPrintedEncodedPhoneNumber));

    }
}
