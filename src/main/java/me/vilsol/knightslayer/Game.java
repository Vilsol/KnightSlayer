package me.vilsol.knightslayer;

import org.json.JSONObject;

public class Game {

    private int id;
    private Knight knight;
    private Dragon dragon;
    private Weather weather;
    private int battleCount = 0;
    private BattleResult lastBattle;
    private BattleResult secondToLastBattle;

    public Game(JSONObject gameData) {
        this.id = gameData.getInt("gameId");
        this.knight = new Knight(gameData.getJSONObject("knight"));
        this.dragon = new Dragon(this);
        this.weather = Weather.getWeather(id);

        dragon.prepare(weather);
        dragon.rebuildDragon();
    }

    public int getId() {
        return id;
    }

    public Knight getKnight() {
        return knight;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public Weather getWeather() {
        return weather;
    }

    public int getBattleCount() {
        return battleCount;
    }

    public BattleResult getLastBattle() {
        return lastBattle;
    }

    public BattleResult getSecondToLastBattle() {
        return secondToLastBattle;
    }

    public boolean win() {
        BattleResult battle = battle();
        battleCount += 1;
        while(battle.getGameStatus() == GameStatus.DEFEAT){
            secondToLastBattle = lastBattle;
            lastBattle = battle;

            if(battleCount >= 25){
                return false;
            }

            if(battle.getDefeatCause() == null || battle.getDefeatCause() == DefeatCause.STORM){
                return false;
            }

            switch (battle.getDefeatCause()){
                case ATTACK:
                    dragon.increaseScaleThicknessWeight();
                    break;
                case ARMOR:
                    dragon.increaseClawSharpnessWeightWeight();
                    break;
                case AGILITY:
                    dragon.increaseWingStrengthWeightWeight();
                    break;
                case ENDURANCE:
                    dragon.increaseFireBreathWeightWeight();
                    break;
                case BOAT:
                    dragon.increaseClawSharpnessWeightWeight();
                    dragon.increaseWingStrengthWeightWeight();
                    break;
                case BALANCED:
                    dragon.balance();
                    break;
            }

            dragon.rebuildDragon();
            battle = battle();
            battleCount++;
        }

        secondToLastBattle = lastBattle;
        lastBattle = battle;

        return true;
    }

    public BattleResult battle(){
        return new BattleResult(this);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", knight=" + knight +
                ", dragon=" + dragon +
                ", weather=" + weather +
                ", battleCount=" + battleCount +
                ", lastBattle=" + lastBattle +
                ", secondToLastBattle=" + secondToLastBattle +
                '}';
    }
}
