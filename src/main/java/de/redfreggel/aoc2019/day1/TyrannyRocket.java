package de.redfreggel.aoc2019.day1;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TyrannyRocket {

    public static void main(String[] args) throws IOException {
        TyrannyRocket rocket = new TyrannyRocket();
        System.out.println("Calculation for mass= 12: "+ rocket.calculateModuleConsumption(12)+ " and should be 2");
        System.out.println("Calculation for mass= 14: "+ rocket.calculateModuleConsumption(14)+ " and should be 2");
        System.out.println("Calculation for mass= 1969: "+ rocket.calculateModuleConsumption(1969)+ " and should be 654");
        System.out.println("Calculation for mass= 100756: "+ rocket.calculateModuleConsumption(100756)+ " and should be 33583");

        System.out.println("------------- everything fine so far ---------------------");
        List<Integer> list = new ArrayList<Integer>();
        try{
            File file = new File("C:\\temp\\InteliJ\\AdventOfCode\\src\\main\\resources\\inputDay1_1.txt");
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(file));
                String text = null;

                while ((text = reader.readLine()) != null) {
                    list.add(Integer.parseInt(text));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int calculatedMass = 0;
        if(list.size()>0){
            for (Integer moduleMass: list)   {
                calculatedMass += rocket.calculateModuleConsumption(moduleMass);
            }
        }

        System.out.println("Calculation = "+calculatedMass);
    }

    public int calculateModuleConsumption(int pMass){
        double consumption = Math.ceil(pMass/3)-2;
        return (int) consumption;

    }
}
