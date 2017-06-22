package com.sherybedrock.numberencoding.dictionaries;

/**
 * Interface for different implementations of the dictionaries.
 */
public interface Dictionary {
    /**
     * Load the specified dictionary file into memory.
     * @param fileName full path to the file containing the dictionary.
     * @return true if loading succeeded, false otherwise.
     */
    boolean load(String fileName);

    /**
     * Word count in the dictionary.
     * @return word count in the dictionary.
     */
    int wordCount();

    /**
     * Searches for the specified word.
     * @return integer value >= 0 if and only if the word is found, otherwise integer value < 0.
     */
    int findWord(String word);

}
