package de.redfreggel.aoc2019.day1;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TyrannyRocket {

    public static void main(String[] args) throws IOException {
        TyrannyRocket rocket = new TyrannyRocket();
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

       // rocket.solveRiddle1(list);
        rocket.solveRiddle2(list);
    }

    public void solveRiddle2(List<Integer> pInput){
        int calculatedMass = 0;
        if(pInput.size()>0){
            for (Integer moduleMass: pInput)   {

                int calculatedModuleMass = this.calculateModuleConsumption(moduleMass);
                while (calculatedModuleMass>0){
                    calculatedMass +=calculatedModuleMass;
                    calculatedModuleMass = this.calculateModuleConsumption(calculatedModuleMass);
                }

            }
        }

        System.out.println("Calculation = "+calculatedMass);
    }


    public void solveRiddle1(List<Integer> pInput){
        System.out.println("Calculation for mass= 12: "+ this.calculateModuleConsumption(12)+ " and should be 2");
        System.out.println("Calculation for mass= 14: "+ this.calculateModuleConsumption(14)+ " and should be 2");
        System.out.println("Calculation for mass= 1969: "+ this.calculateModuleConsumption(1969)+ " and should be 654");
        System.out.println("Calculation for mass= 100756: "+ this.calculateModuleConsumption(100756)+ " and should be 33583");

        System.out.println("------------- everything fine so far ---------------------");

        int calculatedMass = 0;
        if(pInput.size()>0){
            for (Integer moduleMass: pInput)   {
                calculatedMass += this.calculateModuleConsumption(moduleMass);
            }
        }

        System.out.println("Calculation = "+calculatedMass);
    }

    private int calculateModuleConsumption(int pMass){
        double consumption = Math.ceil(pMass/3)-2;
        return (int) consumption;

    }
}
