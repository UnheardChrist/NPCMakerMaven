package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import static java.lang.Math.floor;
import static org.example.diceGen.d6;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        String fileName = args[0];
        URL path = ClassLoader.getSystemResource("html/main.html");
        File input = new File(path.toURI());
        Document document = Jsoup.parse(input, "UTF-8");

        int[] statsArray = {1, 2, 3, 4, 5, 6};

        writeHTML writeHTML = new writeHTML(document, new File(fileName),
                new File("Characters"));

        Document charInCharsFolder = writeHTML.openFile();

        Stats stats = new Stats();
        //stats.setStatsToHTML(statsArray);
        character character = new character();
        writeHTML.writeToFile();
    }
}

class Stats {
    Document document;
    String[] statNames = {"Strength", "Dexterity", "Constitution",
            "Intelligence", "Wisdom", "Charisma"};
    Element[] statElement;
    int[] stats = {0, 0, 0, 0, 0, 0};
    int[] modifiers = {0, 0, 0, 0, 0, 0};
    public Stats(/*Document document*/) throws IOException, URISyntaxException {
        //this.document = document;
    }
/*
    public void setStatsFromHTML() {
        int i;
        for(i = 0; i < statNames.length; i++) {
            stats[i] = Integer.parseInt(Objects.requireNonNull(document.getElementById(statNames[i])).text());
        }
    }

    void setStatsToHTML(int[] stats) {
        int i;
        Element stat;
        for(i = 0; i < statNames.length; i++) {
            stat = document.getElementById(statNames[i]);
            if(stat != null) {
                System.out.println(stat.text());
                stat.text(String.valueOf(stats[i]) + " (" + String.valueOf(modifiers[i]) + ")");
                System.out.println(stat.text());
            }
        }
    }
    */

    public void setStats(int[] stats) {
        this.stats = stats;
    }
    public void setStats(int statValue, String stat) {
        int index;
        index = getIndex(stat);
        if(index == -1) {
            System.out.println("Set Stat Name is not valid");
        } else {
            stats[index] = statValue;
        }
    }

    public int[] getStats() {
        return stats;
    }
    public int getStats(String stat) {
        int index = getIndex(stat);
        if(index == -1) {
            System.out.println("Set Stat Name is not valid");
        }
        return stats[index];
    }
    int getIndex(String stat) {
        int i;
        for(i = 0; i < statNames.length; i++) {
            if(Objects.equals(statNames[i], stat)) {
                return i;
            }
        }
        return -1;
    }
    int genStat() {
        int[] rolled = {d6(), d6(), d6(), d6()};
        Arrays.sort(rolled);

        return Arrays.stream(rolled).sum() - rolled[0];
    }
    void genAllStats() {
        int[] tempStats = new int[6];
        int[] modStats = new int[6];
        int i;

        for(i = 0; i < stats.length; i++) {
            tempStats[i] = genStat();
            modStats[i] = abilityModifier(tempStats[i]);
        }

    }
    int abilityModifier(int stat) {
        return (int) floor((stat - 10) / 2);
    }
}

class character {
    private int level;
    private int maxHp;

    character() throws IOException, URISyntaxException {
    }

    protected int getMaxHp() {
        return this.maxHp;
    }
    protected void setMaxHp(int maxHp) {
        this.maxHp += maxHp;
    }
    protected int getLevel() {
        return this.level;
    }
    protected void setLevel(int level) {
        this.level = level;
    }
    charClass charClass;
    race race = new race();
    Stats stats = new Stats();
}

class charClass {
    String charClass;
    charClass(String charClass) {
        this.charClass = charClass;
    }
}

class race{
    Random rand = new Random();
    short max, chosen;
    short min = 1;
    String race, line, subRace;
    String[] lineData;
    //List<String> traits = new List<String>();
    ArrayList<String> traits = new ArrayList<String>();
    readCSVLine raceCSV = new readCSVLine("DataFiles/Race.csv");

    int age, speed, i;
    int[] cstat;

    race() throws IOException, URISyntaxException {

        max = raceCSV.amount();
        chosen = (short) (rand.nextInt(max - min + 1) + min);
        line = raceCSV.readFileLine(chosen);
        System.out.println(line);
        lineData = line.split(",");
        race = lineData[0];
        System.out.println(race);
        //System.out.println(line);
        if(line.contains("\"")) {
            lineData = line.split("\"");
        }
        traits.add(lineData[1]);
        System.out.println(traits);
        subRaces subRacesClass = new subRaces(race);
        subRacesClass.getTraits();
    }

}

class writeHTML {
    Document document;
    Document template;
    File fileName;
    File filePath;
    File dir;
    writeHTML(Document document, File file, File dir) {
        this.fileName = file;
        this.dir = dir;
        this.template = document;
    }

    Document openFile() throws IOException {
        filePath = new File(dir.getPath() + "/" + fileName);
        System.out.println(filePath);
        dir.mkdirs();
        if(filePath.createNewFile()) {
            document = template;
            return template;
        }
        document = Jsoup.parse(filePath, "UTF-8");

        return document;
    }

    void writeToFile() throws IOException {
        Files.writeString(filePath.toPath(), document.outerHtml());
    }

}

class readCSVLine {
    String fileName;

    File file;

    BufferedReader bufferedReader;
    readCSVLine(String fileName) {
        this.fileName = fileName;
        URL path = ClassLoader.getSystemResource(fileName);
        try {
            file = new File(path.toURI());
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    byte i = 0;
    String line;
    String readFileLine(short loc) throws IOException {
        bufferedReader.reset();
        while((line = bufferedReader.readLine()) != null && i != loc)
        {
            i++;
        }
        bufferedReader.close();
        return line;
    }

    short amount() throws IOException {
        short i = 0;
        bufferedReader.mark(50000);
        while((line = bufferedReader.readLine()) != null)
        {
           i++;
        }
        return (short) (i - 1);
    }

    short[] findLinesNames(String race) throws IOException {
        short i = 1;
        short spot = 0;
        short[] lines = new short[amount()];
        bufferedReader.reset();
        line = bufferedReader.readLine();
        while((line = bufferedReader.readLine()) != null) {
            if(line.contains(race))
            {
                lines[spot] = i;
                spot++;
            }
                    i++;
        }
        return lines;
    }
}

class diceGen {
    static Random rand = new Random();
    static int max, min;
    static int d2() {
        max = 2;
        min = 1;
        return rand.nextInt(max - min + 1) + min;
    }
    static int d4() {
        max = 4;
        min = 1;
        return rand.nextInt(max - min + 1) + min;
    }
    static int d6() {
        max = 6;
        min = 1;
        return rand.nextInt(max - min + 1) + min;
    }
    static int d8() {
        max = 8;
        min = 1;
        return rand.nextInt(max - min + 1) + min;
    }
    static int d12() {
        max = 12;
        min = 1;
        return rand.nextInt(max - min + 1) + min;
    }
    static int d20() {
        max = 20;
        min = 1;
        return rand.nextInt(max - min + 1) + min;
    }
}