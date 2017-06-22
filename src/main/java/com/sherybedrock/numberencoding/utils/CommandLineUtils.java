package com.bipinet.numberencoding.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CommandLineUtils {
    /**
     * Parses an array of strings and returns a map containing arguments as keys and a lists of
     * argument options as values.
     * @param args {@link String} array of arguments to be parsed.
     * @return {@link Map} containing arguments as keys and a lists of
     * argument options as values, or null.
     */
    public static final Map<String, List<String>> parseCommandLine(String[] args){
        final Map<String, List<String>> params = new HashMap<>();

        List<String> options = null;
        for (int i = 0; i < args.length; i++) {
            final String a = args[i];
            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    System.out.println("Error at argument " + a);
                    return null;
                }
                options = new ArrayList<>();
                params.put(a.substring(1), options);
            }
            else if (options != null) {
                options.add(a);
            }
            else {
                System.out.println("Illegal parameter usage");
                return null;
            }
        }
        return params;
    }
}
