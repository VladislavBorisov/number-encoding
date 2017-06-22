package com.bipinet.numberencoding.helpers;


import java.io.File;

import static org.junit.Assert.assertTrue;

public class TestHelpers {

    /**
     * Helper method for testing if the specified file exists.
     * @param fullPathToFile full path to the file to be tested.
     */
    public static void testFileExists(String fullPathToFile){
        File file = new File(fullPathToFile);
        //Prove that the file exists!
        assertTrue(String.format("File %s must exist!", file.getAbsolutePath()), file.exists());
    }
}
