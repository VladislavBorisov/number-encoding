package com.bipinet.numberencoding.parameters;

import java.util.*;


/**
 * Final class storing static application parameters that are immutable at runtime.
 */
public final class Parameters {
    /**
     * Immutable map with number to letter mapping.
     * Each map entry store stores number to letter mapping as key-value pair, where key represents the number
     * and value represents the corresponding letter mapping stored as as an unmodifiable {@link Set}
     * of letters as {@link String}.
     *
     * <p>The following mapping from letters to digits is given:
     * <pre>
        E | J N Q | R W X | D S Y | F T | A M | C I V | B K U | L O P | G H Z
        e | j n q | r w x | d s y | f t | a m | c i v | b k u | l o p | g h z
        0 |   1   |   2   |   3   |  4  |  5  |   6   |   7   |   8   |   9
        </pre>
    */
    public static final Map<Integer, Set<String>> DIGIT_TO_LETTER_MAPPING = Collections.unmodifiableMap(
            new HashMap<Integer, Set<String>>() {{
                //0
                put(0, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("E"))));
                //1
                put(1, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("J", "N", "Q"))));
                //2
                put(2, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("R", "W", "X"))));
                //3
                put(3, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("D", "S", "Y"))));
                //4
                put(4, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("F", "T"))));
                //5
                put(5, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("A", "M"))));
                //6
                put(6, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("C", "I", "V"))));
                //7
                put(7, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("B", "K", "U"))));
                //8
                put(8, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("L", "O", "P"))));
                //9
                put(9, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("G", "H", "Z"))));
            }}
    );

    public static final int MAX_GERMAN_DICTIONARY_SIZE = 75000;

    public static final int MAX_GERMAN_DICTIONARY_WORD_LENGTH = 50;

    public static final int MAX_PHONE_NUMBER_LENGTH = 50;

    /**
     * {@link Parameters} class cannot be instantiated or extended.
     */
    private Parameters() {
    }
}
