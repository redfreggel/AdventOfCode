package de.redfreggel.aoc2020.week3.day14;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DockingDataController {

    public static final String maskIdentifier = "mask";
    public static final String maskSplit = "mask = ";
    public static final String valueSplit = "mem\\[|\\] = ";

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay14_2020.txt");
        String text;

        String mask = null;
        Map<String, Long> inputToHandle = new HashMap<>();


        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if(text == null) continue;

                if(text.contains(maskIdentifier)){
                    String[] splittedMask = text.split(maskSplit);
                    //handle old input
                    mask = splittedMask[1];
                    continue;
                }
                //Split the string
                String[] values = Pattern.compile(valueSplit).split(text);
                Long handledValue = handleValue(Long.valueOf(values[2]),mask);
                inputToHandle.put(values[1],handledValue);

            } while (text != null);


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------");
        Long resultRiddle1 = (long)0;
        for(String key: inputToHandle.keySet()){
            System.out.println(inputToHandle.get(key));
            Long value = inputToHandle.get(key);
            resultRiddle1 = resultRiddle1+value;
        }
        System.out.println("Result riddle 1 = " +resultRiddle1);
    }

    public static long handleValue(Long value, String mask){
        //convert value to a "bit" input and reverse it
        StringBuilder builder = new StringBuilder();
        do{
            builder.append(value%2);
            value = value/2;
        }while (value>0);

        char[] maskAsArray = mask.toCharArray();

        //initialize Input
        char[] inputAsArray = new char[maskAsArray.length];
        Arrays.fill(inputAsArray,'0');

        //get the binary Value reversed as charArray
        char[] binaryValue = builder.reverse().toString().toCharArray();

        //put the reversed binaryValue in the comparable char
        int offSet = inputAsArray.length-binaryValue.length;

        for(int i=0; i<binaryValue.length;i++){
            inputAsArray[i+offSet] = binaryValue[i];
        }
        //now createTheOutput

        StringBuilder output = new StringBuilder();
        for(int i=0; i<maskAsArray.length;i++){
            switch (maskAsArray[i]){
                case 'X':
                    output.append(inputAsArray[i]);
                    break;
                case '0':
                    output.append('0');
                    break;
                case '1':
                    output.append('1');
                    break;
            }
        }
        //after the output has been computed compute the new value
        Long outputValue = (long) 0;
        output.reverse();

        long multiplicationBy = 1;
        for(int i = 0; i<output.length();i++){
            Long valueToCompute = Long.parseLong(Character.toString(output.charAt(i)));
            outputValue = outputValue+(valueToCompute*multiplicationBy);
            multiplicationBy = multiplicationBy*2;
        }
        return outputValue;
    }

}
