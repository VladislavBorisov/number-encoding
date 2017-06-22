#Phone Number Encoder


### Instructions
Project should be built and run using Java 1.8.

Required program arguments and values: ```-d <absolute path without spaces to the dictionary file> -i <absolute path without spaces to the input file with phone numbers>``` 

Example: 

*  Windows ```-d C:\temp\dictionary.txt -i C:\temp\input.txt```
*  Linux ```-d /tmp/dictionary.txt -i /tmp/input.txt```

From IDE execute *com.sherybedrock.numberencoding.lists.EncodedPhoneNumberList.main()* passing the required arguments.

Alternatively build with Maven ```mvn clean install``` and execute in the terminal 
```java -jar number-encoding-1.0-SNAPSHOT.jar -d path_to_dictionary -i path_to_phone_numbers_file``` 