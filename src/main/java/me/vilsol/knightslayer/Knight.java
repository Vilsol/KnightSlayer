package me.vilsol.knightslayer;

import org.json.JSONObject;

public class Knight {

    private int attack;
    private int armor;
    private int agility;
    private int endurance;

    public Knight(JSONObject knight){
        this.attack = knight.getInt("attack");
        this.armor = knight.getInt("armor");
        this.agility = knight.getInt("agility");
        this.endurance = knight.getInt("endurance");
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public int getAgility() {
        return agility;
    }

    public int getEndurance() {
        return endurance;
    }

    @Override
    public String toString() {
        return "Knight{" +
                "attack=" + attack +
                ", armor=" + armor +
                ", agility=" + agility +
                ", endurance=" + endurance +
                '}';
    }
}
