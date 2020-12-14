package de.redfreggel.aoc2020.week2.day13;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShuttleSearcher {

    public static final String delimiter = ",|x";
    public static final String empty = "";

    public static void main(String[] args) {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay13_2020.txt");

        //read input
        String text;
        List<String> input = new ArrayList<>();
        //Read the input
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;
                input.add(text);
            } while (text != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Split input
        //first line = earliest time to arrive;
        //second line = available busses
        long timeWhenArrived = Long.parseLong(input.get(0));
    //    StringBuilder build = new StringBuilder();
        //    build.append(input.get(1));
        String[] busServiceTimes = input.get(1).split(delimiter);
        //figure out the highest business numer
        long highestServiceTime =1;
        for(int i = 0; i<busServiceTimes.length;i++){
            if(empty.equals(busServiceTimes[i])) continue;
            long serviceTime = Long.parseLong(busServiceTimes[i]);
            if(serviceTime>highestServiceTime) highestServiceTime = serviceTime;
        }

        //key = time ; value = bus#
        Map<Long, Long> timeShedule = new HashMap<>();
        for(String busServiceTime:busServiceTimes){
            if(empty.equals(busServiceTime)) continue;
            long serviceTime = Long.parseLong(busServiceTime);
            for(long i = serviceTime; i<(timeWhenArrived+highestServiceTime);i=i+serviceTime) {
                //start filling first when the max difference - wise has arrifed
                if(i>=timeWhenArrived-highestServiceTime) {
                    System.out.println("time = "+i + " busline = "+serviceTime);
                    timeShedule.put(i,serviceTime);
                }
            }
        }
        List<Long> timeList =new ArrayList<>(timeShedule.keySet());
        Collections.sort(timeList);
        //figure out the smallest distance
        long timeDifference = 0;
        long time = 0;
        for(int i = 0; i<timeList.size();i++){
            System.out.println("time = "+timeList.get(i));
            long timeDifferenceTemp = timeList.get(i)-timeWhenArrived;
            if(i == 0 || timeDifferenceTemp<0){
                timeDifference =  timeDifferenceTemp;
                continue;
            }
            timeDifference = timeDifferenceTemp;
            time = timeList.get(i);
            break;
        }
        System.out.println("time : "+timeDifference+" busId = "+timeShedule.get(time));
        System.out.println("Result Riddle 1 : "+(timeDifference*timeShedule.get(time)));
    }
}
