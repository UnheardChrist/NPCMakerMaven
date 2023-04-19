package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.floor;
import static org.example.diceGen.d6;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        URL path = ClassLoader.getSystemResource("html/main.html");
        File input = new File(path.toURI());
        Document document = Jsoup.parse(input, "UTF-8");

        int statsArray[] = {1, 2, 3, 4, 5, 6};

        writeHTML writeHTML = new writeHTML(document, new File("firstChar.html"),
                new File("Characters"));
        Document charInCharsFolder = writeHTML.openFile();

        Stats stats = new Stats(charInCharsFolder);
        stats.setStatsToHTML(statsArray);

        writeHTML.writeToFile();
    }
}

class Stats {
    Document document;
    String statNames[] = {"Strength", "Dexterity", "Constitution",
            "Intelligence", "Wisdom", "Charisma"};
    Element statElement[];
    int stats[] = {0, 0, 0, 0, 0, 0};
    public Stats(Document document) throws IOException, URISyntaxException {
        this.document = document;
    }

    public void setStatsFromHTML() {
        int i;
        for(i = 0; i < statNames.length; i++) {
            stats[i] = Integer.parseInt(document.getElementById(statNames[i]).text());
        }
    }
    void setStatsToHTML(int stats[]) {
        int i;
        Element stat;
        for(i = 0; i < statNames.length; i++) {
            stat = document.getElementById(statNames[i]);
            if(stat != null) {
                System.out.println(stat.text());
                stat.text(String.valueOf(stats[i]));
                System.out.println(stat.text());
            }
        }
    }
    public void setStats(int stats[]) {
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
            if(statNames[i] == stat) {
                return i;
            }
        }
        return -1;
    }
    int genStat() {
        int rolled[] = {d6(), d6(), d6(), d6()};
        Arrays.sort(rolled);

        return Arrays.stream(rolled).sum() - rolled[0];
    }
    void genAllStats() {
        int tempStats[] = new int[6];
        int i;

        for(i = 0; i < stats.length; i++) {
            tempStats[i] = genStat();
        }
    }
    int abilityModifier(int stat) {
        return (int) floor((stat - 10) / 2);
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