package de.redfreggel.aoc2020.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class PasswordChecker {

    /*
    Validation 1:
    byr (Birth Year)
iyr (Issue Year)
eyr (Expiration Year)
hgt (Height)
hcl (Hair Color)
ecl (Eye Color)
pid (Passport ID)
cid (Country ID)

optional: cid
     */

    // * Validation 2
    // *
    // *-done- byr (Birth Year) - four digits; at least 1920 and at most 2002.
    // *-done- iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    // *-done- eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    // *
    // *-done> hgt (Height) - a number followed by either cm or in:
    // *   If cm, the number must be at least 150 and at most 193.
    // *   If in, the number must be at least 59 and at most 76.
    // *
    // *-done- hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
    // *-done- ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    // *
    // *-done- pid (Passport ID) - a nine-digit number, including leading zeroes.
    // *
    // * cid (Country ID) - ignored, missing or not.

    //public static final String[] colorECLs = new String[]{"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
    //public static final String[] mandatoryFields = new String[]{"byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:"};

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay4_2020.txt");
        Pattern pattern = Pattern.compile("(?=.*byr)(?=.*iyr)(?=.*eyr)(?=.*hgt)(?=.*hcl)(?=.*ecl)(?=.*pid)");
        Pattern patternHCL = Pattern.compile("#[0-9a-f]{6}");
        Pattern patternID = Pattern.compile("[0-9]{9}");
        Pattern patternHigh = Pattern.compile("\\b[5][9]in|\\b[6][0-9]in|\\b[7][0-6]in|\\b1[5-8][0-9]cm|\\b1[9][0-3]cm");
        Pattern patternELCs = Pattern.compile("amb|blu|brn|gry|grn|hzl|oth");

        int validPWs = 0;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String text;
            StringBuffer buf = new StringBuffer();
            do{
                text = reader.readLine();
                if (text == null || "".equals(text.trim())) {
                    if(buf.toString().trim().length()==0) {
                        continue;
                    }
                    //check first the old pw and than reset the buffer
                    if (pattern.matcher(buf.toString()).find()) {
                        //For challenge 2 the PW has to be checked now if the values also match (damn it ;) )
                        //But Checking only valid PWs
                        String[] pwValues = buf.toString().split(" ");
                        boolean pwValid = true;
                        for (String pwValue : pwValues) {
                            //valuePair[0] = keyword
                            //valuePair[1] = value
                            String[] valuePair = pwValue.split(":");
                            int year;
                            switch (valuePair[0]){
                                //byr (Birth Year) - four digits; at least 1920 and at most 2002.
                                case "byr":
                                    if(valuePair[1].length()!= 4){
                                        pwValid = false;
                                        break;
                                    }
                                    year = Integer.parseInt(valuePair[1]);
                                    // System.out.println("byr match? -> four digits; at least 1920 and at most 2002. value: " + year + " matches? " + (year >= 1920 && year <= 2002));
                                    if (!(year >= 1920 && year <= 2002)) {
                                        pwValid = false;
                                    }
                                    break;
                                //iyr (Issue Year) - four digits; at least 2010 and at most 2020.
                                case "iyr":

                                    if(valuePair[1].length() != 4){
                                        pwValid = false;
                                        break;
                                    }
                                    year = Integer.parseInt(valuePair[1]);
                                    //      System.out.println("iyr match? -> four digits; at least 2010 and at most 2020. value: "+year+ " matches? "+(year >= 2010 && year <= 2020));
                                    if (!(year >= 2010 && year <= 2020)) {
                                        pwValid = false;
                                    }
                                    break;
                                //eyr (Expiration Year) - four digits; at least 2020 and at most 2030
                                case "eyr":
                                    if(valuePair[1].length() != 4){
                                        pwValid = false;
                                        break;
                                    }
                                    year = Integer.parseInt(valuePair[1]);
                                    //     System.out.println("eur match? -> four digits; at least 2020 and at most 2030. value: "+year+ " matches? "+(year >= 2020 && year <= 2030));
                                    if (!(year >= 2020 && year <= 2030)) {
                                        pwValid = false;
                                    }
                                    break;
                                case "hcl":
                                    //hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
                                    //System.out.println("hcl match? -> # followed by exactly six characters 0-9 or a-f value: " + colors[1] + " matches? " + patternHCL.matcher(colors[1]).find());
                                    String colorCode = valuePair[1];
                                    if(colorCode.charAt(0)!='#'){
                                        pwValid = false;
                                        break;
                                    }
                                    String hcl = valuePair[1].split("#")[1];
                                    if(hcl.length()!=6){
                                        pwValid = false;
                                        break;
                                    }
                                    // System.out.println(valuePair[1]);
                                    if (!patternHCL.matcher(valuePair[1]).find()) {
                                        pwValid = false;
                                    }
                                    break;
                                case "ecl":
                                    //ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
                                    // System.out.println("ecl match? -> length = 3 value: "+colors[1]+ " matches? "+ (colors[1].length() == 3));

                                    if (valuePair[1].length() != 3) {
                                        pwValid = false;
                                        break;
                                    }
                                    //System.out.println(valuePair[1]);
                                    if (!patternELCs.matcher(valuePair[1]).find()) {
                                        pwValid = false;
                                    }
                                    break;
                                case "pid":
                                    //   System.out.println("ecl match? -> length = 9 value: "+colors[1]+ " matches? "+ (colors[1].length() == 9));
                                    if (valuePair[1].length() != 9) {
                                        pwValid = false;
                                        break;
                                    }
                                    //    System.out.println("pid match? ->  a nine-digit number, including leading zeroes value: " + colors[1] + " matches? " + patternID.matcher(colors[1]).find());
                                    if (!patternID.matcher(valuePair[1]).find()) {
                                        pwValid = false;
                                    }
                                    break;
                                case "hgt":
                                    /*
                                    a number followed by either cm or in:
     *   If cm, the number must be at least 150 and at most 193.
     *   If in, the number must be at least 59 and at most 76.
                                     */
                                    if (!patternHigh.matcher(valuePair[1]).find()) {
                                        pwValid = false;
                                        break;
                                    }

                                    break;
                                default:
                                    //  System.out.println("I should not be here "+valuePair[0]);
                            }
                        }
                        if (pwValid) {
                            System.out.println(buf.toString());
                            validPWs++;
                        }
                    }
                    buf = new StringBuffer();
                    //     System.out.println("---------------------------------------------------------------------");
                } else {
                    buf.append(text).append(" ");
                }


            }while (text != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Valid PWs: " + validPWs);
    }
}
