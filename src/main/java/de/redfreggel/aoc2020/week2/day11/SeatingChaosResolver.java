package de.redfreggel.aoc2020.week2.day11;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatingChaosResolver {
    //  public static final char floor = '.';
    public static final char emptySeat = 'L';
    public static final char occupiedSeat = '#';

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay11_2020.txt");
        String text;
        char[][] riddleInput1 = null;
        char[][] riddleInput2 = null;

        //Read the input
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
                riddleInput2 = new char[input.size()][inputText.length()];
            }
            for (int j = 0; j < inputText.length(); j++) {
                riddleInput1[i][j] = inputText.charAt(j);
                riddleInput2[i][j] = inputText.charAt(j);
            }
        }

        solveRiddleOne(riddleInput1);
        solveRiddleTwo(riddleInput2);
    }


    private static void solveRiddleTwo(char[][] riddleInput) {
        System.out.println(" Entering riddle 2");
        boolean seatChanged;
        do {
            seatChanged = false;
            char[][] newInput = new char[riddleInput.length][riddleInput[1].length];



            for (int i = 0; i < riddleInput.length; i++) {
                for (int j = 0; j < riddleInput[i].length; j++) {
                    char currentSeat = riddleInput[i][j];
                    /*
                   Also, people seem to be more tolerant than you expected:
                   - it now takes five or more visible occupied seats for an occupied seat to become empty (rather than four or more from the previous rules).
                   - The other rules still apply: empty seats that see no occupied seats become occupied, seats matching no rule don't change, and floor never changes.
                     */
                    int adjacentOccupied = countAdjacentOccupationsRiddle2(i, j, riddleInput, riddleInput.length, riddleInput[1].length);

                    if (currentSeat == emptySeat && adjacentOccupied == 0) {
                        newInput[i][j] = occupiedSeat;
                    } else if (currentSeat == occupiedSeat && adjacentOccupied >= 5) {

                        newInput[i][j] = emptySeat;
                    } else {
                        newInput[i][j] = riddleInput[i][j];
                    }
                }

            }

            for (int i = 0; i < riddleInput.length; i++) {
                seatChanged = seatChanged || Arrays.compare(riddleInput[i], newInput[i]) != 0;
            }

            riddleInput = newInput;
        } while (seatChanged);

        System.out.println("seats stabilized - start counting the seats");

        int seatCounter = 0;
        for (char[] chars : riddleInput) {
            for (char aChar : chars) {
                if (aChar == occupiedSeat) seatCounter++;
            }
        }
        System.out.println("counting finished! Result = " + seatCounter);
    }

    private static void solveRiddleOne(char[][] riddleInput) {
        boolean seatChanged;
        do {
            seatChanged = false;
            char[][] newInput = new char[riddleInput.length][riddleInput[1].length];

            for (int i = 0; i < riddleInput.length; i++) {
                for (int j = 0; j < riddleInput[i].length; j++) {
                    char currentSeat = riddleInput[i][j];
                    int adjacentOccupied = countAdjacentOccupationsRiddle1(i, j, riddleInput, riddleInput.length, riddleInput[1].length);

                    if (currentSeat == emptySeat && adjacentOccupied == 0) {
                        newInput[i][j] = occupiedSeat;
                    } else if (currentSeat == occupiedSeat && adjacentOccupied >= 4) {
                        newInput[i][j] = emptySeat;
                    } else {
                        newInput[i][j] = riddleInput[i][j];
                    }
                }

            }

            for (int i = 0; i < riddleInput.length; i++) {
                seatChanged = seatChanged || Arrays.compare(riddleInput[i], newInput[i]) != 0;
            }

            riddleInput = newInput;

        } while (seatChanged);

        System.out.println("seats stabilized - start counting the seats");

        int seatCounter = 0;
        for (char[] chars : riddleInput) {
            for (char aChar : chars) {
                if (aChar == occupiedSeat) seatCounter++;
            }
        }
        System.out.println("counting finished! Result = " + seatCounter);
    }

    private static int occupied(int x, int y, char[][] floorPlan, int lineCount, int columnCount) {
        if (x >= 0 && x < lineCount && y >= 0 && y < columnCount) {
            if (floorPlan[x][y] == occupiedSeat) {
                return 1;
            }
        }
        return 0;
    }

    private static int countAdjacentOccupationsRiddle1(int line, int column, char[][] floorPlan, int lineCount, int columnCount) {
        return
                occupied(line - 1, column - 1, floorPlan, lineCount, columnCount) +
                        occupied(line - 1, column, floorPlan, lineCount, columnCount) +
                        occupied(line - 1, column + 1, floorPlan, lineCount, columnCount) +
                        occupied(line, column - 1, floorPlan, lineCount, columnCount) +
                        occupied(line, column + 1, floorPlan, lineCount, columnCount) +
                        occupied(line + 1, column - 1, floorPlan, lineCount, columnCount) +
                        occupied(line + 1, column, floorPlan, lineCount, columnCount) +
                        occupied(line + 1, column + 1, floorPlan, lineCount, columnCount)
                ;
    }

    private static boolean hasActualFieldToCheck(int x, int y, int lineCount, int columnCount){
        return (x >= 0 && x < lineCount && y >= 0 && y < columnCount);
    }

    private static boolean isSeat(int x, int y, char[][] floorPlan, int lineCount, int columnCount) {
        if (x >= 0 && x < lineCount && y >= 0 && y < columnCount) {
            return floorPlan[x][y] == occupiedSeat || floorPlan[x][y] == emptySeat;
        }
        return false;
    }



    private static int lookUpForNextVisibleSeat(int x, int y,char[][] floorPlan,int lineCount, int columnCount,int adjustX, int adjustY){

        if(hasActualFieldToCheck(x,y,lineCount,columnCount)){
            if(isSeat(x,y,floorPlan,lineCount,columnCount)){
                return occupied(x,y,floorPlan,lineCount,columnCount);
            }else{
                x = x+adjustX;
                y = y+adjustY;
                return lookUpForNextVisibleSeat(x,y,floorPlan,lineCount,columnCount,adjustX,adjustY);
            }
        }
        return 0;
    }

    private static int countAdjacentOccupationsRiddle2(int line, int column, char[][] floorPlan, int lineCount, int columnCount) {

        return
                lookUpForNextVisibleSeat(line - 1, column - 1, floorPlan, lineCount, columnCount,-1,-1) +
                        lookUpForNextVisibleSeat(line - 1, column, floorPlan, lineCount, columnCount,-1,0) +
                        lookUpForNextVisibleSeat(line - 1, column + 1, floorPlan, lineCount, columnCount,-1,1) +
                        lookUpForNextVisibleSeat(line, column - 1, floorPlan, lineCount, columnCount,0,-1) +
                        lookUpForNextVisibleSeat(line, column + 1, floorPlan, lineCount, columnCount,0,1) +
                        lookUpForNextVisibleSeat(line + 1, column - 1, floorPlan, lineCount, columnCount,1,-1) +
                        lookUpForNextVisibleSeat(line + 1, column, floorPlan, lineCount, columnCount,1,0) +
                        lookUpForNextVisibleSeat(line + 1, column + 1, floorPlan, lineCount, columnCount,1,1)
                ;
    }
}
