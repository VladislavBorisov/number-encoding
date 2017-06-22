package com.sherybedrock.numberencoding.validators.dictionary;

import com.sherybedrock.numberencoding.comparators.GermanWordComparator;
import com.sherybedrock.numberencoding.dictionaries.GermanDictionary;
import com.sherybedrock.numberencoding.validators.dictionary.GermanDictionaryValidator;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class GermanDictionaryValidatorTest {

//    @Test
//    public void testValidateWordCharacters(){
//        GermanDictionaryValidator germanDictionaryValidator = new GermanDictionaryValidator();
//        GermanWordComparator germanWordComparator = new GermanWordComparator();
//        GermanDictionary germanDictionary = new GermanDictionary(
//                50, 50, germanDictionaryValidator, germanWordComparator);
//        //Test valid words
//        String wordWithValidASCIIChars = "A\"A\"DBU\"fvnernO\"vU\"a\"o\"u\"";
//        assertTrue(String.format("Word %s must be valid!", wordWithValidASCIIChars),
//                germanDictionaryValidator.isWordValid(germanDictionary, wordWithValidASCIIChars));
//        //Test invalid words
//        String [] wordsWithInvalidASCIIChars = new String[]{
//                "1ervoeron",
//                "\"ervoeron",
//                "\"\"ervoeron",
//                "ervoeron\"\"",
//                "erv\"\"eron",
//                "AAAbbbooo123",
//                "A\"\"AAbbbooo",
//                "A1\"AAbbbooo",
//                "1243",
//                "dfsdf@%^^&$",
//                ""
//        };
//        for (String wordWithInvalidASCIIChars : wordsWithInvalidASCIIChars){
//            assertFalse(String.format("Word %s must be invalid!", wordWithInvalidASCIIChars),
//                    germanDictionaryValidator.isWordValid(germanDictionary, wordWithInvalidASCIIChars));
//        }
//    }
}
