package org.example;

import java.io.IOException;
import java.util.Random;

class subRaces {
    private readCSVLine subRaceCSV = new readCSVLine("DataFiles/SubRace.csv");
    private Random rand = new Random();
    private short max, chosen, i;
    private short min = 1;
    private String race, line, subRace;
    private short[] subRaces;
    private String[] lineData, traits;
    private int[] stats = new int[7];

    subRaces(String race) throws IOException {
        subRaces = subRaceCSV.findLinesNames(race);

        for(i = 0; i < subRaces.length && subRaces[i] != 0; i++) {
            min = 0;
            max = (short) ((short) i);
        }
        if(max == 0) {
            max = min;
        }
        chosen = (short) (rand.nextInt(max - min + 1) + min);
        line = subRaceCSV.readFileLine(subRaces[chosen]);
        lineData = line.split(",");

    }

    String getRace() {
        return lineData[1];
    }
    int[] getSubStats() {
        min = 0;
        max = 5;
        byte rep1, rep2;
        if(lineData[1] == "Half-Elf") {
            do {
                rep1 = (byte) (rand.nextInt(max - min + 1) + min);
                rep2 = (byte) (rand.nextInt(max - min + 1) + min);
            }
            while((rep1 == 5 || rep2 == 5) || rep1 == rep2);
            stats[rep1] = 1;
            stats[rep2] = 1;
            stats[5] = 2;
            return  stats;
        }
        for(int i = 0; i < 6; i++) {
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
        String lineCopy = line;
        if(lineCopy.contains("\"")) {
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

        return traits;
    }
}
