package org.example;

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
    int[] stats = new int[7];

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
        max = 7;
        byte rep1, rep2;
        if(lineData[1] == "Half-Elf") {
            do {
                rep1 = (byte) (rand.nextInt(max - min + 1) + min);
                rep2 = (byte) (rand.nextInt(max - min + 1) + min);
            }
            while((rep1 == 6 || rep2 == 6) && rep1 == rep2);
            stats[rep1] = 1;
            stats[rep2] = 1;
            stats[6] = 2;
            return  stats;
        }
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
