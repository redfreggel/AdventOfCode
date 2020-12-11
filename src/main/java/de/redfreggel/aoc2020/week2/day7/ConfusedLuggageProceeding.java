package de.redfreggel.aoc2020.week2.day7;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ConfusedLuggageProceeding {
    public static final String myBag = "shiny gold";

    public static void main(String[] args) {
        ConfusedLuggageProceeding proceeding = new ConfusedLuggageProceeding();
        proceeding.process();
    }

    public ConfusedLuggageProceeding() { }

    public void process() {
        Path path = Paths.get(".\\src\\main\\resources\\aoc2020\\inputDay7_2020.txt");

        String text;
        Map<String, List<LuggageNeeded>> riddleInput = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            do {
                text = reader.readLine();
                if (text == null) continue;
                //Starting to unwrap the line
                String[] arrays = text.split("bags contain");

                String bagColorMaster = arrays[0];

                String[] bagSubColors = arrays[1].trim().split(",");
                //if bagSubColors.length == 0 then 0 or 1 color
                if (bagSubColors.length == 1) {
                    String[] numberAndBag = arrays[1].trim().split(" ");
                    if ("no".equals(numberAndBag[0])) {
                        riddleInput.put(bagColorMaster.trim(), new ArrayList<>());
                    } else {
                        //find out color
                        //2 clear indigo bags.
                        StringBuilder buf = new StringBuilder();
                        int number = 0;
                        for (int i = 0; i < numberAndBag.length; i++) {
                            if (i == 0) {
                                number = Integer.parseInt(numberAndBag[i]);
                                continue;
                            }
                            if ("bags".equals(numberAndBag[i]) || "bags.".equals(numberAndBag[i]) || "bag.".equals(numberAndBag[i]) || "bag".equals(numberAndBag[i])) {
                                LinkedList<LuggageNeeded> list = new LinkedList<>();
                                list.add(new LuggageNeeded(number, buf.toString().trim()));
                                riddleInput.put(bagColorMaster.trim(), list);
                                break;
                            }
                            buf.append(numberAndBag[i]).append(" ");

                        }
                    }
                } else {
                    //more than 0 or 1 colors
                    //contain 3 pale blue bags, 1 muted aqua bag, 3 bright fuchsia bags, 1 striped white bag.
                    List<LuggageNeeded> list = new ArrayList<>();
                    for (String bagSubColor : bagSubColors) {
                        String[] numberAndBag = bagSubColor.trim().split(" ");
                        StringBuilder buf = new StringBuilder();
                        int number = 0;
                        for (int j = 0; j < numberAndBag.length; j++) {
                            if (j == 0) {
                                number = Integer.parseInt(numberAndBag[j]);
                                continue;
                            }
                            String keyword = numberAndBag[j];
                            if ("bags".equals(keyword) || "bags.".equals(keyword) || "bag.".equals(keyword) || "bag".equals(keyword)) {
                                list.add(new LuggageNeeded(number, buf.toString().trim()));
                                break;
                            }
                            buf.append(numberAndBag[j]).append(" ");
                        }
                    }
                    riddleInput.put(bagColorMaster.trim(), list);
                }
            } while (text != null);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        int counterPossibleBags = 0;

        for (String key : riddleInput.keySet()) {
            int actualCounter = 0;
            //loop through everything except of shinyGold
            if (myBag.equals(key)) continue;

            List<LuggageNeeded> connectedBags = riddleInput.get(key.trim());

            for (LuggageNeeded actualBag : connectedBags) {
                if (myBag.equals(actualBag.getColorNeeded())) {
                    actualCounter = 1;
                    break;
                }
                if (containsShinyGold(riddleInput.get(actualBag.getColorNeeded()), riddleInput)) {
                    actualCounter = 1;
                    break;
                }
            }

            if (actualCounter > 0) {
                counterPossibleBags++;
            }

        }
        System.out.println("Bags which can contain \"shinyGold\": " + counterPossibleBags);

        List<LuggageNeeded> connectedBags = riddleInput.get(myBag);
        Map<String, Long> luggageVisited = new HashMap<>();
        luggageVisited.put(myBag, (long)1);
        long result = 0;
        for (LuggageNeeded luggageNeeded : connectedBags) {
            if (luggageVisited.get(luggageNeeded.getColorNeeded().trim()) != null) continue;

            long subCall = getAmountLuggage(luggageNeeded.getColorNeeded(), luggageVisited, riddleInput);
            long subResult = luggageNeeded.getCountNeeded() + (luggageNeeded.getCountNeeded() * subCall);
          //  System.out.println("visiting " + luggageNeeded.getColorNeeded() + " count needed: " + luggageNeeded.getCountNeeded() + " multiplying with " + subCall + " results in: " + subResult);
            luggageVisited.put(luggageNeeded.getColorNeeded().trim(), subCall);
            result = result + subResult;
        }
        System.out.println("Unique bags to contain in \"shinyGold\": " + result);

    }

    public long getAmountLuggage(String pActualLuggage, Map<String, Long> pLuggageVisited, Map<String, List<LuggageNeeded>> pRiddleInput) {
        List<LuggageNeeded> connectedBags = pRiddleInput.get(pActualLuggage);

        if (connectedBags.size() == 0) {
            pLuggageVisited.put(pActualLuggage.trim(), (long)0);
            return 0;
        }
        long subResult;
        long resultOutput = 0;
        //at least one additional luggage attached
        for (LuggageNeeded luggageToCheck : connectedBags) {
            //check first if the actual luggage was looked uo
            if (pLuggageVisited.get(luggageToCheck.getColorNeeded()) != null) {
                subResult = pLuggageVisited.get(luggageToCheck.getColorNeeded().trim());
            }else{
                //Now the subResult has to be computed and added to the list
                subResult= getAmountLuggage(luggageToCheck.getColorNeeded(), pLuggageVisited, pRiddleInput);
                pLuggageVisited.put(luggageToCheck.getColorNeeded(),subResult);
            }
            //calculate the fun
            resultOutput =resultOutput+ luggageToCheck.getCountNeeded() + (luggageToCheck.getCountNeeded() * subResult);
        }

        return resultOutput;
    }


    public boolean containsShinyGold(List<LuggageNeeded> pPossibleKeyValues, Map<String, List<LuggageNeeded>> pRiddleInput) {
        int actualCounter = 0;
        //loop through all keys
        for (LuggageNeeded key : pPossibleKeyValues) {
            if (myBag.equals(key.getColorNeeded())) {
                actualCounter = 1;
                break;
            }
            List<LuggageNeeded> connectedBags = pRiddleInput.get(key.getColorNeeded().trim());
            for (LuggageNeeded bagValue : connectedBags) {
                if (actualCounter == 1) break;
                if (myBag.equals(bagValue.getColorNeeded())) {
                    actualCounter = 1;
                    break;
                } else {
                    if (pRiddleInput.get(bagValue.getColorNeeded()) != null) {
                        if (containsShinyGold(pRiddleInput.get(bagValue.getColorNeeded()), pRiddleInput)) {
                            actualCounter = 1;
                            break;
                        }
                    }
                }
            }
        }

        return actualCounter > 0;
    }

    private static class LuggageNeeded {
        private final int countNeeded;
        private final String colorNeeded;

        public LuggageNeeded(int pCountNeeded, String pColorNeeded) {
            this.colorNeeded = pColorNeeded;
            this.countNeeded = pCountNeeded;
        }

        public String getColorNeeded() {
            return colorNeeded;
        }

        public int getCountNeeded() {
            return countNeeded;
        }

    }
}
