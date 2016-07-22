package me.vilsol.knightslayer;

import org.json.JSONObject;

public class Dragon {

    private Game game;

    private int scaleThickness;
    private int clawSharpness;
    private int wingStrength;
    private int fireBreath;

    private double scaleThicknessWeight = 1;
    private double clawSharpnessWeight = 1;
    private double wingStrengthWeight = 1;
    private double fireBreathWeight = 1;

    public Dragon(Game game) {
        this.game = game;

        scaleThicknessWeight = game.getKnight().getAttack();
        clawSharpnessWeight = game.getKnight().getArmor();
        wingStrengthWeight = game.getKnight().getAgility();
        fireBreathWeight = game.getKnight().getEndurance();

        rebuildDragon();
    }

    public void rebuildDragon(){
        double pointsPerWeight = 20.0 / (scaleThicknessWeight +
                        clawSharpnessWeight +
                        wingStrengthWeight +
                        fireBreathWeight);

        scaleThickness = (int) Math.max(Math.min(Math.round(pointsPerWeight * scaleThicknessWeight), 10), 0);
        clawSharpness = (int) Math.max(Math.min(Math.round(pointsPerWeight * clawSharpnessWeight), 10), 0);
        wingStrength = (int) Math.max(Math.min(Math.round(pointsPerWeight * wingStrengthWeight), 10), 0);
        fireBreath = (int) Math.max(Math.min(Math.round(pointsPerWeight * fireBreathWeight), 10), 0);

        int total = scaleThickness + clawSharpness + wingStrength + fireBreath;

        int selected = -1;
        if(total > 20) {
            selected = Math.max(scaleThickness, Math.max(clawSharpness, Math.max(wingStrength, fireBreath)));
        }else if(total < 20) {
            selected = Math.min(scaleThickness, Math.min(clawSharpness, Math.min(wingStrength, fireBreath)));
        }

        if (selected == scaleThickness) {
            scaleThickness += 20 - total;
        } else if (selected == clawSharpness) {
            clawSharpness += 20 - total;
        } else if (selected == wingStrength) {
            wingStrength += 20 - total;
        } else if (selected == fireBreath) {
            fireBreath += 20 - total;
        }
    }

    public void increaseScaleThicknessWeight(){
        scaleThicknessWeight += diffAmount();
    }

    public void increaseClawSharpnessWeightWeight(){
        clawSharpnessWeight += diffAmount();
    }

    public void increaseWingStrengthWeightWeight(){
        wingStrengthWeight += diffAmount();
    }

    public void increaseFireBreathWeightWeight(){
        fireBreathWeight += diffAmount();
    }

    public void decreaseScaleThicknessWeight(){
        scaleThicknessWeight -= diffAmount();
    }

    public void decreaseClawSharpnessWeightWeight(){
        clawSharpnessWeight -= diffAmount();
    }

    public void decreaseWingStrengthWeightWeight(){
        wingStrengthWeight -= diffAmount();
    }

    public void decreaseFireBreathWeightWeight(){
        fireBreathWeight -= diffAmount();
    }

    public void balance() {
        double total = scaleThicknessWeight + clawSharpnessWeight + wingStrengthWeight + fireBreathWeight;
        double average = total / 4d;

        scaleThicknessWeight += (average - scaleThicknessWeight) / 4d;
        clawSharpnessWeight += (average - clawSharpnessWeight) / 4d;
        wingStrengthWeight += (average - wingStrengthWeight) / 4d;
        fireBreathWeight += (average - fireBreathWeight) / 4d;
    }

    public void prepare(Weather weather) {
        switch(weather){
            case HEAVY_RAIN:
                scaleThicknessWeight = 5;
                clawSharpnessWeight = 10;
                wingStrengthWeight = 5;
                fireBreathWeight = 0;
                break;
            case LONG_DRY:
                scaleThicknessWeight = 1;
                clawSharpnessWeight = 1;
                wingStrengthWeight = 1;
                fireBreathWeight = 1;
                break;
        }
    }

    public double diffAmount(){
        return (scaleThicknessWeight +
                clawSharpnessWeight +
                wingStrengthWeight +
                fireBreathWeight) / 4d;
    }

    public int getScaleThickness() {
        return scaleThickness;
    }

    public int getClawSharpness() {
        return clawSharpness;
    }

    public int getWingStrength() {
        return wingStrength;
    }

    public int getFireBreath() {
        return fireBreath;
    }

    public JSONObject toJson() {
        JSONObject dragon = new JSONObject();
        dragon.put("scaleThickness", scaleThickness);
        dragon.put("clawSharpness", clawSharpness);
        dragon.put("wingStrength", wingStrength);
        dragon.put("fireBreath", fireBreath);

        JSONObject wrapper = new JSONObject();
        wrapper.put("dragon", dragon);

        return wrapper;
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "scaleThickness=" + scaleThickness +
                ", clawSharpness=" + clawSharpness +
                ", wingStrength=" + wingStrength +
                ", fireBreath=" + fireBreath +
                ", scaleThicknessWeight=" + scaleThicknessWeight +
                ", clawSharpnessWeight=" + clawSharpnessWeight +
                ", wingStrengthWeight=" + wingStrengthWeight +
                ", fireBreathWeight=" + fireBreathWeight +
                '}';
    }
}
