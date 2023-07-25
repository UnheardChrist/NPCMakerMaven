package org.example;

class subRaces extends race{
    subRaces(String race) {
        super(race);
    }
}
class dwarf extends race {
    String[] abilityScoreIncrease = {"Constitution", "2"};
    int[] ageRange = {0, 350};
    int age;
    int speed = 25;
    String[] traits = {"Darkvision", "Dwarven Resilience", "Dwarven Combat Training", "Tool Proficiency", }
    dwarf(String race) {
        super(race);
    }
}
class hillDwarf extends dwarf {
    String[] abilityScoreIncrease = {"Wisdom", "1"};
    String trait = "Dwarven Toughness";
    int level;
    int dwarvenToughness = 1 + level;
    hillDwarf(String race) {
        super(race);
        this.level = getLevel();
        setMaxHp(dwarvenToughness);
    }
}
class mountainDwarf extends dwarf {
    String[] abilityScoreIncrease = {"Strength", "2"};
    String trait = "Dwarven Armor Training";
    mountainDwarf(String race) {
        super(race);
    }
}

class