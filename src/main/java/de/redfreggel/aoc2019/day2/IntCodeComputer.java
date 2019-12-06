package de.redfreggel.aoc2019.day2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class IntCodeComputer {

    public static final int[] testInput1 = new int[]{1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50};
    public static final int[] testInput2 = new int[]{1, 0, 0, 0, 99};
    public static final int[] testInput3 = new int[]{2, 3, 0, 3, 99};
    public static final int[] testInput4 = new int[]{2, 4, 4, 5, 99, 0};

    public static final int[] myOriginInput = new int[]{1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 10, 1, 19, 2, 9, 19, 23, 2, 13, 23, 27, 1, 6, 27, 31, 2, 6, 31, 35, 2, 13, 35, 39, 1, 39, 10, 43, 2, 43, 13, 47, 1, 9, 47, 51, 1, 51, 13, 55, 1, 55, 13, 59, 2, 59, 13, 63, 1, 63, 6, 67, 2, 6, 67, 71, 1, 5, 71, 75, 2, 6, 75, 79, 1, 5, 79, 83, 2, 83, 6, 87, 1, 5, 87, 91, 1, 6, 91, 95, 2, 95, 6, 99, 1, 5, 99, 103, 1, 6, 103, 107, 1, 107, 2, 111, 1, 111, 5, 0, 99, 2, 14, 0, 0};

    public static final String separator = ",";

    public static void main(String[] args) {
        IntCodeComputer compute = new IntCodeComputer();
        System.out.println("Result Riddle1: " + compute.solveRiddle1());
        System.out.println("Result Riddle2: " +compute.solveRiddle2());
    }

    public int solveRiddle2() {
        //initialize variables
        var noun = 0;
        var verb = 0;
        System.out.println("Processing myInput - Riddle 2");
        for( noun =0; noun <100; noun++){
            for(verb =0 ; verb < 100; verb++){

                Map<Integer, Integer> myResultMap = this.createMap(myOriginInput);
                //  replace position 1 with the noun
                myResultMap.put(1, noun);
                //replace position 2 with verb
                myResultMap.put(2, verb);

                this.calculateIntPos(myResultMap);
                if (myResultMap.get(0)== 19690720)
                {
                    System.out.println(noun);
                    System.out.println(verb);
                    return (100 * noun) + verb;
                }
            }
        }

        return -1;
    }

    public int solveRiddle1() {
        System.out.println("Processing input 1");
        this.showResult(this.calculateIntPos(this.createMap(testInput1)));
        System.out.println("Processing input 2");
        this.showResult(this.calculateIntPos(this.createMap(testInput2)));
        System.out.println("Processing input 3");
        this.showResult(this.calculateIntPos(this.createMap(testInput3)));
        System.out.println("Processing input 4");
        this.showResult(this.calculateIntPos(this.createMap(testInput4)));

        System.out.println("Processing myInput");
        Map<Integer, Integer> myResultMap = this.createMap(myOriginInput);
        //  replace position 1 with the value 12 and
        myResultMap.put(1, 12);
        //replace position 2 with the value 2.
        myResultMap.put(2, 2);
        // What value is left at position 0 after the program halts?
        this.calculateIntPos(myResultMap);
        this.showResult(myResultMap);
        return myResultMap.get(0);
    }

    private Map<Integer, Integer> createMap(int[] pInput) {
        Map<Integer, Integer> intCode = new HashMap<Integer, Integer>();
        for (int i = 0; i < pInput.length; i++) {
            intCode.put(i, pInput[i]);
        }
        return intCode;
    }

    private void showResult(Map<Integer, Integer> pResult) {
        for (int i = 0; i < pResult.size(); i++) {
            System.out.print(pResult.get(i) + separator);
        }
        System.out.println("");
    }

    private Map<Integer, Integer> calculateIntPos(Map<Integer, Integer> pParam) {
        //start processing
        AtomicInteger processingPosition = new AtomicInteger();
        processingPosition.addAndGet(0);
        while (processingPosition.get() < pParam.size()) {
            //System.out.println("key = " + processingPosition + " value = " + intCode.get(processingPosition));
            int codeValue = pParam.get(processingPosition.get());

            if (codeValue == 99) break;

            int lookup1 = pParam.get(processingPosition.get() + 1);
            int lookup2 = pParam.get(processingPosition.get() + 2);
            int placingPosition = pParam.get(processingPosition.get() + 3);

            switch (codeValue) {
                case 1:
                    pParam.put(placingPosition, pParam.get(lookup1) + pParam.get(lookup2));
                    break;
                case 2:
                    pParam.put(placingPosition, pParam.get(lookup1) * pParam.get(lookup2));
                    break;
            }

            processingPosition.addAndGet(4);
        }
        return pParam;
    }

}
