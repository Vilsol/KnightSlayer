package me.vilsol.knightslayer;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.json.JSONObject;

public class BattleResult {

    private JSONObject result;
    private GameStatus gameStatus;
    private DefeatCause defeatCause;

    public BattleResult(Game game) {
        try {
            HttpRequestWithBody request = Unirest.put("http://www.dragonsofmugloar.com/api/game/" + game.getId() + "/solution");
            request.body(game.getDragon().toJson());
            request.header("Content-Type", "application/json");

            result = request.asJson().getBody().getObject();
            gameStatus = GameStatus.getStatus(result.getString("status"));
            defeatCause = DefeatCause.getCause(result.getString("message"));
        } catch (UnirestException ignored) {
        }
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public DefeatCause getDefeatCause() {
        return defeatCause;
    }

    @Override
    public String toString() {
        return "BattleResult{" +
                "result=" + result +
                ", gameStatus=" + gameStatus +
                ", defeatCause=" + defeatCause +
                '}';
    }
}
