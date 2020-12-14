package de.redfreggel.aoc2020.week3.day14;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class DockingDataController {

    public static final String maskIdentifier = "mask";
    public static final String maskSplit = "mask = ";
    public static final String valueSplit = "mem\\[|\\] = ";

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay14_2020.txt");
        solvePart1(path);
        solvePart2(path);
    }

    private static void solvePart2(Path path) {
        String text;
        String mask = null;
        Map<Long, Long> inputToHandle = new HashMap<>();
        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;

                if (text.contains(maskIdentifier)) {
                    String[] splitMask = text.split(maskSplit);
                    //handle old input
                    mask = splitMask[1];
                    continue;
                }
                //Split the string
                String[] values = Pattern.compile(valueSplit).split(text);
                long[] keys = calculateKeysToPlaceValue(Long.parseLong(values[1]), mask);
                for(long key:keys ){
                    inputToHandle.put(key,Long.valueOf(values[2]));
                }
            } while (text != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        long resultRiddle2 =0;
        for (long key : inputToHandle.keySet()) {
            long value = inputToHandle.get(key);
            resultRiddle2 = resultRiddle2 + value;
        }
        System.out.println("Result riddle 2 = " + resultRiddle2);
    }


    private static void solvePart1(Path path) {
        String text;

        String mask = null;
        Map<String, Long> inputToHandle = new HashMap<>();


        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;

                if (text.contains(maskIdentifier)) {
                    String[] splitMask = text.split(maskSplit);
                    //handle old input
                    mask = splitMask[1];
                    continue;
                }
                //Split the string
                String[] values = Pattern.compile(valueSplit).split(text);
                Long handledValue = calculateNewValueForGivenKey(Long.valueOf(values[2]), mask);
                inputToHandle.put(values[1], handledValue);

            } while (text != null);


        } catch (Exception e) {
            e.printStackTrace();
        }

        long resultRiddle1 =  0;
        for (String key : inputToHandle.keySet()) {
            long value = inputToHandle.get(key);
            resultRiddle1 = resultRiddle1 + value;
        }
        System.out.println("Result riddle 1 = " + resultRiddle1);
    }

    public static StringBuilder decodeToBinary(Long pValue) {
        StringBuilder builder = new StringBuilder();
        do {
            builder.append(pValue % 2);
            pValue = pValue / 2;
        } while (pValue > 0);
        return builder;
    }

    public static long decodeToDecimal(StringBuilder pBuilder){
        long outputValue = 0;
        long multiplicationBy = 1;
        for (int i = 0; i < pBuilder.length(); i++) {
            long valueToCompute = Long.parseLong(Character.toString(pBuilder.charAt(i)));
            outputValue = outputValue + (valueToCompute * multiplicationBy);
            multiplicationBy = multiplicationBy * 2;
        }
        return outputValue;
    }

    public static long[] calculateKeysToPlaceValue(long pKey, String mask){

        StringBuilder replacedBinaryValue = handleReplacementX(pKey,mask);
        //now count the X in the String
        long countX = replacedBinaryValue.chars().filter(ch -> ch == 'X').count();
        List<String> possibleValues = new ArrayList<>();
        possibleValues.add(replacedBinaryValue.toString());
        boolean hasXSomewhere = countX>0;
        while(hasXSomewhere){
            List<String> tempList = new ArrayList<>();
            for(String stringToHandel:possibleValues){
                tempList.add(stringToHandel.replaceFirst("X","0"));
                tempList.add(stringToHandel.replaceFirst("X","1"));
            }
            countX = countX-1;
            hasXSomewhere = countX>0;
            possibleValues = tempList;
        }

        long[] keysToAddValue = new long[possibleValues.size()];
        for(int i =0; i<possibleValues.size(); i++){
            StringBuilder build = new StringBuilder(possibleValues.get(i));
            keysToAddValue[i] = decodeToDecimal(build.reverse());
        }

      //  output.reverse();
      //  //after the output has been computed compute the new value
      //  return  decodeToDecimal(output);


        return keysToAddValue;
    }
    public static StringBuilder handleReplacementX(Long value, String mask){
        char[] maskAsArray = mask.toCharArray();

        //initialize Input
        char[] inputAsArray = new char[maskAsArray.length];
        Arrays.fill(inputAsArray, '0');

        //get the binary Value reversed as charArray
        char[] binaryValue = decodeToBinary(value).reverse().toString().toCharArray();

        //put the reversed binaryValue in the comparable char
        int offSet = inputAsArray.length - binaryValue.length;

        for (int i = 0; i < binaryValue.length; i++) {
            inputAsArray[i + offSet] = binaryValue[i];
        }
        //now createTheOutput
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < maskAsArray.length; i++) {
            switch (maskAsArray[i]) {
                case 'X':
                    output.append('X');
                    break;
                case '0':
                    output.append(inputAsArray[i]);
                    break;
                case '1':
                    output.append('1');
                    break;
            }
        }
        return output;
    }

    public static StringBuilder handleReplacement(Long value, String mask){
        char[] maskAsArray = mask.toCharArray();

        //initialize Input
        char[] inputAsArray = new char[maskAsArray.length];
        Arrays.fill(inputAsArray, '0');

        //get the binary Value reversed as charArray
        char[] binaryValue = decodeToBinary(value).reverse().toString().toCharArray();

        //put the reversed binaryValue in the comparable char
        int offSet = inputAsArray.length - binaryValue.length;

        for (int i = 0; i < binaryValue.length; i++) {
            inputAsArray[i + offSet] = binaryValue[i];
        }
        //now createTheOutput

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < maskAsArray.length; i++) {
            switch (maskAsArray[i]) {
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
        return output;
    }

    public static long calculateNewValueForGivenKey(Long value, String mask) {
        StringBuilder output = handleReplacement(value, mask);

        //after the output has been computed compute the new value
       return  decodeToDecimal(output.reverse());
    }

}
