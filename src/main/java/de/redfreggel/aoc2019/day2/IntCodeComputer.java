package de.redfreggel.aoc2019.day2;

import java.util.HashMap;
import java.util.Map;

public class IntCodeComputer {

    public static final String testInput1 = "1,9,10,3,2,3,11,0,99,30,40,50";
    public static final String testInput2 = "1,0,0,0,99";

    public static final String myInput = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,2,9,19,23,2,13,23,27,1,6,27,31,2,6,31,35,2,13,35,39,1,39,10,43,2,43,13,47,1,9,47,51,1,51,13,55,1,55,13,59,2,59,13,63,1,63,6,67,2,6,67,71,1,5,71,75,2,6,75,79,1,5,79,83,2,83,6,87,1,5,87,91,1,6,91,95,2,95,6,99,1,5,99,103,1,6,103,107,1,107,2,111,1,111,5,0,99,2,14,0,0";

    public static final String separator = ",";

    public static void main(String[] args) {
        int startPoint;
        int mapValue = 0;
        String nextString = testInput2;
        Map<Integer, Integer> intCode = new HashMap<Integer, Integer>();
        while (nextString.contains(separator)) {
            startPoint = 0;
            String subString = nextString.substring(startPoint, nextString.indexOf(separator));
            intCode.put(mapValue, Integer.parseInt(subString));
            mapValue++;
            startPoint = nextString.indexOf(separator) + 1;
            nextString = nextString.substring(startPoint, nextString.length());
        }
        if (nextString.length() > 0) {
            intCode.put(mapValue, Integer.parseInt(nextString));
        }

        //start processing
        int processingPositition = 0;
        while (processingPositition <= mapValue) {
            //System.out.println("key = " + processingPositition + " value = " + intCode.get(processingPositition));
            int codeValue = intCode.get(processingPositition);
            int lookup1 = intCode.get(processingPositition + 1);
            int lookup2 = intCode.get(processingPositition + 2);
            int placingPosition = intCode.get(processingPositition + 3);

            switch (codeValue) {
                case 1:
                    intCode.put(placingPosition, intCode.get(lookup1) + intCode.get(lookup2));
                    break;
                case 2:
                    intCode.put(placingPosition, intCode.get(lookup1) * intCode.get(lookup2));
                    break;
                case 99:
                    //Stop here
                    processingPositition = mapValue;
                    break;
            }

            processingPositition += 4;
        }

        processingPositition = 0;
        while (processingPositition <= mapValue) {
            //System.out.println("key = " + processingPositition + " value = " + intCode.get(processingPositition));
            System.out.print(intCode.get(processingPositition) + separator);
            processingPositition++;
        }
        System.out.println("");

    }

}
