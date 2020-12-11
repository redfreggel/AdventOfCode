package de.redfreggel.aoc2020.week1.day2;

public class PasswordChecker {
    private final String password;
    private final int numberOfMinOccurrences;
    private final int  numberOfMaxOccurrences;
    private final char charToValidate;

    public PasswordChecker(String pPassword, int minOccur, int maxOccur, char charToValidate){
        this.password = pPassword;
        this.numberOfMinOccurrences = minOccur;
        this.numberOfMaxOccurrences = maxOccur;
        this.charToValidate = charToValidate;
    }

    public boolean isValidRiddle1(){
        //this method has to check if the given password is valid
        long count = password.chars().filter(ch -> ch == charToValidate).count();
        return count >= numberOfMinOccurrences && count <=numberOfMaxOccurrences;
    }

    public boolean isValidRiddle2() {
        //check if char occurs either at pos 1 or pos 2
        char charAtPos1 = password.charAt(numberOfMinOccurrences -1);
        char charAtPos2 = password.charAt(numberOfMaxOccurrences-1);

        if(charAtPos1 == charAtPos2) return false;
        return charAtPos1 == charToValidate || charAtPos2 == charToValidate;
    }
}
