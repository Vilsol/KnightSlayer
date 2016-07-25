package me.vilsol.knightslayer;

import org.json.JSONObject;

public class Game {

    private int id;
    private Knight knight;
    private Dragon dragon;
    private Weather weather;
    private BattleResult battle;

    public Game(JSONObject gameData) {
        this.id = gameData.getInt("gameId");
        this.knight = new Knight(gameData.getJSONObject("knight"));
        this.dragon = new Dragon(this);
        this.weather = Weather.getWeather(id);
        dragon.prepare(weather);
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

    public BattleResult getBattle() {
        return battle;
    }

    public boolean win() {
        if(weather == Weather.STORM){
            battle = BattleResult.noBattle();
            return true;
        }

        return (battle = battle()).getGameStatus() == GameStatus.VICTORY;
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
                ", battle=" + battle +
                '}';
    }
}
