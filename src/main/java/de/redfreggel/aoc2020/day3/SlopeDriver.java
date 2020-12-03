package de.redfreggel.aoc2020.day3;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SlopeDriver {

    public static final char tree = '#';

    /* Task 2:
        Right 1, down 1.
        Right 3, down 1. (This is the slope you already checked.)
        Right 5, down 1.
        Right 7, down 1.
        Right 1, down 2.
     */

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay3_2020_task_1.txt");
        List<SloppySlope> sloppySlopes = new ArrayList<>();
        sloppySlopes.add(new SloppySlope(1,1));
        sloppySlopes.add(new SloppySlope(3,1));
        sloppySlopes.add(new SloppySlope(5,1));
        sloppySlopes.add(new SloppySlope(7,1));
        sloppySlopes.add(new SloppySlope(1,2));

        /*
        Task 1
        Starting at the top-left corner of your map and following a slope of right 3 and down 1.
        How many trees would you encounter?
         */
        int slopeDown = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String fileText;

            do{
                fileText = reader.readLine();
                StringBuilder treeLine;
                if(slopeDown== 0){
                    fileText = reader.readLine();
                }
                slopeDown++;
                for(SloppySlope slope : sloppySlopes){
                    slope.moveDownByOne();
                    if(!slope.hasToCheck()){
                        continue;
                    }
                    slope.moveRight();
                    if(fileText != null) {//not at the end
                        treeLine = new StringBuilder(fileText);
                        while( treeLine.length() < slope.getBufferedRightPosition()){
                            treeLine.append(fileText);
                        }

                        if (treeLine.charAt(slope.getActualRightPosition()) == tree) slope.hitByTree();
                    }
                }
            }while(fileText !=  null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long result = 1;
        for(SloppySlope slope : sloppySlopes){
            result = result*slope.getHitByTreeCounter();
            System.out.println("Hit by trees per slope: "+slope.getHitByTreeCounter());
        }
        System.out.println("RiddleResult = "+result);
    }

}
