package de.redfreggel.aoc2020.day2;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PasswordCracker {
    public static final char delimiterPW = ':';
    public static final char delimiterMinMax = '-';
    public static final char delimiterChar = ' ';

    public static void main(String[] args){
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay2_2020_task_1.txt");
        List<PasswordChecker> passwordsToCheck = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String text;

            /*input line looks like this for Riddle 1
             5-9 g: ggccggmgn
             5-9 <- amount of values
             g   <- letter to look for
             :   <- separator
             ggccggmgn <-password to check
             */
            while ((text = reader.readLine()) != null) {
                //initalize variables
                int minOccur;
                int maxOccur;
                char charToValidate;
                String passwordToCheck;
                //Here the password needs to be Split
                passwordToCheck = text.substring(text.indexOf(delimiterPW)+1).trim();
                minOccur = Integer.parseInt(text.substring(0,text.indexOf(delimiterMinMax)));
                maxOccur = Integer.parseInt(text.substring(text.indexOf(delimiterMinMax)+1,text.indexOf(delimiterChar)));
                charToValidate = text.substring(text.indexOf(delimiterChar)+1,text.indexOf(delimiterPW)).charAt(0);

                //create the method and put it to the list
                PasswordChecker pwtoCheck = new PasswordChecker(passwordToCheck,minOccur,maxOccur,charToValidate);
                passwordsToCheck.add(pwtoCheck);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int countCorrectPWsRiddle1 = 0;
        int countCorrectPWsRiddle2 = 0;
        for(PasswordChecker checkPw: passwordsToCheck){
            if(checkPw.isValidRiddle1()) countCorrectPWsRiddle1++;
            if(checkPw.isValidRiddle2()) countCorrectPWsRiddle2++;
        }
        System.out.println("Correct passwords given for Riddle 1: "+countCorrectPWsRiddle1 );
        System.out.println("Correct passwords given for Riddle 2: "+countCorrectPWsRiddle2 );

    }
}
