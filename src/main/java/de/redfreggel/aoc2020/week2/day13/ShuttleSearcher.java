package de.redfreggel.aoc2020.week2.day13;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShuttleSearcher {

    public static final String delimiter = ",|x";
    public static final String delimiterRiddle2 = ",";
    public static final String minute = "x";
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

        //solveRiddle1(input);
        solveRiddle2(input);

    }

    private static void solveRiddle2(List<String> input) {
        long timestart = System.currentTimeMillis();
        String[] busServiceTimes = input.get(1).split(delimiterRiddle2);
        //bus , timeDiff
        LinkedList<Bus> busSequenceToFind = new LinkedList<>();
        for (int i = 0; i < busServiceTimes.length; i++) {
            if (empty.equals(busServiceTimes[i])) continue;
            if (minute.equals(busServiceTimes[i])) continue;

            long serviceTime = Long.parseLong(busServiceTimes[i]);
            Bus bus = new Bus(serviceTime, i);
            busSequenceToFind.add(bus);
        }
        for (Bus bus : busSequenceToFind) {
            System.out.println(bus.toString());
        }

        long time = 0;
        long matchedTime = 0;
        int busCount = busSequenceToFind.size()-1;
        Bus initalBus = busSequenceToFind.get(busCount);
        boolean matchFound;

        do {
            time++;
            while(time%initalBus.busNumber != 0){
                time++;
            }

            long tempTime = time;
            boolean positionMatch = false;
            int positionCounter = busCount;
            while(!positionMatch){

                Bus bus = busSequenceToFind.get(positionCounter);
                if (tempTime > bus.busNumber && tempTime % bus.busNumber == 0) {
                    if (positionCounter == 0) {
                        matchedTime = tempTime;
                        positionMatch = true;
                        break;
                    }
                    tempTime = tempTime - (bus.startDifference - busSequenceToFind.get(positionCounter - 1).startDifference);
                    positionCounter--;
                }else {
                    break;
                }
            }
            matchFound = positionMatch;

        } while (!matchFound);
        System.out.println("match found at time " + matchedTime);
        System.out.println("time elapsed = "+(System.currentTimeMillis()-timestart));

    }

    private static void solveRiddle1(List<String> input) {

        //Split input
        //first line = earliest time to arrive;
        //second line = available busses
        long timeWhenArrived = Long.parseLong(input.get(0));
        String[] busServiceTimes = input.get(1).split(delimiter);
        //figure out the highest business numer
        long highestServiceTime = 1;
        for (String s : busServiceTimes) {
            if (empty.equals(s)) continue;
            long serviceTime = Long.parseLong(s);
            if (serviceTime > highestServiceTime) highestServiceTime = serviceTime;
        }

        //key = time ; value = bus#
        Map<Long, Long> timeShedule = new HashMap<>();
        for (String busServiceTime : busServiceTimes) {
            if (empty.equals(busServiceTime)) continue;
            long serviceTime = Long.parseLong(busServiceTime);
            for (long i = serviceTime; i < (timeWhenArrived + highestServiceTime); i = i + serviceTime) {
                //start filling first when the max difference - wise has arrifed
                if (i >= timeWhenArrived - highestServiceTime) {
                    System.out.println("time = " + i + " busline = " + serviceTime);
                    timeShedule.put(i, serviceTime);
                }
            }
        }
        List<Long> timeList = new ArrayList<>(timeShedule.keySet());
        Collections.sort(timeList);
        //figure out the smallest distance
        long timeDifference = 0;
        long time = 0;
        for (int i = 0; i < timeList.size(); i++) {
            System.out.println("time = " + timeList.get(i));
            long timeDifferenceTemp = timeList.get(i) - timeWhenArrived;
            if (i == 0 || timeDifferenceTemp < 0) {
                timeDifference = timeDifferenceTemp;
                continue;
            }
            timeDifference = timeDifferenceTemp;
            time = timeList.get(i);
            break;
        }
        System.out.println("time : " + timeDifference + " busId = " + timeShedule.get(time));
        System.out.println("Result Riddle 1 : " + (timeDifference * timeShedule.get(time)));
    }

    public static class Bus {
        long busNumber;
        long startDifference;

        public Bus(long pBusNumber, long pStartDifference) {
            this.busNumber = pBusNumber;
            startDifference = pStartDifference;
        }

        @Override
        public String toString() {
            return "Bus{" +
                    "busNumber=" + busNumber +
                    ", startDifference=" + startDifference +
                    '}';
        }
    }
}
