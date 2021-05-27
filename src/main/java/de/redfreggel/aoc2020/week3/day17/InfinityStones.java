package de.redfreggel.aoc2020.week3.day17;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class InfinityStones {

    public static final char activePosition = '#';
    public static final char inactivePosition = '.';

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay17_2020.txt");

        String text;
        char[][] riddleInput1 = null;
        List<String> input = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;
                input.add(text);
            } while (text != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //make list of text consumable for the riddle
        for (int i = 0; i < input.size(); i++) {
            String inputText = input.get(i);
            if (i == 0 && inputText.length() > 0) {
                riddleInput1 = new char[input.size()][inputText.length()];
            }
            for (int j = 0; j < inputText.length(); j++) {
                riddleInput1[i][j] = inputText.charAt(j);
            }
        }

        Map<Integer, char[][]> initialInput = new HashMap<>();
        initialInput.put(0, riddleInput1);
        solveRiddle1(initialInput);
    }

    private static void solveRiddle1(Map<Integer, char[][]> riddleInput1) {

        int iterations = 6;
        for (int currentIteration = 0; currentIteration < iterations; currentIteration++) {

            //get minimumKey
            List<Integer> zPositions = new ArrayList<>(riddleInput1.keySet());
            Collections.sort(zPositions);




            //preparing new input
            List<Integer> newKeys = new ArrayList<>(riddleInput1.keySet());
            int minimumKeyZ = zPositions.get(0) - 1;
            int maximumZ = zPositions.get(zPositions.size() - 1) + 1;
            newKeys.add(minimumKeyZ);
            newKeys.add(maximumZ);
            int newXLength = riddleInput1.get(0).length + 2;
            int newYLength = riddleInput1.get(0)[0].length + 2;


            //now i need to go through each of the possibleOptions
            Collections.sort(newKeys);

            Map<Integer, char[][]> temporaryInput = new HashMap<>();

            for (Integer z : newKeys) {
                char[][] temporaryInputPosZ = new char[newXLength][newYLength];//input which needs to be filled
                for (int i = 0; i < newXLength; i++) { //row
                    for (int j = 0; j < newXLength; j++) { //column
                        //this is the current position to handle

                        int sourroundingActives = 0;

                        char[][] virtualExpandedComparableInput = new char[newXLength][newXLength];
                        // char[][] inputToCompareWith = null;


                        for (int positionChecker = -1; positionChecker < 2; positionChecker++) {
                            //System.out.println("position Checker pos = "+positionChecker);
                            //get the grid before, actual or after
                            if (!riddleInput1.containsKey(z + positionChecker)) {
                               continue;
                            }else{
                                //make a copy
                                for (int a = -1; a < newXLength - 1; a++) {
                                    for (int b = -1; b < newYLength - 1; b++) {
                                        //  System.out.println(a+" "+b);
                                        if (a == -1 || b == -1) {
                                            virtualExpandedComparableInput[a + 1][b + 1] = inactivePosition;
                                            //  System.out.println(a+" "+b +" "+inactivePosition);
                                        } else if (a == newXLength - 2 || b == newYLength - 2) {
                                            virtualExpandedComparableInput[a + 1][b + 1] = inactivePosition;
                                        } else {
                                            // char[][] temp = riddleInput1.get(z-postionChecker);
                                            virtualExpandedComparableInput[a + 1][b + 1] = riddleInput1.get(z + positionChecker)[a][b];
                                        }
                                    }
                                }
                            }


                            //Check the grid

                            if (positionChecker == 0) { // if same position -> only count Adjecent
                                sourroundingActives = sourroundingActives+ countAdjacentOccupationsRiddle1(i, j, virtualExpandedComparableInput, newXLength, newYLength);
                            } else {
                                sourroundingActives =  sourroundingActives+ countAllOccupationsRiddle1(i, j, virtualExpandedComparableInput, newXLength, newYLength);
                            }

                        }

                    //    System.out.println("Handle position of new input.. x=" + i + " j=" + j + " z=" + z + " surrounding = " + sourroundingActives);
                        boolean isCubeCurrentlyHandledActive = false;


                        if (riddleInput1.containsKey(z)) {
                            if (virtualExpandedComparableInput[i][j] == activePosition) {
                                isCubeCurrentlyHandledActive = true;
                            }
                        }

                        //check if cube is active
                        if (sourroundingActives == 3 && !isCubeCurrentlyHandledActive) {
                            temporaryInputPosZ[i][j] = activePosition;
                        } else if ((sourroundingActives == 2 || sourroundingActives == 3) && isCubeCurrentlyHandledActive) {
                            temporaryInputPosZ[i][j] = activePosition;
                        } else {
                            temporaryInputPosZ[i][j] = inactivePosition;
                        }
                    }


                    //  if(currentIteration== 1)break;
                }
                temporaryInput.put(z, temporaryInputPosZ);


            }


            riddleInput1 = temporaryInput;
            //      System.out.println(riddleInput1);
            System.out.println("-----ENDE interation " + currentIteration + "----------------");

            List<Integer> keys = new ArrayList<>(riddleInput1.keySet());
            Collections.sort(keys);
            for (Integer key : keys) {
                System.out.println("key = " + key);
                for (char[] row : riddleInput1.get(key)) {
                    System.out.println(Arrays.toString(row));
                }
            }
           // if (currentIteration == 2) break;
        }
        //now go through riddleInput and count #
        int counter = 0;
        for(Integer key : riddleInput1.keySet()){
            char[][] values = riddleInput1.get(key);
            for (char[] value : values) {
                for (char c : value) {
                    if (activePosition == c) counter++;
                }
            }
        }
        System.out.println("Riddle 1 counter = "+counter);
    }

    private static int countAdjacentOccupationsRiddle1(int line, int column, char[][] floorPlan, int lineCount, int columnCount) {
        return occupied(line - 1, column - 1, floorPlan, lineCount, columnCount) +
                occupied(line - 1, column, floorPlan, lineCount, columnCount) +
                occupied(line - 1, column + 1, floorPlan, lineCount, columnCount) +
                occupied(line, column - 1, floorPlan, lineCount, columnCount) +
                occupied(line, column + 1, floorPlan, lineCount, columnCount) +
                occupied(line + 1, column - 1, floorPlan, lineCount, columnCount) +
                occupied(line + 1, column, floorPlan, lineCount, columnCount) +
                occupied(line + 1, column + 1, floorPlan, lineCount, columnCount);
    }

    private static int countAllOccupationsRiddle1(int line, int column, char[][] floorPlan, int lineCount, int columnCount) {
        return
                occupied(line - 1, column - 1, floorPlan, lineCount, columnCount) +
                        occupied(line - 1, column, floorPlan, lineCount, columnCount) +
                        occupied(line - 1, column + 1, floorPlan, lineCount, columnCount) +
                        occupied(line, column - 1, floorPlan, lineCount, columnCount) +
                        occupied(line, column + 1, floorPlan, lineCount, columnCount) +
                        occupied(line + 1, column - 1, floorPlan, lineCount, columnCount) +
                        occupied(line + 1, column, floorPlan, lineCount, columnCount) +
                        occupied(line + 1, column + 1, floorPlan, lineCount, columnCount) +
                        occupied(line, column, floorPlan, lineCount, columnCount);
    }

  /*  private static boolean hasActualFieldToCheck(int x, int y, int lineCount, int columnCount) {
        return (x >= 0 && x < lineCount && y >= 0 && y < columnCount);
    }
*/
    
    private static int occupied(int x, int y, char[][] grid, int lineCount, int columnCount) {
        if (x >= 0 && x < lineCount && y >= 0 && y < columnCount) {
            if (grid[x][y] == activePosition) {
                return 1;
            }
        }
        return 0;
    }
}
