package de.redfreggel.aoc2019.day4;

import java.util.regex.Pattern;

public class SecureContainer {

    public final static String regEx1 = ".*11.*";
    public final static String regEx2 = ".*22.*";
    public final static String regEx3 = ".*33.*";
    public final static String regEx4 = ".*44.*";
    public final static String regEx5 = ".*55.*";
    public final static String regEx6 = ".*66.*";
    public final static String regEx7 = ".*77.*";
    public final static String regEx8 = ".*88.*";
    public final static String regEx9 = ".*99.*";

    public final static int inputStart = 152085;
    public final static int inputEnd =  670283;

    public static void main(String[] args) {
        int matches = 0;
        for(int i = inputStart; i<=inputEnd; i++){
            String tocheck = Integer.toString(i);


               if (Pattern.matches(regEx1, tocheck)
                     || Pattern.matches(regEx2, tocheck)
                    || Pattern.matches(regEx3, tocheck)
                    || Pattern.matches(regEx4, tocheck)
                    || Pattern.matches(regEx5, tocheck)
                     || Pattern.matches(regEx6, tocheck)
                    || Pattern.matches(regEx7, tocheck)
                    || Pattern.matches(regEx8, tocheck)
                   || Pattern.matches(regEx9, tocheck)
             ) {

                int firstDigit = i/100000;
                int secondDigit =(i-(firstDigit*100000))/10000;
                int thirdDigit = (i-((firstDigit*100000)+(secondDigit*10000)))/1000;
                int forthDigit = (i-((firstDigit*100000)+(secondDigit*10000)+(thirdDigit*1000)))/100;
                int fifthDigit = (i-((firstDigit*100000)+(secondDigit*10000)+(thirdDigit*1000)+(forthDigit*100)))/10;
                int sixDigit =   (i-((firstDigit*100000)+(secondDigit*10000)+(thirdDigit*1000)+(forthDigit*100)+(fifthDigit*10)))/1;

                if((firstDigit<= secondDigit)&&(secondDigit<=thirdDigit)&& (thirdDigit<=forthDigit)&&(forthDigit<=fifthDigit) &&(fifthDigit<=sixDigit)) {
                   // System.out.println(i);
                    matches++;
                }
             }
        }
        System.out.println(matches);
    }
}
