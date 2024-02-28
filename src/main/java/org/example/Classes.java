package org.example;

import java.io.IOException;
import java.util.*;

public class Classes {
    private readCSVLine classesCSV = new readCSVLine("DataFiles/Classes.csv");
    private Random rand = new Random();
    private String[] lineData, armors, weapons, tools, savingThrows, skills, equipment, chosenSkills;
    private String line, className, hitDice;

    Classes() throws IOException {
        short min = 1;
        short max = classesCSV.amount();
        short loc = (short) (rand.nextInt(max - min + 1) + min);
        line = classesCSV.readFileLine(loc);
        getData();
        chooseSkills();
    }
    private void getData() {
        lineData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        //System.out.println(Arrays.toString(lineData));
        className = lineData[charClass.NAME.ordinal()];
        System.out.println(className);
        hitDice = lineData[charClass.HITDICE.ordinal()];
        armors = lineData[charClass.ARMOR.ordinal()].split(",");
        weapons = lineData[charClass.WEAPONS.ordinal()].split(",");
        tools = lineData[charClass.TOOLS.ordinal()].split(",");
        savingThrows = lineData[charClass.SAVINGTHROWS.ordinal()].split(",");
        skills = lineData[charClass.SKILLS.ordinal()].split(",");
    }

    private void chooseSkills() {
        short i;

        skills[0] = skills[0].split("\"")[1];
        skills[skills.length - 1] = skills[skills.length - 1].split("\"")[0];

        ArrayList<Short> chosen = new ArrayList<>();
        chosenSkills = new String[Integer.parseInt(skills[0])];

        for(i = 0; i < skills.length - 1; i++) {
            chosen.add((short) (i + 1));
        }
        Collections.shuffle(chosen);
        for (i = 0; i < chosenSkills.length; i++) {
            chosenSkills[i] = skills[chosen.get(i)];
        }
        System.out.println(Arrays.toString(chosenSkills));
    }
}
