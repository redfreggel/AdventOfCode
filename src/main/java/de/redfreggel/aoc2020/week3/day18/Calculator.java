package de.redfreggel.aoc2020.week3.day18;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Calculator {

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay18_2020.txt");
        solvePart1(path);
        solvePart2(path);
    }

    private static void solvePart2(Path path) {
        System.out.println("test 1 + (2 * 3) + (4 * (5 + 6))  = " + (calculatePriorityAddition("1 + (2 * 3) + (4 * (5 + 6))") == 51));
        System.out.println("test 2 * 3 + (4 * 5)  = " + (calculatePriorityAddition("2 * 3 + (4 * 5)") == 46));
        System.out.println("test 5 + (8 * 3 + 9 + 3 * 4 * 3)  = " + (calculatePriorityAddition("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 1445));
        System.out.println("test 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))  = " + (calculatePriorityAddition("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 669060));
        System.out.println("test ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2  = " + (calculatePriorityAddition("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 23340));

        String text;
        long result = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {

                text = reader.readLine();
                if (text == null) continue;
                result = result + calculatePriorityAddition(text);
            } while (text != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result riddle 18 part 2 = " + result);


    }

    private static void solvePart1(Path path) {
        System.out.println("test 2 * 3  = " + (calculateEqualPriority("2 * 3") == 6));
        System.out.println("test 2 * 3 + (4 * 5)  = " + (calculateEqualPriority("2 * 3 + (4 * 5)") == 26));
        System.out.println("test 5 + (8 * 3 + 9 + 3 * 4 * 3)  = " + (calculateEqualPriority("5 + (8 * 3 + 9 + 3 * 4 * 3)") == 437));
        System.out.println("test 5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))  = " + (calculateEqualPriority("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))") == 12240));
        System.out.println("test ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2  = " + (calculateEqualPriority("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2") == 13632));

        String text;

        long result = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;
                result = result + calculateEqualPriority(text);
            } while (text != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("result riddle 18 part 1 = " + result);

    }

    private static long calculatePriorityAddition(String text) {
        long result;
        //example lines 1 + (2 * 3) + (4 * (5 + 6))
        StringBuilder build = new StringBuilder(text);
        long bracketCount = build.chars().filter(ch -> ch == '(').count();
        while (bracketCount > 0) {
            //example lines 1 + (2 * 3) + (4 * (5 + 6))
            //get the first opening of that
            int index = build.indexOf("(");
            int endIndex = 0;
            //look for the corresponding index of this one
            int meanwhileOpenBrackets = 0;
            int countCloseBrackets = 0;
            for (int i = 0; i < build.length(); i++) {
                if (build.charAt(i) == '(') {
                    meanwhileOpenBrackets++;
                }
                if (meanwhileOpenBrackets == 0 && build.charAt(i) == ')') {
                    endIndex = i;
                    break;
                }
                if (build.charAt(i) == ')') {
                    countCloseBrackets++;
                    if (meanwhileOpenBrackets == countCloseBrackets) {
                        endIndex = i;
                        break;
                    }
                }
            }
            String subBracketsToHandle = build.substring(index + 1, endIndex);
            //    System.out.println(subBracketsToHandle);
            long resultToReplace = calculatePriorityAddition(subBracketsToHandle);
            build.replace(index, endIndex + 1, Long.toString(resultToReplace));
            bracketCount = build.chars().filter(ch -> ch == '(').count();
        }

        //Here the calculation
        //Example 2 * 3 + (4 * 5)
        //-> 2 * 3 + 20
        //-> 2 * 23
        //-> 46

        result = 1;
        String[] valuesToMultiply = build.toString().split(" \\* ");

        for(int i = 0; i<valuesToMultiply.length; i++){
            if(valuesToMultiply[i].contains("+")){
                long tempValue =0;
                String[] valuesToAdd = valuesToMultiply[i].split(" \\+ ");
                for (String s : valuesToAdd) {
                    tempValue = tempValue + Long.parseLong(s);
                }
                valuesToMultiply[i] = Long.toString(tempValue);
            }
            result = result*Long.parseLong(valuesToMultiply[i]);
        }
        return result;
    }


    private static long calculateEqualPriority(String text) {
        //  System.out.println("handling : "+text);
        long result;
        //example lines 1 + (2 * 3) + (4 * (5 + 6))
        StringBuilder build = new StringBuilder(text);

        long bracketCount = build.chars().filter(ch -> ch == '(').count();
        while (bracketCount > 0) {
            //example lines 1 + (2 * 3) + (4 * (5 + 6))
            //get the first opening of that
            int index = build.indexOf("(");
            int endIndex = 0;
            //look for the corresponding index of this one
            int meanwhileOpenBrackets = 0;
            int countCloseBrackets = 0;
            for (int i = 0; i < build.length(); i++) {
                if (build.charAt(i) == '(') {
                    meanwhileOpenBrackets++;
                }
                if (meanwhileOpenBrackets == 0 && build.charAt(i) == ')') {
                    endIndex = i;
                    break;
                }
                if (build.charAt(i) == ')') {
                    countCloseBrackets++;
                    if (meanwhileOpenBrackets == countCloseBrackets) {
                        endIndex = i;
                        break;
                    }
                }
            }
            String subBracketsToHandle = build.substring(index + 1, endIndex);
            //    System.out.println(subBracketsToHandle);
            long resultToReplace = calculateEqualPriority(subBracketsToHandle);
            build.replace(index, endIndex + 1, Long.toString(resultToReplace));
            bracketCount = build.chars().filter(ch -> ch == '(').count();
        }

        String[] values = build.toString().split(" ");
        for (int i = 2; i < values.length; i = i + 2) {
            long value1 = Long.parseLong(values[i - 2]);
            long value2 = Long.parseLong(values[i]);
            if ('+' == values[i - 1].charAt(0)) {
                values[i] = Long.toString(value1 + value2);
            } else {
                values[i] = Long.toString(value1 * value2);
            }
        }
        result = Long.parseLong(values[values.length - 1]);
        return result;
    }
}
