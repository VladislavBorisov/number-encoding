package com.bipinet.numberencoding.dictionaries;

import com.bipinet.numberencoding.validators.dictionary.DictionaryValidator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract implementation of the {@link Dictionary} providing the core functionality that
 * can be reused by the sub-classes.
 */
public abstract class AbstractDictionary implements Dictionary {
    /**
     * List storing the words. Must be assigned a value at construction time and
     * should not be changed at runtime.
     */
    protected final List<String> dictionaryStore = new ArrayList<String>();
    /**
     * Stores maximum allowed dictionaries size. Must be assigned a value at construction time and
     * should not be changed at runtime.
     */
    protected final int maxDictionarySize;
    /**
     * Stores maximum allowed word length. Must be assigned a value at construction time and
     * should not be changed at runtime.
     */
    protected final int maxWordLength;
    /**
     * Validator used to validate different dictionaries parameters. Must be assigned a value at construction time and
     * should not be changed at runtime.
     */
    protected final DictionaryValidator dictionaryValidator;

    /**
     * Constructs an instance of the dictionaries with the passed in parameters.
     * @param maxDictionarySize maximum allowed dictionaries size.
     * @param maxWordLength maximum allowed word length in the dictionaries.
     * @param dictionaryValidator reference to an instance of the {@link DictionaryValidator}.
     */
    public AbstractDictionary(final int maxDictionarySize,
                              final int maxWordLength,
                              final DictionaryValidator dictionaryValidator) {
        this.maxDictionarySize = maxDictionarySize;
        this.maxWordLength = maxWordLength;
        this.dictionaryValidator = dictionaryValidator;
    }

    public List<String> getDictionaryStore() {
        return dictionaryStore;
    }

    public int getMaxDictionarySize() {
        return maxDictionarySize;
    }

    public int getMaxWordLength() {
        return maxWordLength;
    }

    /**
     * Validate and load the specified dictionary file into memory.
     * Silently handles {@link FileNotFoundException} and {@link IOException} exceptions.
     * Check if the method finishes successfully and handle accordingly if it fails.
     * @param fileName full path to the file containing the dictionary.
     * @return true if loading succeeded,
     * false if a {@link FileNotFoundException} or {@link IOException} occurred.
     */
    @Override
    public boolean load(String fileName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String word;
            while ((word = in.readLine()) != null) {
                //Validate and add the word to the dictionaryStore.
               if (!validateAndAddWord(word)) return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return abortLoading(String.format("Aborting! Cannot find file %s.", fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return abortLoading(String.format("Aborting! Cannot read file %s.", fileName));
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return abortLoading(String.format("Aborting! An error occurred when closing file reader for %s.",
                            fileName));
                }
            }
        }
        return true;
    }

    /**
     * Word count in the dictionary.
     * @return word count in the dictionary.
     */
    @Override
    public int wordCount() {
        return dictionaryStore.size();
    }

    /**
     * Searches for the specified in word.
     * @param word {@link String} word to find.
     * @return the index of the search key, if it is contained in the lists; otherwise, (-(insertion point) - 1).
     */
    @Override
    public int findWord(String word) {
        return Collections.binarySearch(this.dictionaryStore, word);
    }

    /**
     * Prints a message to the System.out and clears the {@link #dictionaryStore}.
     * @param message {@link String} containing the text ro be printed.
     * @return false;
     */
    private boolean abortLoading(String message){
        System.err.println(message);
        if (this.dictionaryStore != null){
            this.dictionaryStore.clear();
        }
        return false;
    }

    /**
     * In the case of successful validation adds the word to the {@link #dictionaryStore}.
     * @param word word to be validated and added to the {@link #dictionaryStore}
     * @return true if the word was added to the {@link #dictionaryStore}, false otherwise.
     */
    private boolean validateAndAddWord(String word){
        if (!this.dictionaryValidator.isDictionarySizeLessThanMaxAllowed(this)){
            return abortLoading(String.format(
                    "Aborting! File contains too many lines and exceeds the maximum allowed dictionaries size %s.",
                    this.maxDictionarySize));
        }
        //Check if length of the word does not exceed maxWordLength.
        if (!this.dictionaryValidator.isWordValid(this, word)){
            return abortLoading(String.format(
                    "Aborting! Word %s contains too many characters and exceeds the maximum allowed word " +
                            "length %s.", word, this.maxWordLength));
        }
        this.dictionaryStore.add(word);
        return true;
    }
}
