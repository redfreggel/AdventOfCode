package de.redfreggel.aoc2020.day9;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncodingError {


    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay9_2020.txt");
        String text;
        int preambleLength = 25;
        //compile every possible value
        double riddle1Result= 0; // = 731031916;
        double[] preambleInput = new double[preambleLength]; //maximum length of
        double[] fullPreambleInput = new double[1000];
        int position = 0;
        int counter = 0;
        boolean firstBatchReceived = false;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;
                //if I have reached the preamble to check, than I need to reset the possible values
                if (position / preambleLength == 1) {
                    position = 0;
                    firstBatchReceived = true;
                }
                double valueToMatch = Double.parseDouble(text);
                if (firstBatchReceived) {
                    boolean foundAMatchRiddle1 = false;
                    for (int i = 0; i < preambleLength; i++) {
                        if (!foundAMatchRiddle1) {
                            double valueA = preambleInput[i];
                            for (int j = 0; j < preambleLength; j++) {
                                if (i == (j + 1) % preambleLength) continue;
                                double valueB = preambleInput[((j + 1) % preambleLength)];
                                if (valueToMatch == valueA + valueB) {
                                    foundAMatchRiddle1 = true;
                                    break;
                                }
                            }
                        }
                    }
                    //check outside the loop if the match was found for riddle 1
                    if (!foundAMatchRiddle1) {
                        System.out.println("Result Riddle 1 =  " + BigDecimal.valueOf(valueToMatch));
                        riddle1Result = valueToMatch;
                        break;
                    }
                }
                preambleInput[position] = valueToMatch;
                fullPreambleInput[counter] = valueToMatch;
                position++;
                counter++;
            } while (text != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Here to start riddle 2
        // find a contiguous set of at least two numbers

        for (int i = 0; i < fullPreambleInput.length - 2; i++) {

            int possibleCombinations = 2;

            while (possibleCombinations < fullPreambleInput.length) {
                double valueInARun = 0;
                double smallest = 9999999999999999999.9;
                double biggest = 0;

                for (int j = i; j < possibleCombinations; j++) {

                    if (smallest > fullPreambleInput[j]) smallest = fullPreambleInput[j];
                    if (biggest < fullPreambleInput[j]) biggest = fullPreambleInput[j];

                    //if reached riddleInput it cannot be valid combination any longer
                    if (fullPreambleInput[j] == riddle1Result) {
                        break;
                    }
                    valueInARun = valueInARun + fullPreambleInput[j];
                    //if valueIn the run > riddleInput -> also not right
                    if (valueInARun > riddle1Result) {
                        break;
                    }

                    if (valueInARun == riddle1Result) {
                        System.out.println(BigDecimal.valueOf(valueInARun));
                        System.out.println("smallest: " + BigDecimal.valueOf(smallest));
                        System.out.println("biggest: " + BigDecimal.valueOf(biggest));
                        System.out.println("sum is: " + BigDecimal.valueOf(smallest + biggest));
                        break;
                    }
                }
                if (valueInARun > riddle1Result) {
                    // System.out.println("Forcing break as value "+valueInARun +" is greater than "+riddle1Result);
                    break;
                }
                if (valueInARun == riddle1Result) break;
                possibleCombinations++;
            }

        }

    }
}
