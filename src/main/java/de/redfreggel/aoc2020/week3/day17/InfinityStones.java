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
            Map<Integer, char[][]> temporaryInput = new HashMap<>();
            //get minimumKey
            List<Integer> zPositions = new ArrayList<>(riddleInput1.keySet());
            Collections.sort(zPositions);

            //initialize x and y
            int xLength = riddleInput1.get(0).length;
            int yLength = riddleInput1.get(0)[0].length;


            //preparing new input
            int minimumKeyZ = zPositions.get(0)-1;
            int maximumZ = zPositions.get(zPositions.size() - 1)+1;
            int newXLength = xLength + 2;
            int newYLength = yLength + 2;
            for (int j = minimumKeyZ; j <= maximumZ; j++) {
                temporaryInput.put(j, new char[newXLength][newYLength]);
            }

            //now i need to go through each of the possibleOptions
            List<Integer> keysToHandle = new ArrayList<>(temporaryInput.keySet());
            Collections.sort(keysToHandle);

            for (Integer z : keysToHandle) {
                char[][] temporaryInputPosZ = temporaryInput.get(z);
                for (int i = 0; i < temporaryInputPosZ.length; i++) {
                    for (int j = 0; j < temporaryInputPosZ[i].length; j++) {
                        //this is the current position to handle

                        int sourroundingActives = 0;

                        //starting with the row upfront
                        if (riddleInput1.containsKey(z - 1)) {
                            char[][] inputToCompareWith = riddleInput1.get(z - 1);
                            //Kopiert
                            char[][] virtualExpandedComparableInput = new char[temporaryInputPosZ.length][temporaryInputPosZ[i].length];
                            for(int a = -1; a<newXLength-1; a++){
                                for(int b = -1; b<newYLength-1;b++){
                                    if(a<0 || b<0){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    } else if(a>=inputToCompareWith.length || b>=inputToCompareWith[0].length){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    }else{
                                        virtualExpandedComparableInput[a+1][b+1] = inputToCompareWith[a][b];
                                    }
                                }
                                sourroundingActives=countAllOccupationsRiddle1(i,j,virtualExpandedComparableInput,virtualExpandedComparableInput.length,virtualExpandedComparableInput[0].length);
                            }
                        }
                        //next the current row
                        if (riddleInput1.containsKey(z)) {
                            char[][] inputToCompareWith = riddleInput1.get(z);
                            //Kopiert
                            char[][] virtualExpandedComparableInput = new char[temporaryInputPosZ.length][temporaryInputPosZ[i].length];
                            for(int a = -1; a<newXLength-1; a++){
                                for(int b = -1; b<newYLength-1;b++){
                                    if(a<0 || b<0){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    } else if(a>=inputToCompareWith.length || b>=inputToCompareWith[0].length){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    }else{
                                        virtualExpandedComparableInput[a+1][b+1] = inputToCompareWith[a][b];
                                    }
                                }
                            }
                            sourroundingActives=countAdjacentOccupationsRiddle1(i,j,virtualExpandedComparableInput,virtualExpandedComparableInput.length,virtualExpandedComparableInput[0].length);

                        }
                        //last the z position behind
                        if (riddleInput1.containsKey(z + 1)) {
                            char[][] inputToCompareWith = riddleInput1.get(z + 1);
                            //Kopiert
                            char[][] virtualExpandedComparableInput = new char[temporaryInputPosZ.length][temporaryInputPosZ[i].length];
                            for(int a = -1; a<newXLength-1; a++){
                                for(int b = -1; b<newYLength-1;b++){
                                    if(a<0 || b<0){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    } else if(a>=inputToCompareWith.length || b>=inputToCompareWith[0].length){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    }else{
                                        virtualExpandedComparableInput[a+1][b+1] = inputToCompareWith[a][b];
                                    }
                                }
                            }
                            sourroundingActives=countAllOccupationsRiddle1(i,j,virtualExpandedComparableInput,virtualExpandedComparableInput.length,virtualExpandedComparableInput[0].length);
                        }

                        System.out.println("Handle position of new input.. x=" + i + " j=" + j + " z=" + z+ " surrounding = "+sourroundingActives);
                        boolean isCubeCurrentlyHandledActive = false;
                        if (riddleInput1.containsKey(z)) {
                            char[][] gridToCheckForActive = riddleInput1.get(z);
                            //Kopiert
                            char[][] virtualExpandedComparableInput = new char[temporaryInputPosZ.length][temporaryInputPosZ[i].length];
                            for(int a = -1; a<newXLength-1; a++){
                                for(int b = -1; b<newYLength-1;b++){
                                    if(a<0 || b<0){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    } else if(a>=gridToCheckForActive.length || b>=gridToCheckForActive[0].length){
                                        virtualExpandedComparableInput[a+1][b+1]= inactivePosition;
                                    }else{
                                        virtualExpandedComparableInput[a+1][b+1] = gridToCheckForActive[a][b];
                                    }
                                }
                            }

                            if(virtualExpandedComparableInput[i][j] == activePosition){
                                isCubeCurrentlyHandledActive = true;
                            }

                        }

                        //check if cube is active
                        if ((sourroundingActives == 2 || sourroundingActives == 3) && isCubeCurrentlyHandledActive) {
                            temporaryInputPosZ[i][j] = activePosition;
                        } else if (sourroundingActives == 3 && !isCubeCurrentlyHandledActive) {
                            temporaryInputPosZ[i][j] = activePosition;
                        } else {
                            temporaryInputPosZ[i][j] = inactivePosition;
                        }
                    }
                }

            }
            riddleInput1 = temporaryInput;
            //      System.out.println(riddleInput1);
            System.out.println("---------------------");
            List<Integer> keys = new ArrayList<>(riddleInput1.keySet());
            Collections.sort(keys);
            for (Integer key : keys) {
                System.out.println("key = " + key);
                for (char[] row : riddleInput1.get(key)) {
                    System.out.println(Arrays.toString(row));
                }
            }
            //if(currentIteration== 0) break;

        }
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
                        occupied(line + 1, column + 1, floorPlan, lineCount, columnCount)+
                        occupied(line, column, floorPlan, lineCount, columnCount);
    }

    private static boolean hasActualFieldToCheck(int x, int y, int lineCount, int columnCount) {
        return (x >= 0 && x < lineCount && y >= 0 && y < columnCount);
    }

    private static int occupied(int x, int y, char[][] grid, int lineCount, int columnCount) {
        if (x >= 0 && x < lineCount && y >= 0 && y < columnCount) {
            if (grid[x][y] == activePosition) {
                return 1;
            }
        }
        return 0;
    }
}
