package de.redfreggel.aoc2020.day8;


import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class InfinityLoopController {
    public static final String commandDoNothing ="nop";
    public static final String commandAccumulate = "acc";
    public static final String commandJump="jmp";


    public static void main(String[] args){
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay8_2020.txt");

        String text;
        Map<Integer, PositionControl>  positionInput= new HashMap<>();
        int position = 0;
        //read input first
        int countNopeJump =0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if(text == null)continue;
                String[] separated = text.split(" ");
                positionInput.put(position, new PositionControl(separated[0], Integer.parseInt(separated[1])));
                position++;
                if(commandJump.equals(separated[0]) || commandDoNothing.equals(separated[0])) countNopeJump++;
            } while (text != null);
        }catch (Exception e){
            e.printStackTrace();
        }


        boolean doubleExecutionDetected;
        int accumulator=0;
        int originPosition = -1;
        PositionControl originPositionControl = null;
        Set<Integer> positionExchanged = new HashSet<>();
        //run through the count of
        for(int i=0; i<countNopeJump;i++){
            doubleExecutionDetected = false;
            position = 0;
            accumulator =0;

            boolean hasChanged = false;
            if(originPosition != -1 && originPositionControl != null){
                positionInput.put(originPosition,originPositionControl);
                //loop through and set to not visited;
                for(int j= 0; j<positionInput.size(); j++){
                    positionInput.get(j).resetCommandExecuted();
                }
            }

            System.out.println("-------------------Running variant: "+i+" of "+countNopeJump+" ----------------");
            do{
                PositionControl positionControl = positionInput.get(position);
                if(position>=positionInput.size()){
                    break;
                }

                if(positionControl.hasCommandExecuted()){
                    doubleExecutionDetected = true;
                    continue;
                }


                switch (positionControl.getCommand()){
                    case commandDoNothing:
                        //if it hasn't exchanged a position
                        if(!hasChanged){
                            //and the current position is not the already exchanged one
                            if(positionExchanged.size()>0 && positionExchanged.contains(position)){
                                // do not exchange and proceed as normal
                                position++;
                            }else{
                                hasChanged = true;
                                originPosition = position;
                                positionExchanged.add(position);

                                originPositionControl = positionControl;
                                PositionControl changedControl = new PositionControl(commandJump,positionControl.getOffSet());
                                changedControl.commandExecuted();
                                position = position+positionControl.getOffSet();
                                positionInput.put(originPosition,changedControl);

                   //             System.out.println("Exchanging position from JUMP to NOPE at position: "+originPosition+" next Position: "+position);
                            }
                        }else{
                            position++;
                        }
                        break;
                    case commandJump:
                        if(!hasChanged ){

                            if(positionExchanged.size()>0 && positionExchanged.contains(position)){
                                // do not exchange and proceed as normal
                                position = position+positionControl.getOffSet();
                            }
                            else{
                                hasChanged = true;
                                originPosition = position;
                                positionExchanged.add(position);

                                originPositionControl = positionControl;
                                PositionControl changedControl = new PositionControl(commandDoNothing,positionControl.getOffSet());
                                positionInput.put(originPosition,changedControl);
                                changedControl.commandExecuted();
                                position++;

                            //    System.out.println("Exchanging position from JUMP to NOPE at position: "+originPosition+" next Position: "+position);

                            }
                        }else{
                            position = position+positionControl.getOffSet();
                        }
                        break;
                    case commandAccumulate:
                        position++;
                        accumulator = accumulator+positionControl.getOffSet();
                 //       System.out.println("Accumulating : "+positionControl.getOffSet() +" TempResult: "+accumulator);
                        break;
                }
                positionControl.commandExecuted();

            }while(!doubleExecutionDetected);

          //  System.out.println("Accumulation = "+accumulator);

            //if no doubleExecution was detected
            if(!doubleExecutionDetected){
                break;
            }
            if(positionInput.size()<position) break;
        }

        System.out.println("Accumulation = "+accumulator);
    }
    private static class PositionControl{
        private boolean commandExecuted= false;
        private final String command;
        private final int offSet;

        public  PositionControl(String pCommand, int pOffSet){
            this.command = pCommand;
            this.offSet = pOffSet;
        }
        public void commandExecuted(){
            commandExecuted = true;
        }

        public boolean hasCommandExecuted(){
            return commandExecuted;
        }

        public String getCommand(){
            return this.command;
        }

        public int getOffSet(){
            return this.offSet;
        }

        public void resetCommandExecuted(){
            this.commandExecuted = false;
        }
    }
}
