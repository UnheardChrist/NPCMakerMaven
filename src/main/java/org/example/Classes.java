package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Classes {
    //private readCSVLine classesCSV = new readCSVLine("DataFiles/Classes.csv");
    private Random rand = new Random();
    private String[] lineData, armors, weapons, tools, savingThrows, skills, equipment;
    private String line, className, hitDice;

    Classes() throws IOException {
        short min = 1;
     //   short max = classesCSV.amount();
      //  short loc = (short) (rand.nextInt(max - min + 1) + min);
       // line = classesCSV.readFileLine(loc);
        getData();
    }
    private void getData() {
        line = "Barbarian,\"Axes,Sword,Knife\",arm ";
        lineData = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        System.out.println(Arrays.toString(lineData));
        className = lineData[charClass.NAME.ordinal()];
        hitDice = lineData[charClass.HITDICE.ordinal()];
        armors = lineData[charClass.ARMOR.ordinal()].split(",");
        weapons = lineData[charClass.WEAPONS.ordinal()].split(",");
        tools = lineData[charClass.TOOLS.ordinal()].split(",");
        savingThrows = lineData[charClass.SAVINGTHROWS.ordinal()].split(",");
        skills = lineData[charClass.SKILLS.ordinal()].split(",");
    }
}
