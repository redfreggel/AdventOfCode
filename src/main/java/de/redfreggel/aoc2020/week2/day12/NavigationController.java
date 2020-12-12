package de.redfreggel.aoc2020.week2.day12;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NavigationController {

    private static final char[] directions = new char[]{'E','S','W','N'};

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay12_2020.txt");
        String text;

        Position position = new Position();
        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;

                char action =text.charAt(0);
                int movements = Integer.parseInt(text.substring(1));

                switch (action){
                    //Action N means to move north by the given value
                    case 'N':
                        position.moveNorth(movements);
                        break;
                    //Action S means to move south by the given value
                    case 'S':
                        position.moveSouth(movements);
                        break;
                    //Action E means to move east by the given value
                    case 'E':
                        position.moveEast(movements);
                        break;
                    //Action W means to move west by the given value
                    case 'W':
                        position.moveWest(movements);
                        break;
                    //Action L means to turn left the given number of degrees
                    case 'L':
                        position.switchDirectionLeft(movements);
                        break;
                    //Action R means to turn right the given number of degrees
                    case 'R':
                        position.switchDirectionRight(movements);
                        break;
                    //Action F means to move forward by the given value in the direction the ship is currently facing
                    case 'F':
                        position.moveForward(movements);
                        break;
                }

            } while (text != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(position.getManhattansDistanceResult());

    }

    private static class Position{
        private int x = 0;
        private int y= 0;
        private char direction = 'E';
        private int directionPosition = 0;

        public void moveForward(int value){
            switch (direction){
                case 'E':
                    x=x+value;
                    break;
                case 'W':
                    x=x-value;
                    break;
                case 'N':
                    y=y+value;
                    break;
                case 'S':
                    y=y-value;
                    break;
            }
        }

        public void moveWest(int value){
            x = x-value;
        }

        public void moveEast(int value) {
            x = x+value;
        }
        public void moveSouth(int value) {
            y = y-value;
        }
        public void moveNorth(int value) {
            y = y+value;
        }

        public void switchDirectionRight(int degrees) {
            int positionsToMove = degrees/90;

            if((directionPosition+positionsToMove)>=4){
                directionPosition = (directionPosition+positionsToMove)%4;
            }else{
                directionPosition = directionPosition+positionsToMove;

            }
         //   System.out.println("Switched direction from : "+direction+" to: "+directions[directionPosition]+" with movement by "+degrees+" degrees");
            direction = directions[directionPosition];
        }

        public void switchDirectionLeft(int degrees) {
            int positionsToMove = degrees/90;

            if((directionPosition-positionsToMove)<0){
                directionPosition = 4+((directionPosition-positionsToMove)%4);
            }else{
                directionPosition = directionPosition-positionsToMove;

            }
         //   System.out.println("Switched direction from : "+direction+" to: "+directions[directionPosition]+" with movement by - "+degrees+" degrees");
            direction = directions[directionPosition];
        }

        public int getManhattansDistanceResult(){
            if (x <0 ) x = x*-1;
            if(y <0 ) y = y*-1;
            return x+y;
        }

    }
}
