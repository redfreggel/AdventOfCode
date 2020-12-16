package de.redfreggel.aoc2020.week3.day16;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TicketTranslationHelper {

    public static final String myTicketIdentifier = "your ticket:";
    public static final String ticketDelimiter = ",";
    public static final String emptyLine = "";
    public static final String nearByTicketIdentifier = "nearby tickets:";
    public static final String regexRangeSplit = "-|\\b or \\b";


    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay16_2020.txt");
        solvePart1(path);
        solvePart2(path);
    }

    private static void solvePart2(Path path) {
        String text;
        long[] myTicketNumbers = null;
        List<long[]> otherTickets = null;
        Set<Range> availableRanges = new HashSet<>();

        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;

                if (emptyLine.equals(text)) {
                    text = reader.readLine();
                }

                if (myTicketIdentifier.equals(text)) {
                    text = reader.readLine();
                    myTicketNumbers = extractTicketValues(text);
                    continue;
                }
                if (nearByTicketIdentifier.equals(text)) {
                    otherTickets = new ArrayList<>();
                    continue;
                }
                if (myTicketNumbers != null && myTicketNumbers.length > 0 && otherTickets != null) {
                    otherTickets.add(extractTicketValues(text));
                    continue;
                }
                String rangeName = text.substring(0, text.indexOf(':'));
                String[] ranges = text.substring(text.indexOf(':') + 2).split(regexRangeSplit);
                Range range = new Range(rangeName, Long.parseLong(ranges[0]), Long.parseLong(ranges[1]), Long.parseLong(ranges[2]), Long.parseLong(ranges[3]));
                availableRanges.add(range);
            } while (text != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Input has been read.. now check on values

        List<long[]> cleanedTickets = new ArrayList<>();
        for (long[] values : otherTickets) {
            boolean toKeepValues = fitsRowToARange(values, availableRanges);
            if (toKeepValues) cleanedTickets.add(values);
        }


        Map<Long, List<String>> possibleValuesForMatch = new HashMap<>();
        for (int i = 0; i < myTicketNumbers.length; i++) {

            //go through the available values
            for (Range range : availableRanges) {
                boolean eachNumberMatchesRange = true;
                for (long[] keptValues : cleanedTickets) {
                    eachNumberMatchesRange = eachNumberMatchesRange && range.fitNumberToRange(keptValues[i]);
                    if (!eachNumberMatchesRange) break;
                }
                if (eachNumberMatchesRange) {
                    if (range.fitNumberToRange(myTicketNumbers[i])) {
                        //to check if the range alrady exists. There could be conflicts!
                        if (possibleValuesForMatch.containsKey(myTicketNumbers[i])) {
                            List<String> list = possibleValuesForMatch.get(myTicketNumbers[i]);
                            list.add(range.rangeName);
                        } else {
                            List<String> list = new ArrayList<>();
                            list.add(range.rangeName);
                            possibleValuesForMatch.put(myTicketNumbers[i], list);
                        }
                    }
                }
            }
        }
   //     System.out.println(possibleValuesForMatch);
        List<Long> handledValues = new ArrayList<>();
        List<String> keyWordsToRemove = new ArrayList<>();
        Map<String, Long> matchingValuesToRange = new HashMap<>();
        boolean everythingSorted = false;
        while (!everythingSorted) {
            for (long number : possibleValuesForMatch.keySet()) {
                List<String> possibleValues = possibleValuesForMatch.get(number);
                if (possibleValues.size() == 1) {
                    handledValues.add(number);
                    keyWordsToRemove.add(possibleValues.get(0));
                    matchingValuesToRange.put(possibleValues.get(0), number);
                }
            }
            for (Long key : handledValues) {
                possibleValuesForMatch.remove(key);
            }
            for (long number : possibleValuesForMatch.keySet()) {
                for (String keyword : keyWordsToRemove) {
                    List<String> possibleValues = possibleValuesForMatch.get(number);
                    possibleValues.remove(keyword);
                }
            }
            if (possibleValuesForMatch.size() == 0) {
                everythingSorted = true;
            }
        }
        //System.out.println(matchingValuesToRange);
        long result = 1;
        for (String keyword : matchingValuesToRange.keySet()) {
            if (keyword.contains("departure")) {
                result = result * matchingValuesToRange.get(keyword);
            }
        }

        System.out.println("Result part 2: " + result);


    }

    public static boolean fitsRowToARange(long[] valueToHandle, Set<Range> ranges) {
        boolean matchFound = true;

        for (long value : valueToHandle) {
            boolean valueMatchedAnyRange = false;
            for (Range range : ranges) {
                valueMatchedAnyRange = valueMatchedAnyRange || range.fitNumberToRange(value);
            }
            matchFound = matchFound && valueMatchedAnyRange;
        }

        return matchFound;

    }

    private static void solvePart1(Path path) {
        String text;
        long[] myTicketNumbers = null;
        List<long[]> otherTickets = null;
        Set<Range> availableRanges = new HashSet<>();
        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;

                if (emptyLine.equals(text)) {
                    text = reader.readLine();
                }

                if (myTicketIdentifier.equals(text)) {
                    text = reader.readLine();
                    myTicketNumbers = extractTicketValues(text);
                    continue;
                }
                if (nearByTicketIdentifier.equals(text)) {
                    otherTickets = new ArrayList<>();
                    continue;
                }
                if (myTicketNumbers != null && myTicketNumbers.length > 0 && otherTickets != null) {
                    otherTickets.add(extractTicketValues(text));
                    continue;
                }
                String rangeName = text.substring(0, text.indexOf(':'));
                String[] ranges = text.substring(text.indexOf(':') + 2).split(regexRangeSplit);
                Range range = new Range(rangeName, Long.parseLong(ranges[0]), Long.parseLong(ranges[1]), Long.parseLong(ranges[2]), Long.parseLong(ranges[3]));
                availableRanges.add(range);
            } while (text != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Input has been read.. now check on values
        long result = 0;
        for (long[] values : otherTickets) {
            for (long value : values) {
                boolean valueFitsToAnyRange = false;
                for (Range range : availableRanges) {
                    valueFitsToAnyRange = valueFitsToAnyRange || range.fitNumberToRange(value);
                }
                if (!valueFitsToAnyRange) {
                    //  System.out.println(value);
                    result = result + value;
                }
            }

        }
        System.out.println("Result riddle 1=" + result);
    }

    public static long[] extractTicketValues(String pText) {
        String[] delimitedValues = pText.split(ticketDelimiter);
        long[] extractedValues = new long[delimitedValues.length];
        for (int i = 0; i < delimitedValues.length; i++) {
            extractedValues[i] = Long.parseLong(delimitedValues[i]);
        }
        return extractedValues;
    }

    private static class Range {
        private final String rangeName;
        private final long start1;
        private final long end1;
        private final long start2;
        private final long end2;

        public Range(String pRangeName, long pStart1, long pEnd1, long pStart2, long pEnd2) {
            this.rangeName = pRangeName;
            this.start1 = pStart1;
            this.end1 = pEnd1;
            this.start2 = pStart2;
            this.end2 = pEnd2;
        }

        public boolean fitNumberToRange(long pValue) {
            return (start1 <= pValue && end1 >= pValue) || (start2 <= pValue && end2 >= pValue);
        }
    }
}
