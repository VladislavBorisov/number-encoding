package com.sherybedrock.numberencoding.dictionaries;

import com.sherybedrock.numberencoding.comparators.GermanWordComparator;
import com.sherybedrock.numberencoding.dictionaries.GermanDictionary;
import com.sherybedrock.numberencoding.helpers.TestHelpers;
import com.sherybedrock.numberencoding.parameters.Parameters;
import com.sherybedrock.numberencoding.validators.dictionary.DictionaryValidator;
import com.sherybedrock.numberencoding.validators.dictionary.GermanDictionaryValidator;

import org.junit.Test;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GermanDictionaryTest {

    /**
     * File reference to the resources directory.
     */
    private static final File RESOURCES_DIRECTORY = new File("src" + File.separator + "test" + File.separator + "resources");
    /**
     * Dictionary validator to be used in all instances of {@link GermanDictionary}
     */
    private static final DictionaryValidator DICTIONARY_VALIDATOR = new GermanDictionaryValidator();
    private static final Comparator GERMAN_WORD_COMPARATOR = new GermanWordComparator();

    @Test
    public void testValidGermanDictionaryCanBeLoaded(){
        String fullPathToDictionaryFile = RESOURCES_DIRECTORY.getAbsolutePath() + File.separator +
                "valid_dictionary_size_73113.txt";
        //Prove the file exists! This is important!
        TestHelpers.testFileExists(fullPathToDictionaryFile);
        int expectedValidDictionarySize = 73113;
        GermanDictionary validGermanDictionary = new GermanDictionary(
                Parameters.MAX_GERMAN_DICTIONARY_SIZE,
                Parameters.MAX_GERMAN_DICTIONARY_WORD_LENGTH,
                DICTIONARY_VALIDATOR,
                GERMAN_WORD_COMPARATOR);
        assertTrue("Valid dictionaries must be loaded successfully.",
                validGermanDictionary.load(fullPathToDictionaryFile));
        assertThat(String.format("Loaded dictionaries size must be %s.", expectedValidDictionarySize),
                expectedValidDictionarySize, is(validGermanDictionary.wordCount()));
    }

    @Test
    public void testGermanDictionaryWithInvalidSizeCanNotBeLoaded(){
        String fullPathToDictionaryFile = RESOURCES_DIRECTORY.getAbsolutePath() + File.separator + "invalid_dictionary_size_83113.txt";
        //Prove the file exists! This is important!
        TestHelpers.testFileExists(fullPathToDictionaryFile);
        int expectedInvalidDictionarySize = 0;
        GermanDictionary invalidGermanDictionary = new GermanDictionary(
                Parameters.MAX_GERMAN_DICTIONARY_SIZE,
                Parameters.MAX_GERMAN_DICTIONARY_WORD_LENGTH,
                DICTIONARY_VALIDATOR,
                GERMAN_WORD_COMPARATOR);
        assertFalse("Invalid dictionaries must NOT be loaded.", invalidGermanDictionary.load(fullPathToDictionaryFile));
        assertThat(String.format("Loaded dictionaries size must be %s.", expectedInvalidDictionarySize),expectedInvalidDictionarySize, is(invalidGermanDictionary.wordCount()));
    }

//    @Test
//    public void testGermanDictionaryWithInvalidWordLengthCanNotBeLoaded(){
//        String fullPathToDictionaryFile = RESOURCES_DIRECTORY.getAbsolutePath() + File.separator + "dictionary_size_73113_with_invalid_word_length.txt";
//        //Prove the file exists! This is important!
//        TestHelpers.testFileExists(fullPathToDictionaryFile);
//        int expectedDictionarySize = 0;
//        GermanDictionary germanDictionary = new GermanDictionary(
//                Parameters.MAX_GERMAN_DICTIONARY_SIZE,
//                Parameters.MAX_GERMAN_DICTIONARY_WORD_LENGTH,
//                DICTIONARY_VALIDATOR,
//                GERMAN_WORD_COMPARATOR);
//        assertFalse("Dictionary with invalid word length must NOT be loaded.",
//                germanDictionary.load(fullPathToDictionaryFile));
//        assertThat(String.format("Loaded dictionaries size must be %s.", expectedDictionarySize),
//                expectedDictionarySize, is(germanDictionary.wordCount()));
//    }

    @Test
    public void testFindExistingWord(){
        final String fullPathToDictionaryFile = RESOURCES_DIRECTORY.getAbsolutePath() + File.separator +
                "valid_dictionary_size_73113.txt";
        final int expectedValidDictionarySize = 73113;
        final Map<String, Integer> existingWordsAndExpectedIndexes = new HashMap<String, Integer>()
        {{
            put("Abbau", 11);
            put("zynismusfo\"rdernd", 73112);
            put("zynismusfordernd", 73112);
            put("Innenschwimmba\"der", 21638);
            put("Innenschwimmbader", 21638);
            put("Ausflu\"sse", 3221);
            put("Ausflusse", 3221);
        }};
        //Prove the file exists! This is important!
        TestHelpers.testFileExists(fullPathToDictionaryFile);
        GermanDictionary validGermanDictionary = new GermanDictionary(
                Parameters.MAX_GERMAN_DICTIONARY_SIZE,
                Parameters.MAX_GERMAN_DICTIONARY_WORD_LENGTH,
                DICTIONARY_VALIDATOR,
                GERMAN_WORD_COMPARATOR);
        //Load the dictionary!
        validGermanDictionary.load(fullPathToDictionaryFile);
        assertThat(String.format("Loaded dictionary size must be %s.", expectedValidDictionarySize),
                expectedValidDictionarySize, is(validGermanDictionary.wordCount()));

        for (Map.Entry<String, Integer> entry : existingWordsAndExpectedIndexes.entrySet()){
            final String existingWordToFind = entry.getKey();
            final int expectedExistingWordIndex = entry.getValue();
            assertThat(String.format("Word %s must be found at index %s.", existingWordToFind, expectedExistingWordIndex),
                    validGermanDictionary.findWord(existingWordToFind), is(expectedExistingWordIndex));
        }
    }

    @Test
    public void testFindNonExistingWord(){
        final String fullPathToDictionaryFile = RESOURCES_DIRECTORY.getAbsolutePath() + File.separator + "valid_dictionary_size_73113.txt";
        final int expectedValidDictionarySize = 73113;
        final Map<String, Integer> nonExistingWordsAndExpectedIndexes = new HashMap<String, Integer>()
        {{
            put("AbbauR", -13);
            put("zynismusfo\"rderndE", -73114);
            put("zynismusforderndG", -73114);
            put("Innenschwimmba\"derK", -21640);
            put("InnenschwimmbaderT", -21640);
            put("Ausflu\"sseW", -3223);
            put("AusflusseX", -3223);
            put("@$%^%$&$@5", -1);
            put("01235", -1);
            put("gggehwrtbkbi", -60678);
            put("GBA\"hf", -15560);
            put("kjk-du", -63081);
        }};
        //Prove the file exists! This is important!
        TestHelpers.testFileExists(fullPathToDictionaryFile);
        GermanDictionary validGermanDictionary = new GermanDictionary(
                Parameters.MAX_GERMAN_DICTIONARY_SIZE,
                Parameters.MAX_GERMAN_DICTIONARY_WORD_LENGTH,
                DICTIONARY_VALIDATOR,
                GERMAN_WORD_COMPARATOR);
        //Load the dictionary!
        validGermanDictionary.load(fullPathToDictionaryFile);
        assertThat(String.format("Loaded dictionary size must be %s.", expectedValidDictionarySize),
                expectedValidDictionarySize, is(validGermanDictionary.wordCount()));

        for (Map.Entry<String, Integer> entry : nonExistingWordsAndExpectedIndexes.entrySet()){
            final String nonExistingWordToFind = entry.getKey();
            final int expectedNonExistingWordIndex = entry.getValue();
            assertThat(String.format("Word %s must be found at index %s.", nonExistingWordToFind, expectedNonExistingWordIndex),
                    validGermanDictionary.findWord(nonExistingWordToFind), is(expectedNonExistingWordIndex));
        }
    }
}
