package de.redfreggel.aoc2020.day10;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JoltageUsage {

    static int highestAdapter = 0;
    static int matchCount = 0;
    //static Map<Integer, String> riddleMap = new HashMap<>();
    static List<Integer> myJoltages = new ArrayList<>();
    // private final static String delimiter = ",";
    static int keyCounter =0;

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay10_2020_origin.txt");
        String text;

        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;

                myJoltages.add(Integer.parseInt(text));

            } while (text != null);

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        Collections.sort(myJoltages);
        //now the fun can begin
        //Riddle 1
        int inputJoltage = 0;
        int countDiff1 = 0;
        int countDiff3 = 1;//starting with 1 as the mobile device has default +3

        for (Integer nextJoltageAdapter : myJoltages) {
            if ((inputJoltage + 1) == nextJoltageAdapter) countDiff1++;
            if ((inputJoltage + 3) == nextJoltageAdapter) countDiff3++;
            inputJoltage = nextJoltageAdapter;
            //System.out.println(inputJoltage);
        }

        System.out.println("--------------- Riddle 1 Results ----------- ");
        System.out.println("diffs 1 joltage: " + countDiff1);
        System.out.println("diffs 3 joltage: " + countDiff3);
        System.out.println("results (count 1 joltages) * (count 3 joltages) = " + (countDiff1 * countDiff3));
        //Second riddle - all possible combinations

        highestAdapter = myJoltages.get(myJoltages.size() - 1);


        Map<Integer, List<ValuePair>> pairs = new HashMap<>();
        List<ValuePair> values = new ArrayList<>();
        int currentIndex = 0;
        pairs.put(keyCounter, values);
        keyCounter++;

        int maxCountUp = 3;
        if (currentIndex + maxCountUp >= myJoltages.size()) {
            maxCountUp = myJoltages.size() - currentIndex - 1;
        }
        //checking the next three from the list
        int currentJolt = myJoltages.get(currentIndex);
        int nextJolt;
        for (int i = 1; i <= maxCountUp; i++) {
            nextJolt = myJoltages.get(currentIndex + i);

            if ((nextJolt - currentJolt) >= 1 && (nextJolt - currentJolt) <= 3) {
                values.add(new ValuePair(currentIndex, currentJolt, currentIndex + i, nextJolt));
                resolveRecoursive(currentIndex + 1, pairs);
            }
        }
        List<Integer> keys = new ArrayList<>(pairs.keySet());
        Collections.sort(keys);
        System.out.println("keys = "+keys.size());

        findCombinations(0, keys, pairs/*, array*/);

        System.out.println("Possible combinations =  " + matchCount);


    }

    public static void findCombinations(int pStartIndex, List<Integer> keys, Map<Integer, List<ValuePair>> pairs) {
        List<ValuePair> matchingValues = pairs.get(keys.get(pStartIndex));
        if (matchingValues.size() == 0) return;
        for (ValuePair pair: matchingValues) {
            if (pair.nextValue == highestAdapter) {
                matchCount++;
                break;
            } else {
                findCombinations(pair.nextIndex, keys, pairs);
            }
        }
    }

    public static void resolveRecoursive(int pIndexToUse, Map<Integer, List<ValuePair>> pPairs) {
        int currentIndex = pIndexToUse;
        List<ValuePair> values = new ArrayList<>();

        pPairs.put(keyCounter, values);
        keyCounter++;

        int maxCountUp = 3;
        if (currentIndex + maxCountUp >= myJoltages.size()) {
            maxCountUp = myJoltages.size() - currentIndex - 1;
        }
        //checking the next three from the list
        int currentJolt = myJoltages.get(currentIndex);
        int nextJolt;
        for (int i = 1; i <= maxCountUp; i++) {
            nextJolt = myJoltages.get(currentIndex + i);

            if ((nextJolt - currentJolt) >= 1 && (nextJolt - currentJolt) <= 3) {
                values.add(new ValuePair(currentIndex, currentJolt, currentIndex + i, nextJolt));
                resolveRecoursive(currentIndex + 1, pPairs);
            }
        }
    }


    private static class ValuePair {
        final int currentValue;
        final int currentIndex;
        final int nextValue;
        final int nextIndex;

        public ValuePair(int pCurrentIndex, int pCurrentValue, int pNextIndex, int pNextValue) {
            this.currentIndex = pCurrentIndex;
            this.currentValue = pCurrentValue;
            this.nextIndex = pNextIndex;
            this.nextValue = pNextValue;
        }

        @Override
        public String toString() {
            return "ValuePair{" +
                    "currentValue=" + currentValue +
                    ", currentIndex=" + currentIndex +
                    ", nextValue=" + nextValue +
                    ", nextIndex=" + nextIndex +
                    '}';
        }
    }


}
