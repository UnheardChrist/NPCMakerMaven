package org.example;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

class subRaces {
    readCSVLine subRaceCSV = new readCSVLine("DataFiles/SubRace.csv");
    Random rand = new Random();
    short max, chosen, i;
    short min = 1;
    String race, line, subRace;
    short[] subRaces;
    String[] lineData, traits;
    int[] stats;

    subRaces(String race) throws IOException {
        subRaces = subRaceCSV.findLinesNames(race);
        System.out.println(subRaces[0]);
        System.out.println(subRaces[1]);

        for(i = 0; i < subRaces.length && subRaces[i] != 0; i++) {
            min = 0;
            max = (short) ((short) i);
        }
        if(max == 0) {
            max = min;
        }
        System.out.println(min + " and " + max);
        chosen = (short) (rand.nextInt(max - min + 1) + min);
        System.out.println(chosen);
        line = subRaceCSV.readFileLine(subRaces[chosen]);
        lineData = line.split(",");
        subRace = lineData[1];
        System.out.println(subRace);
    }

    int[] getSubStats() {
        for(int i = 0; i < 7; i++) {
            stats[i] = Integer.parseInt(lineData[i + 2]);
        }
        return stats;
    }
    int getAge() {
        return Integer.parseInt(lineData[charStats.AGE.ordinal()]);
    }
    String getSize() {
        return lineData[charStats.SIZE.ordinal()];
    }
    int getSpeed() {
        return Integer.parseInt(lineData[charStats.SPEED.ordinal()]);
    }
    String[] getTraits() {
        System.out.println(lineData.length);
        if(line.contains("\"")) {
            traits = line.split("\"");
        } else {
            traits = new String[1];
             try {
                traits[0] = lineData[charStats.TRAITS.ordinal()];
            }
            catch (java.lang.ArrayIndexOutOfBoundsException exception)
            {
                traits[0] = "";
            }
        }
        System.out.println("SUBDIVISION");

        System.out.println(traits[0]);
        return traits;
    }
}
