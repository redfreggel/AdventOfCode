package de.redfreggel.aoc2020.week1.day1;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExpenseSumMultiplier {

    public static int requestedSum=2020;

    public static void main (String[] args) {
        List<Integer> list = new ArrayList<>();
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay1_2020_task_1.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String text;

            while ((text = reader.readLine()) != null) {
                list.add(Integer.parseInt(text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExpenseSumMultiplier multiplier = new ExpenseSumMultiplier();
        System.out.println(multiplier.find2020MatchAndMultiply_TwoNumbers(list));
        System.out.println(multiplier.find2020MatchAndMultiply_ThreeNumbers(list));

    }

    public int find2020MatchAndMultiply_TwoNumbers(List<Integer> pListOfPotentialValues){
        int number1;
        int number2;
        for(int i= 0; i<pListOfPotentialValues.size();i++){
            number1 = pListOfPotentialValues.get(i);
            for(int j= 0; j<pListOfPotentialValues.size();j++){
                if(i==j) continue;
                number2 = pListOfPotentialValues.get(j);

                if(number1+number2 == requestedSum) return number1*number2;
            }
        }
        throw new RuntimeException("Could not find result");
    }

    public int find2020MatchAndMultiply_ThreeNumbers(List<Integer> pListOfPotentialValues){
        int number1;
        int number2;
        int number3;

        for(int i= 0; i<pListOfPotentialValues.size();i++){
            number1 = pListOfPotentialValues.get(i);
            for(int j= 0; j<pListOfPotentialValues.size();j++){
                if(i==j) continue;
                number2 = pListOfPotentialValues.get(j);

                for(int k= 0; k<pListOfPotentialValues.size();k++){
                    if(i==j || i ==k || j == k ) continue;
                    number3 = pListOfPotentialValues.get(k);

                    if(number1+number2+number3 == requestedSum) return number1*number2*number3;
                }

            }

        }
        throw new RuntimeException("Could not find result");
    }

}
