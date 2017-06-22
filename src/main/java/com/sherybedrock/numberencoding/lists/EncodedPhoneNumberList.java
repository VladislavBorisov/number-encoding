package com.bipinet.numberencoding.lists;

import com.bipinet.numberencoding.comparators.GermanWordComparator;
import com.bipinet.numberencoding.dictionaries.Dictionary;
import com.bipinet.numberencoding.dictionaries.GermanDictionary;
import com.bipinet.numberencoding.encoders.NumberEncoder;
import com.bipinet.numberencoding.encoders.PhoneNumberEncoder;
import com.bipinet.numberencoding.mapping.NumberToWordMapping;
import com.bipinet.numberencoding.mapping.PhoneNumberToWordMapping;
import com.bipinet.numberencoding.parameters.Parameters;
import com.bipinet.numberencoding.utils.CommandLineUtils;
import com.bipinet.numberencoding.validators.dictionary.GermanDictionaryValidator;
import com.bipinet.numberencoding.validators.number.PhoneNumberValidator;
import com.bipinet.numberencoding.validators.number.NumberValidator;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link NumberList} for loading and encoding phone numbers.
 */
public class EncodedPhoneNumberList extends AbstractNumberList {
    private final Dictionary dictionary;
    private final NumberEncoder numberEncoder;
    private final NumberToWordMapping numberToWordMapping;
    private enum arguments {
        d,i;
    }

    /**
     * Constructs an instance of the {@link EncodedPhoneNumberList} with the passed in parameters.
     * @param numberValidator reference to an instance of the {@link NumberValidator}.
     * @param dictionary reference to an instance of the {@link Dictionary}.
     * @param numberEncoder reference to an instance of the {@link NumberEncoder}.
     * @param numberToWordMapping reference to an instance of the {@link NumberToWordMapping}.
     */
    public EncodedPhoneNumberList(
            NumberValidator numberValidator,
            Dictionary dictionary,
            NumberEncoder numberEncoder,
            NumberToWordMapping numberToWordMapping) {
        super(numberValidator);
        this.dictionary = dictionary;
        this.numberEncoder = numberEncoder;
        this.numberToWordMapping = numberToWordMapping;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public NumberEncoder getNumberEncoder() {
        return numberEncoder;
    }

    public NumberToWordMapping getNumberToWordMapping() {
        return numberToWordMapping;
    }

    /**
     * Load and encode numbers in the specified file. Silently handles
     * {@link FileNotFoundException} and {@link IOException} exceptions.
     * Check if the method finishes successfully and handle accordingly if it fails.
     * @param fileName full path to the file containing the numbers.
     * @return true if numbers were loaded successfully,
     *  false if a {@link FileNotFoundException} or {@link IOException} occurred.
     */
    @Override
    public boolean load(String fileName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String number;
            //System.out.println(String.format("Encoding numbers from file %s.", fileName));
            while ((number = in.readLine()) != null) {
                //Validate and encode the phone number.
                if (this.numberValidator.isValid(number)) {
                    this.numberEncoder.encode(number);
                } else {
                    continue;
                    //System.out.println(String.format("Skipping! Number format of %s is invalid.", number));
                }
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
     * Prints a message to the System.out and return false.
     * @param message {@link String} containing the text ro be printed.
     * @return false;
     */
    private boolean abortLoading(String message){
        System.out.println(message);
        return false;
    }

    /**
     * Initialize the {@link EncodedPhoneNumberList} with the required components, load the dictionary,
     * prepare word to number mappings and start encoding.
     * @param pathToDictionaryFile path to the dictionary.
     * @param pathToInputFile path to the file with phone numbers.
     */
    private static void start(final String pathToDictionaryFile, final String pathToInputFile){
        //Initialise and load the dictionary for the file.
        GermanDictionary germanDictionary = new GermanDictionary(
                Parameters.MAX_GERMAN_DICTIONARY_SIZE,
                Parameters.MAX_GERMAN_DICTIONARY_WORD_LENGTH,
                new GermanDictionaryValidator(),
                new GermanWordComparator());
        //If something fails exit with error code 1.
        if (!germanDictionary.load(pathToDictionaryFile))
            System.exit(1);
        //Map the dictionary words to numbers.
        PhoneNumberToWordMapping phoneNumberToWordMapping = new PhoneNumberToWordMapping(
                Parameters.DIGIT_TO_LETTER_MAPPING);
        phoneNumberToWordMapping.mapWords(germanDictionary.getDictionaryStore());
        //Load and encode the phone numbers from the input file.
        EncodedPhoneNumberList encodedPhoneNumberList = new EncodedPhoneNumberList(
                new PhoneNumberValidator(Parameters.MAX_PHONE_NUMBER_LENGTH),
                germanDictionary,
                new PhoneNumberEncoder(phoneNumberToWordMapping),
                phoneNumberToWordMapping);
        //If something fails exit with error code 1.
        if (!encodedPhoneNumberList.load(pathToInputFile))
            System.exit(1);
    }

    /**
     * Get user input and validate.
     * Valid user input -d value -i value.
     * @param args {@link String} array with input args.
     * @return {@link Map} containing arguments as keys and a lists of
     * argument options as values or null;
     */
    static Map<String, List<String>> parseInput(String[] args) {
        Map<String, List<String>> params = CommandLineUtils.parseCommandLine(args);
        //must contain one argument and one option per argument.
        if (
                (params != null) && (params.size() != 0) &&
                (params.get(arguments.d.toString()) != null) &&
                (params.get(arguments.i.toString()) != null) &&
                (params.get(arguments.d.toString()).size() == 1) &&
                (params.get(arguments.i.toString()).size() == 1)
                ) {
            return params;
        }
        return null;
    }


    /**
     * Print usage instructions.
     */
    private static void printUsage(){
        StringBuilder usageBuilder = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        usageBuilder.append("Required arguments and values: ");
        usageBuilder.append(String.format(
                "-%s <absolute path without spaces to the dictionary file> ", arguments.d));
        usageBuilder.append(String.format(
                "-%s <absolute path without spaces to the input file with phone numbers> ", arguments.i));
        usageBuilder.append(lineSeparator);
        usageBuilder.append("Example:");
        usageBuilder.append(lineSeparator);
        usageBuilder.append(String.format(
                "  * Windows -%s C:\\temp\\dictionary.txt -%s C:\\temp\\input.txt", arguments.d, arguments.i));
        usageBuilder.append(lineSeparator);
        usageBuilder.append(String.format(
                "  * Linux -%s /tmp/dictionary.txt -%s /tmp/input.txt", arguments.d, arguments.i));
        System.out.println(usageBuilder.toString());
    }

    /**
     * Main method to initialise the phone number encoding.
     * @param args
     */
    public static void main(String[] args) {
        Map<String, List<String>> params = parseInput(args);
        if (params != null ){
            final String pathToDictionaryFile = params.get(arguments.d.toString()).get(0);
            final String pathToInputFile = params.get(arguments.i.toString()).get(0);
            if (!new File(pathToDictionaryFile).exists()){
                System.err.println(String.format("File %s does not exist!", pathToDictionaryFile));
                System.exit(1);
            }
            if (!new File(pathToInputFile).exists()){
                System.err.println(String.format("File %s does not exist!", pathToInputFile));
                System.exit(1);
            }
            //long startTime = System.currentTimeMillis();
            //Start loading dictionary and encoding!
            start(pathToDictionaryFile, pathToInputFile);
            //long estimatedTime = System.currentTimeMillis() - startTime;
            //System.out.print("ELAPSED TIME: " + estimatedTime);
        } else {
            System.err.println("Error occurred when parsing input arguments.");
            printUsage();
            System.exit(1);
        }
    }
}
