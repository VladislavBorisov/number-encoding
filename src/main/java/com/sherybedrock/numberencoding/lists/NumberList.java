package com.sherybedrock.numberencoding.lists;

/**
 * Provide a contract for loading and processing number lists.
 */
public interface NumberList {
    /**
     * Load the specified file with numbers.
     * @param fileName full path to the file containing the numbers.
     * @return true if loading succeeded, otherwise false.
     */
    boolean load(String fileName);
}
