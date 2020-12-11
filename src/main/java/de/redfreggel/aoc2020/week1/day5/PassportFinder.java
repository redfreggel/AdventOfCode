package de.redfreggel.aoc2020.week1.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PassportFinder {
    /*
    A seat might be specified like FBFBBFFRLR, where
    F means "front"
    B means "back"
    L means "left"
    R means "right"

    first 7 characters will either be F or B
    these specify exactly one of the 128 rows on the plane (numbered 0 through 127)
    the first letter indicates whether the seat is in the front (0 through 63) or the back (64 through 127)

    last three characters will be either L or R;
    one of the 8 columns of seats on the plane (numbered 0 through 7)
    L means to keep the lower half, while R means to keep the upper half.

    seat ID: multiply the row by 8, then add the column


    Examples:
        BFFFBBFRRR: row 70, column 7, seat ID 567.
        FFFBBBFRRR: row 14, column 7, seat ID 119.
        BBFFBBFRLL: row 102, column 4, seat ID 820.
     */

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay5_2020.txt");

        double highestSeat=0;
        List<Double> seatsFilled= new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String text;

            do {
                text = reader.readLine();
                if (text == null) continue;
                if (text.length() == 10) {
                    String rowPuzzle = text.substring(0, 7);
                 //   System.out.println(rowPuzzle);
                    String columnPuzzle = text.substring(7, 10);
                 //   System.out.println(columnPuzzle);

                    //identify Row
                    int smallestRow = 0;
                    int highestRow = 127;
                    int matchedRow = 0;
                    for (int i = 0; i < rowPuzzle.length(); i++) {
                        int diff = (highestRow - smallestRow) / 2;
                        switch (rowPuzzle.charAt(i)) {
                            case 'B':
                                smallestRow = smallestRow + diff + 1;
                                matchedRow = highestRow;
                                break;
                            case 'F':
                                highestRow = highestRow - diff - 1;
                                matchedRow = smallestRow;
                                break;
                            default:
                                //do nothing
                        }
                    }

                    int smallestColumn = 0;
                    int highestColumn = 7;
                    int matchedColumn= 0;
                    for (int i = 0; i < columnPuzzle.length(); i++) {
                        int diff = (highestColumn - smallestColumn) / 2;
                        switch (columnPuzzle.charAt(i)) {
                            case 'R':
                                smallestColumn = smallestColumn + diff + 1;
                                matchedColumn = highestColumn;
                                break;
                            case 'L':
                                highestColumn = highestColumn - diff - 1;
                                matchedColumn = smallestColumn;
                                break;
                            default:
                                //do nothing
                        }
                    }
                    double seatId = ((matchedRow*8)+matchedColumn);
                    seatsFilled.add(seatId);
                    if(highestSeat< seatId) highestSeat = seatId;
                }

            } while (text != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("HighestSeat: " + highestSeat);
        Collections.sort(seatsFilled);
        double mySeat = 0.0;
        for(int i = 0; i< seatsFilled.size(); i++){

            if(i== 0) continue;
            if( i<seatsFilled.size()-1){
                //previousSeat
                Double previousSeat = seatsFilled.get(i-1);
                Double currentSeat = seatsFilled.get(i);
                Double nextSeat = seatsFilled.get(i+1);

                if(previousSeat+1 != currentSeat){
                    mySeat=previousSeat+1;
                    break;
                }
                if(currentSeat+1 != nextSeat){
                    mySeat=currentSeat+1;
                    break;
                }
            }

        }
        System.out.println("MySeatId: "+mySeat);
    }
}
