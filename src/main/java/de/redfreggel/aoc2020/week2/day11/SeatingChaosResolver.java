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
        char[][] riddleInput = null;

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
                riddleInput = new char[input.size()][inputText.length()];
            }
            for (int j = 0; j < inputText.length(); j++) {
                riddleInput[i][j] = inputText.charAt(j);
            }
        }
        /*
        If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.

        If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.

        Otherwise, the seat's state does not change.
        Floor (.) never changes; seats don't move, and nobody sits on the floor.

        adjacent = one of the eight positions immediately up, down, left, right, or diagonal from the seat
         */

        boolean seatChanged;

        //int loop =0;
        do {
            //System.out.println("loop position: "+loop );
            seatChanged=false;
            char[][] newInput = new char[riddleInput.length][riddleInput[1].length];

            for (int i = 0; i < riddleInput.length; i++) {
                for (int j = 0; j < riddleInput[i].length; j++) {
                    char currentSeat = riddleInput[i][j];
                    int adjacentOccupied = countAdjacentOccupations(i, j, riddleInput);

                  //  if(currentSeat == floor){
                  //      newInput[i][j]= floor;
                  //      continue;
                  //  }
                    if(currentSeat == emptySeat && adjacentOccupied==0){
                        newInput[i][j] = occupiedSeat;
                      //  seatChanged = true;
                    }
                    else if(currentSeat == occupiedSeat && adjacentOccupied>=4){
                        newInput[i][j] = emptySeat;
                    //    seatChanged = true;

                    }else{
                        newInput[i][j] = riddleInput[i][j];
                    }
                }

            }

            for(int i =0; i<riddleInput.length; i++){
                seatChanged = seatChanged || Arrays.compare(riddleInput[i],newInput[i])!=0;
            }
/*
            System.out.println("origin");
            for (char[] chars : riddleInput) {
                System.out.println(Arrays.toString(chars));
            }
            System.out.println("modified");
            for (char[] chars : newInput) {
                System.out.println(Arrays.toString(chars));
            }


 */
            riddleInput = newInput;
        //    loop++;
         //   break;
        } while (seatChanged);

        System.out.println("seats stabilized - start counting the seats");

        int seatCounter = 0;
        for (char[] chars : riddleInput) {
            for (char aChar : chars) {
                if (aChar == occupiedSeat) seatCounter++;
            }
        }
        System.out.println("counting finished! Result = "+seatCounter);

    }

    public static int countAdjacentOccupations(int line, int column, char[][] floorPlan) {
        int valueToReturn = 0;

        //Check if something is on top, if line =1 than there is a row above
        if(line>0){
            char top = floorPlan[line - 1][column];
            if(top == occupiedSeat) valueToReturn++;

            //check if there is space left on the left
            if(0<(column-1)){
                char topLeft = floorPlan[line - 1][column - 1];
                if(topLeft==occupiedSeat) valueToReturn++;
            }
            if(column<floorPlan[line].length-1){
                char topRight = floorPlan[line - 1][column + 1];
                if(topRight == occupiedSeat) valueToReturn++;
            }
        }
        //check the bottom
        if((line)<floorPlan.length-1){
            char bottom = floorPlan[line + 1][column];
            if(bottom == occupiedSeat) valueToReturn++;
            //check if there is space left on the left
            if(0<(column-1)){
                char bottomLeft = floorPlan[line + 1][column - 1];
                if(bottomLeft == occupiedSeat) valueToReturn++;
            }

            if(column<floorPlan[line].length-1){
                char bottomRight = floorPlan[line + 1][column + 1];
                if(bottomRight == occupiedSeat) valueToReturn++;
            }
        }/*else{
           // System.out.println("reached Bottom " + line+ "  "+floorPlan.length);
        }*/
        //left
        if(0<(column-1)){
            char left = floorPlan[line][column - 1];
            if(left == occupiedSeat) valueToReturn++;
        }
        //right
        if((column)<floorPlan[line].length-1){
            char right = floorPlan[line][column + 1];
            if(right == occupiedSeat) valueToReturn++;
        }
        return valueToReturn;
    }
}
