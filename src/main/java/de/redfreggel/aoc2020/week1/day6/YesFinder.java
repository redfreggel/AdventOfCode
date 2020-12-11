package de.redfreggel.aoc2020.week1.day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class YesFinder {

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay6_2020.txt");

        String text;
        StringBuffer bufAnyOneYES = new StringBuffer();
        StringBuffer bufEveryOneYES = new StringBuffer();
        int counterPeople = 0;
       // List<String> answers = new ArrayList<>();
        int finalCounterAnyOneYES = 0;
        int finalCounterEveryOneYES = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null || "".equals(text.trim())) {
                    if(bufAnyOneYES.toString().trim().length()==0) {
                        continue;
                    }

                    Map<String, Integer> map = new HashMap<>();
                    for(int i = 0;i<bufEveryOneYES.length(); i++){
                        if(map.containsKey(bufEveryOneYES.substring(i,i+1))){
                            map.put(bufEveryOneYES.substring(i,i+1),map.get(bufEveryOneYES.substring(i,i+1))+1);
                        }
                        else {
                            map.put(bufEveryOneYES.substring(i,i+1),1);
                        }
                    }
                    for(String key : map.keySet()){
                        if(map.get(key) == counterPeople){
                            finalCounterEveryOneYES++;
                        }
                    }

                    finalCounterAnyOneYES +=bufAnyOneYES.length();
                    bufAnyOneYES = new StringBuffer();
                    bufEveryOneYES = new StringBuffer();
                    counterPeople =0;
                }else{
                    if(bufAnyOneYES.length()==0){
                        bufAnyOneYES.append(text);
                    }else{
                        for(int i = 0; i<text.length();i++){
                            String tempText = text;
                            int finalI = i;
                            long count = bufAnyOneYES.chars().filter(ch -> ch == tempText.charAt(finalI)).count();
                            if(count== 0) bufAnyOneYES.append(tempText.charAt(i));
                        }
                    }
                    bufEveryOneYES.append(text);
                    counterPeople++;
                }


            } while (text != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("FinalCounter AnyOne = YES : "+finalCounterAnyOneYES);
        System.out.println("FinalCounter EveryOne = YES:  = "+finalCounterEveryOneYES);
    }
}
