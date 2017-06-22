package com.bipinet.numberencoding.mapping;


import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PhoneNumberToWordMappingTest {

    @Test
    public void testGetASCIILetters(){
        PhoneNumberToWordMapping phoneNumberToWordMapping = new PhoneNumberToWordMapping();
        Map<String, String> expectedStringToWordMapping = new HashMap<String, String>()
        {{
            put("aenjvnoent", new String("aenjvnoent#^&$%^&/\\"));
            put("AAAMMbvn", new String("#^&$AAAMMbvn%^&/\\"));
            put("Aou", new String("A\"o\"u\""));
            put("aenjvnoent", new String("1234aenjvnoent#^&$%^&/\\"));
            put("QWREQWR", new String("QW|||REQ<<>WR@$%^&&**&))++/\\\\"));
        }};
        for (final Map.Entry<String, String> entry : expectedStringToWordMapping.entrySet()){
            final String expectedWord = entry.getKey();
            final String actualWord = phoneNumberToWordMapping.getASCIILetters(entry.getValue());
            assertThat(String.format("Actual word must be equal to %s ", expectedWord),
                    actualWord, is(expectedWord));

        }
    }

    @Test
    public void testGetWordsAsASCIILetters(){
        PhoneNumberToWordMapping phoneNumberToWordMapping = new PhoneNumberToWordMapping();
        List expectedWordsASCIILettersList = new ArrayList<>(
                Arrays.asList(
                        "aenjvnoent",
                        "AAAMMbvn",
                        "Aou",
                        "aenjvnoent",
                        "QWREQWR")
        );
        phoneNumberToWordMapping.getNumberToWordMappingStore().put("1", new ArrayList<>(
                Arrays.asList(
                        "aenjvnoent#^&$%^&/\\",
                        "#^&$AAAMMbvn%^&/\\",
                        "A\"o\"u\"",
                        "1234aenjvnoent#^&$%^&/\\",
                        "QW|||REQ<<>WR@$%^&&**&))++/\\\\"))
        );
        assertThat(String.format("Actual words must be equal to %s ", expectedWordsASCIILettersList),
                phoneNumberToWordMapping.getWordsAsASCIILetters("1"), is(expectedWordsASCIILettersList));

    }

    @Test
    public void testMapWords(){
        final List sampleDictionary = new ArrayList<>(
                Arrays.asList(
                        "an",
                        "blau",
                        "Bo\"",
                        "Boot",
                        "bo\"s",
                        "da",
                        "Fee",
                        "fern",
                        "Fest",
                        "fort",
                        "je",
                        "jemand",
                        "mir",
                        "Mix",
                        "Mixer",
                        "Name",
                        "neu",
                        "o\"d",
                        "Ort",
                        "so",
                        "Tor",
                        "Torf",
                        "Wasser")
        );
        final Map<String, Collection<String>> expectedNumberToWordMapping = new HashMap<String, Collection<String>>();
        expectedNumberToWordMapping.put("112", new ArrayList<>());
        expectedNumberToWordMapping.put("562", new ArrayList<>(Arrays.asList("mir", "Mix")));
        expectedNumberToWordMapping.put("482", new ArrayList<>(Arrays.asList("Tor")));
        expectedNumberToWordMapping.put("10", new ArrayList<>(Arrays.asList("je")));
        expectedNumberToWordMapping.put("83", new ArrayList<>(Arrays.asList("o\"d")));
        expectedNumberToWordMapping.put("38", new ArrayList<>(Arrays.asList("so")));
        expectedNumberToWordMapping.put("4824", new ArrayList<>(Arrays.asList("fort", "Torf")));

        PhoneNumberToWordMapping phoneNumberToWordMapping = new PhoneNumberToWordMapping();
        phoneNumberToWordMapping.mapWords(sampleDictionary);

        for (Map.Entry<String, Collection<String>> expectedNumberToWord: expectedNumberToWordMapping.entrySet()){
            final String number = expectedNumberToWord.getKey();
            final  Collection<String> words = expectedNumberToWord.getValue();
            assertThat(String.format("Number %s must be mapped to words:  %s", number, words),
                    phoneNumberToWordMapping.getWords(number),
                    is(words));
        }
    }
}
