package com.sherybedrock.numberencoding.parameters;

import java.util.*;

/**
 * Final class storing static application parameters that are immutable at runtime.
 */
public final class Parameters {
	
    public static final int MAX_GERMAN_DICTIONARY_SIZE = 75000;
    public static final int MAX_GERMAN_DICTIONARY_WORD_LENGTH = 50;
    public static final int MAX_PHONE_NUMBER_LENGTH = 50;
    
    /**
     * Immutable map with number to letter mapping.
     * Each map entry store stores number to letter mapping as key-value pair, where key represents the number
     * and value represents the corresponding letter mapping stored as as an unmodifiable {@link Set} of letters as {@link String}.
     *
     * <p>The following mapping from letters to digits is given:
     * <pre>
		Number encoding table:
		a v | f m x | b l t | d k u | c j w | e n | g o r | h p | i q | s y z
		A V | F M X | B L T | D K U | C J W | E N | G O R | H P | I Q | S Y Z
		 0  |   1   |   2   |   3   |   4   |  5  |   6   |  7  |  8  |   9
       </pre>
    */
    public static final Map<Integer, Set<String>> DIGIT_TO_LETTER_MAPPING = Collections.unmodifiableMap(new HashMap<Integer, Set<String>>() {{
                //0
				put(0, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("A", "V"))));
                //1
                put(1, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("F", "M", "X"))));
                //2
                put(2, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("B", "L", "T"))));
                //3
                put(3, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("D", "K", "U"))));
                //4
                put(4, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("C", "J", "W"))));
                //5
                put(5, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("E", "N"))));
                //6
                put(6, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("G", "O", "R"))));
                //7
                put(7, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("H", "P"))));
                //8
                put(8, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("I", "Q"))));
                //9
                put(9, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("S", "Y", "Z"))));
            }}
    );

    /**
     * {@link Parameters} class cannot be instantiated or extended.
     */
	private Parameters() {
	}
}
